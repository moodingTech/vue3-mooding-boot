package cn.mooding.modules.system.controller;

import cn.mooding.common.model.constant.Constants;
import cn.mooding.common.model.constant.SysConfig;
import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.UploadGiteeImgBed;
import cn.mooding.common.utils.file.FileUploadUtils;
import cn.mooding.common.utils.file.FileUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.config.ServerConfig;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用请求处理
 *
 * @Author BlueFire
 * @Date 28/8/2021 -上午9:42
 */
@RestController
@Api(tags = "系统通用请求")
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = SysConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public ResponseResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = SysConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("fileName", fileName);
            data.put("url", url);
            return ResponseResult.okResult(data);
        } catch (Exception e) {
            return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = SysConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 上传图片
     *
     * @param multipartFile 文件对象
     * @return
     * @throws IOException
     */
    @PostMapping("/common/uploadImg")
    public ResponseResult uploadImg(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        log.info("uploadImg()请求已来临...");
        //根据文件名生成指定的请求url
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new Exception("文件对象名称({})非法，不允许下载。 ");
        }
        String targetURL = UploadGiteeImgBed.createUploadFileUrl(originalFilename);
        log.info("目标url：" + targetURL);


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
                System.out.println(content);
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


//        //        借助HttpUtil工具类发送POST请求
//        String JSONResult = HttpUtil.post(targetURL, uploadBodyMap);
//        //解析响应JSON字符串
//        JSONObject jsonObj = JSONUtil.parseObj(JSONResult);
//        //请求失败
//        if(jsonObj == null || jsonObj.getObj("commit") == null){
//            return ResponseResult.errorResult(CommonExceptionEnum.IMAGE_UPLOAD_ERROR);
//        }
//        log.info("响应data为："+content.getObj("download_url"));
//        //请求成功：返回下载地址
//        JSONObject content = JSONUtil.parseObj(jsonObj.getObj("content"));
//        return  ResponseResult.okResult(content.getObj("download_url"));
        return ResponseResult.okResult();
    }
}
