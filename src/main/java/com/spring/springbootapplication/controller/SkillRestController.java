package com.spring.springbootapplication.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springbootapplication.dto.LearningDataDTO;
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

    @PostMapping("/add")
        public ResponseEntity<?> addSkill(@RequestBody LearningDataDTO dto) {
        try {
            // カテゴリ英語で取れてくるから忘れないで
            // カテゴリIDのバリデーション
            if (dto.getCategoryId() == null || dto.getCategoryId() < 1 || dto.getCategoryId() > 3) {
                return ResponseEntity.badRequest().body("無効なカテゴリIDです。");
            }

            // DTOにユーザーIDが設定されているか確認
            if (dto.getUserId() == null) {
                return ResponseEntity.badRequest().body("ユーザーIDが設定されていません。");
            }

            // 重複チェック
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
            String errorMessage = String.format("「%s」は既に登録されています（DBエラー）。", dto.getLearningDataName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);

        } catch (Exception e) {
            // その他の例外
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登録処理中にエラーが発生しました。");
        }
    }
}
