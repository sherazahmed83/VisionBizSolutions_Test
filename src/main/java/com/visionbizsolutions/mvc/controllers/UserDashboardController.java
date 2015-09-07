package com.visionbizsolutions.mvc.controllers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.visionbizsolutions.FileUploadProcessorDelegator;
import com.visionbizsolutions.Utils;
import com.visionbizsolutions.mvc.commands.Contact;
import com.visionbizsolutions.mvc.models.FileMeta;
import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.service.UserService;
import com.visionbizsolutions.security.filter.DashboardSessionManagmentFilter;

@Controller
public class UserDashboardController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private DashboardSessionManagmentFilter filter = new DashboardSessionManagmentFilter();
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserDashboardController.class);

	@RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
	public ModelAndView defaultPage(Locale locale, HttpServletRequest request) {
		logger.info("Welcome to welcome Page. The client locale is {}.", locale);
		Contact contact = new Contact();
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();

			String username = userDetail.getUsername();
			logger.info("username taken from SecurityContext " + username);
			User user = userService.getUserByUsername(username);
			if (user != null) {
				Utils.populateContact(user, contact);
			}
			logger.info("Contact object populated with username "
					+ contact.getUsername());
		}
		ModelAndView mav = new ModelAndView("userMain");
		mav.addObject("countriesMap", Utils.getCountriesMap());
		mav.addObject("command", contact);
		mav.addObject(DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_PARAM_NAME, filter.generateSecurityToken(request, encoder));

		return mav;

	}

	@RequestMapping(value = "/dashboard/updateuser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateUserProfile(@RequestBody String contactObjectJSON,
			ModelMap model, HttpServletRequest request) {
		
		logger.info("Updating User Profile for JSON" + contactObjectJSON);
		
		if(filter.isUserSessionExpired(request, encoder)){			
			return "You session has expired. Please login again to proceed!!";
		}
		
		Contact contact = null;
		try {
			contact = Utils.getContactObjectFromJSON(contactObjectJSON);

			logger.info("Updating User Profile for username = ",
					contact.getUsername());
			User dbUser = userService.getUserByUsername(contact.getUsername());
			Utils.populateUser(contact, dbUser);

			User user = userService.create(dbUser);
			Utils.populateContact(user, contact);
		} catch (Exception e) {
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			return "Error in updating user details in database.";
		}
		model.addAttribute("command", contact);
		return "true";
	}

	@RequestMapping(value = { "/dashboard/changePassword" }, method = RequestMethod.GET)
	public ModelAndView changePassword(Locale locale, HttpServletRequest request) {
		logger.info(
				"Welcome to Change Password Page. The client locale is {}.",
				locale);
		ModelAndView mav = new ModelAndView("changePassword");
		mav.addObject(DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_PARAM_NAME, filter.generateSecurityToken(request, encoder));
		return mav;
	}

	@RequestMapping(value = "/dashboard/updatepassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean updatePassword(@RequestBody String passwordsJSON) {
		logger.info("Updating User Passwords for JSON" + passwordsJSON);

		try {

			String passwords[] = Utils.getPasswordsFromJSON(passwordsJSON);
			logger.info("Passwords received : " + Arrays.toString(passwords));
			// check if user is login
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();

			if (auth instanceof UsernamePasswordAuthenticationToken) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				logger.debug("Processing for user's details: " + userDetail);

				String username = userDetail.getUsername();
				logger.info("Username taken from SecurityContext " + username);
				User user = userService.getUserByUsername(username);
				
//				mdPassEncoder.isPasswordValid(user.getPassword(), passwords[0], user.getSalt())){
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//				mdPassEncoder.encodePassword(passwords[1], salt));

				if (user != null) {
					if (user.getPassword() != null && encoder
							.matches(passwords[0], user.getPassword())) {
 
//						String salt = String.valueOf(Utils.getSalt());
//						user.setSalt(salt);

						user.setPassword(encoder.encode(passwords[1]));
						userService.create(user);
					} else {
						return false;
					}
				}
			}

		} catch (Exception e) {
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/dashboard/uploadFilePage", method = RequestMethod.GET)
	public ModelAndView loadUploadFileForm(Locale locale, HttpServletRequest request) {
		logger.info("Welcome to Upload Page. The client locale is {}.", locale);

		ModelAndView mav = new ModelAndView("uploadFilePage");
		mav.addObject(DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_PARAM_NAME, filter.generateSecurityToken(request, encoder));
		return mav;
	}

	@RequestMapping(value = "/dashboard/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(			
			HttpServletRequest request, HttpServletResponse response)
			throws FileUploadException, Exception {

		
		String dirPath = null;
		Long fileSizeAllowed = null;
		try {
			dirPath = Utils.loadProperties().getProperty(
					"user.file.storing.dir");
			fileSizeAllowed = Long.parseLong(Utils.loadProperties()
					.getProperty("user.file.storing.upload.size.bytes"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Utils.getStackTrace(e.fillInStackTrace()));
			FileMeta meta = new FileMeta();
			meta.setFileName("");
			meta.setFileType("We applogies for inconvenience, "
					+ "there is some error at the server-side. Please try again after some time. ");
			LinkedList<FileMeta> files = new LinkedList<FileMeta>();
			files.add(meta);
			return files;
		}
		
		logger.info("Processing uploaded file");
		
		String fileUploadRequestType = null;
	
		if (request instanceof MultipartHttpServletRequest) {
			fileUploadRequestType = FileUploadProcessorDelegator.TYPE_MULTIPART_HTTP_SERVLET_REQUEST;
		} else {
			fileUploadRequestType = FileUploadProcessorDelegator.TYPE_HTTP_SERVLET_REQUEST;
		}
		
		FileUploadProcessorDelegator delegator = new FileUploadProcessorDelegator(fileUploadRequestType);
		
		return delegator.processUploadedFile(request, fileSizeAllowed, dirPath, userService);

	}
	
	
	@RequestMapping(value = { "/dashboard/getFilesLog" }, method = RequestMethod.GET)
	public ModelAndView getUploadedFilesLog(Locale locale) {
		logger.info("Welcome to files log Page. The client locale is {}.", locale);
		
		ModelAndView mav = new ModelAndView();
		
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();

			String username = userDetail.getUsername();
			logger.info("Username taken for checking user is verified " + username);
			User user = userService.getUserByUsername(username);
			if(user != null){
				mav.addObject("filesSet", user.getFiles());				
			}
		}
		mav.setViewName("filesLog");
		return mav;
	}

}
