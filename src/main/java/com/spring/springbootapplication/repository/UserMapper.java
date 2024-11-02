package com.spring.springbootapplication.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.entity.User;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    // @Insert("INSERT INTO users (user_name, email, password, created_at, updated_at) VALUES (#{userName}, #{email}, #{password}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    // void insertUser(User user);

    
    @Insert("INSERT INTO users (user_name, email, password, created_at, updated_at) VALUES (#{userName}, #{email}, #{password}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user); 


    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Integer id);
}