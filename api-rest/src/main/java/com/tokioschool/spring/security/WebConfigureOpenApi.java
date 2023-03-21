package com.tokioschool.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class WebConfigureOpenApi {

	@Bean
	public OpenAPI customOpenApi() {
		//Configura la opcion de autorizacion para el portal generado con la documentacion
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components()
					.addSecuritySchemes("bearerAuth", new SecurityScheme()
						.name("Bearer Token")
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT"))
					)
				.info(new Info().title("Filmo Tokio")
				.description("Api Rest")
				.contact(new Contact()
					.name("Reviews`s Films")
					.email("infoTokio@tokioShool.com")
					.url("https://tokioSchool.com"))
				.version("1.0"));
			}
}
