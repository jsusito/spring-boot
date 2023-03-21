package com.tokioschool.spring.store.domain;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized

public class ResourceDescription {
	
	
	private String name;
	
	private String contentType;
	
	private int size;
	
	private String description;
	
		
}
