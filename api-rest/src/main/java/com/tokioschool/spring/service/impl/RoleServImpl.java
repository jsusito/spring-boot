package com.tokioschool.spring.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.spring.domain.Role;
import com.tokioschool.spring.domain.repository.RoleRepository;
import com.tokioschool.spring.service.RoleService;

@Service
public class RoleServImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	@Override
	public Set<Role> findAll() {
		Iterable<Role> roleIterable = roleRepository.findAll();
		Set<Role> role = new HashSet<>();
		roleIterable.forEach(r -> role.add(r));
		return role;
	}
	@Override
	public Optional<Role> findByName(String role) {
		
		return roleRepository.findByName(role);
	}

}
