package br.com.emanuelgabriel.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.webmvc.ui.SwaggerConfig;


@OpenAPIDefinition(
        info = @Info(description = "API de gerenciamento e controle de Categorias",
                termsOfService = "Termos de serviço",
                title = "API Categorias",
                version = "1.0.0",
                contact = @Contact(name = "Emanuel Gabriel Sousa", email = "emanuel.gabriel.sousa@protonmail.com", url = "https://www.linkedin.com/in/emanuel-gabriel-sousa/")))
public class ConfigSwagger extends SwaggerConfig {
}
