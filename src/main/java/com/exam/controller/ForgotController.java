//package com.exam.controller;
//
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.exam.repo.UserRepository;
//import com.exam.service.UserService;
//
//@Controller
//@CrossOrigin(origins="*", allowedHeaders = "*")
//
//@RequestMapping("/forgot")
//public class ForgotController {
//	@Autowired
//    private UserService userService;
//	Random random=new Random(100000);
////	private UserRepository userRepo;
//
//	
//@PostMapping("/")
// public String sendOTP(@RequestParam("email") String email) {
////    String userEmail = userRepo.findByEmail();
//	 System.out.println("EMAIL:"+email);
//	 
////	 String otp=generatePassword(6);
//	int otp=random.nextInt(999999);
//	 System.out.println("OTP:"+otp);
//	return String.valueOf(otp);
// }
//	
//
//public String verifyOTP(@RequestParam("otp") String otp) {
//	
//	return null;
//}
//
////public static String generatePassword(int length) {
////    String allowedChars ="0123456789!@#$%^&*";
////    Random random = new Random();
////    StringBuilder password = new StringBuilder();
////
////    for (int i = 0; i < length; i++) {
////        int randomIndex = random.nextInt(allowedChars.length());
////        char randomChar = allowedChars.charAt(randomIndex);
////        password.append(randomChar);
////    }
////    return password.toString();
////    
////}
//}
