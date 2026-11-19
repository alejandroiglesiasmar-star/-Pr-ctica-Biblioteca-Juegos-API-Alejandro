package com.aagpazos.bibliotecavideojuegospractica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gameVaultOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GameVault API")
                        .version("1.0")
                        .description("API REST de la biblioteca de videojuegos GameVault")
                        .contact(new Contact()
                                .name("Alejandro Iglesias Martín")
                                .email("alejandroiglesiasmar@gmail.com")));
    }
}