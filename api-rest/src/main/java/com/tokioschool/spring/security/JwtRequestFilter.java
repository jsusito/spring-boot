package com.tokioschool.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
	//Cada vez que se lanza una peticion spring hace que pase por aqui
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Cada vez que interceptamos una peticion preguntamos por este atributo que es el que tiene el token
		final String requestToken = request.getHeader("Authorization");
		
		String username = null;
		String jwtToken = null;
		
		//Por convencion los token JSON vienen asi "Bearer " y la clave cifrada
		if(requestToken != null && requestToken.startsWith("Bearer ")) {
			jwtToken = requestToken.substring(7);
			try {
				username = jwtTokenUtil.getUsername(jwtToken);
			}catch(IllegalArgumentException iae) {
				logger.error("No se pudo obtener el token");
			}catch(ExpiredJwtException ejw) {
				logger.error("token expirado");
			}
		} 
		else {
			logger.warn("Token sin Bearin delante");                                                                  
		}
		
		//Si hay usuario 
		if(username !=null) {
			//Comprobamos que el usuario este en la base de datos.
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			//Que el usuario sea el mismo que el token en la base de datos
			if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				//metemos informacion de peticion de la peticion(request)
				UsernamePasswordAuthenticationToken userPassAuthentToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null , userDetails.getAuthorities());
				userPassAuthentToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//metemos toda esta informacion en el contexto de seguridad para que pueda entrar y no validarse otra vez pero en el segundo filtro
				//El filtro que va despues de RequestFilter, filtro de spring Security
				SecurityContextHolder.getContext().setAuthentication(userPassAuthentToken);
			}
		}
		//
		//Si está autenticado lo dejara pasar,  y si no no lo dejara pasar a no ser que valla 
		//al /login, y ya le da paso al filtro de SpringSecurity.
		filterChain.doFilter(request, response);
	}
	
}

