package com.tokioschool.web.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokioschool.web.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Modifying
	@Transactional
	@Query("UPDATE users u SET lastLogin = :now WHERE u.id =:id ")
	void updateByLastLogin(@Param("id") Long id, @Param("now") LocalDateTime now);

}
