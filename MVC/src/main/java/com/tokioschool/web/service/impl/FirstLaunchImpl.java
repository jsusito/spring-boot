package com.tokioschool.web.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.Role;
import com.tokioschool.web.domain.User;
import com.tokioschool.web.service.RoleService;
import com.tokioschool.web.service.UserService;



@Service
public class FirstLaunchImpl {
	Logger logger = LoggerFactory.getLogger(FirstLaunchImpl.class);
	
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	@Value("${admin.user}")
	String USER;
	
	@Value("${admin.password}")
	String PASSWORD;
	
	public void checkInit() {
		User user = null;
		Role roleUser;
		Role roleAdmin;
		int count = 0;
		
		
		Optional<User> userOpt = userService.findByUsername(USER);
		if(userOpt.isEmpty()) {
			count++;
		}
		
		Set<Role> roles = roleService.findAll();
		if(roles.size() < 2) {
			count++;
		}
		
		if(count == 2) {
			logger.info("Creando Usuario Administrador");
			roleUser = Role.builder().name("USER").build();
			roleAdmin = Role.builder().name("ADMIN").build();
			roleService.save(roleAdmin);
			roleService.save(roleUser);
			
			String passwordEncoder = getPasswordEncoder(PASSWORD);
			user = User.builder().username(USER).password(passwordEncoder).email("tokioSchool@tokioschool.com").creationDate(LocalDate.now())
					.birtDate(LocalDate.now()).active(true).lastLogin(LocalDateTime.now()).build();
			user.setRoles(new HashSet<>());
			user.getRoles().add(roleAdmin);
			userService.save(user);
			
			logger.info("Usuario creado: " + USER + " contraseña: " + PASSWORD);
		}
		
	}
	private String getPasswordEncoder(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(password.length());
		return encoder.encode(password);
		
}
	
}
