package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.model.exam.Quiz;
import com.exam.model.exam.UserQuiz;
import com.exam.service.UserQuizService;

@CrossOrigin("*")
@RestController
@RequestMapping("/userquiz")
public class UserQuizController {
	@Autowired
	UserQuizService userQuizService;
	//show the list of quiz user has given to user
    @GetMapping("/{currentUserID}")
    public ResponseEntity<?> getUserQuizzes(@PathVariable Long currentUserID) {
        List<UserQuiz> userQuizzes = userQuizService.getQuizzesByUserId(currentUserID);
        return ResponseEntity.ok(userQuizzes);
    }
//    @GetMapping("/{qid}")
//    public Quiz quiz(@PathVariable("qid") Long qid) {
//        return this.quizService.getQuiz(qid);
//    }
//

}
