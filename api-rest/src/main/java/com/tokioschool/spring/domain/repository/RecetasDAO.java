package com.tokioschool.spring.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Receta;


@Repository
public interface RecetasDAO extends JpaRepository<Receta, Long>{
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Receta r SET r.imagen =:imagen WHERE r.id =:id")
	int updateImage(@Param("id") long id, @Param("imagen") String image);
}
