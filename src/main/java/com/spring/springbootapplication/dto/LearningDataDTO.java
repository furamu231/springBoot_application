package com.spring.springbootapplication.dto;

public class LearningDataDTO {
    private String learningDataName;
    private Integer learningTime;
    private Integer categoryId; 
    private Integer userId;    
    private String registeredMonth;

    public String getLearningDataName() {
        return learningDataName;
    }

    public void setLearningDataName(String learningDataName) {
        this.learningDataName = learningDataName;
    }

    public Integer getLearningTime() {
        return learningTime;
    }

    public void setLearningTime(Integer learningTime) {
        this.learningTime = learningTime;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRegisteredMonth() {
        return registeredMonth;
    }

    public void setRegisteredMonth(String registeredMonth) {
        this.registeredMonth = registeredMonth;
    }
}