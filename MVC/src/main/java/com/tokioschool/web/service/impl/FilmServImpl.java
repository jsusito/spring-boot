package com.tokioschool.web.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.Film;
import com.tokioschool.web.repository.FilmRepository;
import com.tokioschool.web.service.FilmService;

@Service
public class FilmServImpl implements FilmService {

	@Autowired
	FilmRepository filmRepository;
	@Override
	public Set<Film> findByYear(int year) {
		return filmRepository.findByYear(year);
	}
	@Override
	public Set<Film> findByTitleContainingOrderByTitle(String title) {
		
		return filmRepository.findByTitleContainingOrderByTitle(title);
	}
	@Override
	public void save(Film film) {
		filmRepository.save(film);
	}
	@Override
	public Film findById(Long id) {
		return filmRepository.findById(id).get();
		
	}

}
