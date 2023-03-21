package com.tokioschool.spring.service;

import java.util.Optional;
import java.util.Set;

import com.tokioschool.spring.domain.Role;

public interface RoleService {
	Set<Role> findAll();
	Optional<Role> findByName(String role);
}
