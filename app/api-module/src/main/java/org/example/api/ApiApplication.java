package org.example.api;

import org.example.auth.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@Import({SecurityConfig.class})
@SpringBootApplication(scanBasePackages = {
        "org.example.auth",
        "org.example.api"
})
@EnableAsync
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
