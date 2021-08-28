package cn.mooding.common.utils;

import cn.mooding.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取i18n资源文件
 *
 * @Author BlueFire
 * @Date 23/3/2021 -下午10:37
 */
public class MessageUtils {
    /**
     * 根据消息键 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @return 获取国际化翻译值
     */
    public String getMessage(String code) {
        return message(code,  new   Object[]{});
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
