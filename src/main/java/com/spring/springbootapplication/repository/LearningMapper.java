package com.spring.springbootapplication.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.spring.springbootapplication.dto.LearningDataResponse;
import com.spring.springbootapplication.entity.LearningData;

@Mapper
public interface LearningMapper {

    @Select("SELECT id FROM categories WHERE category_name = #{categoryName}")
    Integer findCategoryIdByName(@Param("categoryName") String categoryName);

    @Insert("INSERT INTO learning_data (learning_data_name, learning_time, category_id, user_id, registered_month, created_at, updated_at) " +
            "VALUES (#{learningDataName}, #{learningTime}, #{categoryId}, #{userId}, CURRENT_DATE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertLearningData(LearningData learningData);

    @Select("SELECT id, learning_data_name, learning_time, category_id, user_id, registered_month, created_at, updated_at " +
            "FROM learning_data WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<LearningData> findLearningDataByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM learning_data WHERE category_id = " +
        "(SELECT id FROM categories WHERE category_name = #{categoryName}) " +
        "AND user_id = #{userId} ORDER BY created_at DESC")
    List<LearningData> findLearningDataByCategoryAndUser(@Param("categoryName") String categoryName, @Param("userId") Integer userId);

    @Select("SELECT * FROM learning_data WHERE category_id = " +
        "(SELECT id FROM categories WHERE category_name = #{categoryName}) " +
        "AND user_id = #{userId} " +
        "AND TO_CHAR(registered_month, 'YYYY-MM') = #{month} " +
        "ORDER BY created_at DESC")
    List<LearningData> findLearningDataByMonthAndUser(@Param("categoryName") String categoryName,
                                                  @Param("userId") Integer userId,
                                                  @Param("month") String month);
    
    @Select("SELECT ld.learning_data_name, c.category_name " +
        "FROM learning_data ld " +
        "JOIN categories c ON ld.category_id = c.id " +
        "ORDER BY ld.created_at DESC " +
        "LIMIT 1")
    LearningDataResponse findLatestLearningDataWithCategory();}