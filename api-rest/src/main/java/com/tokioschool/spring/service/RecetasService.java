package com.tokioschool.spring.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.spring.domain.Receta;
import com.tokioschool.spring.domain.dto.RecetaDTO;
import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;
import com.tokioschool.spring.store.domain.dto.ResourceIdDTO;

public interface RecetasService {
	
	RecetaDTO getReceta(long id);
	
	Set<RecetaDTO> getRecetas();
	
	long save(Receta receta);
	
	Optional<ResourceIdDTO> saveImage(MultipartFile multiPartFile, String descripcion);
	
	int updateImage(long id, String UUID);
	
	ImageResourceDTO getImage(String resourceId);
}
