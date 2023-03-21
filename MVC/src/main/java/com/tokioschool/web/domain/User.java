package com.tokioschool.web.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	//@Getter(value = AccessLevel.NONE)
	LocalDate birtDate;
	
	@CreationTimestamp 
	private LocalDate creationDate; 
	@UpdateTimestamp  
	private LocalDateTime lastLogin;
	
	private boolean active;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name="users_roles", 
				joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles;
	
	@OneToMany(mappedBy = "user")
	Set<Film> films;
	
	@OneToMany(mappedBy = "user")
	Set<Score> filmsScores;
	
	@OneToMany(mappedBy = "user")
	Set<Review> reviews;
	
	@Transient //La utilizamos para enviar el tipo de usuario en los formularios  de registro
	Role roleForm;
	
	@Override
	public int hashCode() {
		
		return  Objects.hash(id, surname);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj == null)
			return false;
		
		if( !(obj instanceof User))
			return false;
		
		User user = (User) obj;
		return (this.username.equals(user.username));
			
		
		
	}
	@Override
	public String toString() {
		return username; 
		
	}



	public void setBirtDate(String currentBirtDate) {
		birtDate = LocalDate.parse(currentBirtDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
}
