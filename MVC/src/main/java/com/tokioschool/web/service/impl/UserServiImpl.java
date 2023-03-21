package com.tokioschool.web.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokioschool.web.domain.User;
import com.tokioschool.web.repository.UserRepository;
import com.tokioschool.web.service.UserService;

@Service
public class UserServiImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Override
	public User findById(Long id) {

		return userRepository.findById(id).get();
	}
	@Override
	public Optional<User> findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	@Override
	public void updateByLastLogin(Long id, LocalDateTime now) {
		userRepository.updateByLastLogin(id, now);
	}

}
