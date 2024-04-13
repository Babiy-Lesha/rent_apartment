package org.example.api_getway_aprtment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGetwayAprtmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGetwayAprtmentApplication.class, args);
    }

}
