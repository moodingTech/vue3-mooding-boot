import cn.mooding.common.model.constant.SysConfig;
import cn.mooding.common.utils.AddressUtils;
import cn.mooding.common.utils.string.CharsetKit;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author BlueFire
 * @Date 25/8/2021 -上午7:38
 */
@SpringBootTest
public class HttpUtilsTest {
   /* private static final String TEST_URL = "https://m.kuaidi100.com/result.jsp?nu=YT9728141940266&com=yuantong";
    private static final Logger log = LoggerFactory.getLogger(HttpUtilsTest.class);

    @Test
    public void getIpAdree() {
        // IP地址查询
        String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
        String ip = "119.131.39.84";
        String rspStr = cn.mooding.common.utils.http.HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", CharsetKit.GBK);
        if (StringUtils.isEmpty(rspStr)) {

            log.error("获取地理位置异常 {}", rspStr);
            log.error("获取地理位置异常 {}", ip);
        }

        log.info("addressEnabled {}", SysConfig.getProfile());
        JSONObject obj = JSONObject.parseObject(rspStr);
        String region = obj.getString("pro");
        String city = obj.getString("city");
        log.info(String.format("%s %s", region, city));
    }

    @Test
    public void testGetHtmlPageResponse() {
        HttpUtils httpUtils = HttpUtils.getInstance();
        httpUtils.setTimeout(30000);
        httpUtils.setWaitForBackgroundJavaScript(30000);
        try {
            String htmlPageStr = httpUtils.getHtmlPageResponse(TEST_URL);
            System.out.println(htmlPageStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHtmlPageResponseAsDocument() {
        HttpUtils httpUtils = HttpUtils.getInstance();
        httpUtils.setTimeout(30000);
        httpUtils.setWaitForBackgroundJavaScript(30000);
        try {
            Document document = httpUtils.getHtmlPageResponseAsDocument(TEST_URL);
            //TODO
            System.out.println(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage("http://ent.sina.com.cn/film/");//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

        //TODO 下面的代码就是对字符串的操作了,常规的爬虫操作,用到了比较好用的Jsoup库

        Document document = Jsoup.parse(pageXml);//获取html文档
        List<Element> infoListEle = document.getElementById("feedCardContent").getElementsByAttributeValue("class", "feed-card-item");//获取元素节点等
        infoListEle.forEach(element -> {
            System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").text());
            System.out.println(element.getElementsByTag("h2").first().getElementsByTag("a").attr("href"));
        });
    }

    @Test
    public void captureJavascript() throws Exception {
        String strURL = "https://news.163.com/";

        URL url = new URL(strURL);

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        InputStreamReader input = new InputStreamReader(httpConn

                .getInputStream(), "utf-8");

        BufferedReader bufReader = new BufferedReader(input);

        String line = "";

        StringBuilder contentBuf = new StringBuilder();

        while ((line = bufReader.readLine()) != null) {
            contentBuf.append(line);

        }

        System.out.println("captureJavascript()的结果：\n" + contentBuf.toString());

    }

    *//**
     * selenium 解决数据异步加载问题
     * https://npm.taobao.org/mirrors/chromedriver/
     *
     *//*
    @Test
    public void selenium() {
        String url = "https://m.kuaidi100.com/result.jsp?nu=YT9728141940266&com=yuantong";
        // 设置 chromedirver 的存放位置
        System.getProperties().setProperty("webdriver.chrome.driver", "D://project/git/mooding-boot/mooding-boot/mooding-module-system/src/main/resources/chromedriver_win32/chromedriver.exe");
        // 设置无头浏览器，这样就不会弹出浏览器窗口
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(url);
        // 获取到要闻新闻列表
        System.out.println("DGL" + webDriver.findElements(By.xpath("//*[@id=\"main\"]/div[2]/section/div[1]/ul")));
        List<WebElement> webElements = webDriver.findElements(By.className("result-success"));
        System.out.println("Ssss:" + webElements.size());
        for (WebElement webElement : webElements) {
            // 提取新闻连接
//            String article_url = webElement.getAttribute("href");
//            // 提取新闻标题
//            String title = webElement.getText();
//            if (article_url.contains("https://news.163.com/")) {
//                System.out.println("文章标题：" + title + " ,文章链接：" + article_url);
//            }
            System.out.println("DGL\n" + webElement.findElement(By.className("result-list")).getText());
        }
        webDriver.close();
    }

    @Test
    public void seleniumTest() {
        WebDriver driver = open();
        pageTurning(driver);

//        driver.quit();

    }

    *//**
     * 翻页
     *//*
    public static void pageTurning(WebDriver driver) {
        WebElement content = driver.findElement(By.className("contenttext"));
        parseElement(content);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement pageTools = driver.findElement(By.className("page-tools-bottom"));
        WebElement paginationBox = pageTools.findElement(By.id("pagination_box"));
        //下一页的按钮
        WebElement next = paginationBox.findElement(By.className("next"));
        //按钮存在并且可点击
        if (null != next && next.isEnabled()) {
            //点击翻页
            next.click();
            System.out.println("点击下一页");
            try {
                //等待页面加载
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pageTurning(driver);
        }

    }

    public static void parseElement(WebElement content) {
        List<WebElement> trs = content.findElements(By.tagName("tr"));
        System.out.println(trs.size());

        for (WebElement tr : trs) {
            List<WebElement> tds = tr.findElements(By.tagName("td"));

            WebElement td = tds.get(1);
            WebElement newsContent = td.findElement(By.className("news-content"));

            String id = newsContent.getAttribute("id");

            WebElement newsItem = newsContent.findElement(By.className("news-item"));

            String title = newsItem.findElement(By.className("profile-title")).findElement(By.className("ng-scope")).findElement(By.className("ng-binding")).getText();
            String summary = newsItem.findElement(By.className("news-item-title")).getText();

            WebElement newsItemTools = newsItem.findElement(By.className("news-item-tools"));

            WebElement mr5 = newsItemTools.findElement(By.className("mr5"));
            List<WebElement> industries = mr5.findElements(By.tagName("span")).get(1).findElements(By.tagName("div"));
            StringBuilder industry = new StringBuilder();
            if (!CollectionUtils.isEmpty(industries)) {
                for (WebElement ele : industries) {
                    industry.append(ele.getText()).append(",");
                }
                industry.deleteCharAt(industry.length() - 1);
            }

            WebElement mr10 = newsItemTools.findElement(By.className("mr10"));
            String addr = mr10.findElements(By.tagName("span")).get(1).findElement(By.tagName("div")).getText();

            WebElement relativeKeyword = newsItemTools.findElement(By.className("relative-keyword"));
            String keyword = relativeKeyword.findElements(By.tagName("span")).get(1).getText();

            WebElement btnGroup = newsItemTools.findElement(By.className("btn-group"));
            String url = btnGroup.findElements(By.className("inline-block")).get(3).findElement(By.tagName("a")).getAttribute("href");


            String source1 = tds.get(3).findElement(By.tagName("span")).getText();
            String source2 = tds.get(3).findElement(By.tagName("div")).getText();
            List<WebElement> timeEle = tds.get(4).findElements(By.tagName("span"));
            String time1 = timeEle.get(0).getText();
            String time2 = timeEle.get(1).getText();

            System.out.println("id：" + id);
            System.out.println("标题：" + title);
            System.out.println("摘要：" + summary);
            System.out.println("行业：" + industry.toString());
            System.out.println("地址：" + addr);
            System.out.println("关键字：" + keyword);

            System.out.println("来源：" + source1 + " " + source2);
            System.out.println("时间：" + time1 + " " + time2);

            System.out.println("源地址：" + url);

        }
    }

    public static WebDriver open() {
//        System.setProperty( "webdriver.chrome.driver" ,CHROME_DRIVER_PATH);
//        System.setProperty("webdriver.chrome.driver", "D://project/git/mooding-boot/mooding-boot/mooding-module-system/src/main/resources/chromedriver_win32/chromedriver.exe");
// 设置 chromedirver 的存放位置
        System.getProperties().setProperty("webdriver.chrome.driver", "D://project/git/mooding-boot/mooding-boot/mooding-module-system/src/main/resources/chromedriver_win32/chromedriver.exe");
        // 设置无头浏览器，这样就不会弹出浏览器窗口
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();


        driver.get("https://news.163.com/");

//        String cookies = "a=a;b=b;c=c";
//        //为了绕过登录，在此处设置cookie信息
//        if (StringUtils.isNotBlank(cookies)) {
//            String[] cookieArr = cookies.split("\\;");
//            for (String cookieStr : cookieArr) {
//                if (StringUtils.isNotBlank(cookieStr)) {
//                    cookieStr = cookieStr.trim();
//                    String[] entry = cookieStr.split("\\=");
//                    driver.manage().addCookie(new Cookie(entry[0].trim(), entry[1].trim()));
//                }
//            }
//        }
//        driver.get("https://www.xxxx.com/newEdition/keywordSearchBody.action?monitorType=1");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return driver;
    }
*/
}
