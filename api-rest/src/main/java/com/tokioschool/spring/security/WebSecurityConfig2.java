package com.tokioschool.spring.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;



//@Configuration
//@EnableWebSecurity
/**
 * @author jsusi
 * Clase sin uso solo como apuntes del procedimiento en las versiones anteriores de spring.
 */
@RequiredArgsConstructor
public class WebSecurityConfig2 extends WebSecurityConfigurerAdapter {
	
	//Esta clase se encarga de mostrar el error
	final private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	final private JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	//Spring se autentifica automaticamente pero tenemos que indicarla que puede utilizar el paquete.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests().antMatchers("/login"
					, "/swagger-ui-custom.html"
					, "/swagger-ui/**"
					, "/swagger-resources",
					"/configuration/security"
					, "/configuration/ui"
					, "/api-docs/**"
					, "/recetas/**"
					, "/testExchangeAuthentication")
			.permitAll()
			.anyRequest().authenticated().and()
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //mensaje de error
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //conexion sin estado
		
		//Que antes de ejecutarse el filtro de spring para todo el logado ejecute el nuestro el jwtRequestFilter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		
	}
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
								.bearerFormat("JWT")
						)
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
