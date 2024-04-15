package com.example.usercontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserControlApplication.class, args);
    }


}
