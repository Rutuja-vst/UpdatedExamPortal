package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.UserQuiz;
import com.exam.repo.QuizRepository;
import com.exam.repo.UserRepository;
import com.exam.service.QuizService;
import com.exam.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserService userService;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return this.quizRepository.findById(quizId).get();
    }

    @Override
    public void deleteQuiz(Long quizId) {
        this.quizRepository.deleteById(quizId);
    }

    @Override
    public List<Quiz> getQuizzesOfCategory(Category category) {
        return this.quizRepository.findBycategory(category);
    }


    //get active quizzes

    @Override
    public List<Quiz> getActiveQuizzes() {
        return this.quizRepository.findByActive(true);
    }

    @Override
    public List<Quiz> getActiveQuizzesOfCategory(Category c) {
        return this.quizRepository.findByCategoryAndActive(c, true);
    }

//	@Override
//	public void showMarksToAdmin(Long currentUserId, String quizName, Double marks) {
//		User user=userService.getUser(currentUserId);
//		user.setMarks(marks);
//		user.setAssignedQuiz(quizName);
//		userService.updateUser(user);
//	};
    
    public void showMarksToAdmin(Long currentUserId, String quizName, Double marks) {
    	 User user = userService.getUser(currentUserId);
         UserQuiz userquiz = new UserQuiz();
         userquiz.setQuizName(quizName);
         userquiz.setMarks(marks);
         
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH);
         sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Set the timezone to Indian Standard Time (IST)
         String formattedDate = sdf.format(new Date());
         try {
//             Date parsedDate = sdf.parse(formattedDate);
//             userquiz.setAttemptDate(parsedDate);
        	 Date parsedDate = sdf.parse(formattedDate);
             userquiz.setAttemptDate(parsedDate);					
         }catch (ParseException e) {
             e.printStackTrace();
         }
//         userquiz.setAttemptDate(new Date());
         user.addUserQuiz(userquiz);
         user.setMarks(marks);
         user.setAssignedQuiz(quizName);
         userService.updateUser(user);
         userService.save(user);
    }
    
   

}
