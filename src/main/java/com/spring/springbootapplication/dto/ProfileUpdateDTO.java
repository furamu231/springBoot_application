package com.spring.springbootapplication.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfileUpdateDTO {

    private String profile;
    private MultipartFile profileImage;

    // GetterとSetter
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}