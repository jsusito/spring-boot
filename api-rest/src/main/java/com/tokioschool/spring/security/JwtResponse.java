package com.tokioschool.spring.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Se encarga de llevar el token en Formato JSON

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
}
