# 一、创建spring boot 项目

## 1.   概述

```
工程基于Spring-boot 2.3.1.RELEASE 版本构建，工程父项目为mooding,项目依赖、modules组织 并通过继承方式集成Spring-boot。
```

父项目下分2个公共子项目：

> **·**	Mooding-base-common : 是整个工程的配置核心，包括所有集成三方框架的配置定义，比如redis、kafka等。除此之外还包括项目每个模块及整个项目的常量定义;
>
> **·**	Mooding-module-system ：项目中用到的Dto、Pojo、Mapper、Enums定义工程;

## 2  创建父类项目

![image-20210321080555948](D:\project\git\mooding-boot\项目课件\assets\image-20210321080555948.png)

![image-20210321080632769](D:\project\git\mooding-boot\项目课件\assets\image-20210321080632769.png)

![image-20210321080659141](D:\project\git\mooding-boot\项目课件\assets\image-20210321080659141.png)

### 2.1  基本设置

#### 2.1.1  设置本地仓库

![image-20210321081029306](D:\project\git\mooding-boot\项目课件\assets\image-20210321081029306.png)

#### 2.1.2  设置项目编码

编码统一设置为UTF-8

![image-20210321081104842](D:\project\git\mooding-boot\项目课件\assets\image-20210321081104842.png)

### 2.2  pom.xml配置

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.mooding</groupId>
    <artifactId>mooding-boot</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--继承Springboot工程-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.4</version>
    </parent>

    <!-- 依赖包版本管理 -->
    <properties>
        <!-- 项目编译JDK版本 -->
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <java.version>1.8</java.version>
        <!-- 项目源码及编译输出的编码 -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <!-- mybatis-plus -->
        <mybatis-plus.version>3.4.2</mybatis-plus.version>
        <!--swagger-->
        <swagger.version>3.0.0</swagger.version>
        <!--velocity模板引擎-->
        <velocity.version>2.2</velocity.version>
        <!--Java连接mysql数据库-->
        <mysql.version>8.0.23</mysql.version>
        <!-- druid数据源驱动 -->
        <druid.version>1.2.5</druid.version>
        <!--动态数据源-->
        <dynamic-datasource.version>3.3.2</dynamic-datasource.version>
        <!--lombok-->
        <lombok.version>1.18.8</lombok.version>
        <!--fastjson-->
        <fastjson.version>1.2.75</fastjson.version>
    </properties>

    <dependencies>
        <!-- SpringBoot 核心包 :核心启动器，包含了自动配置、日志和YAML-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- SpringBoot 测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- SpringBoot 拦截器 支持面向方面的编程即AOP，包括spring-aop和AspectJ-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- SpringBoot Web容器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- mybatis-plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>

        <!-- druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>${druid.version}</version>
        </dependency>

        <!-- 动态数据源 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>${dynamic-datasource.version}</version>
        </dependency>

        <!-- 阿里JSON解析器 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>

        <!--数据库相关配置-->
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
            <scope>runtime</scope>
        </dependency>

        <!-- Swagger API文档 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>${swagger.version}</version>
        </dependency>

        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- 打包时跳过测试 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skip>true</skip>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                </configuration>
            </plugin>
        </plugins>
    </build>
    <!-- 定义远程仓库为国内的aliyun-->
    <repositories>
        <repository>
            <id>public</id>
            <name>aliyun nexus</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>
</project>
```

## 3   创建common子项目[mooding-Base-common]

### 3.1创建通用模块

![image-20210321085930101](D:\project\git\mooding-boot\项目课件\assets\image-20210321085930101.png)

![image-20210321085942479](D:\project\git\mooding-boot\项目课件\assets\image-20210321085942479.png)

![image-20210321085954008](D:\project\git\mooding-boot\项目课件\assets\image-20210321085954008.png)



### 3.2  pom.xml配置

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>mooding-boot</artifactId>
        <groupId>cn.mooding</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <description>共通Common模块： 底层工具类、注解、接口</description>
    <artifactId>mooding-Base-common</artifactId>

</project>
```

### 3.3添加相关的Java类

#### 3.3.1 通用的结果返回类

```
package cn.mooding.common.model.enums;

/**
 * 请求返回结果枚举
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:22
 */
public enum HttpCodeEnum {
    // 成功段200
    SUCCESS(200, "操作成功"),

    // 登录段1~50
    LOGIN_USER_NOT_EXIST(12, "该用户不存在"),
    LOGIN_USER_DEL(14, "该用户已经注销"),
    LOGIN_USER_FREEZE(16, "该用户已冻结"),

    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    SERVER_ERROR(503, "服务器内部错误"),

    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    DATA_TOO_LENGTH(1003, "字段太长,超出数据库字段的长度");

    int code;
    String message;

    HttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

```

```
package cn.mooding.common.model.dto;

import cn.mooding.common.model.enums.HttpCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用的结果返回类
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class ResponseResult<T> implements Serializable {
    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;
    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code;
    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    private T data;

    public ResponseResult() {
        this.success = true;
        this.code = 200;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        result.success = false;
        return result.error(code, msg);
    }

    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        result.success = true;
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getMessage());
        if (data != null) {
            result.setData(data);
        }
        result.success = true;
        return result;
    }

    public static ResponseResult okResult() {
        ResponseResult result = setHttpCodeEnum(HttpCodeEnum.SUCCESS, HttpCodeEnum.SUCCESS.getMessage());
        result.success = true;
        return result;
    }

    public static ResponseResult errorResult(HttpCodeEnum enums) {
        return setHttpCodeEnum(enums, enums.getMessage(), false);
    }

    public static ResponseResult errorResult(HttpCodeEnum enums, String message) {
        return setHttpCodeEnum(enums, message, false);
    }

    public static ResponseResult setHttpCodeEnum(HttpCodeEnum enums) {
        return okResult(enums.getCode(), enums.getMessage());
    }

    private static ResponseResult setHttpCodeEnum(HttpCodeEnum enums, String message) {
        return okResult(enums.getCode(), message);
    }

    private static ResponseResult setHttpCodeEnum(HttpCodeEnum enums, String message, boolean success) {
        return okResult(enums.getCode(), message);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.message = msg;
        this.success = false;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        this.success = true;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
        this.success = true;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
```

#### 3.3.2 全局异常

```
package cn.mooding.common.model.exception;

/**
 * 自定义异常
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午11:03
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

```

```
package cn.mooding.common.model.exception;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午11:05
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public ResponseResult handleRRException(BaseException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
    }

    /**
     * 路径不存在异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        // return Result.error(404, "路径不存在，请检查路径是否正确");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 数据库数据存在异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseResult handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        // return Result.error("数据库中已存在该记录");
        return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST);
    }
  
    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseResult handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        // return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);

    }

    /**
     * 段太长,超出数据库字段的长度
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseResult handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        // return Result.error("字段太长,超出数据库字段的长度");
        return ResponseResult.errorResult(HttpCodeEnum.DATA_TOO_LENGTH);
    }


    /**
     * 其他异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        // return Result.error("操作失败，"+e.getMessage());
        return ResponseResult.errorResult(HttpCodeEnum.SERVER_ERROR);
    }
}

```

#### 3.3.3 全局注解

```
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
```

```
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
```

### 3.4 common模块结构

![image-20210321112554919](D:\project\git\mooding-boot\项目课件\assets\image-20210321112554919.png)

## 4  创建common子项目[mooding-Base-common]

![image-20210321112713728](D:\project\git\mooding-boot\项目课件\assets\image-20210321112713728.png)

![image-20210321112720498](D:\project\git\mooding-boot\项目课件\assets\image-20210321112720498.png)

### 4.1 新增环境配置文件

新增spring boot 配置文件 application.yml

```
spring:
  profiles:
    active: dev
swagger:
  application-name: ${spring.application.name}
  application-version: 1.0
  application-description: springfox swagger 3.0整合Demo
  try-host: http://localhost:${server.port}

# 系统基本设置
mooding:
  captchaType: 'math'

mybatis:
  mapper-locations: classpath:mybatis/**/*.xml
  type-aliases-package: cn.mooding.modules.**.mapper
```

新增spring boot开发环境 配置文件 application-dev.yml

```
server:
  port: 8081
  tomcat:
    max-swallow-size: -1
  compression:
    enabled: true
    min-response-size: 1024
    mime-types: application/javascript,application/json,application/xml,text/html,text/xml,text/plain,text/css,image/*

management:
  endpoints:
    web:
      exposure:
        include: metrics,httptrace

spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  #json 时间戳统一转换
  jackson:
    date-format:   yyyy-MM-dd HH:mm:ss
    time-zone:   GMT+8
  aop:
    proxy-target-class: true
  activiti:
    check-process-definitions: false
    #启用作业执行器
    async-executor-activate: false
    #启用异步执行器
    job-executor-activate: false
  jpa:
    open-in-view: false
  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
  datasource:
    druid:
      stat-view-servlet:
        enabled: true
        loginUsername: admin
        loginPassword: 123456
        allow:
      web-stat-filter:
        enabled: true
    dynamic:
      druid: # 全局druid参数，绝大部分值和默认保持一致。(现已支持的参数如下,不清楚含义不要乱设置)
        # 连接池的配置信息
        # 初始化大小，最小，最大
        initial-size: 5
        min-idle: 5
        maxActive: 20
        # 配置获取连接等待超时的时间
        maxWait: 60000
        # 配置间隔 多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        timeBetweenEvictionRunsMillis: 60000
        # 配置一个连接在池中最小生存的时间，单位是毫秒
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        # 打开PSCache，并且指定每个连接上PSCache的大小
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        filters: stat,wall,slf4j
        # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/mooding-master?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
          # 多数据源配置
          #multi-datasource1:
          #url: jdbc:mysql://localhost:3306/mooding-save?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          #username: root
          #password: root
          #driver-class-name: com.mysql.cj.jdbc.Driver
#mybatis plus 设置
mybatis-plus:
  mapper-locations: classpath:mybatis/**/*.xml
  type-aliases-package: cn.mooding.modules.**.mapper
  global-config:
    # 关闭MP3.0自带的banner
    banner: false
    db-config:
      #主键类型  0:"数据库ID自增",1:"该类型为未设置主键类型", 2:"用户输入ID",3:"全局唯一ID (数字类型唯一ID)", 4:"全局唯一ID UUID",5:"字符串全局唯一ID (idWorker 的字符串表示)";
      id-type: 3
      # 默认数据库表下划线命名
      table-underline: true
  configuration:
    # 这个配置会将执行的sql打印出来，在开发或测试的时候可以用
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    # 返回类型为Map,显示null对应的字段
    call-setters-on-nulls: true
#Mybatis输出sql日志
logging:
  level:
    org.jeecg.modules.system.mapper: debug
#enable swagger
swagger:
  enable: true
```

新增spring boot生产环境 配置文件 application-prod.yml

```
server:
  port: 8081
  tomcat:
    max-swallow-size: -1
  compression:
    enabled: true
    min-response-size: 1024
    mime-types: application/javascript,application/json,application/xml,text/html,text/xml,text/plain,text/css,image/*

management:
  endpoints:
    web:
      exposure:
        include: metrics,httptrace

spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  #json 时间戳统一转换
  jackson:
    date-format:   yyyy-MM-dd HH:mm:ss
    time-zone:   GMT+8
  aop:
    proxy-target-class: true
  activiti:
    check-process-definitions: false
    #启用作业执行器
    async-executor-activate: false
    #启用异步执行器
    job-executor-activate: false
  jpa:
    open-in-view: false
  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
  datasource:
    druid:
      stat-view-servlet:
        enabled: true
        loginUsername: admin
        loginPassword: 123456
        allow:
      web-stat-filter:
        enabled: true
    dynamic:
      druid: # 全局druid参数，绝大部分值和默认保持一致。(现已支持的参数如下,不清楚含义不要乱设置)
        # 连接池的配置信息
        # 初始化大小，最小，最大
        initial-size: 5
        min-idle: 5
        maxActive: 20
        # 配置获取连接等待超时的时间
        maxWait: 60000
        # 配置间隔 多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        timeBetweenEvictionRunsMillis: 60000
        # 配置一个连接在池中最小生存的时间，单位是毫秒
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        # 打开PSCache，并且指定每个连接上PSCache的大小
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        filters: stat,wall,slf4j
        # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      datasource:
        master:
          url: jdbc:mysql://49.235.52.198:3306/mooding-master?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          username: root
          password: root
          driver-class-name: com.mysql.cj.jdbc.Driver
          # 多数据源配置
          #multi-datasource1:
          #url: jdbc:mysql://49.235.52.198:3306/mooding-save?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          #username: root
          #password: root
          #driver-class-name: com.mysql.cj.jdbc.Driver
#mybatis plus 设置
mybatis-plus:
  mapper-locations: classpath:mybatis/**/*.xml
  type-aliases-package: cn.mooding.modules.**.mapper
  global-config:
    # 关闭MP3.0自带的banner
    banner: false
    db-config:
      #主键类型  0:"数据库ID自增",1:"该类型为未设置主键类型", 2:"用户输入ID",3:"全局唯一ID (数字类型唯一ID)", 4:"全局唯一ID UUID",5:"字符串全局唯一ID (idWorker 的字符串表示)";
      id-type: 3
      # 默认数据库表下划线命名
      table-underline: true
  configuration:
    # 这个配置会将执行的sql打印出来，在开发或测试的时候可以用
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    # 返回类型为Map,显示null对应的字段
    call-setters-on-nulls: true
#Mybatis输出sql日志
logging:
  level:
    org.jeecg.modules.system.mapper: debug
#enable swagger
swagger:
  enable: flase
```

### 4.2新增启动类

```
package cn.mooding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.oas.annotations.EnableOpenApi;
import java.net.UnknownHostException;

/**
 * @Author BlueFire
 * @Date 6/12/2020 -上午7:21
 */
@Slf4j
@SpringBootApplication
@EnableOpenApi
public class MoodingJarApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws UnknownHostException {
      SpringApplication.run(MoodingJarApplication.class, args);
    }
}
```

### 4.3 代码生成器工具类

> AutoGenerator 是 MyBatis-Plus 的代码生成器，通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，极大的提升了开发效率。

#### 4.3.1新增依赖

​	在pom.xml文件新增依赖

```
<!--代码生成器 依赖-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.4.2</version>
</dependency>
<!-- 模板引擎 依赖，MyBatis-Plus 支持 Velocity（默认）-->
<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.3</version>
</dependency>
```

#### 4.3.2创建代码生成器工具类

```
package cn.mooding.common.utils.generate;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * mybatis plus 自动生成后台代码
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午11:32
 */
public class MyCodeGenerate {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/mooding-module-system/src/main/java");
        gc.setAuthor("BlueFire");
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://49.235.52.198:3306/mooding?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("cn.mooding.modules.system");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/mooding-module-system/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"t_", "t_sys_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略表名映射到实体的命名策略(下划线到驼峰)
        strategy.setInclude(new String[]{"sys_user"}); // 需要生成的表
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        //strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);


        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
```

#### 4.3.3运行代码生成器工具类

生成用户表相关代码结构如下

![image-20210321123910099](D:\project\git\mooding-boot\项目课件\assets\image-20210321123910099.png)

## 5测试

在用户信息表 前端控制器SysUserController.java新增查询用户信息的接口

```
package cn.mooding.modules.system.controller;


import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private ISysUserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public ResponseResult list() {
        List<SysUser> list = userService.list();
        return ResponseResult.okResult(list);
    }

}
```

运行MoodingJarApplication.java启动项目

```
2021-03-21 13:29:43.363  INFO 7720 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2021-03-21 13:29:43.532  INFO 7720 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2021-03-21 13:29:43.708  INFO 7720 --- [           main] cn.mooding.MoodingJarApplication         : Started MoodingJarApplication in 3.996 seconds (JVM running for 5.391)

```

启动成功，用浏览器或者postman访问

```
http://localhost:8081/sysUser/list
```

![image-20210321133229219](D:\project\git\mooding-boot\项目课件\assets\image-20210321133229219.png)

项目已经正常启动，并且已经关联数据库。

# 二、加入 Git 版本管理

建议在项目的一开始就加入版本管理，通过版本管理，我们可以得到很多好处，例如：

- 代码备份
- 多人协作
- 历史记录
- ....

下面是具体的操作流程。

（1）创建远程仓库（GitHub、Gitee、coding）。。。。

（2）把本地仓库推送到远程仓库

如果没有本地仓库：

```shell
# 初始化本地仓库
git init

# 把文件添加到暂存区
git add README.md

#提交之前撤销所有添加的文件
git rm -r --cached .

# 把暂存区文件提交到本地仓库形成历史记录
git commit -m "first commit"

# 添加远端仓库地址到本地仓库
git remote add origin https://github.com/lipengzhou/toutiao-publish-admin.git

# 推送到远程仓库
git push -u origin master

# 强制覆盖已有的分支
git push -u origin master -f 
```



如果已有本地仓库

> VueCLI 在创建项目的时候自动帮你初始化了 `Git` 仓库，并且基于初始代码默认执行了一次提交。

```shell
git remote add origin https://github.com/lipengzhou/toutiao-publish-admin.git

# -u 就是记住本次推送的信息，下次就不用写推送信息了，可以直接 git push
git push -u origin master
```

（3）之后如果有代码变动需要提交

```shell
git add
git commit

# 推送到远程仓库
# 推送的时候如果不改变远程仓库和分支的话就直接
git push

# 如果推送的远程仓库或是分支改变了
$ git push -u 远程仓库 分支名称
```



> 扩展：管理远程仓库地址信息
>
> ```shell
> # 查看使用帮助
> git remote --help
> 
> # 查看所有的远程仓库信息
> git remote -v
> 
> # 添加远程仓库地址信息
> git remote add 名称 远程仓库地址
> 
> # 删除指定的远程仓库信息
> git remote remove 名称
> 
> # 修改远程仓库地址信息
> git remote set-url 远程仓库地址名称 新地址
> ```



# 三、整合Redis缓存

1.在父模块的pom.xml增加依赖

```
        <!-- 整合Redis缓存支持 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

2.在配置开发环境application-dev.yml配置文件中新增

```
##默认密码为空
spring:
  redis:
    host: localhost
    # Redis服务器连接端口
    port: 6379
    password: 123456
    jedis:
      pool:
        #连接池最大连接数（使用负值表示没有限制）
        max-active: 100
        # 连接池中的最小空闲连接
        max-idle: 10
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 100000
    # 连接超时时间（毫秒）
    timeout: 5000
    #默认是索引为0的数据库
    database: 0
```

3.创建RedisConfig

```
package cn.mooding.config;

import cn.mooding.common.model.constant.CacheConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

import static java.util.Collections.singletonMap;

/**
 * Redis配置类
 * 默认情况下RedisTemplate模板只能支持字符串，自定义一个RedisTemplate，设置序列化器，这样可以很方便的操作实例对象。
 *
 * @Author BlueFire
 * @Date 21/3/2021 -下午4:38
 */
@Configuration
@EnableCaching // 开启缓存支持
public class RedisConfig {
    /**
     * RedisTemplate配置
     *
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //om.activateDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 缓存配置管理器
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(LettuceConnectionFactory factory) {
        // 配置序列化（缓存默认有效期 6小时）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 以锁写入的方式创建RedisCacheWriter对象
        //RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
        // 创建默认缓存配置对象
        /* 默认配置，设置缓存有效期 1小时*/
        // RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
        /* 自定义配置test:demo 的超时时间为 5分钟*/
        RedisCacheManager cacheManager = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(factory)).cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(singletonMap(CacheConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues()))
                .transactionAware().build();
        return cacheManager;
    }
}
```

4.创建RedisCache工具类

```
package cn.mooding.common.utils.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisCache 工具类
 *
 * @Author BlueFire
 * @Date 21/3/2021 -下午4:49
 */
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     * @param key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection
     */
    public void deleteObject(Collection collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.members();
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 获得缓存的基本对象列表
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
```

5.开始测试：

1.启用缓存@EnableCaching：Application入口：

```
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
@EnableCaching//启用缓存
@MapperScan(value = {"cn.mooding.modules.system.mapper"})
public class MoodingJarApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(MoodingJarApplication.class, args);
    }
}
```

# 四、整合Swagger3

> 前后端分离的项目，接口文档的存在十分重要。与手动编写接口文档不同，swagger是一个自动生成接口文档的工具，在需求不断变更的开发环境下，手动编写文档的效率实在太低。与新版的swagger3相比swagger2配置更少，只需要简单操作如下。

1.在父模块的pom.xml增加依赖

```
<!-- Swagger API文档 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

2.创建Swagger配置类

```
package cn.mooding.config;
 
import io.swagger.annotations.ApiOperation;
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
 * @Author BlueFire
 * @email D102601560@gamil.com
 * @Date 2020/10/24 -14:30
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    //可以配置在application文件中
    /**
     * 与Spring 同一级的配置
     *swagger:
     *   enable: true
     *   application-name: ${spring.application.name}
     *   application-version: 1.0
     *   application-description: springfox swagger 3.0整合Demo
     *   try-host: http://localhost:${server.port}
     */
    @Value("${swagger.enabled}")
    private boolean swaggerEnabled;
    // Boolean swaggerEnabled=true;//ture 启用Swagger3.0 fasle 禁用（生产环境要禁用）
    @Bean
    public Docket createRestApi(){
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
 
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("适用于前后端分离统一的接口文档")
                //作者信息
                //.contact(new Contact("name","url", "email"))
                .contact(new Contact("木丁","www.mooding.cn", "D102601560@gamil.com"))
                .version("1.0")
                .build();
    }
}
```

# 五、整合SpringSecurity实现JWT认证

> 微服务架构，前后端分离目前已成为互联网项目开发的业界标准，其核心思想就是前端（APP、小程序、H5页面等）通过调用后端的API接口，提交及返回JSON数据进行交互。
>  在前后端分离项目中，首先要解决的就是登录及授权的问题。微服务架构下，传统的session认证限制了应用的扩展能力，无状态的JWT认证方法应运而生，该认证机制特别适用于分布式站点的单点登录（SSO）场景

1.在父模块的pom.xml增加依赖

```
<!-- spring security 安全认证 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<!--Token生成与解析-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```

2.application.yml配置中加入jwt配置信息：

```
# token配置
token:
  # 令牌自定义标识
  header: Authorization
  # 令牌前缀
  token-start-with: Bearer
  # 令牌秘钥
  secret: abcdefghijklmnopqrstuvwxyz
  # 令牌有效期（默认30分钟）
  expireTime: 30
```

3.登录用户身份权限

```
package cn.mooding.modules.security;

import cn.mooding.modules.system.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:17
 */
@Data
public class LoginUser implements UserDetails
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息
     */
    private SysUser user;
    

    public LoginUser()
    {
    }

    public LoginUser(SysUser user, Set<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return user.getPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled()
    {
        return true;
    }

        @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
}
```

4.用户登录对象

```
package cn.mooding.modules.security;

import lombok.Data;

/**
 * 用户登录对象
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:21
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}

```

5.token验证处理

```
package cn.mooding.modules.security.service;

import cn.mooding.common.model.constant.CommonConstant;
import cn.mooding.common.utils.AddressUtils;
import cn.mooding.common.utils.IdUtils;
import cn.mooding.common.utils.IpUtils;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:15
 */
@Component
public class TokenService {
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(CommonConstant.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUser user = redisCache.getCacheObject(userKey);
            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonConstant.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param token 令牌
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(CommonConstant.TOKEN_PREFIX)) {
            token = token.replace(CommonConstant.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return CommonConstant.LOGIN_TOKEN_KEY + uuid;
    }
}
```

6.定义认证失败处理类

```
package cn.mooding.modules.security.handle;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:02
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        int code = HttpCodeEnum.UNAUTHORIZED.getCode();
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(ResponseResult.errorResult(code, msg)));
    }
}

```

7.自定义退出处理类

```
package cn.mooding.modules.security.handle;

import cn.mooding.common.model.dto.ResponseResult;
import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午7:13
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(ResponseResult.okResult(HttpCodeEnum.SUCCESS.getCode(), "退出成功")));
    }
}
```

8.安全服务工具类

```
package cn.mooding.modules.security.utils;

import cn.mooding.common.model.enums.HttpCodeEnum;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.modules.security.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午8:15
 */
public class SecurityUtils {
    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new BaseException("获取用户账户异常");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BaseException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}

```

9.token过滤器 验证token有效性

```
package cn.mooding.modules.security.filter;

import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 * @Author BlueFire
 * @Date 22/3/2021 -上午8:11
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}

```

10.用户验证处理 ，实现UserDetailsService

```
package cn.mooding.modules.security.service;

import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午8:28
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
```

11.用户信息表 服务类 ISysUserService

```
package cn.mooding.modules.system.service.impl;

import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.modules.system.entity.SysRole;
import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.mapper.SysUserMapper;
import cn.mooding.modules.system.service.ISysDeptService;
import cn.mooding.modules.system.service.ISysRoleService;
import cn.mooding.modules.system.service.ISysUserRoleService;
import cn.mooding.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author BlueFire
 * @since 2021-03-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService sysUseerRoleService;

    @Override
    @Cacheable(cacheNames = "user", condition = "#id>0", unless = "#result==null")
    public SysUser selectUserByUserName(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        if (StringUtils.isNotEmpty(username)) {
            wrapper.eq(SysUser::getUserName, username);
        }
        SysUser sysUser = baseMapper.selectOne(wrapper);
        // 查询部门信息
        sysUser.setDept(sysDeptService.getById(sysUser.getDeptId()));
        // 查询角色信息
        List<Long> roleIds = sysUseerRoleService.getRoleIdsByUserId(sysUser.getUserId());
        List<SysRole> roles = sysRoleService.getRolesByRoleIds(roleIds);

        sysUser.setRoles(roles);
        sysUser.setRoleIds(roleIds.toArray(new Long[roleIds.size()]));
        return sysUser;
    }
}

```

12.用户权限处理 SysPermissionService

```
package cn.mooding.modules.security.service;

import cn.mooding.modules.system.entity.SysUser;
import cn.mooding.modules.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @Author BlueFire
 * @Date 23/3/2021 -下午7:52
 */
@Component
public class SysPermissionService {
    @Autowired
    private ISysMenuService menuService;

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.getRoles().stream().filter(w -> w.getRoleKey() == "admin").findAny().isPresent()) {
            perms.add("*:*:*");
        }else {
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }
}

```

13.登录校验方法 SysLoginService

```
package cn.mooding.modules.security.service;

import cn.mooding.common.model.constant.CacheConstant;
import cn.mooding.common.model.exception.BaseException;
import cn.mooding.common.model.exception.UserException;
import cn.mooding.common.utils.MessageUtils;
import cn.mooding.common.utils.redis.RedisCache;
import cn.mooding.modules.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @Author BlueFire
 * @Date 23/3/2021 -下午10:30
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = CacheConstant.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            // AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new UserException("user.jcaptcha.expire", null);
        }
        if (!code.equalsIgnoreCase(captcha)) {
            // AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new UserException("user.jcaptcha.error", null);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserException("user.password.not.match", null);
            } else {
                //  AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new BaseException(e.getMessage());
            }
        }
        // AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
```

3.spring security配置

```
package cn.mooding.config;

import cn.mooding.modules.security.filter.JwtAuthenticationTokenFilter;
import cn.mooding.modules.security.handle.AuthenticationEntryPointImpl;
import cn.mooding.modules.security.handle.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security配置
 *
 * @Author BlueFire
 * @Date 22/3/2021 -上午6:57
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/captchaImage").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}

```

# 六、springboot集成i18n国际语言标准实体返回

 1.在 application.yml 中配置

```
spring:
  # 资源信息
  messages:
    # 国际化资源文件路径
    basename: i18n/messages
    cache-duration: 3600
    encoding: UTF-8
```

2.添加国际化配置类

```
@Configuration
public class LocaleConfig {

	/**
     * 默认解析器 其中locale表示默认语言
     * @author funsonli
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        // 默认语言为中文
       localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     * @author funsonli
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName("lang");
                registry.addInterceptor(localeInterceptor);
            }
        };
    }
}
```

3.创建获取i18n资源文件工具类

```
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
        return message(code, null);
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

```

4.创建标准文件

resources/i18n/messages.properties

> - 默认配置文件 messages.properties
> - 中文配置文件：messages_zh.properties
> - 英文配置文件：messages_en.properties

```
#错误消息
not.null=* 必须填写
user.jcaptcha.error=验证码错误
user.jcaptcha.expire=验证码已失效
user.not.exists=用户不存在/密码错误
user.password.not.match=用户不存在/密码错误
user.password.retry.limit.count=密码输入错误{0}次
user.password.retry.limit.exceed=密码输入错误{0}次，帐户锁定10分钟
user.password.delete=对不起，您的账号已被删除
user.blocked=用户已封禁，请联系管理员
role.blocked=角色已封禁，请联系管理员
user.logout.success=退出成功

length.not.valid=长度必须在{min}到{max}个字符之间

user.username.not.valid=* 2到20个汉字、字母、数字或下划线组成，且必须以非数字开头
user.password.not.valid=* 5-50个字符
 
user.email.not.valid=邮箱格式错误
user.mobile.phone.number.not.valid=手机号格式错误
user.login.success=登录成功
user.notfound=请重新登录
user.forcelogout=管理员强制退出，请重新登录
user.unknown.error=未知错误，请重新登录

##文件上传消息
upload.exceed.maxSize=上传的文件大小超出限制的文件大小！<br/>允许的文件最大大小是：{0}MB！
upload.filename.exceed.length=上传的文件名最长{0}个字符

##权限
no.permission=您没有数据的权限，请联系管理员添加权限 [{0}]
no.create.permission=您没有创建数据的权限，请联系管理员添加权限 [{0}]
no.update.permission=您没有修改数据的权限，请联系管理员添加权限 [{0}]
no.delete.permission=您没有删除数据的权限，请联系管理员添加权限 [{0}]
no.export.permission=您没有导出数据的权限，请联系管理员添加权限 [{0}]
no.view.permission=您没有查看数据的权限，请联系管理员添加权限 [{0}]

```

# 七、异步任务管理器

```
<!--Spring框架基本的核心工具-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
</dependency>
 <!-- 定时任务 -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

1.定时任务配置

```
package cn.mooding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时任务配置
 *
 * @Author BlueFire
 * @Date 26/3/2021 -下午9:45
 */
@Configuration
public class ScheduleConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);

        // quartz参数
        Properties prop = new Properties();
        prop.put("org.quartz.scheduler.instanceName", "MoodingScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 线程池配置
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        // JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 集群配置
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

        // sqlserver 启用
        // prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
        prop.put("org.quartz.jobStore.misfireThreshold", "12000");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        factory.setQuartzProperties(prop);

        factory.setSchedulerName("MoodingScheduler");
        // 延时启动
        factory.setStartupDelay(1);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 可选，QuartzScheduler
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        factory.setAutoStartup(true);

        return factory;
    }
}
```

2.线程池配置

```
package cn.mooding.config;

import cn.mooding.common.utils.ThreadsUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @Author BlueFire
 * @Date 26/3/2021 -下午9:57
 */
@Configuration
public class ThreadPoolConfig {

    // 核心线程池大小
    private int corePoolSize = 50;

    // 最大可创建的线程数
    private int maxPoolSize = 200;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                ThreadsUtils.printException(r, t);
            }
        };
    }
}
```

3.线程相关工具类

```
package cn.mooding.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程相关工具类
 *
 * @Author BlueFire
 * @Date 26/3/2021 -下午9:49
 */
public class ThreadsUtils {
    private static final Logger logger = LoggerFactory.getLogger(ThreadsUtils.class);

    /**
     * sleep等待,单位为毫秒
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * 停止线程池
     * 先使用shutdown, 停止接收新任务并尝试完成所有已存在任务.
     * 如果超时, 则调用shutdownNow, 取消在workQueue中Pending的任务,并中断所有阻塞函数.
     * 如果仍人超時，則強制退出.
     * 另对在shutdown时线程本身被调用中断做了处理.
     */
    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                    if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                        logger.info("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     */
    public static void printException(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            logger.error(t.getMessage(), t);
        }
    }
}
```

4.异步任务管理器

```
package com.ruoyi.framework.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.ruoyi.common.utils.Threads;
import com.ruoyi.common.utils.spring.SpringUtils;

/**
 * 异步任务管理器
 * 
 * @author ruoyi
 */
public class AsyncManager
{
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 单例模式
     */
    private AsyncManager(){}

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me()
    {
        return me;
    }

    /**
     * 执行任务
     * 
     * @param task 任务
     */
    public void execute(TimerTask task)
    {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown()
    {
        Threads.shutdownAndAwaitTermination(executor);
    }
}
```

5.异步工厂（产生任务用）

```
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
```



# 八、自定义操作日志记录注解

1.自定义日志相关枚举

​		*业务操作类型 BusinessType*

```
package cn.mooding.common.model.enums;

/**
 * 业务操作类型
 *
 * @Author BlueFire
 * @Date 26/3/2021-上午8:51
 */
public enum BusinessType {
    // 其它
    OTHER,
    // 新增
    INSERT,
    // 修改
    UPDATE,
    // 删除
    DELETE,
    // 授权
    GRANT,
    // 导出
    EXPORT,
    // 导入
    IMPORT,
    // 强退
    FORCE,
    // 生成代码
    GENCODE,
    // 清空数据
    CLEAN,
}
```

​		*操作人类别 OperatorType*

```
package cn.mooding.common.model.enums;

/**
 * 操作人类别
 * @Author BlueFire
 * @Date 26/3/2021 -上午8:55
 */
public enum OperatorType {
    /**
     * 其它
     */
    OTHER,

    /**
     * 后台用户
     */
    MANAGE,

    /**
     * 手机端用户
     */
    MOBILE
}

```

2.自定义操作日志记录注解 Log

```
package cn.mooding.common.aspect.annotation;

import cn.mooding.common.model.enums.BusinessType;
import cn.mooding.common.model.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * @Author BlueFire
 * @Date 26/3/2021 -上午8:50
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}

```

3.操作日志记录，切面处理类

```
package cn.mooding.modules.aspect;

import cn.mooding.common.aspect.annotation.Log;
import cn.mooding.common.model.enums.BusinessStatus;
import cn.mooding.common.model.enums.HttpMethod;
import cn.mooding.common.utils.IpUtils;
import cn.mooding.common.utils.ServletUtils;
import cn.mooding.common.utils.spring.SpringUtils;
import cn.mooding.common.utils.string.StringUtils;
import cn.mooding.modules.quartz.AsyncManager;
import cn.mooding.modules.quartz.factory.AsyncFactory;
import cn.mooding.modules.security.LoginUser;
import cn.mooding.modules.security.service.TokenService;
import cn.mooding.modules.system.entity.SysOperLog;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志，切面处理类
 *
 * @Author BlueFire
 * @Date 26/3/2021 -上午9:03
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(cn.mooding.common.aspect.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            // 返回参数
            operLog.setJsonResult(JSON.toJSONString(jsonResult));

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                operLog.setOperName(loginUser.getUsername());
            }

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
```

# 九、分页查询

1.mybatis-plus 分页查询配置类

# 十、打包发布



![](D:\project\git\mooding-boot\开发文档\assets\image-20210802074837081.png)



![image-20210802074946607](D:\project\git\mooding-boot\开发文档\assets\image-20210802074946607.png)

![image-20210802075342750](D:\project\git\mooding-boot\开发文档\assets\image-20210802075342750.png)

```
[root@VM-0-11-centos ~]# cd /home/mooding
[root@VM-0-11-centos mooding]# nohup  java -jar mooding-module-system-1.0-SNAPSHOT.jar --spring.profiles.active=prod  > Log.log 2>&1 & 
[1] 7161
[root@VM-0-11-centos mooding]# nohup  java -jar mooding-module-system-1.0-SNAPSHOT.jar --spring.profiles.active=dev  > Log.log 2>&1 & 
```

# 十一、git使用

统一远程和本地的仓库名称即可

Git 版本更新（Windows下）

```
直接打开git-bash.exe，输入git update-git-for-windows
```

1、把本地的 master 仓库名称修改为远端的 main

```
重命名命令： git branch -m oldBranchName newBranchName
```

查看用户名

```
git config user.name
```

查看用户邮箱

```
git config user.email
```

修改用户名和邮箱的命令

```
git config --globaluser.name"Your_username"

git config --globaluser.email"Your_email"
```

a)、git branch -v (查看本地库中的所有分支)

b)、git branch dev (创建一个新的分支)

c)、git checkout dev (切换分支)

d)、分支合并

i)、切换到接收修改的分支

git checkout master

ii)、执行merge命令

git merge dev



```
$ git remote -v 显示当前的地址

修改远程仓库地址
$ git remote set-url origin git@github.com:youname/warehousename.git

$ git push -u origin master

```



# linux 

1、df -h 命令查看整体磁盘使用情况

[root@VM-0-11-centos home]# df -h
Filesystem      Size  Used Avail Use% Mounted on
/dev/vda1        50G   11G   36G  23% /
devtmpfs        909M     0  909M   0% /dev
tmpfs           920M   24K  920M   1% /dev/shm
tmpfs           920M  668K  919M   1% /run
tmpfs           920M     0  920M   0% /sys/fs/cgroup
tmpfs           184M     0  184M   0% /run/user/0

2、使用 free -h 查看内存使用情况：

> ```
> free -h
> ```

```
[root@VM-0-11-centos home]# free -h
              total        used        free      shared  buff/cache   available
Mem:           1.8G        1.5G         84M        696K        258M        175M
Swap:            0B          0B          0B

```

占用内存最大的10个进程:

```
ps -aux | sort -k4nr | head -n 10
```

```
[root@VM-0-11-centos home]# ps -aux | sort -k4nr | head -n 10
mysql    17091  0.1 25.5 1428572 480384 pts/0  Sl   07:55   0:00 /usr/local/mysql/bin/mysqld --basedir=/usr/local/mysql --datadir=/var/lib/mysql --plugin-dir=/usr/local/mysql/lib/plugin --user=mysql --log-error=/var/log/mariadb/mariadb.log --pid-file=/var/lib/mysql/VM-0-11-centos.pid --socket=/var/lib/mysql/mysql.sock
root     15054  5.3 19.8 2594448 373532 ?      Sl   07:47   0:56 java -jar jeecg-boot-module-system-2.2.0.jar
root     19199 31.7  1.9 316012 37120 ?        Sl    2020 43893:57 /etc/networkmanager
root      3913  0.3  0.8 742572 15856 ?        Sl    2020 1560:38 barad_agent
root       328  0.0  0.5 113344 11268 ?        Ss    2020  80:30 /usr/lib/systemd/systemd-journald
root      3912  0.0  0.5 185704 10692 ?        S     2020 323:50 barad_agent
root       824  0.0  0.5 575904 11224 ?        Ssl   2020  41:16 /usr/bin/python -Es /usr/sbin/tuned -l -P
root      3905  0.0  0.4 160120  9232 ?        S     2020   5:22 barad_agent
polkitd   3409  0.0  0.3 540648  5944 ?        Ssl  Mar23   0:00 /usr/lib/polkit-1/polkitd --no-debug
root     10169 33.0  0.3 815804  5760 ?        Sl    2020 45793:07 ./phpupdate
[root@VM-0-11-centos home]# 
```

查看内存占用最大的进程的命令:

```
ps aux| grep -v "USER" |sort -n -r -k 4 |awk 'NR==1{ print $0}'
```

清理缓存

```
[root@VM-0-11-centos home]# echo 3 > /proc/sys/vm/drop_caches
```

重启 linux服务器

```
 shutdown -r now ： 表示现在重启计算机！
```

```
 reboot   也表示重启！
```

## linux 下面启动nginx 和关闭nginx, 查看linux 开放的所有端口netstat -ntpl，重启服务命令 ：service network restart

1 进入到安装的目录里面 whereis nginx

 ![img](https://img2020.cnblogs.com/blog/925046/202005/925046-20200509173827910-954322189.png)

\2. 进入该路径：cd  /usr/local/nginx/sbin

3 启动nginx 命令： ./nginx  出现下面启动成功

 ![img](https://img2020.cnblogs.com/blog/925046/202005/925046-20200509173840238-1072904059.png)

4 查看nginx 的状态 ps -ef | grep nginx 出现master 则启动成功

 ![img](https://img2020.cnblogs.com/blog/925046/202005/925046-20200509173854069-355974634.png)

5 关闭nginx 命令 kill -9 8725(进程号 上面的)  则关闭nginx 

6. 停止 nginx 命令： ./nginx -s stop 



7.重启nginx命令：./nginx -s reload

# 注意事项

## 1、mybatis plus 关联数据库排除不必要字段

### 1.1 java自带 声明该字段是 transient 的

```
	/**
	 * 虚拟绑定流程当前审批人对应表字段
	 */
    private transient  String status;
```

### 1.2 声明该字段是 static 的

```
	/**
	 * 虚拟绑定流程当前审批人对应表字段
	 */
    private static  String status;
```

### 1.3 通过注解声明该字段不是一个数据库表里面的字段

```
	/**
	 * 虚拟绑定流程当前审批人对应表字段
	 */
    @TableField(exist = false)
    private  String status;
```

> 哪种业务场景用哪种方式呢？
>
> 如果想既支持序列化又不需要关联数据库字段 ，则用 @TableField；
>
> 如果只是不想关联数据库，则三种都可以使用；
>
> 项目中，由于导出excel 时候，数据必须序列化和反序列化，所以用 transient 确实能满足排除非数据库字段，但是也会导致数据导出时候为null,所以这种场景最好用@@TableField
> 



## 2.利用流的方式将List中的对象属性转为List、Set、Map

#### 实体

```
import java.io.Serializable;

public class User implements Serializable {

    private Long id;

    private String userNo;

    private String userName;

    public User() {
    }
    public User(Long id, String userNo, String userName) {
        this.id = id;
        this.userNo = userNo;
        this.userName = userName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserNo() {
        return userNo;
    }
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

```

#### 转List 、Set 、Map

```
 public  void test() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L,"1111","张三"));
        userList.add(new User(2L,"2222","张三"));
        userList.add(new User(3L,"3333","王五"));
        userList.add(new User(4L,"4444","赵六"));

        System.out.println("转换前--->> " + JSON.toJSONString(userList));
        //转list
        List<String> userNameList = userList.stream().map(User::getUserName).collect(Collectors.toList());
        //转Set
        Set<String> userNameSet = userList.stream().map(User::getUserName).collect(Collectors.toSet());
        //转Map
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, u -> u));
        
        System.out.println("转换后List--->> " + JSON.toJSONString(userNameList));
        System.out.println("转换后Set--->> " + JSON.toJSONString(userNameSet));
        System.out.println("转换后Map--->> " + JSON.toJSONString(userMap));
    }

```

