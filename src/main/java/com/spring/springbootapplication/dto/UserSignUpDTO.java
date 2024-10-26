package com.spring.springbootapplication.dto;

import com.spring.springbootapplication.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserSignUpDTO {

    @NotBlank(message = "氏名は必ず入力してください")
    @Size(max = 255, message = "氏名は255文字以内で入力してください")
    private String userName;

    @NotBlank(message = "メールアドレスは必ず入力してください")
    @Email(message = "メールアドレスが正しい形式ではありません")
    private String email;

    @NotBlank(message = "パスワードは必ず入力してください")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", 
             message = "英数字8文字以上で入力してください")
    private String password;

    public UserSignUpDTO() {
    }

    public UserSignUpDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserSignUpDTO fromEntity(User user) {
        UserSignUpDTO dto = new UserSignUpDTO();
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User toEntity() {
        User user = new User();
        user.setUserName(this.userName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}