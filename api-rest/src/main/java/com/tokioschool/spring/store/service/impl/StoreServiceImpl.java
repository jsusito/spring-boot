package com.tokioschool.spring.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.spring.store.config.StoreConfigProperties;
import com.tokioschool.spring.store.domain.ResourceDescription;
import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;
import com.tokioschool.spring.store.domain.dto.ResourceIdDTO;
import com.tokioschool.spring.store.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {

	private final StoreConfigProperties storeConfigProperties;
	private final ObjectMapper objectMapper;
	
	@Override
	public Optional<ResourceIdDTO> save(MultipartFile mp, String description) {
		
		ResourceIdDTO resourceIdDTO = ResourceIdDTO.builder().resourceId(UUID.randomUUID()).build();
		
		ResourceDescription resourceDescription = ResourceDescription.builder()
				.contentType(mp.getContentType())
				.description(description)
				.name(mp.getName())
				.size((int) mp.getSize())
				.build();
		
		Path pathContent = storeConfigProperties.getPath(resourceIdDTO.getResourceId().toString());
		Path pathDescription = storeConfigProperties.getPath(resourceIdDTO.getResourceId().toString() + ".json");
		
		try {
			objectMapper.writeValue(pathDescription.toFile(), resourceDescription);
		} catch (IOException e) {
			log.error("Exceptions in saveResource");
			return Optional.empty();
		}
		
		try {
			Files.write(pathContent,mp.getBytes());
		} catch (IOException e) {
			log.error("Error in save Resource");
			return Optional.empty();
		}
		
		return Optional.of(resourceIdDTO);
	}

	@Override
	public Optional<ImageResourceDTO> find(UUID resourceId) {

		Path pathFromContent = storeConfigProperties.getPath(resourceId.toString()); 
		Path pathFromDescription = storeConfigProperties.getPath( resourceId + ".json");
		
		if (!Files.exists(pathFromContent)) 
		{ 
			return Optional.empty(); 
		}
		
		byte[] bytes; 
		
		try {
			bytes = Files.readAllBytes(pathFromContent); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		ResourceDescription resourceDescription; 
		try {
			resourceDescription = objectMapper.readValue(pathFromDescription.toFile(), ResourceDescription.class);
		} catch (IOException e) {
			log.error("Exception in findResource", e);
			return Optional. empty(); 
		}
		
		return Optional.of( 
				ImageResourceDTO.builder()
				.resourceId(resourceId)
				.content(bytes)
				.description(resourceDescription.getDescription())
				.contentType(resourceDescription.getContentType())
				.size(resourceDescription.getSize())
				.name(resourceDescription.getName())
				.build());
	}

	@Override
	public void delete(UUID resourceId) {
		
		Path pathFromContent = storeConfigProperties.getPath(resourceId.toString()); 
		Path pathFromDescription = storeConfigProperties.getPath(resourceId + ".json");
		
		try { 
			Files.deleteIfExists(pathFromContent); 
			Files.deleteIfExists(pathFromDescription);
		} catch (IOException e)	{ 
			log.error("“Exception in deleteResource", e);
		}
	}

	

}
