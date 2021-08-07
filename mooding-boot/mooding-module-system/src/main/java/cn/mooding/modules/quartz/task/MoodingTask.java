package cn.mooding.modules.quartz.task;

import cn.mooding.common.utils.string.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试 案例
 * @Author BlueFire
 * @Date 26/7/2021 -下午10:48
 */
@Component("moodingTask")
public class MoodingTask {

    public void mdMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void mdParams(String params) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("执行有参方法：" + params);
    }

    public void mdNoParams()
    {
        System.out.println("执行无参方法");
    }
}
