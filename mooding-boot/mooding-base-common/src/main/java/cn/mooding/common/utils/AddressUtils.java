package cn.mooding.common.utils;

import cn.mooding.common.model.constant.SysConfig;
import cn.mooding.common.utils.http.HttpUtils;
import cn.mooding.common.utils.string.CharsetKit;
import cn.mooding.common.utils.string.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取地址类
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:53
 */
@Component
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

//    private static boolean addressEnabled;


    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

//    @Value("${mooding.addressEnabled}")
//    public static void setAddressEnabled(boolean addressEnabled) {
//        AddressUtils.addressEnabled = addressEnabled;
//    }
//
//    public static boolean isAddressEnabled() {
//        return addressEnabled;
//    }

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (SysConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", CharsetKit.GBK);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", rspStr);
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return address;
    }
}
