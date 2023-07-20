package com.exam.controller;

import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.model.exam.Quiz;
import com.exam.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	private User user;

    //creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

    	System.out.println("user"+user.toString());

        user.setProfile("default.png");
        //encoding password with bcryptpasswordencoder
        String pass=user.getPassword();

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();	
        
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);


        return this.userService.createUser(user, roles, pass);

    }
    @GetMapping("/get-all")
    public List<User> getAlluser() {
    	return this.userService.getAllUser();
    }
    //get user by id
    @GetMapping("/id/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return this.userService.getUser(userId);
    }

    
//get user by username
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return this.userService.getUser(username);
    }

    //delete the user by id
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
    }


    //update api
    @PutMapping(value="/up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestBody User user) {
    	System.out.println("data--"+user);
        return ResponseEntity.ok(this.userService.updateUser(user));
    }
   
    @PutMapping("/updatePassword/{username}/{newpassword}")
    public String updatePassword( @PathVariable String username,@PathVariable String newpassword) {
		return userService.updatePassword( username,  newpassword);
    }
    
//    @PutMapping("/updatepassword")
//    public String updatePassword( @RequestParam("username") String username,@RequestParam("newpassword") String newpassword) {
//		return userService.updatePassword( username,  newpassword);
//    }
    @PostMapping("/userdata/uploadfile")
	public ResponseEntity<?> uploadSheet(@RequestBody MultipartFile file){
		if(userService.checkExcelFormat(file)) {
			
			this.userService.save(file);
			return ResponseEntity.ok(Map.of("message","file is uploaded and data is saved to db"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload Excel file");
	}
	

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserFoundException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }


}
