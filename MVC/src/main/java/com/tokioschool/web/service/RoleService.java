package com.tokioschool.web.service;

import java.util.Optional;
import java.util.Set;

import com.tokioschool.web.domain.Role;

public interface RoleService {
	Set<Role> findAll();
	Optional<Role> findByName(String role);
	void save(Role role);
}
