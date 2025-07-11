package org.example.user_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .version("1.0.0")
                        .description("REST API для управления пользователями с поддержкой HATEOAS. ")
                        .contact(new Contact()
                                .name("Максим")
                                .email("mail@mail.ru")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server")
                ));
    }
} 