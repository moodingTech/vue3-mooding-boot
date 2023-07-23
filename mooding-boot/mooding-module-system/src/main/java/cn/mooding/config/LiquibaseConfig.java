package cn.mooding.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Liquibase 的配置文件
 *
 * @Author BlueFire
 * @Date 2/12/2021 -下午8:16
 */
@Configuration
public class LiquibaseConfig {

    @Value("${liquibase.changelog}")
    private String changelog;

    @Value("${liquibase.enabled}")
    private boolean enabled;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        // 指定changelog的位置，这里使用的一个master文件引用其他文件的方式
//        主变更日志配置路径。默认为classpath:/db/changelog/db.changelog-master.yaml，
        liquibase.setChangeLog(changelog);
        liquibase.setShouldRun(enabled);
//        要使用的运行时上下文的逗号分隔列表。
        liquibase.setContexts("development,test,production");
        return liquibase;
    }
}
