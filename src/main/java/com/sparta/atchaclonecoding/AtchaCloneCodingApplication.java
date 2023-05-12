package com.sparta.atchaclonecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AtchaCloneCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtchaCloneCodingApplication.class, args);
    }

}
