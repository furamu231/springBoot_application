package com.spring.springbootapplication.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.springbootapplication.dto.LearningDataResponse;
import com.spring.springbootapplication.entity.LearningData;

@Mapper
public interface LearningMapper {

    @Select("SELECT id FROM categories WHERE category_name = #{categoryName}")
    Integer findCategoryIdByName(@Param("categoryName") String categoryName);

    @Insert("INSERT INTO learning_data (learning_data_name, learning_time, category_id, user_id, registered_month, created_at, updated_at) " +
        "VALUES (#{learningDataName}, #{learningTime}, #{categoryId}, #{userId}, #{registeredMonth}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
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
    LearningDataResponse findLatestLearningDataWithCategory();

    
    // @Select("SELECT COUNT(*) FROM learning_data WHERE user_id = #{userId} AND learning_data_name = #{learningDataName}")
    // int checkDuplicateLearningDataName(@Param("userId") Integer userId, @Param("learningDataName") String learningDataName);

    @Select("SELECT COUNT(*) FROM learning_data WHERE user_id = #{userId} AND learning_data_name = #{learningDataName} AND registered_month = #{registeredMonth}")
int checkDuplicateLearningDataName(@Param("userId") Integer userId, @Param("learningDataName") String learningDataName, @Param("registeredMonth") LocalDate registeredMonth);
    @Update("UPDATE learning_data SET learning_time = #{learningTime}, updated_at = CURRENT_TIMESTAMP WHERE id = #{id}")
    void updateLearningTime(@Param("id") Integer id, @Param("learningTime") Integer learningTime);

    @Select("SELECT * FROM learning_data WHERE id = #{id}")
    LearningData findLearningDataById(Integer id);

    @Delete("DELETE FROM learning_data WHERE id = #{id}")
    void deleteLearningDataById(Integer id);


    @Select("""
        SELECT c.category_name AS category,
               TO_CHAR(ld.registered_month, 'YYYY-MM') AS month,
               SUM(ld.learning_time) AS total_time
        FROM learning_data ld
        JOIN categories c ON ld.category_id = c.id
        WHERE ld.user_id = #{userId}
          AND ld.registered_month >= CURRENT_DATE - INTERVAL '3 months'
        GROUP BY c.category_name, TO_CHAR(ld.registered_month, 'YYYY-MM')
        ORDER BY c.category_name, month
    """)
    List<Map<String, Object>> findTotalLearningTime(@Param("userId") Integer userId);
}

//