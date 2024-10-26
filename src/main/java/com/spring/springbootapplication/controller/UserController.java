package com.spring.springbootapplication.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "form"; 
    }

    @PostMapping("/signup") 
    public String signUp(@Valid @ModelAttribute("user") UserSignUpDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "form"; 
        }
        userService.registerUser(userDTO);
        return "redirect:/users/userList"; 
    }

    // ユーザー登録確認用 (削除必須)

    @GetMapping("/userList") 
    public String showUserList(Model model) {
        List<UserSignUpDTO> users = userService.getUserCredentials(); 
        model.addAttribute("users", users);
        return "userList"; 
    }
}