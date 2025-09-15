package com.Lucas.QuizApp.dao;

import com.Lucas.QuizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
//        @Query("SELECT q FROM Quiz q JOIN FETCH q.questions WHERE q.id = :id")
//        Optional<Quiz> findByIdWithQuestions(@Param("id") int id);
}
