package com.tokioschool.web.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
	
	
	
}
