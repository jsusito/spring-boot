package com.tokioschool.spring.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor() 
public class Response {

	
	//Creamos errore personalizados de la aplicacion
	public static final int NO_ERROR = 0;
	public static final int NO_FOUND = 101;
	public static final int NO_CONTENT = 201;
	public static final int OBJECT_EXISTS = 404;
	public static final String NO_MESSAGE = "";
	
	
	
	//Esta clase interna es la encargada de crear el cuerpo del objeto JSON
	private int error;
	private String message;
}
