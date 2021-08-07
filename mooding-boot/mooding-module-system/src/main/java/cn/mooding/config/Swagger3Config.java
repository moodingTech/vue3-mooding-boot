package cn.mooding.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger配置类
 * @Author BlueFire
 * @Date 21/3/2021 -下午7:56
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    //可以配置在application文件中
    /**
     * 与Spring 同一级的配置
     * swagger:
     * enable: true
     * application-name: ${spring.application.name}
     * application-version: 1.0
     * application-description: springfox swagger 3.0整合Demo
     * try-host: http://localhost:${server.port}
     */
    /** 是否开启swagger */
    @Value("${swagger.swaggerEnabled}")
    private boolean swaggerEnabled;
   // Boolean swaggerEnabled = true;//ture 启用Swagger3.0 fasle 禁用（生产环境要禁用）

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径使用@Api的controller
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("适用于前后端分离统一的接口文档")
                //作者信息
                //.contact(new Contact("name","url", "email"))
                .contact(new Contact("木丁", "www.mooding.cn", "D102601560@gamil.com"))
                .version("1.0")
                .build();
    }
}