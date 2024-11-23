package com.marketplace.onlinemarketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@OpenAPIDefinition(
//        info = @Info(
//                title = "Online Marketplace API",
//                version = "1.0",
//                description = "API documentation for Online Marketplace"
//        )
//)
@EnableMongoRepositories(basePackages = "com.marketplace.onlinemarketplace.mongoRepo")
@EnableJpaRepositories(basePackages = "com.marketplace.onlinemarketplace.repository")
public class OnlineMarketPlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketPlaceApplication.class, args);
    }

}
