package cn.mooding.common.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.file.FileTypeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 配置码云图床信息
 * 上传Gitee图床工具类
 *
 * @Author BlueFire
 * @Date 4/9/2021 -下午4:02
 */
public class UploadGiteeImgBed {
    private static final Logger log = LoggerFactory.getLogger(UploadGiteeImgBed.class);

    /**
     * 码云私人令牌
     */
    public static final String ACCESS_TOKEN = "0e2f681d18b2e884019903eabbef8dgl";  //这里不展示我自己的了，需要你自己补充

    /**
     * 码云个人空间名
     */
    public static final String OWNER = "moodingtech";

    /**
     * 上传指定仓库
     */
    public static final String REPO = "img";


    /**
     * 上传时指定存放图片路径
     */
    public static final String PATH = "/upload/%s/" + DateUtils.getYearMonth() + "/"; //使用到了日期工具类


    /**
     * 用于提交描述
     */
    public static final String ADD_MESSAGE = "add img";
    public static final String DEL_MESSAGE = "DEL img";

    //API
    /**
     * 新建(POST)、获取(GET)、删除(DELETE)文件：()中指的是使用对应的请求方式
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    public static final String API_CREATE_POST = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";


    /**
     * 生成创建(获取、删除)的指定文件路径
     *
     * @param originalFilename
     * @return
     */
    public static String createUploadFileUrl(String originalFilename) {
        //获取文件后缀
        String suffix = FileTypeUtils.getFileType(originalFilename);//使用到了自己编写的FileUtils工具类
        //拼接存储的图片名称

        long l = System.currentTimeMillis();

        //     String fileName = System.currentTimeMillis() + "_" + IdUtils.simpleUUID() +"."+ suffix;
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(System.currentTimeMillis()) + "." + suffix;

        String uploadPath = "";
        if ("jpg".equals(suffix) || "png".equals(suffix)) {
            uploadPath = String.format(PATH, "img");
        } else {
            uploadPath = String.format(PATH, "file");
        }
        //填充请求路径
        String url = String.format(UploadGiteeImgBed.API_CREATE_POST,
                UploadGiteeImgBed.OWNER,
                UploadGiteeImgBed.REPO,
                uploadPath + fileName);
        return url;
    }

    /**
     * 获取创建文件的请求体map集合：access_token、message、content
     *
     * @param multipartFile 文件字节数组
     * @return 封装成map的请求体集合
     */
    public static Map<String, String> getUploadBodyMap(byte[] multipartFile) {
        HashMap<String, String> bodyMap = new HashMap<>(3);
        bodyMap.put("access_token", UploadGiteeImgBed.ACCESS_TOKEN);
        bodyMap.put("message", UploadGiteeImgBed.ADD_MESSAGE);
        bodyMap.put("content", Base64.encode(multipartFile));
        return bodyMap;
    }

    /**
     * 获取创建文件的请求体map集合：access_token、message、content
     *
     * @param multipartFile 文件字节数组
     * @return 封装成map的请求体集合
     */
    public static Map<String, Object> getUploadBodyObjectMap(byte[] multipartFile) {
        HashMap<String, Object> bodyMap = new HashMap<>(3);
        bodyMap.put("access_token", UploadGiteeImgBed.ACCESS_TOKEN);
        bodyMap.put("message", UploadGiteeImgBed.ADD_MESSAGE);
        bodyMap.put("content", Base64.encode(multipartFile));
        return bodyMap;
    }

    public static String uploadGiteeFile(MultipartFile multipartFile) throws Exception {
        String giteeUrl = "";
        //根据文件名生成指定的请求url
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new BaseException("文件对象名称({})非法，不允许下载。 ");
        }
        String targetURL = createUploadFileUrl(originalFilename);
        //请求体封装
        Map<String, String> uploadBodyMap = UploadGiteeImgBed.getUploadBodyMap(multipartFile.getBytes());

        //打开浏览器创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建HttpPost请求,设置URL访问路径
        HttpPost httpPost = new HttpPost(targetURL);

        //声明List集合,封装表单中的参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : uploadBodyMap.keySet()) {
            String value = uploadBodyMap.get(key);
            params.add(new BasicNameValuePair(key, value));
        }
        //创建表单的Entity对象,第一个参数是封装好的表单数据,第二个是参数是编码
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
        //设置表单的Entity对象到Post请求中
        httpPost.setEntity(formEntity);
        //按回车发起请求,返回响应,使用HttpClient对象发起请求
        CloseableHttpResponse response = null;
        try {
            //使用HttpClient发起请求，获取response
            response = httpClient.execute(httpPost);
            //解析响应
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取响应体
                HttpEntity httpEntity = response.getEntity();
                //解析响应体
                String content = EntityUtils.toString(httpEntity, "utf-8");
                log.info("上传图片到gitee：", content);
                giteeUrl = targetURL;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭response
                response.close();
                //关闭HttpClient
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return giteeUrl;
    }

    public static String uploadFileToGitee(MultipartFile multipartFile) throws Exception {
        String giteeUrl = "";
        //根据文件名生成指定的请求url
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new BaseException("文件对象名称({})非法，不允许下载。 ");
        }
        String targetURL = createUploadFileUrl(originalFilename);
        //请求体封装
        Map<String, Object> uploadBodyMap = getUploadBodyObjectMap(multipartFile.getBytes());
        //借助HttpUtil工具类发送POST请求
        String JSONResult = HttpUtil.post(targetURL, uploadBodyMap);
        //解析响应JSON字符串
        JSONObject jsonObj = JSONUtil.parseObj(JSONResult);
        //请求失败
        if (jsonObj == null || jsonObj.getObj("commit") == null) {
            log.error("gitee 请求失败：", jsonObj);
            throw new BaseException("请求 Gitee 服务器失败。 ");
        }
        //请求成功：返回下载地址
        JSONObject content = JSONUtil.parseObj(jsonObj.getObj("content"));
        log.info("响应data为：" + content.getObj("html_url"));
        giteeUrl = (String) content.getObj("html_url");
        return giteeUrl;
    }


}
