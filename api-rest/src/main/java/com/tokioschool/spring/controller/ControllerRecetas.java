package com.tokioschool.spring.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.spring.domain.Receta;
import com.tokioschool.spring.domain.dto.RecetaDTO;
import com.tokioschool.spring.domain.repository.RecetasDAO;
import com.tokioschool.spring.service.RecetasService;
import com.tokioschool.spring.store.domain.dto.ImageResourceDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor

@CrossOrigin
@RequestMapping("/recetas")
public class ControllerRecetas {
	
	private final RecetasService recetaService;
	private final RecetasDAO recetasDAO;
	
	@GetMapping({"recetas", "recetas/" })
	public ResponseEntity<Set<RecetaDTO>> getRecetas(){
		return ResponseEntity.ok(recetaService.getRecetas());
	}
	
	@PostMapping("add-recetas")
	public ResponseEntity<Long> addReceta(@RequestBody RecetaDTO recetaDTO){
		
		Receta receta = Receta.builder()
				.name(recetaDTO.getName())
				.ingredients(recetaDTO.getIngredients())
				.price(recetaDTO.getPrice())
				.descripcion(recetaDTO.getDescripcion())
				.build();
		
		long idReceta = recetaService.save(receta); 
		
		return ResponseEntity.ok(idReceta); 
			
	}
	
	@PostMapping("add-img/{id}")
	public ResponseEntity<?> addImagen(
			@RequestParam("imagen") MultipartFile multiPartFile, 
			@PathVariable("id") long id) throws IllegalAccessException{
		Optional<Receta> recetaOpt = recetasDAO.findById(id);
		
		if(recetaOpt.isEmpty())
			throw new IllegalAccessException("Can't find this id");
		
		String UUID = recetaService.saveImage(multiPartFile, "imagenReceta")
				.get()
				.getResourceId().toString();
		
		recetaService.updateImage(id, UUID);	
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("get-img/{resourceId}")
	public ResponseEntity<byte[]> getImage(@PathVariable("resourceId") String UUID){
		
		ImageResourceDTO imageResourceDTO = recetaService.getImage(UUID);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(imageResourceDTO.getContentType()))
				.contentLength(imageResourceDTO.getSize())
				.body(imageResourceDTO.getContent());
				
	}
}


