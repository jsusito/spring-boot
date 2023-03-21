package com.tokioschool.spring.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.spring.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Optional<Role> findByName(String role);

}
