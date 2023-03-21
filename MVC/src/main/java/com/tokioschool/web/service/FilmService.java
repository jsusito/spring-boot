package com.tokioschool.web.service;

import java.util.Set;

import com.tokioschool.web.domain.Film;

public interface FilmService {

	public Set<Film> findByYear(int year);
	public Set<Film> findByTitleContainingOrderByTitle(String title);
	public Film findById(Long id);
	public void save(Film film);
}
