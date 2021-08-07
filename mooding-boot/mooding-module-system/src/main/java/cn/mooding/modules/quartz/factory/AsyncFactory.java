package cn.mooding.modules.quartz.factory;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.utils.AddressUtils;
import cn.mooding.common.utils.IpUtils;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.spring.SpringUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.system.entity.SysLogininfo;
import cn.mooding.modules.system.entity.SysOperLog;
import cn.mooding.modules.system.service.ISysLogininfoService;
import cn.mooding.modules.system.service.ISysOperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @Author BlueFire
 * @Date 26/3/2021 -下午10:18
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
                                             final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(StringUtils.getBlock(ip));
                s.append(address);
                s.append(StringUtils.getBlock(username));
                s.append(StringUtils.getBlock(status));
                s.append(StringUtils.getBlock(message));
                sys_user_logger.info("dgl" + message);
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLogininfo logininfo = new SysLogininfo();
                logininfo.setUserName(username);
                logininfo.setIpaddr(ip);
                logininfo.setLoginLocation(address);
                logininfo.setBrowser(browser);
                logininfo.setOs(os);
                logininfo.setMsg(message);
                logininfo.setLoginTime(LocalDateTime.now());
                // 日志状态
                if (CommonConstant.LOGIN_SUCCESS.equals(status) || CommonConstant.LOGOUT.equals(status)) {
                    logininfo.setStatus(CommonConstant.SUCCESS);
                } else if (CommonConstant.LOGIN_FAIL.equals(status)) {
                    logininfo.setStatus(CommonConstant.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLogininfoService.class).insertLogininfo(logininfo);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
