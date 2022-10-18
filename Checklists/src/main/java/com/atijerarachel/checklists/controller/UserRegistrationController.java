package com.atijerarachel.checklists.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atijerarachel.checklists.dto.UserRegistrationDto;
import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.service.UserService;

//Insert new users into user table in the DB
@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

   @Autowired
   private UserService userService;

   @ModelAttribute("user")
   public UserRegistrationDto userRegistrationDto() {
       return new UserRegistrationDto();
   }

   @GetMapping("/registration-form")
   public String showRegistrationForm(Model model) {
       return "register";
   }

   @PostMapping("/registered")
   public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result){

       User existing = userService.findByEmail(userDto.getEmail());
       if (existing != null){
           result.rejectValue("email", null, "There is already an account registered with that email");
       }

       if (result.hasErrors()){
           return "register";
       }

      userService.save(userDto);
      logger.info("New user successfully registered");
      return "redirect:/register/sucessful";
   }
}