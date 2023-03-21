package com.tokioschool.spring.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String name;
	String surname;
	
	@Column(nullable = false)
	String username;
	
	@Column(nullable = false)
	String password;
	
	@Column(nullable = false)
	String email;
	
	String image;
	
	
	@Column(nullable = false)
	@Setter(value = AccessLevel.NONE)
	LocalDate birtDate;
	
	LocalDate creationDate;
	LocalDateTime lastLogin;
	boolean active;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="users_roles", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles;
	
	@OneToMany(mappedBy = "user")
	Set<Film> films;
	
	@OneToMany(mappedBy = "user")
	Set<Score> filmsScores;
	
	@OneToMany(mappedBy = "user")
	Set<Review> reviews;
	
	@Override
	public int hashCode() {
		
		return  Objects.hash(id, surname);
	}
	
	@Override
	public String toString() {
		return username; 
		
	}



	public void setBirtDate(String currentBirtDate) {
		birtDate = LocalDate.parse(currentBirtDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	
	
}
