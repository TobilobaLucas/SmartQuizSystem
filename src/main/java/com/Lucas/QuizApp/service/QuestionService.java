package com.Lucas.QuizApp.service;

import com.Lucas.QuizApp.dao.QuestionDao;
import com.Lucas.QuizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDao.findByCategory(category);
    }
    public Question addQuestion(Question question) {
        return questionDao.save(question);
    }
}
