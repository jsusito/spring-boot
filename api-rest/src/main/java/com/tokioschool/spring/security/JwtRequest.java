package com.tokioschool.spring.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Se encarga de llevar el usuario y la clave de camino a /login

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
	private String username;
	private String password;
}
