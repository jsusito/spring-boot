package com.tokioschool.web.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tokioschool.web.domain.Film;
import com.tokioschool.web.domain.User;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
	
	Set<Film> findByYear(int year);
	Set<Film> findByTitleContainingOrderByTitle(String title);
	
	@Modifying
	@Transactional
	@Query("UPDATE films f SET f.poster =:image  WHERE f.id =:id")
	void updateFilmImage(@Param("id") long id, @Param("image") String image);
	
	Set<Film> findByUser(User user);
}
