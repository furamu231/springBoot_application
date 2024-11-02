package com.spring.springbootapplication.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        request.login(userDTO.getUserName(), userDTO.getPassword());

        Authentication auth = new UsernamePasswordAuthenticationToken(
        userDTO.getUserName(), userDTO.getPassword(), userService.loadUserByUsername(userDTO.getUserName()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/users/success/" + userId;
    }

    @GetMapping("/signin") 
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
        model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
    }
    return "signin"; 
    }

    @PostMapping("/signin")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
    try {
        // メールアドレスを使ってユーザー情報を取得
        UserDetails userDetails = userService.loadUserByEmail(email);
        
        // 認証トークンの作成
        Authentication auth = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(), 
            password, 
            userDetails.getAuthorities()
        );
        
        // 認証コンテキストに設定
        SecurityContextHolder.getContext().setAuthentication(auth);

        // ログイン成功時のリダイレクト
        return "redirect:/users/success"; // 成功時のリダイレクト先
    } catch (UsernameNotFoundException e) {
        // 認証失敗時のエラーメッセージ設定
        model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
        return "signin"; // ログインフォームに戻る
    } catch (Exception e) {
        // その他の例外処理
        model.addAttribute("errorMessage", "ログイン中にエラーが発生しました。再試行してください。");
        return "signin"; // ログインフォームに戻る
    }
}

    @GetMapping("/success/{id}")
    public String confirm(@PathVariable Integer id, Model model) {
        System.out.println("User ID: " + id);
        model.addAttribute("userId", id);
        return "top";
    }
}