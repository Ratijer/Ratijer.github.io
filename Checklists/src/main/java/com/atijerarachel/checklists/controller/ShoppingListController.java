package com.atijerarachel.checklists.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//For shopping checklist and to-do checklist
@Controller
public class ShoppingListController {

	@GetMapping("/shoppinguser")
	public String shoppinguser() {
		return "shoppinguser";
	}
}