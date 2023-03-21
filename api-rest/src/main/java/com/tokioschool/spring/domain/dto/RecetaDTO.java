package com.tokioschool.spring.domain.dto;

import org.modelmapper.ModelMapper;

import com.tokioschool.spring.domain.Receta;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class RecetaDTO {
    
	@Hidden
	long id;
	
	String name;
	
	String imagen;
	
	String ingredients;
	
	float price;
	
	String descripcion;
	
		
	public static RecetaDTO convertDTO(Receta receta) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(receta, RecetaDTO.class);
		
	}
}
