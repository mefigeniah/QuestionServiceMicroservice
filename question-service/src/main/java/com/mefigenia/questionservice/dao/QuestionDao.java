package com.mefigenia.questionservice.dao;

import com.mefigenia.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT id FROM question Where category=:category ORDER BY random() LIMIT :numQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer numQuestions);


    @Query(value = "SELECT right_answer FROM question WHERE id=:questionId", nativeQuery = true)
    String findByRightAnswer(Integer questionId);
}
