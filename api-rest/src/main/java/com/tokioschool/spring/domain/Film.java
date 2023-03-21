package com.tokioschool.spring.domain;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
@Entity(name = "films")
public class Film {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(nullable = false)
	String title;
	
	int year;
	int duration;
	
	@Column(nullable = false)
	String sipnosis;
	
	String poster;
	boolean migrate;
	LocalDate dateMigrate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;
	
	@OneToMany(mappedBy = "film")
	Set<Review> reviews;
	
	@OneToMany(mappedBy = "film")
	Set<Score> scores;
	
	@ManyToOne
	@JoinColumn(name = "director_id")
	Person filmDirector;
	
	@ManyToOne
	@JoinColumn(name = "photographer_id")
	Person filmPhotographer;
	
	@ManyToMany
	@JoinTable(name="musicians_films", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> musicians;
	
	@ManyToMany
	@JoinTable(name="actors_films", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> actors;
	
	@ManyToMany
	@JoinTable(name="screenwriter_film", joinColumns = @JoinColumn(name="film_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	private Set<Person> screenWriters;
	
	
}
