package com.visionbizsolutions.security.authentication.dao.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.visionbizsolutions.Utils;
import com.visionbizsolutions.orm.jpa.bean.UserAttempts;
import com.visionbizsolutions.orm.jpa.dao.UserDetailsDao;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(LimitLoginAuthenticationProvider.class);

	@Autowired
	private UserDetailsDao userDetailsDao;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Autowired
	@Qualifier("passwordEncoder")
	@Override
	public void setPasswordEncoder(Object passwordEncoder) {
		super.setPasswordEncoder(passwordEncoder);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		try {
			logger.debug("Authenticating user");
			Authentication auth = super.authenticate(authentication);
			logger.info("User Authenticated!!");

			// if reach here, means login success, else an exception will be
			// thrown
			// reset the user_attempts
			// This means a user have done some mistake in login less
			// then 5 times and
			// then successfully login
			userDetailsDao.resetFailAttempts(authentication.getName(), false);

			return auth;

		} catch (BadCredentialsException e) {
			logger.error("User " + authentication.getName() + " is not authenticated, user didn't provided right username/password!"
					+ System.getProperty("line.separator")
					+ Utils.getStackTrace(e.fillInStackTrace()));
			// invalid login, update to user_attempts
			userDetailsDao.updateFailAttempts(authentication.getName());
			throw e;

		} catch (LockedException e) {
			logger.error("User " + authentication.getName() + " account is locked, due to false 4 login attempts!"
					+ System.getProperty("line.separator")
					+ Utils.getStackTrace(e.fillInStackTrace()));

			// this user is locked!
			String error = "";
			UserAttempts userAttempts = userDetailsDao
					.getUserAttempts(authentication.getName());

			if (userAttempts != null) {
				Date lastLoginAttempt = userAttempts.getLastModified();
				SimpleDateFormat format = new SimpleDateFormat(
						"MM-dd-yyyy HH:mm");
				String _fLastLoginAttempt = format.format(lastLoginAttempt);
				long minutes = Utils.getMinutes(lastLoginAttempt);

				error = "<span style=\"font-size: 12pt;\">User account is locked for 30 minutes!</span><br><br>Username : <strong>"
						+ authentication.getName()
						+ "</strong><br>Last Login Attempt : "
						+ _fLastLoginAttempt
						+ "<br>Please try again after <strong>"
						+ (30 - minutes) + "</strong> minutes.";
			} else {
				error = e.getMessage();
			}

			throw new LockedException(error);

		} catch (Exception e) {
			logger.error("User " + authentication.getName() + " authentication has some other problem!"
					+ System.getProperty("line.separator")
					+ Utils.getStackTrace(e.fillInStackTrace()));
			throw e;
		}

	}
}
