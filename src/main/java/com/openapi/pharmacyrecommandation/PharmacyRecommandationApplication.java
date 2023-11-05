package com.openapi.pharmacyrecommandation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PharmacyRecommandationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyRecommandationApplication.class, args);
    }

}
