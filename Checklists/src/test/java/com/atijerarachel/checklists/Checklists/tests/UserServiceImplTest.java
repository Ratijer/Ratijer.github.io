package com.atijerarachel.checklists.Checklists.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atijerarachel.checklists.entities.Role;
import com.atijerarachel.checklists.entities.ShoppingList;
import com.atijerarachel.checklists.entities.TodoList;
import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.entities.UserLists;
import com.atijerarachel.checklists.repository.UserRepository;
import com.atijerarachel.checklists.service.UserServiceImpl;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Initialize users
	User validUser1 = new User("OnetestEmail@email.com", "validUser1", "12345678");
	User validUser2 = new User("TwotestEmail@email.com", "validUser2", "87654321");

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		// Input users into DB.
//		 validUser1.setPassword(passwordEncoder.encode(validUser1.getPassword()));
		validUser1.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser1);

//		 validUser2.setPassword(passwordEncoder.encode(validUser2.getPassword()));
		validUser2.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser2);
	}

	//Also a test for loadUserByUsername
	@ParameterizedTest
	@CsvSource({ "OnetestEmail@email.com,true", "invalid123@email,false" })
	void findByEmailTest(String email, boolean veracity) {
		boolean result = false;
		if (userRepository.findByEmail(email) != null) {
			result = true;
		} else {
			result = false;
		}
		assertTrue(result == veracity);
	}

	@Test
	void saveTest() {
		boolean userFound = false;

		// Save a new user
		User user = new User();

		// User list related
		ShoppingList sl = new ShoppingList();
		TodoList tl = new TodoList();
		UserLists ui = new UserLists(tl, sl);

		//Add user and other list related stuff to to DB
		user.setAccountName("validUser3");
		user.setEmail("ThreetestEmail@email.com");
		user.setPassword(passwordEncoder.encode("87654321"));
		user.setUserLists(ui);
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(user);

		// Find new user. True if found. Should be true.
		if (userRepository.findByEmail(user.getEmail()) == null) {
			userFound = false;
		} else {
			userFound = true;
		}

		userRepository.delete(user); // Remove newly added user
		assertTrue(userFound == true);
	}

	@AfterAll
	void tearDownAfterClass() throws Exception {
		userRepository.delete(validUser1);
		userRepository.delete(validUser2);
	}
}
