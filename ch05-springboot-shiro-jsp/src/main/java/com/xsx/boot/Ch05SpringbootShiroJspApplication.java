package com.xsx.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = "com.xsx.boot.mapper")
public class Ch05SpringbootShiroJspApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch05SpringbootShiroJspApplication.class, args);
    }

}
