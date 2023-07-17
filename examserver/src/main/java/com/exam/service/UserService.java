package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    //creating user
    public User createUser(User user, Set<UserRole> userRoles, String pass) throws Exception;
    
    //get user by userid 
    public User getUser(Long userId);
    
    //get user by username
    public User getUser(String username);
    
    //update user
    public User updateUser(User user);

    //delete user by id
    public void deleteUser(Long userId);

    //get All user 
	public List<User> getAllUser();

	//update password to change password
	public String updatePassword(String username,String newpassword);
	
	public void save(MultipartFile file);
	public boolean checkExcelFormat(MultipartFile file) ;

	public User save(User user);

	
	
	
}
