package com.tokioschool.spring.store.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;
import com.tokioschool.spring.store.domain.dto.ResourceIdDTO;

public interface StoreService {

	Optional<ImageResourceDTO> find(UUID ResourceId);
	
	void delete(UUID ResourceId);

	Optional<ResourceIdDTO> save(MultipartFile mp, String description);
	
	
}
