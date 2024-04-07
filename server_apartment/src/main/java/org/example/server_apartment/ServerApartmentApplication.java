package org.example.server_apartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerApartmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApartmentApplication.class, args);
    }

}
