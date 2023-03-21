package com.tokioschool.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tokioschool.web.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Optional<Role> findByName(String role);

}
