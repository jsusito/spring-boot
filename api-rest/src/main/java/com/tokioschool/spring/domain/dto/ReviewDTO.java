package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;

import com.tokioschool.spring.domain.Review;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
		@Schema(required = true)
		String title;
		@Schema(required = true)
		String textReview;
		@Schema(required = true)
		LocalDate date;
		@Schema(required = true)
		String username;
		@Schema(required = true)
		long idUser;
		@Schema(required = true)
		long idFilm;
	
	
	public static ReviewDTO fullConvertDTO(Review review) {
		return ReviewDTO.builder().title(review.getTitle())
				.textReview(review.getTextReview())
				.date(review.getDate())
				.username(review.getUser().getUsername())
				.idUser(review.getUser().getId())
				.idFilm(review.getFilm().getId())
				.build();
	}
}
