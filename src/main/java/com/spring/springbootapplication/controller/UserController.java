package com.spring.springbootapplication.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.dto.ProfileUpdateDTO;
import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
        ProfileUpdateDTO dto = new ProfileUpdateDTO();
        dto.setProfile(user.getProfile());
        model.addAttribute("dto", dto);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUserProfile(@PathVariable Integer id,
                                @ModelAttribute ProfileUpdateDTO dto,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        try {
            User user = userService.findUserById(id);

            session.setAttribute("userId", id);

            String profileText = dto.getProfile();
            if (profileText == null || profileText.length() < 50 || profileText.length() > 200) {
                redirectAttributes.addFlashAttribute("errorMessage", "プロフィール文は50文字以上、200文字以下で入力してください。");
                return "redirect:/users/edit/" + id;
            }
            user.setProfile(profileText);

            if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
                long imageSize = dto.getProfileImage().getSize();
                long maxSize = 101 * 1024 * 1024; 

                if (imageSize > maxSize) {
                    redirectAttributes.addFlashAttribute("errorMessage", "画像サイズが大きすぎます。101MB 以下の画像をアップロードしてください。");
                    return "redirect:/users/edit/" + id;
                }

                user.setProfileImage(dto.getProfileImage().getBytes());
            }

            userService.updateUserProfile(user);
            return "redirect:/users/success/" + id;

        } catch (IOException e) {
            return "redirect:/users/edit/" + id;
        }
    }

    @GetMapping("/editSkill/{id}")
public String showEditSkillForm(@PathVariable Integer id, Model model) {
    User user = userService.findUserById(id);
    model.addAttribute("user", user);

    // 必要に応じて DTO オブジェクトを作成して渡すこともできます
    // SkillEditDTO dto = new SkillEditDTO();
    // model.addAttribute("dto", dto);

    return "editSkill";
}
}