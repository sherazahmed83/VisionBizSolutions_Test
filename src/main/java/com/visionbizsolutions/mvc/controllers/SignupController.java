package com.visionbizsolutions.mvc.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.visionbizsolutions.MailSender;
import com.visionbizsolutions.Utils;
import com.visionbizsolutions.mvc.commands.Contact;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.service.UserService;

@Controller
public class SignupController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	private static final Logger logger = LoggerFactory
			.getLogger(SignupController.class);

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView loadSignupForm(Locale locale) {
		logger.info("Welcome to Signup Page. The client locale is {}.", locale);
		return new ModelAndView("signup", "command", new Contact());
	}

	@RequestMapping(value = "/isusernameexists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean isUsernameExists(@RequestBody String usernameJSONString) {
		logger.debug("Checking username!!");
		Object object = JSONValue.parse(usernameJSONString);
		JSONObject jsonObject = (JSONObject) object;

		String username = "";
		if (jsonObject.containsKey("username")) {
			Object objectValue = (Object) jsonObject.get("username");

			if (objectValue instanceof String) {
				String strValue = (String) objectValue;
				if (strValue != null && !strValue.trim().equals("")) {
					username = String.valueOf(strValue);
				}
			}
		}// end if username is there

		logger.debug("User provided username = " + username);

		return userService.isUsernameAvailable(username);
	}

	@RequestMapping(value = "/isemailexists", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean isEmailExists(@RequestBody String emailJSONString) {
		logger.debug("Checking email!!");
		Object object = JSONValue.parse(emailJSONString);
		JSONObject jsonObject = (JSONObject) object;

		String email = "";
		if (jsonObject.containsKey("email")) {
			Object objectValue = (Object) jsonObject.get("email");

			if (objectValue instanceof String) {
				String strValue = (String) objectValue;
				if (strValue != null && !strValue.trim().equals("")) {
					email = String.valueOf(strValue);
				}
			}
		}// end if email is there

		logger.debug("User provided email = " + email);

		return userService.isEmailAvailable(email);
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST, produces = "text/html")
	public String addUser(@ModelAttribute("SpringWeb") Contact contact,
			ModelMap model, HttpServletRequest request) throws Exception {
		logger.info("Processing Adding User request.");

		/**
		 * Step 1: Check whether the user used CAPTCHA and it is not the ROBOT
		 * who is wants to connect with our Web-site.
		 */
		logger.info("Validating Captcha!!");
		if (Utils.captchaValidatated(request)) {
			/**
			 * Step 2: Make a verification code
			 */
			logger.info("Generating Email Verification Code");
			contact.setCreated(new Date());

			String data = contact.getUsername() + ":" + contact.getEmail()
					+ ":" + contact.getCreated();
			String verificationCode = encoder.encode(data);
			contact.setVerificationHash(verificationCode);
			contact.setPassword(encoder.encode(contact.getPassword()));
//			String salt = String.valueOf(Utils.getSalt());
//			contact.setPassword(mdPassEncoder.encodePassword(contact.getPassword(), salt));
//			contact.setSalt(salt);
			
			/**
			 * Step 3: Add the user in the DB, Put enabled = false;
			 */
			logger.info("Saving User");
			User user = userService.create(contact);

			/**
			 * Step 4: Send an Email for Email Verification
			 */
			logger.info("Sending Email for Email Verification");
			MailSender mailSender = new MailSender();
			String message = mailSender.sendEmailVerificationMessage(
					verificationCode, user.getEmail(), user.getLastName(),
					user.getFirstName());

			if (message != null) {
				logger.info("Redirecting user to account created page.");
				return "accountCreated";
			}

			return "signup";
		} else {
			model.addAttribute("message",
					"Captcha Not accepted, Please try again!!");
			return "signup";
		}
	}

	@RequestMapping(value = "/verification")
	public String verifyUser(ModelMap model, HttpServletRequest request)
			throws Exception {
		logger.info("Processing Adding User request.");

		Date verificationRequestDateTime = new Date();
		String verificationCode = request.getParameter("verificationcode");
		
		logger.info("Verification Code received :  " + verificationCode);

		if (verificationCode != null) {
			User user = userService.getUserByVerificationCode(verificationCode);
			logger.debug("Is user available with " + verificationCode
					+ (user == null ? ", Not available." : ", Available."));
			if (user == null) {
				return "verificationError";
			} else {
				/**
				 * Checking if the user account is created before 5 days ago,
				 * if YES then delete that account when user click on the
				 * email verification link
				 */
				Date userCreationDate = user.getCreated();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(userCreationDate);
				calendar.set(Calendar.DAY_OF_YEAR,
						calendar.get(Calendar.DAY_OF_YEAR) + 5);
				if (calendar.getTime().getTime() <= verificationRequestDateTime
						.getTime()) {
					userService.delete(user);
					return "accountDeleted";
				} else {
					user.setEnabled(Boolean.TRUE);
					user.setVerificationHash("");
					userService.create(user);
					return "verificationSuccess";
				}
			}
		} else {
			return "verificationError";
		}
	}
}
