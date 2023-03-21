package com.tokioschool.spring.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tokioschool.spring.domain.dto.ReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "reviews")
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(nullable = false)
	String title;
	String textReview;
	LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "film_id")
	Film film;
	
	public static Review convertToReview(ReviewDTO reviewDTO) {
		User user = User.builder().id(reviewDTO.getIdUser()).build();
		Film film = Film.builder().id(reviewDTO.getIdFilm()).build();
		
		return Review.builder()
				.title(reviewDTO.getTitle()).textReview(reviewDTO.getTextReview())
				.date(reviewDTO.getDate()).user(user).film(film).build();
				
				
	}
	
}
