package com.entor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.entor.*.mapper")
public class VisualUserManagementSystemApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(VisualUserManagementSystemApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
