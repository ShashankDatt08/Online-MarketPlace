package com.marketplace.onlinemarketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Online Marketplace API",
//                version = "1.0",
//                description = "API documentation for Online Marketplace"
//        )
//)
public class OnlineMarketPlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketPlaceApplication.class, args);
    }

}
