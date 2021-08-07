package cn.mooding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;
import java.rmi.UnknownHostException;


/**
 * @Author BlueFire
 * @Date 21/3/2021 -下午12:27
 */

@SpringBootApplication
@EnableOpenApi
@EnableCaching //启用缓存
public class MoodingJarApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(MoodingJarApplication.class, args);
    }
}
