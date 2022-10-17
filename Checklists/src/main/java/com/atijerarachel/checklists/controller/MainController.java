package com.atijerarachel.checklists.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.service.UserService;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String root() {
		return "index";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/bonus")
	public String bonus() {
		return "bonus";
	}

	@GetMapping("/credits")
	public String credits() {
		return "credits";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/register/sucessful")
	public String registrationSucessful() {
		return "registrationsuccessful";
	}

	@GetMapping("/shopping")
	public String shopping() {
		return "shopping";
	}

	@GetMapping("/todo")
	public String todo() {
		return "todo";
	}

	@GetMapping("/userpage")
	public String userPage(Model model) {
		User currentUser = userService.getCurrentUser();

		// Get full name of user
		model.addAttribute("accountName", currentUser.getAccountName());
		return "userpage";
	}

	@GetMapping("/logoutpage")
	public String logout() {
		return "logoutpage";
	}
}