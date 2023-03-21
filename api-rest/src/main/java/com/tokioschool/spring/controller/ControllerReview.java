package com.tokioschool.spring.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tokioschool.spring.domain.Film;
import com.tokioschool.spring.domain.Review;
import com.tokioschool.spring.domain.User;
import com.tokioschool.spring.domain.dto.ReviewDTO;
import com.tokioschool.spring.service.ReviewService;
import com.tokioschool.spring.service.UserService;
import com.tokioschool.spring.util.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Tag(name ="Reviews" , description ="Reviews the film by idUser and idFilm ")
public class ControllerReview {

	private final ReviewService reviewService;
	private final UserService userService;
	
	Logger logger = LoggerFactory.getLogger(ControllerReview.class);
	
	@Operation(summary ="Get Reviews by film Primary Key ")
	@GetMapping("/review/{id}")
	private ResponseEntity<Set<ReviewDTO>> getReview(@PathVariable Long id){
		
		Film film = Film.builder().id(id).build();
		
		logger.info("Consultando BD review");
		Set<Review> reviews = reviewService.findByFilm(film);
		Set<ReviewDTO> reviewsDTO = new HashSet<ReviewDTO>();
		reviews.stream().forEach(r -> reviewsDTO.add( convertDTO(r)));
		
		logger.info("Devolviendo consulta review");
		return ResponseEntity.ok(reviewsDTO);
	}
	
	
	//TODO hacer metodo para guardar las review
	@Operation(summary ="Save film`s review")
	@PostMapping("/new-review")
	public ResponseEntity<Response> newReview(@RequestBody ReviewDTO reviewDTO){
		 
		Review review = Review.convertToReview(reviewDTO);
		
		Optional<Review> reviewOpt = reviewService.findByUserAndFilm(review.getUser(), review.getFilm());
		
		if(reviewOpt.isPresent())
			 throw new ResponseStatusException(HttpStatus.FOUND ,"Ya existe la critica");
		reviewService.save(review);
		
		Response response = new Response(Response.NO_ERROR, Response.NO_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	@Operation(summary ="get all reviews of a user")
	@GetMapping("/user/{id}/review")
	ResponseEntity<Set<ReviewDTO>> getReviewByUser(@PathVariable Long id){
		Optional<User> userOpt = userService.findById(id);
		if(userOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.FOUND ,"No existe el usuario");
		
		logger.info("Consultando BD review");
		
		Set<Review> reviews = reviewService.findByUser(userOpt.get());
		Set<ReviewDTO> reviewsDTO = new HashSet<ReviewDTO>();
		reviews.stream().forEach(r -> reviewsDTO.add( convertDTO(r)));
		
		logger.info("Devolviendo consulta review");
		return new ResponseEntity<Set<ReviewDTO>>(reviewsDTO,HttpStatus.OK);
	}


	private ReviewDTO convertDTO(Review review) {
		
		ReviewDTO reviewDTO = ReviewDTO.fullConvertDTO(review);
		return reviewDTO;
	}
}
