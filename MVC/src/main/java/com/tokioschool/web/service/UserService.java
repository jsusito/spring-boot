package com.tokioschool.web.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.tokioschool.web.domain.User;

public interface UserService {
	User findById(Long id);

	Optional <User> findByUsername(String username);
	void save(User user);
	
	void updateByLastLogin(Long id, LocalDateTime now);
}
