package cn.mooding.common.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据字典注解
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:13
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    //  数据code
    String dicCode();

    // 数据Text
    String dicText() default "";

    //数据字典表
    String dictTable() default "";
}

