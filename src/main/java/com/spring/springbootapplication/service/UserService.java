package com.spring.springbootapplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder; 

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder(); 
    }

    public void registerUser(UserSignUpDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); 
       
        userMapper.insertUser(user); 
    }
    
    public List<UserSignUpDTO> getUserCredentials() {
        List<User> users = userMapper.getAllUsers(); 

        users.forEach(user -> System.out.println("dbからのレスポンス: " + user.getUserName()));
        
        return users.stream()
                    .map(UserSignUpDTO::fromEntity)                     
                    .collect(Collectors.toList()); 
    }
}