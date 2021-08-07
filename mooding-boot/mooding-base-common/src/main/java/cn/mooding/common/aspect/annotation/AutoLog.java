package cn.mooding.common.aspect.annotation;

import cn.mooding.common.model.constant.CommonConstant;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 0:操作日志;1:登录日志;2:定时任务;
     */
    int logType() default CommonConstant.LOG_TYPE_2;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除）
     */
    int operateType() default 0;
}
