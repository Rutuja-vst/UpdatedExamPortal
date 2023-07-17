package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.model.exam.UserQuiz;
import com.exam.repo.UserQuizRepository;

@Service
public class UserQuizService {
	private UserQuizRepository userQuizRepository;

	public UserQuizService(UserQuizRepository userQuizRepository) {
        this.userQuizRepository = userQuizRepository;
    }

    public List<UserQuiz> getQuizzesByUserId(Long userId) {
        return userQuizRepository.findByUserId(userId);
    }


}
