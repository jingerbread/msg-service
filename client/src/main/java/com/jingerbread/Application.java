package com.jingerbread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@ComponentScan( basePackages = { "com.jingerbread.services" } )
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    @Bean
    @Primary
    @Autowired
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String dbUrl,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password,
                                 @Value("${spring.datasource.driver-class-name}") String driverClass) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName(driverClass);

        return dataSourceBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.setWebEnvironment(false);
        springApplication.run(Application.class, args);
        System.out.println("******* Client application started *******");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            log.warn("Finished");
        }
    }
}
