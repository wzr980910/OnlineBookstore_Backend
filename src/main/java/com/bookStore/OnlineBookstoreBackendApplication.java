package com.bookStore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.bookStore.mapper")
public class OnlineBookstoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookstoreBackendApplication.class, args);
    }

}
