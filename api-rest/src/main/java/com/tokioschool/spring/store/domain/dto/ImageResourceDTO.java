package com.tokioschool.spring.store.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ImageResourceDTO {
	
	
	private UUID resourceId;
	
	private String name;
	
	private String contentType;
	
	private int size;
	
	private String description;
	
	private byte[] content;
	
		
}
