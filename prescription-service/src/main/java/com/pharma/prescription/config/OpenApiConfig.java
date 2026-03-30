package com.pharma.prescription.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI pharmaStreamOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pharma-Stream: Prescription Service")
                        .description("Digital R&D API for Clinical Prescription Management and Validation")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Naveen Komma")
                                .email("naveen.komma@example.com")));
    }
}