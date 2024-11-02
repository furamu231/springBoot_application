package com.spring.springbootapplication.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
    public String signUp(@Valid @ModelAttribute("user") UserSignUpDTO userDTO, BindingResult result, HttpServletRequest request) throws ServletException {
        if (result.hasErrors()) {
            return "signup";
        }
        Integer userId = userService.registerUser(userDTO);
        System.out.println("Generated userId: " + userId);

        // 自動ログイン処理
        request.login(userDTO.getUserName(), userDTO.getPassword());

        Authentication auth = new UsernamePasswordAuthenticationToken(
        userDTO.getUserName(), userDTO.getPassword(), userService.loadUserByUsername(userDTO.getUserName()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);


        // 認証済みの状態でリダイレクト
        return "redirect:/users/success/" + userId;
    }

    @GetMapping("/signin") 
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        }
        return "signin"; 
    }

    @GetMapping("/success/{id}")
    public String confirm(@PathVariable Integer id, Model model) {
        System.out.println("User ID: " + id);
        model.addAttribute("userId", id);
        return "top";
    }
}
