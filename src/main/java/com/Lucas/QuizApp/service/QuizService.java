package com.Lucas.QuizApp.service;

import com.Lucas.QuizApp.dao.QuestionDao;
import com.Lucas.QuizApp.dao.QuizDao;
import com.Lucas.QuizApp.model.Question;
import com.Lucas.QuizApp.model.QuestionWrapper;
import com.Lucas.QuizApp.model.Quiz;
import com.Lucas.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        Quiz savedQuiz = quizDao.saveAndFlush(quiz); // force save + flush
//        Quiz savedQuiz = quizDao.save(quiz);
        System.out.println("Saved quiz with " + savedQuiz.getQuestions().size() + " questions");

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for(Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(),q.getOption4());
            questionForUser.add(qw);
        }
        System.out.println("Quiz: " + quiz.get());
        System.out.println("Questions from DB: " + questionsFromDB);
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }



    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        List<Question> questions = quiz.getQuestions();
        int right = 0;

        for (Response response : responses) {
            // Find matching question by ID
            Question matchingQuestion = questions.stream()
                    .filter(q -> q.getId() == response.getId())
                    .findFirst()
                    .orElse(null);

            if (matchingQuestion != null) {
                // Debug logging
                System.out.println("Checking Question ID: " + matchingQuestion.getId());
                System.out.println("User answered: '" + response.getResponse() + "'");
                System.out.println("Correct answer: '" + matchingQuestion.getRightAnswer() + "'");

                if (response.getResponse() != null &&
                        matchingQuestion.getRightAnswer() != null &&
                        response.getResponse().trim().equalsIgnoreCase(matchingQuestion.getRightAnswer().trim())) {
                    right++;
                }
            }
        }

        System.out.println("Final Score: " + right + " out of " + questions.size());

        return new ResponseEntity<>(right, HttpStatus.OK);
    }

}
