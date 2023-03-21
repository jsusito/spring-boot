package com.tokioschool.web.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
	
		String title;
		String textReview;
		LocalDate date;
		String username;
		long idUser;
		long idFilm;
	

		public static ReviewDTO convertDTO(Review review) {
			return ReviewDTO.builder().title(review.getTitle())
					.textReview(review.getTextReview())
					.date(review.getDate())
					.username(review.getUser().getUsername())
					.idUser(review.getUser().getId())
					.idFilm(review.getFilm().getId())
					.build();
		}
	
}
