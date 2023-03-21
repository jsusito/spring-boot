package com.tokioschool.spring.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Optional<Review> findByUserAndFilm(User user, Film film);
	
	Set<Review> findByFilm(Film film);
	
	Set<Review> findByUser(User user);
}
