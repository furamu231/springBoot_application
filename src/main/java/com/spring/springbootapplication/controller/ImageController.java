package com.spring.springbootapplication.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

// 画像取得用のAPI

@RestController
public class ImageController {

    private final UserService userService;

    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/images/{id}")
    public ResponseEntity<byte[]> getUserProfileImage(@PathVariable Integer id) {
        User user = userService.findUserById(id);

        if (user.getProfileImage() != null && user.getProfileImage().length > 0) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(user.getProfileImage());
        }

        try {
            Path defaultImagePath = Paths.get("src/main/resources/static/images/gray.jpg");
            byte[] defaultImage = Files.readAllBytes(defaultImagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(defaultImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}