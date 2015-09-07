package com.visionbizsolutions.mvc.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.visionbizsolutions.MailSender;
import com.visionbizsolutions.Utils;
import com.visionbizsolutions.mvc.commands.Contact;

@Controller
public class ContactUsController {

	private static final Logger logger = LoggerFactory
			.getLogger(ContactUsController.class);

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView loadContactForm(Locale locale) {
		logger.info("Welcome to ContactUs Page. The client locale is {}.",
				locale);
		return new ModelAndView("contact", "command", new Contact());
	}

	@RequestMapping(value = "/requestEnquiry", method = RequestMethod.POST)
	public String requestEnquiry(@ModelAttribute("SpringWeb") Contact contact,
			ModelMap model, HttpServletRequest request) throws Exception {
		logger.info("Processing your Enquiry request.");
		
		if (Utils.captchaValidatated(request)) {
			MailSender mailSender = new MailSender();
			mailSender.sendEnquiryMailToInfo(contact.toString());
			
			return "thankyou";
		} else {
			model.addAttribute("message", "Captcha Not accepted, Please try again!!");
			return "contact";
		}

	}

}
