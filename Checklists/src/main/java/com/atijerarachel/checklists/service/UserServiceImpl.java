package com.atijerarachel.checklists.service;

import java.util.Arrays;
import java.util.Collection;
//import java.util.HashSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atijerarachel.checklists.dto.UserRegistrationDto;
import com.atijerarachel.checklists.entities.Role;
import com.atijerarachel.checklists.entities.ShoppingList;
import com.atijerarachel.checklists.entities.TodoList;
import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.entities.UserLists;
import com.atijerarachel.checklists.exceptions.CurrentUserNotFoundException;
import com.atijerarachel.checklists.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	// Insert into DB
	public User save(UserRegistrationDto registration) { // Exception not all required items are given
		User user = new User();

		// User list related
		ShoppingList sl = new ShoppingList();
		TodoList tl = new TodoList();
		UserLists ui = new UserLists(tl, sl);

		// Add user and other list related stuff to to DB
		user.setAccountName(registration.getName());
		user.setEmail(registration.getEmail());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		user.setUserLists(ui);
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	// For login
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
		    logger.error("User not found");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		logger.info("User successfully logged in");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	// Get current user
	@Override
	public User getCurrentUser() {
		User currentUser = new User();
		try {
			// Get currently signed in user
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal == null) {
				logger.error("Cannot find currently signed in user");
				throw new CurrentUserNotFoundException("Cannot find currently signed in user");
			}

			// Get current user's email/username
			String userName = ((UserDetails) principal).getUsername();
			currentUser = userService.findByEmail(userName);

		} catch (CurrentUserNotFoundException e) {
			e.printStackTrace();
		}
		return currentUser;

	}

	// No exception handling version
//	@Override
//	public User getCurrentUser() {
//		User currentUser = new User();
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		// Get current user's email/username
//		String userName = ((UserDetails) principal).getUsername();
//		currentUser = userService.findByEmail(userName);
//
//		return currentUser;
//
//	}
}