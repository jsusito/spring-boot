package com.tokioschool.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tokioschool.spring.security.JwtRequest;
import com.tokioschool.spring.security.JwtResponse;
import com.tokioschool.spring.security.JwtTokenUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControllerUser {
	
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	
	@Tag(name="Login", description ="Authentication (get the Bearer)")
	@PostMapping(value="/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) throws Exception{
		
		//comprueba que el usuario y contraseña existan, llama internamente a FilterSecurity y a userDetails.
		authenticate(authRequest.getUsername(), authRequest.getPassword());
		
		//El token se genera según el "secreto" y con el nombre del Usuario
		final String token = jwtTokenUtil.generateToken(authRequest.getUsername());
		//Devolvemos el token en el cuerpo de la respuesta
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException de) {
			throw new Exception("User disable", de);
		}catch(BadCredentialsException bce) {
			throw new Exception("Invalid credentials", bce);
		}
	}
}
