package com.studysync;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main Spring Boot Application for StudySync API.
 * Exposes REST endpoints for user management, study groups, and sessions.
 */
@SpringBootApplication
public class StudySyncApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudySyncApplication.class, args);
    }

    /**
     * Configure OpenAPI/Swagger documentation.
     *
     * @return OpenAPI configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("StudySync API")
                .version("1.0.0")
                .description("REST API for StudySync - A collaborative study group management system")
                .contact(new Contact()
                    .name("StudySync Team")
                    .email("support@studysync.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
