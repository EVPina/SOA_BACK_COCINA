package com.soa.soacocina.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Don Belisario - API Servicio de Cocina")
                        .version("1.0.0")
                        .description("API REST para la gestión de órdenes de producción en la cocina del restaurante Don Belisario")
                        .contact(new Contact()
                                .name("Don Belisario Team")
                                .email("soporte@donbelisario.com")
                                .url("https://donbelisario.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Servidor Local"),
                        new Server().url("https://api.donbelisario.com/cocina").description("Servidor Producción")
                ));
    }
}