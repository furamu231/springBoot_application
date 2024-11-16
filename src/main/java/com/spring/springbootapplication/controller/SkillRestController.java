package com.spring.springbootapplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springbootapplication.dto.LearningDataResponse;
import com.spring.springbootapplication.service.LearningService;

// ここでモーダル用のjson返す

@RestController
@RequestMapping("/api/skills")
public class SkillRestController {

    private final LearningService learningService;

    public SkillRestController(LearningService learningService) {
        this.learningService = learningService;
    }

    @GetMapping("/added")
    public ResponseEntity<LearningDataResponse> getAddedLearningData() {
        LearningDataResponse response = learningService.findAddedLearningData();

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}