package com.tokioschool.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tokioschool.web.domain.Person;
import com.tokioschool.web.service.PersonService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerPerson {
	
	private Logger logger = LoggerFactory.getLogger(ControllerPerson.class);
	final PersonService personService;
	
	@GetMapping("/new-person")
	public String newPerson(Model model) {
		
		String result = null;
		Person person = new Person();
		model.addAttribute("person", person);
		model.addAttribute("result", result);
		model.addAttribute("diferentPerson", Person.TypePerson.values());
		return "/person/new-person";
		
	}
	
	@PostMapping("/new-person")
	public String registerPerson(@ModelAttribute Person person, Model model) {
		
		Person newPerson = person;
		logger.info("Procedemos a Guardar una Persona en la Tabla bd");
		personService.save(newPerson);
		model.addAttribute("result", "Se registro correctamente");
		return "/person/new-person";
		
	}
}
