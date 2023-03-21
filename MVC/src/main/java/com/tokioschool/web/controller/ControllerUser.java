package com.tokioschool.web.controller;

import java.util.HashSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tokioschool.web.domain.Role;
import com.tokioschool.web.domain.User;
import com.tokioschool.web.service.RoleService;
import com.tokioschool.web.service.UserService;

@Controller
public class ControllerUser {
	
	Logger logger = LoggerFactory.getLogger(ControllerUser.class);
	

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	@GetMapping("/login")
	public String signIn(Model model) {
		return "login";
	}
	
		
	
	@GetMapping("/index")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/new-user")
	public String newUser(Model model, @RequestParam(value = "username", defaultValue = "") String username) {
		model.addAttribute("username", username);
		model.addAttribute("newUser", new User());
		model.addAttribute("diferentRoles", roleService.findAll());
		return "/user/new-user";
	}
	
	@PostMapping("/new-user")
	public String registerUser(Model model, @ModelAttribute User user) {
		
		//Verificamos que no existe otro nombre de usuario igual;
		Optional<User> u = userService.findByUsername(user.getUsername());
		if(u.isPresent())
			return "redirect:/new-user?username=" + u.get().getUsername();
		
		User newUser = user;
		//newUser.setCreationDate(LocalDate.now());
		//newUser.setLastLogin(LocalDateTime.now());
		
		newUser.setPassword(getPasswordEncoder(user.getPassword()));
		newUser.setActive(true);
		
		//Nulo porque al ser usuario no aparece el formulario para seleccionar roles
		//Se crean los usuarios por defecto con Role User
		if(newUser.getRoleForm()==null) {
			Role role = roleService.findByName("USER").get();
			newUser.setRoles(new HashSet<Role>());
			newUser.getRoles().add(role);
		}
		//Al ser administrador se puede elegir entre Role Admin o User
		else {
			Role role = newUser.getRoleForm();
			newUser.setRoles(new HashSet<>());
			newUser.getRoles().add(role);
			
		}
		logger.info("************************* guardando usuario ***********************************");	
		userService.save(newUser);
		logger.info("************************* Nuevo usuario  guardado ***********************************");
		model.addAttribute("result", "Usuario Guardado Correctamente");
		return "user/registeredUser";
	}
	
	private String getPasswordEncoder(String password) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(password.length());
			return encoder.encode(password);
			
	}
	
}
	

