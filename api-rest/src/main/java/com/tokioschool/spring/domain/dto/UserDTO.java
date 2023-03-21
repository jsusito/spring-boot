package com.tokioschool.spring.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private long id;
    private String username;
    private String password;
    
    
    private String nif;
    
    private String name;
    
    private String surname;
    private String email;
    
    private String address;
    
    private String city;
    private String postalCode;
    
    private String province;
    
    private String country;
    
    private String image;
    private LocalDate creationDate;
    private LocalDateTime lastLogin;
    private boolean active;
    private int age;
    

}
