package com.loloao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.loloao.mapper")
public class LoloaoBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoloaoBlogApplication.class, args);
    }
}
