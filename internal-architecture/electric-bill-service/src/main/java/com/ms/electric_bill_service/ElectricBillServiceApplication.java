package com.ms.electric_bill_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableFeignClients
@SpringBootApplication
public class ElectricBillServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectricBillServiceApplication.class, args);
    }

}
