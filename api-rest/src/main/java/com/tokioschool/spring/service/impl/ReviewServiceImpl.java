package com.tokioschool.spring.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.repository.ReviewRepository;
import com.tokioschool.spring.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Override
	public Optional<Review> findByUserAndFilm(User user, Film film) {

		return reviewRepository.findByUserAndFilm(user, film);
	}

	@Override
	public Set<Review> findByFilm(Film film) {

		return reviewRepository.findByFilm(film);
	}

	@Override
	public void save(Review review) {
		reviewRepository.save(review);
	}

	@Override
	public Set<Review> findByUser(User user) {
		
		return reviewRepository.findByUser(user);
	}

}
