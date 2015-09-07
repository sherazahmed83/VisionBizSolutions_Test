package com.visionbizsolutions.orm.jpa.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visionbizsolutions.mvc.commands.Contact;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.bean.UserFile;
import com.visionbizsolutions.orm.jpa.bean.UserRole;
import com.visionbizsolutions.orm.jpa.dao.UserDao;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	/**
	 * This method takes a userName and checks whether this
	 * is available for the new user to be taken as its own
	 * 
	 * @param username
	 * @return
	 */
	public Boolean isUsernameAvailable(String username) {
		boolean result = true;
		
		Collection <User> users = dao.findUsersByUsername(username);
		if (users.size() > 0){
			result = false;
		}
		
		return result;
	}// end of isUsernameAvailable
	
	public Boolean isEmailAvailable(String email) {
		boolean result = true;

		Collection <User> users = dao.findUsersByEmail(email);
		if (users.size() > 0){
			result = false;
		}
		
		return result;
	}// end of isEmailAvailable
	
	public User create(Contact contact){
		User user = new User();
		
		user.setUsername(contact.getUsername());
		user.setPassword(contact.getPassword());
		user.setSalt(contact.getSalt());
		user.setCompany(contact.getCompany());
		user.setFirstName(contact.getFname());
		user.setLastName(contact.getLname());
		user.setAddress(contact.getAddress());
		user.setCountry(contact.getCountry());
		user.setProvince(contact.getProvince());
		user.setZip(Integer.valueOf(contact.getZip()));
		user.setPhone(contact.getPhone());
		user.setEmail(contact.getEmail());
		user.setPreferred(contact.getPreferred());
		user.setStage(contact.getStage());
		user.setSoftware(contact.getSoftware());
		user.setFrequency(contact.getFrequency());
		user.setAbout(contact.getAbout());
		user.setEnabled(Boolean.FALSE);
		user.setVerificationHash(contact.getVerificationHash());
		user.setCreated(contact.getCreated());
		user.setAccountNonExpired(contact.isAccountNonExpired());
		user.setAccountNonLocked(contact.isAccountNonLocked());
		user.setCredentialsNonExpired(contact.isCredentialsNonExpired());

		user = dao.save(user);
		UserRole role = new UserRole();
		role.setAuthority(contact.getRole());
		role.setCreated(new Date());
		dao.saveRole(user.getId(), role);		
		
		return user;
		
	}// end of saveUser method
	
	public User getUserByVerificationCode(String code) {
		Collection<User> users = dao.findUsersByVerficationCode(code);
		User user = null;
		if(users != null && users.size() == 1){
			Iterator<User> iterator = users.iterator();
			if(iterator.hasNext()){
				user = iterator.next();
			}
		}
		return user;
	}

	public User getUserByUsername(String username) {
		Collection<User> users = dao.findUsersByUsername(username);
		User user = null;
		if(users != null && users.size() == 1){
			Iterator<User> iterator = users.iterator();
			if(iterator.hasNext()){
				user = iterator.next();
			}
		}
		return user;
	}

	public User create(User user){
		return dao.save(user);
	}
	
	public void delete(User user){
		dao.delete(user);
	}
	
	public User saveFile(User user, String fileName,Long fileSize, String fileType) {
		UserFile file = new UserFile();
		file.setFileName(fileName);
		file.setFileType(fileType);
		file.setFileSize(fileSize);
		file.setCreated(new Date());
		
		dao.saveFile(user.getId(), file);
		
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		Collection<User> users = dao.findUsersByEmail(email);
		User user = null;
		if(users != null && users.size() == 1){
			Iterator<User> iterator = users.iterator();
			if(iterator.hasNext()){
				user = iterator.next();
			}
		}
		return user;
	}

}
