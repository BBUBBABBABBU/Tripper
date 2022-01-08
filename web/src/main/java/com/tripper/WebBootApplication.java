package com.tripper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.tripper"} )
public class WebBootApplication {

    private static final String APPLICATION_PROPERTIES = "spring.config.location="
                                                        + "classpath:/application.yml,"
                                                        + "classpath:/private.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebBootApplication.class).properties(APPLICATION_PROPERTIES).run(args);
//    SpringApplication.run(WebBootApplication.class, args);
    }
}