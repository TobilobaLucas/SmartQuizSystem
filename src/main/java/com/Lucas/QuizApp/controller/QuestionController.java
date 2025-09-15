package com.Lucas.QuizApp.controller;

import com.Lucas.QuizApp.model.Question;
import com.Lucas.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    //Getting all Question
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        System.out.println(questionService.getAllQuestions());
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }
    //Getting Question By category
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return new ResponseEntity<>(questionService.getQuestionByCategory(category), HttpStatus.OK);
    }
    //Add a qustion
    @PostMapping("add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question saved = questionService.addQuestion(question);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
