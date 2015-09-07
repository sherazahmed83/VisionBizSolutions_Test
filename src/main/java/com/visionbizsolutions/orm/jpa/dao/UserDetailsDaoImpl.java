package com.visionbizsolutions.orm.jpa.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visionbizsolutions.Utils;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.bean.UserAttempts;

@Repository
@Transactional
public class UserDetailsDaoImpl implements UserDetailsDao {

	private static final int MAX_ATTEMPTS = 5;
	private static final int MINUTES_TO_ACCOUNT_ACTIVATION = 30;
	private static final Logger logger = LoggerFactory
			.getLogger(UserDetailsDaoImpl.class);
	
	@Autowired
	private UserDao dao;
	
	private EntityManager em = null;

	/**
	 * Sets the entity manager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void updateFailAttempts(String username) {

		logger.debug("Adding Fail attempt to database for user with username " + username);
		UserAttempts userAttemptsObj = getUserAttempts(username);

		if (userAttemptsObj == null) {
			logger.debug("User Attempt Object at first instance does not exists");
			if (isUserExists(username)) {
				User user = getUser(username);
				
				UserAttempts userAttempts = new UserAttempts();
				userAttempts.setAttempts(1);
				userAttempts.setLastModified(new Date());
				
				user.setAttempts(userAttempts);
				logger.info("Updating user object with UserAttempts");
				saveUser(user);
			}
		} else {
			logger.debug("User Attempt Object instance exists and found.");
			if (isUserExists(username)) {
				logger.debug("Updating user attempts by 1 upto " + MAX_ATTEMPTS);
				User user = getUser(username);

				UserAttempts attempts = user.getAttempts();
				attempts.setAttempts(attempts.getAttempts() + 1);
				attempts.setLastModified(new Date());
				saveUser(user);				
			}

			if (userAttemptsObj.getAttempts() >= MAX_ATTEMPTS) {
				User user = getUser(username);
				user.setAccountNonLocked(false);
				saveUser(user);
				throw new LockedException("User Account is locked!");
			}
		}
	}//updateFailAttempts()

	@Override
	public void resetFailAttempts(String username, boolean isActivateTimeCheckRequired) {
		
		logger.debug("Reseting User details");
		if(isUserExists(username)){	
			User user = getUser(username);
			
			if(isActivateTimeCheckRequired){
				
				Date lastModified = user.getAttempts().getLastModified();
				long minutes = Utils.getMinutes(lastModified);
				
				logger.debug("User blocked about " + minutes + " minutes ago.");
				
				if (minutes - MINUTES_TO_ACCOUNT_ACTIVATION >= 0) {
					user.setAccountNonLocked(true);
					
					UserAttempts attempts = user.getAttempts();
					attempts.setAttempts(0);
					attempts.setLastModified(null);
					
					saveUser(user);
				}
				
			} else {				
				UserAttempts attempts = user.getAttempts();
				
				if (attempts != null) {
					
					user.setAccountNonLocked(true);
					attempts.setAttempts(0);
					attempts.setLastModified(null);
					
					saveUser(user);			
				}
			}//end of else
		}
	}//resetFailAttempts()

	@Override
	public UserAttempts getUserAttempts(String username) {

		logger.debug("Fetching user with username " + username);
		UserAttempts userAttempts = null;

		User user = getUser(username);
		if(user != null){
			userAttempts = user.getAttempts();
		}
		logger.info("UserAttempts is null " + (userAttempts == null));
		return userAttempts;
	}

	private boolean isUserExists(String username) {

		boolean result = false;
		User user = getUser(username);
		if (user != null) {
			result = true;
		}

		return result;
	}
	

	private User getUser(String username) {
		Collection<User> users = dao.findUsersByUsername(username);
		
		User user = null;
		if (users != null && users.size() == 1) {
			Iterator<User> iterator = users.iterator();
			if (iterator.hasNext()) {
				user = iterator.next();
			}
		}
		return user;
	}
		
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveUser(User user) {
		em.merge(user);
	}
	
}
