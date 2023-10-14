package com.wsj.apiconsumerapp;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 入口类
 *
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.wsj.apiconsumerapp.mapper")
public class ApiConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiConsumerApp.class, args);
    }

}
