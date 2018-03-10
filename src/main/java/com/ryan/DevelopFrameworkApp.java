package com.ryan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class DevelopFrameworkApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new DevelopFrameworkApp()
                .configure(new SpringApplicationBuilder(DevelopFrameworkApp.class))
                .run(args);
    }
}

