package org.example.rent_apartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RentApartmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApartmentApplication.class, args);
    }

}
