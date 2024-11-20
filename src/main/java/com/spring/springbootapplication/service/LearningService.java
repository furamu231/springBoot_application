package com.spring.springbootapplication.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.spring.springbootapplication.dto.LearningDataDTO;
import com.spring.springbootapplication.dto.LearningDataResponse;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.repository.LearningMapper;

@Service
public class LearningService {

    private final LearningMapper learningMapper;

    public LearningService(LearningMapper learningMapper) {
        this.learningMapper = learningMapper;
    }

    // カテゴリIDの取得
    public Integer findCategoryIdByName(String categoryName) {
        return learningMapper.findCategoryIdByName(categoryName);
    }

    // 学習データの保存
    public void saveLearningData(LearningDataDTO dto) {
        LearningData learningData = new LearningData();
        learningData.setLearningDataName(dto.getLearningDataName());
        learningData.setLearningTime(dto.getLearningTime());
        learningData.setCategoryId(dto.getCategoryId());
        learningData.setUserId(dto.getUserId());
        LocalDate registeredMonth = LocalDate.parse(dto.getRegisteredMonth());
        learningData.setRegisteredMonth(registeredMonth);

        learningMapper.insertLearningData(learningData);
    }

    public List<LearningData> findLearningDataByCategoryAndUser(String categoryName, Integer userId) {
        return learningMapper.findLearningDataByCategoryAndUser(categoryName, userId);
    }

    public List<LearningData> findLearningDataByMonthAndUser(String categoryName, Integer userId, String month) {
        return learningMapper.findLearningDataByMonthAndUser(categoryName, userId, month);
    }

    public LearningDataResponse findAddedLearningData() {
        return learningMapper.findLatestLearningDataWithCategory();
    }

    public boolean isLearningDataNameDuplicated(Integer userId, String learningDataName, LocalDate registeredMonth) {
        int count = learningMapper.checkDuplicateLearningDataName(userId, learningDataName, registeredMonth);
        return count > 0;
    }

    public void updateLearningTime(Integer id, Integer learningTime) {
        learningMapper.updateLearningTime(id, learningTime);
    }

    public boolean isLearningDataExists(Integer id) {
        return learningMapper.findLearningDataById(id) != null;
    }
    
    public void deleteLearningData(Integer id) {
        learningMapper.deleteLearningDataById(id);
    }

    public List<Map<String, Object>> getTotalLearningTime(Integer userId) {
        return learningMapper.findTotalLearningTime(userId);
    }
}