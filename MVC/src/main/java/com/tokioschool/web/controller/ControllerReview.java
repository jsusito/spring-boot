package com.tokioschool.web.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tokioschool.web.domain.ReviewDTO;
import com.tokioschool.web.security.UserSession;
import com.tokioschool.web.service.UserService;
import com.tokioschool.web.util.HelperRest;

@Controller
public class ControllerReview {

	private static final String REVIEW_FILM_URL= "http://localhost:8089/review/";

	
	@Value("${url.restapi.login}")
	String URL_API_REST;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	HttpSession httpSession;
	
	private Logger logger = LoggerFactory.getLogger(ControllerReview.class); 
	
	@GetMapping("/review/{film_id}")
	public String getReviews(Model model, @PathVariable long film_id) throws JsonProcessingException {
		
		//Obtenemos cabecera con el token ya formada.
		HttpHeaders header = HelperRest.getTokenAuthorizacionHeader(UserSession.USERNAME, UserSession.PASSWORD, httpSession, URL_API_REST);
		//Cuerpo vacio solo mandamos la cabecera con la autentificacion
		HttpEntity<Void> jwtEntity = new HttpEntity<>(header);
		
		logger.info("Procedemos a mandar la solicitud consultar review de film");
		String url = REVIEW_FILM_URL + film_id;
		ResponseEntity<ReviewDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, jwtEntity, ReviewDTO[].class);
		logger.info("Guardada la solicitud en el response");
		model.addAttribute("reviews" , response.getBody());
		return "film/film";
	}

	
}
