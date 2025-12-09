package com.itbaifantuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.itbaifantuan")
public class PolarisWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolarisWebManagementApplication.class, args);
    }
}
