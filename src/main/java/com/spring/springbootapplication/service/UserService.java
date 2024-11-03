package com.spring.springbootapplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dto.UserSignUpDTO;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserMapper;

@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Integer registerUser(UserSignUpDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
       
        userMapper.insertUser(user); 
        return user.getId();
    }

    public User findUserById(Integer id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    public Integer findUserIdByUsername(String username) {
        User user = userMapper.findByUserName(username);
        return user != null ? user.getId() : null;
    }

    public Integer findUserIdByEmail(String email) {
        User user = userMapper.findByEmail(email); 
        return user != null ? user.getId() : null; 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username); 
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(email); 
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) 
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}