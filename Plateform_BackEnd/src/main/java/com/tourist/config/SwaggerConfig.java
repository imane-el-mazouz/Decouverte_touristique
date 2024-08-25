package com.tourist.config;


import com.support_App.model.Admin;
import com.support_App.model.Technician;
import com.support_App.model.UserU;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(apiInfo());
    }
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSchemas("Admin", new Schema<Admin>().implementation(Admin.class))
//                        .addSchemas("Technician", new Schema<Technician>().implementation(Technician.class))
//                        .addSchemas("UserU", new Schema<UserU>().implementation(UserU.class)))
//                .info(new Info().title("API Documentation").version("1.0"));
//    }


    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    private Info apiInfo() {
        return new Info()
                .title("Authentication Service Api Doc")
                .version("1.0.0")
                .description("HTTP APIs to manage user registration and authentication.")
                .contact(new Contact().name("IMANE EL MAZOUZ"));
    }
}

