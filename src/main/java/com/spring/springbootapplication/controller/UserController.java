package com.spring.springbootapplication.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup") 
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new UserSignUpDTO()); 
        return "signup"; 
    }

    @PostMapping("/signup") 
    public String signUp(@Valid @ModelAttribute("user") UserSignUpDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "signup"; 
        }
        userService.registerUser(userDTO);
        return "redirect:/users/success"; 
    }

    @GetMapping("/signin") 
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        }
        return "signin"; 
    }

    @GetMapping("/success")
    public String confirm(Principal principal, Model model) {
        String username = principal.getName(); 
        model.addAttribute("username", username); 
        return "home"; 
    }
}