package com.tokioschool.web.service;

import org.springframework.web.multipart.MultipartFile;

import com.tokioschool.web.domain.Film;

public interface FilmImageService {
	public  String recordImageFilm(Film film, MultipartFile file);
}
