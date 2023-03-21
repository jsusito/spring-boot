package com.tokioschool.web.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tokioschool.web.domain.User;
import com.tokioschool.web.security.UserSession;
import com.tokioschool.web.service.UserService;

@Component
public class AuthenticationSuccessImpl implements AuthenticationSuccessHandler {
	
	@Autowired
	UserService userService;
	
	@Autowired
	User userSessionScope;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		
		User userLogin = userService.findByUsername(username).get();
		userSessionScope.setSurname(username);
		userSessionScope.setLastLogin(userLogin.getLastLogin());
		
		
		//Son las constatntes que utilizamos para conectarnos al servicio Rest.
		UserSession.PASSWORD = password;
		UserSession.USERNAME = username;
		
		userService.updateByLastLogin(userLogin.getId(), LocalDateTime.now());
		response.sendRedirect(("http://localhost:8080/index"));
	}

}
