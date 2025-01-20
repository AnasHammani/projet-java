package com.aliboucoding.jpa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name="Anas",
                        email="hammani.anas10@gmail.com"
                ),
                description = "Open API documentation for Spring Security",
                title = "OpenApi specifications - Anas",
                version = "1.0",
                license = @License(
                        name="Licence Name",
                        url="https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "exterieur Environment (google link)",
                        url = "https://www.google.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name="bearerAuth"
                )
        }
)

@SecurityScheme(
        name="bearerAuth",
        description = "JWT token auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)

public class OpenApiConfig {


}
