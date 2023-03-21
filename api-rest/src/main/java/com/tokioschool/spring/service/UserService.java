package com.tokioschool.spring.service;

import java.util.Optional;

import com.tokioschool.spring.domain.User;

public interface UserService {
	Optional<User> findById(Long id);

	Optional <User> findByUsername(String username);
	void save(User user);
}
