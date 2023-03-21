package com.tokioschool.web.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.Role;
import com.tokioschool.web.repository.RoleRepository;
import com.tokioschool.web.service.RoleService;

@Service
public class RoleServImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	@Override
	public Set<Role> findAll() {
		//Iterable<Role> roleIterable = roleRepository.findAll();
		Set<Role> roles = new HashSet<>();
		//roleIterable.forEach(r -> roles.add(r));
		roleRepository.findAll().forEach(roles::add);
		
		return roles;
	}
	@Override
	public Optional<Role> findByName(String role) {
		
		return roleRepository.findByName(role);
	}
	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

}
