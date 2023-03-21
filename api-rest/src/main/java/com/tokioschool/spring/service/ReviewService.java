package com.tokioschool.spring.service;

import java.util.Optional;
import java.util.Set;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;

public interface ReviewService {
	Optional<Review> findByUserAndFilm(User user, Film film);
	Set<Review> findByFilm(Film film);
	Set<Review> findByUser(User user);
	void save(Review review);
}
