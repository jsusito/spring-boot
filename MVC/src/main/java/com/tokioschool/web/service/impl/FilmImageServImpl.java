package com.tokioschool.web.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.web.config.ConstantsConfig;
import com.tokioschool.web.domain.Film;
import com.tokioschool.web.repository.FilmRepository;
import com.tokioschool.web.service.FilmImageService;


/**
 * @author jsusi
 * Se encarga de almacenar la imagen en el directorio del sistema, le cambia el nombre que corresponde con el mismo Id
 * que tiene el Film.
 */
@Service
public class FilmImageServImpl implements FilmImageService {
	
	@Autowired
	FilmRepository filmRepository;
	@Override
	public String recordImageFilm(Film film, MultipartFile file) {
		Optional<Film> filmPersist = filmRepository.findById(film.getId());
		//Comprobar producto
		long id = filmPersist.get().getId();
		
		int positionExtension = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(positionExtension + 1);
		String target= ConstantsConfig.IMAGE_DIR_FILM.toString() + "/" + id + "." + extension; 
		Path path = Paths.get(target);
		
		String nameImage = id + "." + extension;
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			filmRepository.updateFilmImage(id, nameImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameImage;
	}

}
