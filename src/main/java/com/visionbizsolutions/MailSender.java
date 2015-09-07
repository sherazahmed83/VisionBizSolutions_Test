package com.visionbizsolutions;

import java.util.Properties;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

	private static final Logger logger = LoggerFactory
			.getLogger(MailSender.class);

	private Properties mailProperties = null;
	private static String username = null;
	private static String password = null;
	private static String host = null;

	public MailSender() {
		logger.debug("Loading application configuration properties");
		mailProperties = Utils.loadProperties();
		username = mailProperties.getProperty("mail.auth.username");
		password = mailProperties.getProperty("mail.auth.password");
		host = mailProperties.getProperty("mail.smtp.host");
	}

	public void sendEnquiryMailToInfo(String emailText) throws Exception {

		String to = mailProperties.getProperty("mail.enquiry.send.to");
		String from = mailProperties.getProperty("mail.enquiry.send.from");
		String subject = mailProperties.getProperty("mail.enquiry.subject");
		String footer = mailProperties.getProperty("mail.enquiry.footer");

		Email email = new SimpleEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.addTo(to);
		email.setFrom(from);
		email.setSubject(subject);
		email.setSSLOnConnect(true);

		// Now set the actual message
		email.setMsg(emailText + footer);

		logger.info("Sending Message....");
		email.send();
		logger.info("Message sent successfully....");

	}// sendEnquiryMailToInfo()

	public String sendEmailVerificationMessage(String verificationCode,
			String toEmailAddress, String lastName, String firstName)
			throws EmailException {

		String from = mailProperties.getProperty("mail.verification.send.from");
		String subject = mailProperties
				.getProperty("mail.verification.subject");
		String verificationURL = mailProperties
				.getProperty("mail.verification.url");
		String initHtml = mailProperties
				.getProperty("mail.verification.init.html");
		String para1 = mailProperties
				.getProperty("mail.verification.body.para1");
		String para2 = mailProperties
				.getProperty("mail.verification.body.para2");
		String clickableTest = mailProperties
				.getProperty("mail.verification.body.clickable.text");
		String para3 = mailProperties
				.getProperty("mail.verification.body.para3");
		String para4 = mailProperties
				.getProperty("mail.verification.body.para4");
		String footer = mailProperties
				.getProperty("mail.verification.body.footer");
		String endHtml = mailProperties
				.getProperty("mail.verification.end.html");
		String textMessage = mailProperties
				.getProperty("mail.verification.body.textMessage");

		StringBuilder emailText = new StringBuilder();
		emailText.append(initHtml);
		emailText.append(para1);
		emailText.append(para2);
		emailText.append("<a href=\"");
		emailText.append(verificationURL);
		emailText.append(verificationCode);
		emailText.append("\">");
		emailText.append(clickableTest);
		emailText.append("</a>");
		emailText.append(para3);
		emailText.append("<a href=\"");
		emailText.append(verificationURL);
		emailText.append(verificationCode);
		emailText.append("\">");
		emailText.append(verificationURL);
		emailText.append(verificationCode);
		emailText.append("</a>");
		emailText.append(para4);
		emailText.append(footer);
		emailText.append(endHtml);

		/**
		 * Making an HTML Email by using commons-email library
		 */
		HtmlEmail email = new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.addTo(toEmailAddress, lastName + "," + firstName);
		email.setFrom(from);
		email.setSubject(subject);
		email.setSSLOnConnect(true);

		// set the html message
		email.setHtmlMsg(emailText.toString());
		// set the alternative message
		email.setTextMsg(textMessage);

		logger.info("Sending Message....");

		return email.send();
	}

	public String sendPasswordChangeMessage(String passwordAssistanceCode,
			String toEmailAddress, String lastName, String firstName)
			throws EmailException {

		String from = mailProperties.getProperty("mail.password.assistance.send.from");
		String subject = mailProperties
				.getProperty("mail.password.assistance.subject");
		String passwordAssistanceURL = mailProperties
				.getProperty("mail.password.assistance.url");
		String initHtml = mailProperties
				.getProperty("mail.password.assistance.init.html");
		String para1 = mailProperties
				.getProperty("mail.password.assistance.body.para1");
		String para2 = mailProperties
				.getProperty("mail.password.assistance.body.para2");
		String para3 = mailProperties
				.getProperty("mail.password.assistance.body.para3");
		String para4 = mailProperties
				.getProperty("mail.password.assistance.body.para4");
		String footer = mailProperties
				.getProperty("mail.password.assistance.body.footer");
		String endHtml = mailProperties
				.getProperty("mail.password.assistance.end.html");
		String textMessage = mailProperties
				.getProperty("mail.password.assistance.body.textMessage");

		StringBuilder emailText = new StringBuilder();
		emailText.append(initHtml);
		emailText.append(para1);
		emailText.append(para2);
		emailText.append("<a href=\"");
		emailText.append(passwordAssistanceURL);
		emailText.append(passwordAssistanceCode);
		emailText.append("\">");
		emailText.append(passwordAssistanceURL);
		emailText.append(passwordAssistanceCode);
		emailText.append("</a>");
		emailText.append(para3);
		emailText.append(para4);
		emailText.append(footer);
		emailText.append(endHtml);

		/**
		 * Making an HTML Email by using commons-email library
		 */
		HtmlEmail email = new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(username, password);
		email.addTo(toEmailAddress, lastName + "," + firstName);
		email.setFrom(from);
		email.setSubject(subject);
		email.setSSLOnConnect(true);

		// set the html message
		email.setHtmlMsg(emailText.toString());
		// set the alternative message
		email.setTextMsg(textMessage);

		logger.info("Sending Message....");

		return email.send();
	}
	
}
