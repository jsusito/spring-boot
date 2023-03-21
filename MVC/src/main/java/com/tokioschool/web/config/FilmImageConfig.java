package com.tokioschool.web.config;

import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.tokioschool.web.config.ConstantsConfig.IMAGE_DIR_FILM;

@Configuration
public class FilmImageConfig {
	
	@Value("${images.source}")
	private String sourceDir;
	
	@Bean
	public void createDirectory() {
		ConstantsConfig.SOURCE_DIR = sourceDir;
		
		//Obtenemos la ruta donde estarán las imágenes
		ConstantsConfig.toDir();
		
		Logger logger = LoggerFactory.getLogger(FilmImageConfig.class);
		
		try {
			logger.info("Comprobamos existencia directorio");
			if(!Files.isDirectory(IMAGE_DIR_FILM)) {
				logger.info("Creamos directorio donde se subiran las imagenes que carguen los usuarios");
				Files.createDirectory(IMAGE_DIR_FILM);
				logger.info("Se creo directorio en " + IMAGE_DIR_FILM);
				logger.info("Copiamos todos los archivos");
				FileUtils.copyDirectory(ConstantsConfig.fromDir().toFile(), IMAGE_DIR_FILM.toFile());
				logger.info("archivos Copiados");
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
		
}
