package com.tokioschool.web.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tokioschool.web.config.ConstantsConfig;
import com.tokioschool.web.domain.Film;
import com.tokioschool.web.domain.Person;
import com.tokioschool.web.domain.Person.TypePerson;
import com.tokioschool.web.domain.Review;
import com.tokioschool.web.domain.ReviewDTO;
import com.tokioschool.web.domain.Score;
import com.tokioschool.web.domain.User;
import com.tokioschool.web.exceptions.FoundReviewException;
import com.tokioschool.web.security.UserSession;
import com.tokioschool.web.service.FilmImageService;
import com.tokioschool.web.service.FilmService;
import com.tokioschool.web.service.PersonService;
import com.tokioschool.web.service.ScoreService;
import com.tokioschool.web.service.UserService;
import com.tokioschool.web.util.HelperRest;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerFilm {

	@Value("${url.restapi.login}")
	private String URL_API_REST;
	
	final FilmService filmService;
	final PersonService personService;
	final FilmImageService filmImageService;
	final UserService userService;
	final ScoreService scoreService;
	final RestTemplate restTemplate;
	final HttpSession httpSession;
	
	final User userSession;
	@Autowired private Film filmSesion;
	
	private Logger logger = LoggerFactory.getLogger(ControllerFilm.class);
	private final String SAVE_REVIEW_URL= "http://localhost:8089/new-review";
	private final String REVIEW_FILM_URL= "http://localhost:8089/review/";
	
	@GetMapping("/prueba")
	public String prueba(Model model) {
		return "";
	}
	
	@GetMapping({"/search-film", "/search-films/{title}"})
	public String searchFilm( Model model, @PathVariable(name = "title", required = false) String title) {
		
		if(title != null) {
			Set<Film> films = null;
			films = filmService.findByTitleContainingOrderByTitle(title);
			model.addAttribute("films", films);
			return "/film/search-film";
		}
			
		
		Film film = new Film();
		model.addAttribute("film", film);
		model.addAttribute("sourceDirFilm", ConstantsConfig.SOURCE_DIR);
		return "/film/result-search-film";
	}
	
	
	
	
	@PostMapping("/show-film")
	public RedirectView showFilm(@ModelAttribute Film film) {
		return new RedirectView("/search-films/" + film.getTitle());
	}
	
	
	@GetMapping("/new-film")
	public String newFilm(Model model) {
		Set<Person> selectScreenWriters = personService.findByTypePerson(TypePerson.GUIONISTA);
		Set<Person> directors = personService.findByTypePerson(TypePerson.DIRECTOR);
		Set<Person> actors = personService.findByTypePerson(TypePerson.ACTOR);
		Set<Person> musicians = personService.findByTypePerson(TypePerson.MUSICO);
		Set<Person> photographers = personService.findByTypePerson(TypePerson.FOTOGRAFO);
		Film film = new Film();
		film.setYear(LocalDate.now().getYear());
		film.setDuration(130);
		
	
		model.addAttribute("film", film);
		model.addAttribute("selectScreenWriter", selectScreenWriters);
		model.addAttribute("directors", directors);
		model.addAttribute("actors", actors);
		model.addAttribute("selectMusicians", musicians);
		model.addAttribute("selectphotographers", photographers);
		return "/film/new-film";
	}
	
	@PostMapping("/new-film")
	public String registerFilm(@ModelAttribute Film currentFilm, Model model) {
		
		User user = userService.findByUsername(UserSession.USERNAME).get();		
		//Por defecto la película nueva está siempre sin migrar
		currentFilm.setMigrate(false);
		currentFilm.setUser(user);
		
		filmService.save(currentFilm);
		
		//Guardamos en atributo
		filmSesion = currentFilm;
						
		return "/film/new-film";
	}
	
	@PostMapping("/save-image/{id}")
	public String saveImage(@RequestParam("image") MultipartFile image, Model model, @PathVariable("id") long id) {
		Film film = filmService.findById(id);
		filmImageService.recordImageFilm(film, image);
		return "redirect:/register-film";
	
	}
	//Muestra la pelicula recien registrada
	@GetMapping("/register-film")
	public String registedFilm(Model model) {
		Set<Film> films = filmService.findByTitleContainingOrderByTitle(filmSesion.getTitle());
		model.addAttribute("films", films);
		return "/film/search-film";
	}
	
	@GetMapping("edit-film/{id}")
	public String editFilm(Model model, @PathVariable("id") Long id ) {
		Film film = filmService.findById(id);
		
		model.addAttribute("film", film);
		model.addAttribute("selectScreenWriter", film.getScreenWriters());
		model.addAttribute("directors", film.getFilmDirector());
		model.addAttribute("actors", film.getActors());
		model.addAttribute("selectMusicians", film.getMusicians());
		model.addAttribute("selectphotographers", film.getFilmPhotographer());
		model.addAttribute("poster", film.getPoster());
		return "/film/edit-film";
	}
	
	
	
	
	
			
	//Muestra la informacion de la pelicula y da la opcion de puntuar.
	@GetMapping("/film/{id}")
	public String addNewfilm(Model model, @PathVariable(value = "id" ) Long idFilm ) throws JsonProcessingException {
		boolean check = false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(authentication.getName()).get();		
		
		//Comprueba si el usuario ya ha puntuado
		Optional<Score> score = scoreService.findByUserIdAndFilmId(user.getId(), idFilm);
		if(score.isPresent()) {		
			check = true;
			model.addAttribute("points", score.get().getValue());
		}
		
		Film film = filmService.findById(idFilm);
		filmSesion = film;
		 
		
		model.addAttribute("score", new Score());		
		model.addAttribute("check",check);
		addModelNewFilm(model, film);
		
		//Comprobamos si el usuario a realizado la crítica
		ReviewDTO[] reviewPersist = getReviews(idFilm);
		List<ReviewDTO> reviews = Arrays.asList(reviewPersist);
		boolean reviewExist = reviews.stream().anyMatch(r ->  ((r.getIdFilm() == film.getId()) && r.getIdUser() == user.getId()));
		
		model.addAttribute("reviewExist" , reviewExist);
		model.addAttribute("reviews", reviews);
		return "/film/film";
	}
	
	@PostMapping("/add-review")
	public String addReview(@ModelAttribute Review review) throws Exception {
		
		//Completamos los datos que faltan de la review que mando el usuario desde el formulario
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(authentication.getName()).get();
		
		
		Film film = Film.builder().id(filmSesion.getId()).build();
		LocalDate now = LocalDate.now();
		
		review.setDate(now);
		review.setUser(user);
		review.setFilm(film);
		
		//Obtenemos cabecera con el token ya formada.
		HttpHeaders header = HelperRest.getTokenAuthorizacionHeader(UserSession.USERNAME, UserSession.PASSWORD, httpSession, URL_API_REST);
		ReviewDTO reviewDTO = ReviewDTO.convertDTO(review);
		
		HttpEntity<ReviewDTO> jwtEntity = new HttpEntity<>(reviewDTO, header);
		ResponseEntity<ReviewDTO> responseEntity = restTemplate.exchange(SAVE_REVIEW_URL, HttpMethod.POST, jwtEntity, ReviewDTO.class);
		
		if(responseEntity.getStatusCode() != HttpStatus.ACCEPTED) {
			
			throw new FoundReviewException("Ya hiciste la critica, no se puede repetir\nInfo: " + responseEntity.getStatusCodeValue());
		}
		return "redirect:/film/" + review.getFilm().getId();
	}
	
	@PostMapping("/film")
	public String registerPoinst(Model model, @ModelAttribute("score") Score currentScore){
		//TODO corregir da fallo 
		Film film = filmService.findById(filmSesion.getId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userPersist = userService.findByUsername(authentication.getName()).get();
		
		Score score = Score.builder().value(currentScore.getValue()).user(userPersist).film(film).build();
		
		Optional<Score> scorePersist = scoreService.findByUserIdAndFilmId(userSession.getId(), film.getId());
		
		if(scorePersist.isEmpty())
		{
			logger.info("**************************save film**************************************");
			score = scoreService.save(score);
		}
		
		return "redirect:/film/" + film.getId();
	}
	
	private void addModelNewFilm(Model model, Film film) {
		
		model.addAttribute("newReview", new Review());
		model.addAttribute("title", film.getTitle());
		model.addAttribute("year", film.getYear());
		model.addAttribute("director", film.getFilmDirector());
		model.addAttribute("guionistas", film.getScreenWriters());
		model.addAttribute("musicos", film.getMusicians());
		model.addAttribute("fotografo", film.getFilmPhotographer());
		model.addAttribute("actores", film.getActors());
		model.addAttribute("sinopsis", film.getSipnosis());
		model.addAttribute("poster", film.getPoster());
	}
	
	
	private ReviewDTO[] getReviews(long film_id) throws JsonProcessingException {
		
		//Obtenemos cabecera con el token ya formada.
		HttpHeaders header = HelperRest.getTokenAuthorizacionHeader(UserSession.USERNAME, UserSession.PASSWORD, httpSession, URL_API_REST);
		
		//Cuerpo vacio solo mandamos la cabecera con la autentificacion
		HttpEntity<Void> jwtEntity = new HttpEntity<>(header);
		
		logger.info("Procedemos a mandar la solicitud");
		String url = REVIEW_FILM_URL + film_id;
		ResponseEntity<ReviewDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, jwtEntity, ReviewDTO[].class);
		logger.info("recibimos la solicitud en el response");
		return response.getBody();
	}

}
