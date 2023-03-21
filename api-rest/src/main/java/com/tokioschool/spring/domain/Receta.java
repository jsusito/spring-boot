package com.tokioschool.spring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recetas")
public class Receta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String name;
	
	String imagen;
	
	String ingredients;
	
	float price;
	
	String descripcion;
	
	
}
