package com.exam.service.impl;

import com.exam.helper.UserFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exam.helper.UserNotFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

	private Object bCryptPasswordEncoder;

    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles, String pass) throws Exception {


        User local = this.userRepository.findByUsername(user.getUsername());
        if (local != null) {
            System.out.println("User is already there !!");
            throw new UserFoundException();
        } else {
            //user create
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            sendEmail(user, pass);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(pass);
            	user.setPassword(hashedPassword);
            local = this.userRepository.save(user);
        }
        

        return local;
    }
    
//send Email
    private void sendEmail(User local, String pass) {
 	        // Set the email properties
 	        Properties props = new Properties();
 	        props.put("mail.smtp.host", "smtp.gmail.com");
 	        props.put("mail.smtp.port", "587");
 	        props.put("mail.smtp.auth", "true");
 	        props.put("mail.smtp.starttls.enable", "true");
 	        // Set the email account credentials
 	        final String username = "alerts.virtuoso@gmail.com";
 	        final String password = "rgddqskfyqdnqcqp";
 	        // Create a session with the email account
 	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
 	            protected PasswordAuthentication getPasswordAuthentication() {
 	                return new PasswordAuthentication(username, password);
 	            }
 	        });
 	        
 	    
 	        try {
 	            // Create a message and set the recipient, subject, and content
 	            Message msg = new MimeMessage(session);

 	            msg.setFrom(new InternetAddress(username));
 	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(local.getEmail()));
 	           // msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("pratik.pingale@vpel.in"));
 	            msg.setSubject("Account created in Virutoso Exam Portal");
 	            msg.setText("Hi"+" "+local.getFirstName()+" "+",\n               We are delighted to inform you that your Vpel ExamPortal account "
 	            		+ "\n has been successfully created. We are providing you with the login credentials to access your account.\r\n\n"
 	            		+ "Please find the login credentials below:\r\n"
 	            		+ "\r\n"
 	            		+ ""+ "\r\n"
 	            		+ "USERNAME : "+" "+ local.getUsername() +"\r\n"
 	            		+ "PASSWORD: "+" "+ pass+"\r\n\n\n"
 	            		+""
 	            		+ "Please note that for security reasons, we recommend CHANGING your PASSWORD after your initial login. "
 	            		+ "\n"
 	            		+ "To change your password, please follow these steps:\r\n"
 	            		+ "1. Visit our website at [Website URL].\r\n"
 	            		+ "2. Click on the 'change password' button located at the top of 'login' button.\r\n"
 	            		+ "3. Enter your username and the temporary password provided above.\r\n"
 	            		+ "5. Look for the \"Change Password\" or similar option.\r\n"
 	            		+ "6. Follow the prompts to create a new, secure password of your choice.\r\n"
 	            		+ "7. Save the changes.\r\n"
 	            		+ ""
 	            		+""
 	            		+" \t\t\t\t\t ALL THE BEST!!!"
 	            		+"\n \nRegards,\n"
 	            		+"vpel Team"
 	            		
 	            		); 
 	            // Send the message
 	            Transport.send(msg);
 	            System.out.println("Email sent successfully.");
 	        } catch (MessagingException e) {
 	            System.out.println("Failed to send email.");
 	            e.printStackTrace();
 	        }
    }
	


	//getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

//get all user
	@Override
	public List<User> getAllUser() {
		System.out.println(userRepository.findAll());
		return userRepository.findAll();
	}

//to update user
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}


	@Override
	public User getUser(Long userId) {
		return this.userRepository.findById(userId).get();
	}

	//update-change password

	@Override
	public String updatePassword(String username, String newpassword) {
		System.out.println(username);
		System.out.println(newpassword);

		User temp=userRepository.findByUsername(username);
		if(temp!=null) {
			//temp.setPassword(newpassword);	
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String hashedPassword = passwordEncoder.encode(newpassword);
	        
	        temp.setPassword(hashedPassword);
	         userRepository.save(temp);
	        return "Password updated successfully!!!!";
			}
		else {
	     return "cannot updated!!";
       }
	}

		//check the file type is of excel or not
			public boolean checkExcelFormat(MultipartFile file) {
				String contentType=file.getContentType();
				if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					return true;
				else
					return false;
			}
			public static List<User> convertExcelToList(InputStream is){
				List<User> list=new ArrayList<>();
				try {
					
					XSSFWorkbook workbook=new XSSFWorkbook(is);
					
					XSSFSheet sheet = workbook.getSheetAt(0);
					
					int rowNumber=0;
					
					Iterator<Row> iterator = sheet.iterator();
					
					while(iterator.hasNext())
					{
						Row row = iterator.next();
						if(rowNumber==0) {
							rowNumber++;
							continue;
						}
						
						Iterator<Cell> cells = row.iterator();
										
						int cid=0;
						User u = new User();
						DataFormatter df = new DataFormatter();
						while(cells.hasNext()) {
//							u.setPassword(UUID.randomUUID());
//							System.out.println(u.getPassword());
						Cell cell=cells.next();
							
//							switch(cid){
//							 case 0: u.setId((long)cell.getNumericCellValue());	
//				 				break;
//						
//							case 1: u.setFirstName(cell.getStringCellValue());
//										break;
//							case 2:	u.setLastName(cell.getStringCellValue())
//										;break;
//							case 3:	u.setEmail(cell.getStringCellValue());
//										break;
//						
//							case 4: 
//									String phone= df.formatCellValue(cell);
//									u.setPhone(phone);
//									break;
//							default:break;
//							}
					if (cid == 0) {
					    u.setId((long) cell.getNumericCellValue());
					} else if (cid == 1) {
					    u.setFirstName(cell.getStringCellValue());
					} else if (cid == 2) {
					    u.setLastName(cell.getStringCellValue());
					} else if (cid == 3) {
					    u.setEmail(cell.getStringCellValue());
					} else if (cid == 4) {
					    String phone = df.formatCellValue(cell);
					    u.setPhone(phone);
					} else {
					    break;
					}

							cid++;
						   
							
						}
						u.setUsername(u.getEmail());

						u.setPassword(generatePassword(8));
						System.out.println(u.getPassword());
						list.add(u);
						
						
						
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println(list);
				return list;
			}
//generate random password of given length
			public static String generatePassword(int length) {
		        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		        Random random = new Random();
		        StringBuilder password = new StringBuilder();

		        for (int i = 0; i < length; i++) {
		            int randomIndex = random.nextInt(allowedChars.length());
		            char randomChar = allowedChars.charAt(randomIndex);
		            password.append(randomChar);
		        }
		        return password.toString();
		        
		    }
		
// Check Excel file or not and Assign user role  and call create user function		
			public void save(MultipartFile file) {
				try {
					
//					System.out.println("i am here");
					List<User> users = convertExcelToList(file.getInputStream());
					
					for(int i=0;i<users.size();i++) {
						
						 Set<UserRole> roles = new HashSet<>();

					        Role role = new Role();
					        role.setRoleId(45L);
					        role.setRoleName("NORMAL");

					        UserRole userRole = new UserRole();
					        userRole.setUser(users.get(i));
					        userRole.setRole(role);

					        roles.add(userRole);
						
						
						try {
							createUser(users.get(i),roles, users.get(i).getPassword());
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}catch(IOException e) {
					e.printStackTrace();
					
				}

			}

			@Override
			public User save(User user) {
				return userRepository.save(user);
				
			}
			
			
		
	

	//update-change password

//	@Override
//	public User updatePassword(User user, String username, String currentpassword, String newpassword) {
//		User temp=userRepository.findByUsername(username);
//		if(temp!=null && temp.getPassword().equals(currentpassword)) {
//				temp.setPassword(newpassword);	
//		         userRepository.save(temp);
//		         return "Password updated successfully";
//
//		        
//	}else {
//		return "not updated";
//	}

	}

	

	

