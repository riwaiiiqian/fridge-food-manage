package com.example.fridge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.fridge.mapper")
public class FridgeFoodManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridgeFoodManageApplication.class, args);
    }
}
