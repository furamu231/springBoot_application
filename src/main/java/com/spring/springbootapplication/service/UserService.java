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
    
    public void updateUserProfile(User user) {
        userMapper.updateUserProfile(user);
    }


    public User findUserById(Integer id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    public Integer findByUserNameOrEmail(String username) {
        User user = userMapper.findByUserNameOrEmail(username);
        return user != null ? user.getId() : null;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
    User user = userMapper.findByUserNameOrEmail(identifier);
    if (user == null) {
        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }
    return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserName())  
            .password(user.getPassword())
            .authorities("USER")
            .build();
    }
}

// DB側でメールアドレスが一意制約か確認する