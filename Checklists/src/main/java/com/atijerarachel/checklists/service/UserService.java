package com.atijerarachel.checklists.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.atijerarachel.checklists.dto.UserRegistrationDto;
import com.atijerarachel.checklists.entities.User;

public interface UserService extends UserDetailsService {
	User findByEmail(String email);

	User save(UserRegistrationDto registration);

	User getCurrentUser();
}
