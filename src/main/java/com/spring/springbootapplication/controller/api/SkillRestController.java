package com.spring.springbootapplication.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springbootapplication.dto.LearningDataDTO;
import com.spring.springbootapplication.dto.LearningDataResponse;
import com.spring.springbootapplication.service.LearningService;

// 学習項目系はほぼrestで管理かな？

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

    @PostMapping("/add")
    public ResponseEntity<?> addSkill(@RequestBody LearningDataDTO dto) {
        try {

            // カテゴリ英語で取れてくるから忘れないで
            // カテゴリIDのバリデーション

            if (dto.getCategoryId() == null || dto.getCategoryId() < 1 || dto.getCategoryId() > 3) {
                return ResponseEntity.badRequest().body("無効なカテゴリIDです。");
            }

            if (dto.getUserId() == null) {
                return ResponseEntity.badRequest().body("ユーザーIDが設定されていません。");
            }

            boolean isDuplicate = learningService.isLearningDataNameDuplicated(dto.getUserId(), dto.getLearningDataName());
            if (isDuplicate) {
                String errorMessage = String.format("「%s」は既に登録されています。", dto.getLearningDataName());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
            }

            // データを保存
            learningService.saveLearningData(dto);

            return ResponseEntity.ok("スキルが正常に追加されました。");

        } catch (DataIntegrityViolationException e) {
            // データベースの一意制約違反の場合
            // 発生しないはずだけど
            String errorMessage = String.format("「%s」は既に登録済。", dto.getLearningDataName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

        } catch (Exception e) {
            // その他の例外
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登録処理中にエラーが発生しました。");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLearningTime(@RequestBody LearningDataDTO dto) {
        // デバック用ね
        // System.out.println("Received ID: " + dto.getId());
        // System.out.println("Received LearningTime: " + dto.getLearningTime());

        if (dto.getId() == null || dto.getLearningTime() == null || dto.getLearningTime() < 1) {
            return ResponseEntity.badRequest().body("正しい学習時間入力して");
        }

        learningService.updateLearningTime(dto.getId(), dto.getLearningTime());
        return ResponseEntity.ok("学習時間が正常に更新されました");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLearningData(@PathVariable Integer id) {
        try {
            // データが存在するか確認
            boolean exists = learningService.isLearningDataExists(id);
            if (!exists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("指定された学習データが見つかりません");
            }

            // データを削除
            learningService.deleteLearningData(id);
            return ResponseEntity.ok("学習データが正常に削除されました。");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("削除処理中にエラーが発生しました");
        }
    }

    @GetMapping("/totalLearningTime")
    public ResponseEntity<List<Map<String, Object>>> getTotalLearningTime(@RequestParam("userId") Integer userId) {
        try {
            List<Map<String, Object>> result = learningService.getTotalLearningTime(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
