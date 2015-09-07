package com.visionbizsolutions.orm.jpa.service;

import com.visionbizsolutions.mvc.commands.Contact;
import com.visionbizsolutions.orm.jpa.bean.User;

public interface UserService {

	public Boolean isUsernameAvailable(String username);
	
	public Boolean isEmailAvailable(String email);
	
	public User create(Contact contact);
	
	public User getUserByVerificationCode(String code);

	public User getUserByUsername(String username);

	public User create(User user);
	
	public void delete(User user);
	
	public User saveFile(User user, String fileName,Long fileSize, String fileType);
	
	public User getUserByEmail(String email);

}
