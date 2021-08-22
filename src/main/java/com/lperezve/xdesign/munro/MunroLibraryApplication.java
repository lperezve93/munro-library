package com.lperezve.xdesign.munro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MunroLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MunroLibraryApplication.class, args);
    }
}
