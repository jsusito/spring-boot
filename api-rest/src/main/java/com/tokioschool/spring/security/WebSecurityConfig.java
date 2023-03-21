package com.tokioschool.spring.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity //Activamos para que active la configuracion del Bean SecurityFilterChain
@RequiredArgsConstructor
public class WebSecurityConfig  {
	
	//Esta clase se encarga de mostrar el error
	final private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	final private JwtRequestFilter jwtRequestFilter;
	
	@Bean 
	public SecurityFilterChain configureFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity.csrf().disable()
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
				.anyRequest().authenticated()
				.and()
					.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //mensaje de error
				.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //conexion sin estado
			.and()
				//Que antes de ejecutarse el filtro de spring para todo el logado ejecute el nuestro el jwtRequestFilter
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
		}
	
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
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return authentication;
			}
		};
	}
}
