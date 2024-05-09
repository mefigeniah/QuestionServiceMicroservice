package com.mefigenia.questionservice.service;


import com.mefigenia.questionservice.dao.QuestionDao;
import com.mefigenia.questionservice.model.Question;
import com.mefigenia.questionservice.model.QuestionWrapper;
import com.mefigenia.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao dao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
                try {
            return new ResponseEntity<>(dao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        dao.save(question);

        try {
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        dao.save(question);
        try {
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> deleteQuestion(Question question) {
        dao.delete(question);
        try {
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQuestions) {
        List<Integer> questionsIdsForQuiz = dao.findRandomQuestionsByCategory(category, numQuestions);
        return new ResponseEntity<>(questionsIdsForQuiz, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questions) {
        List<Question> questionsFromDB = new ArrayList<>();
        List<QuestionWrapper> wrappers = new ArrayList<>();

        for(int i = 0; i < questions.size(); i++) {
            questionsFromDB.add(dao.findById(questions.get(i)).get());
        }

        for(Question question : questionsFromDB) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int correctAnswer = 0;
        for(int i = 0; i < responses.size(); i++) {
            if (dao.findByRightAnswer(responses.get(i).getId()).equals(responses.get(i).getResponse())) {
                correctAnswer++;
            }
        }
        return new ResponseEntity<>(correctAnswer, HttpStatus.OK);

    }
}