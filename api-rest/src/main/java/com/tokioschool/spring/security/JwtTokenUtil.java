package com.tokioschool.spring.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@Component
public class JwtTokenUtil {
	//Tiempo de validez del token en segundos = 5 horas
	public static final long JWT_TOKEN_VALIDIDTY = 5 * 60 * 60;
	
	//Cojemos de la configuracion una clave que es la que vamos a coger para encriptar nuestro token
	@Value("${jwt.secret}")
	private String secret;
	
	
	/**
	 * Genera el token para un usuario
	 * UserDetails : Datos del Usuario
	 *  
	 * 
	 * */
	
	public String generateToken(String userName) {
		//claims guardamos el usuario, el token, el tiempo cuando expira .... 
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userName);
	}
	
	//Generamos el token
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		Date currentDate = new Date(System.currentTimeMillis());
		Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDIDTY * 1000);
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(currentDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	
	
	//Accedemos a la informacion del token
	//Fecha de expiracion del token
	public Date getExpirationDate(String token) {
		return getClainFromToken(token, Claims::getExpiration);
	}
	
	//Obtenemos el usuario del token
	public String getUsername(String token) {
		return getClainFromToken(token, Claims::getSubject);
	}
	
	//Verificamos la validez del token
	public boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(expiration);
	}
	
	
	/*
	 * Comprueba si el token corresponde al usuario correcto
	 * y si la fecha no ha expirado. Sino falso en caso contrario.
	 * */
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		return username.equals(userDetails.getUsername()) && (!isTokenExpired(token));
		
	}

	private <T> T getClainFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	//Metodo que nos devuelve un objeto que implementa la interfaz Claims
		//funciona parecido a un objeto tipo Map, Pasandole el token nos da todos los valores y propiedades del token
		private Claims getAllClaims(String token) {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}
	
	
		
}
