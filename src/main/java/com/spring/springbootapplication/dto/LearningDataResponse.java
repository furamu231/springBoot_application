package com.spring.springbootapplication.dto;

public class LearningDataResponse {
    private final String categoryName;
    private final String learningDataName;

    public LearningDataResponse(String categoryName, String learningDataName) {
        this.categoryName = categoryName;
        this.learningDataName = learningDataName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getLearningDataName() {
        return learningDataName;
    }
}