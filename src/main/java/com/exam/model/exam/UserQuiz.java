package com.exam.model.exam;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.exam.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

	@Table(name = "user_quiz")
	public class UserQuiz {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private User user;

	    private String quizName;
	    private Double marks;
	    
//	    @DateTimeFormat(pattern = "dd-MM-yyyy")
//	    @Column(name = "date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date attemptDate;
	    
	    

	    // constructors, getters, and setters
	    public UserQuiz() {
			super();
		}

	    
	    public UserQuiz(Long id, User user, String quizName, Double marks, Date attemptDate) {
			super();
			this.id = id;
			this.user = user;
			this.quizName = quizName;
			this.marks = marks;
			this.attemptDate = attemptDate;
		}


		public Date getAttemptDate() {
			return attemptDate;
		}

		public void setAttemptDate(Date attemptDate) {
			this.attemptDate = attemptDate;
		}

		
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

//		public String getQuiz() {
//			return quizName;
//		}
//
//		public void setQuiz(String quizName) {
//			this.quizName = quizName;
//		}
		
		

		public Double getMarks() {
			return marks;
		}

		public void setMarks(Double marks) {
			this.marks = marks;
		}

		public String getQuizName() {
			return quizName;
		}

		public void setQuizName(String quizName) {
			this.quizName = quizName;
		}

		
		

		@Override
		public String toString() {
			return "UserQuiz [id=" + id + ", user=" + user + ", quizName=" + quizName + ", marks=" + marks + "]";
		}

		
	    
	}

