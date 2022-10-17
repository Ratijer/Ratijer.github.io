package com.atijerarachel.checklists.Checklists.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atijerarachel.checklists.entities.Role;
import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

	@Autowired
	 UserRepository userRepository;

	@Autowired
	 BCryptPasswordEncoder passwordEncoder;

	// Initialize users
	 User validUser1 = new User("OnetestEmail@email.com", "validUser1", "12345678");
	 User validUser2 = new User("TwotestEmail@email.com", "validUser2", "87654321");

	// Create users and put them in database before doing tests
	@BeforeAll
	void setUpBeforeClass() {
		// Input users into DB.
		validUser1.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser1);

		validUser2.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser2);
	}

	// Return user if user if email is found. Return null if email is not found
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
	void queriesNotNullTest() {
		//Get total number of users
		assertNotNull(userRepository.getTotalNumberOfUsers());
	}

	// Remove all test users
	@AfterAll
	void tearDownAfterClass() {
		userRepository.delete(validUser1);
		userRepository.delete(validUser2);
	}

}
