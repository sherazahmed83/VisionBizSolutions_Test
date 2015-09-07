package com.visionbizsolutions.mvc.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.visionbizsolutions.MailSender;
import com.visionbizsolutions.Utils;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.service.UserService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage(Locale locale) {
		logger.info("Welcome to Admin Page. The client locale is {}.",
				locale);
		ModelAndView model = new ModelAndView();
		model.addObject("title",
				"Spring Security Login Form - Database Authentication");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			Locale locale, HttpServletRequest request) {
		logger.info("Welcome to Login Page. The client locale is {}.",
				locale);
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("loginPage");

		return model;

	}
	
	//customize the error message
		private String getErrorMessage(HttpServletRequest request, String key){
	 
			Exception exception = 
	                   (Exception) request.getSession().getAttribute(key);
	 
			String error = "";
			if (exception instanceof BadCredentialsException) {
				error = "Invalid username and password!";
			}else if(exception instanceof LockedException) {
				error = exception.getMessage();
			}else{
				error = "Invalid username and password!";
			}
	 
			return error;
		}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Locale locale) {
		logger.info("Oops! there must be an error that's why we end up at 403 Page. The client locale is {}.",
				locale);
		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

	// for session timeout 
	@RequestMapping(value = "/sessionTimeout", method = RequestMethod.GET)
	public ModelAndView sessionTimeout(Locale locale) {
		logger.info("Oops! Your session is expired. The client locale is {}.",
				locale);
		ModelAndView model = new ModelAndView();
		model.setViewName("sessionTimeout");
		return model;

	}
	
	 
	@RequestMapping(value = "/loadForgotPassPage", method = RequestMethod.GET)
	public ModelAndView loadForgotPasswordPage(Locale locale) {
		logger.info("Welcome to ForgotPassword Page. The client locale is {}.",
				locale);
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotpassword");
		return model;

	}
	
	@RequestMapping(value = "/processforgotpassword", method = RequestMethod.POST)
	public ModelAndView processForgotPassword(Locale locale, HttpServletRequest request) throws EmailException {
		logger.info("Processing ForgotPassword Page. The client locale is {}.",
				locale);
		/**
		 * Step 1: Check whether the user used CAPTCHA and it is not the ROBOT
		 * who is wants to connect with our Web-site.
		 */
		logger.info("Validating Captcha!!");
		if (Utils.captchaValidatated(request)) {
			/**
			 * Step 2: Load user by Email
			 */
			String email = request.getParameter("email");
			if (email != null) {
				User user = userService.getUserByEmail(email);
				
				if (user != null) {
					
					/**
					 * Step 3: Check user is Enabled. Which means this user is a working user
					 *         Otherwise if a user is just created and its password request
					 *         is generated then this logic will destroy the application flow
					 */
					
					if (user.isEnabled()) {
						
						String data = user.getUsername() + ":" + user.getEmail()
								+ ":" + user.getCreated();
						String passwordAssistanceCode = encoder.encode(data);
						user.setVerificationHash(passwordAssistanceCode);
						
						/**
						 * Step 4: Saving the user in the DB with the passwordAssistanceCode
						 *         generated for password change request
						 */
						logger.info("Saving User");
						user = userService.create(user);
						
						/**
						 * Step 5: Send an Email for Email Verification
						 */
						logger.info("Sending Email for Password Change Request");
						MailSender mailSender = new MailSender();
						mailSender.sendPasswordChangeMessage(
								passwordAssistanceCode, user.getEmail(), user.getLastName(),
								user.getFirstName());						
					}
				}
			}
			 
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotpasswordProcessed");
		return model;

	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword(Locale locale, HttpServletRequest request) throws EmailException {
		logger.info("Processing ForgotPassword request. The client locale is {}.",
				locale);
//		Date passwordChangeRequestDateTime = new Date();
		
		ModelAndView mav = new ModelAndView();
		String arb = request.getParameter("arb");
		if (arb != null) {
			User user = userService.getUserByVerificationCode(arb);
			logger.debug("Is user available with " + arb
					+ (user == null ? ", Not available." : ", Available."));
			
			if (user != null) {
				/**
				 * Checking if the user password reset request is sent before 5 days ago,
				 * if YES then delete the verification code from user when user click on the
				 * password change link
				 *
				Date userCreationDate = user.getCreated();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(userCreationDate);
				calendar.set(Calendar.DAY_OF_YEAR,
						calendar.get(Calendar.DAY_OF_YEAR) + 5);
				
				if (calendar.getTime().getTime() <= passwordChangeRequestDateTime
						.getTime()) {
					user.setVerificationHash("");
					userService.create(user);

					mav.setViewName("badRequestPage");
					return mav;
				}
*/
				mav.setViewName("updatePasswordPage");
				mav.addObject("token", arb);
				return mav;
			}			
		}
		mav.setViewName("badRequestPage");
		return mav;
	}
	
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public String updatePassword(Locale locale, HttpServletRequest request) throws EmailException {
		logger.info("Processing Update User Password request.");
		

		String arb = request.getParameter("_TOKEN_ARB");
		String password = request.getParameter("password1");
		
		if (arb != null) {		
			User user = userService.getUserByVerificationCode(arb);
			
			logger.debug("Is user available with " + arb
					+ (user == null ? ", Not available." : ", Available."));
			
			if (user != null) {
				user.setPassword(encoder.encode(password));
				user.setVerificationHash("");
				userService.create(user);
				
				return "passwordUpdateSuccess";
			}			
		}
		return "badRequestPage";
	}

}
