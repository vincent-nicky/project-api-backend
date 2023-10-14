package com.wsj.apiprovidermysql;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wsj.apiprovidermysql.mapper")
@EnableDubbo
public class ApiProviderMysqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiProviderMysqlApplication.class, args);
    }
}
