package com.tokioschool.spring;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tokioschool.web.ProyectoApplication;
import com.tokioschool.web.domain.Film;
import com.tokioschool.web.domain.Role;
import com.tokioschool.web.domain.Score;
import com.tokioschool.web.domain.User;
import com.tokioschool.web.repository.FilmRepository;
import com.tokioschool.web.repository.PersonRepository;
import com.tokioschool.web.repository.RoleRepository;
import com.tokioschool.web.repository.ScoreRepository;
import com.tokioschool.web.repository.UserRepository;

@SpringBootTest(classes = {ProyectoApplication.class})
class ProyectoApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Autowired 
	RoleRepository roleRepository;
	@Autowired
	FilmRepository filmRepository;
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ScoreRepository scoreRepository;
	
	@Test
	void createUser() {
//		Role rol = roleRepository.findById(1L).get();
//		User user = User.builder().name("jesus").surname("ferrer").username("jsusito").password("123456").email("jsusito@gmail.com")
//				.image("jesus").roles(new HashSet<>()).birtDate(LocalDate.of(1980, 02, 14)).creationDate(LocalDate.now()).build();
//		user.getRoles().add(rol);
//		
		Role rol2 = roleRepository.findById(2L).get();
		User user2 = User.builder().name("raton").surname("ferrer").username("raton").password("123456").email("salvador@gmail.com")
				.image("salvador").roles(new HashSet<>()).birtDate(LocalDate.of(1985, 12, 25)).creationDate(LocalDate.now()).build();
		user2.getRoles().add(rol2);
		userRepository.save(user2);
		
	}
	
	@Test
	void createFilms() {
//		Film film1 = Film.builder().title("Alicia Maravillas").sipnosis("maravillas").build();
//		User user = userRepository.findById(1L).get();
//		film1.setUser(user);
//		filmRepository.save(film1);
		
//		Film film2 = Film.builder().title("Viajes Profundos").sipnosis("Del mar").build();
//		User user2 = userRepository.findById(1L).get();
//		film2.setUser(user2);
//		filmRepository.save(film2);
		
		Film film3 = Film.builder().title("Los simsomp").sipnosis("Dibujos").build();
		User user3 = userRepository.findById(2L).get();
		film3.setUser(user3);
		filmRepository.save(film3);
	}
	
	@Test
	void delete() {
		for(Long i = 6L; i <=16; i ++ )
		personRepository.deleteById(i);
	}
	
	@Test
	void findbyUserAndFilm() {
		Score score = scoreRepository.findByUserIdAndFilmId(1L,2L).get();
		System.out.println(score.getValue());
	}
	@Test
	public void passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
		String clave = encoder.encode("dario");
		System.out.println(clave);
	}
	
	@Test
	public void getFilmByUser() {
		User user = User.builder().id(1).build();
		filmRepository.findByUser(user).forEach(System.out::println);
	}
}
