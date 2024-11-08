package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.entity.User;
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
    
        if (request.getUserPrincipal() == null) {
            request.login(userDTO.getUserName(), userDTO.getPassword());
        }
    
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
        var user = userService.findUserById(id); 
        model.addAttribute("user", user); 
        return "top";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        User user = userService.findUserById(id); 
        model.addAttribute("user", user);
        return "edit"; 
    }
    
    @PostMapping("/edit/{id}")
public String updateUserProfile(@PathVariable Integer id,
                                @RequestParam("profile") String profile,
                                @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                                RedirectAttributes redirectAttributes) {
    try {
        User user = userService.findUserById(id);
        user.setProfile(profile);

        // 画像データがアップロードされた場合に設定
        if (profileImage != null && !profileImage.isEmpty()) {
            user.setProfileImage(profileImage.getBytes());
        }

        // 正しく引数を渡す
        userService.updateUserProfile(user);
        redirectAttributes.addFlashAttribute("successMessage", "プロフィールが更新されました");
        return "redirect:/users/success/" + id;
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "プロフィールの更新に失敗しました");
        return "redirect:/users/edit/" + id;
    }
}

}