package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.exam.UserQuiz;

public interface UserQuizRepository extends JpaRepository<UserQuiz, Long>{
    List<UserQuiz> findByUserId(Long userId);


}
