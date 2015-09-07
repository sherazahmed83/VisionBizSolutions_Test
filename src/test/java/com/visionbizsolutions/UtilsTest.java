package com.visionbizsolutions;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.visionbizsolutions.mvc.commands.Contact;

public class UtilsTest {

	private static final Logger logger = LoggerFactory
			.getLogger(UtilsTest.class);

	@Test
	@DirtiesContext
	public void testEncryptionTemplate() throws Exception {
		Integer id = new Integer(1);
		String username = "sherazahmed1";
		String email = "sheraz.ahmed@visionbizsolutions.com";
		Date created = new Date();

		String data = id + ":" + username + ":" + email + ":" + created;

		byte[] salt = Utils.getSalt();
		byte[] hash = Utils.getHash(data, salt);
		logger.debug("The hash for data is " + hash);
		logger.debug("The salt is " + salt);
		String hashBase64 = Utils.byteToBase64(hash);
		String saltBase64 = Utils.byteToBase64(salt);
		logger.debug("The byteToBas64 hash is " + hashBase64);
		logger.debug("The byteToBas64 salt is " + saltBase64);
		
		//Step 2
		byte[] byteSalt = Utils.base64ToByte(saltBase64);
		byte[] dataHash = Utils.getHash(data, byteSalt);
		logger.debug("The hash for data is " + dataHash);
		logger.debug("The salt is " + byteSalt);
		
		logger.debug("" + Arrays.equals(hash, dataHash) + " " + hash.length);
		
		String JSON = "{\"company\":\"VISION BUSINESS SOLUTIONS\",\"address\":\"400 Thompson Creek Rd\",\"country\":\"United States\",\"province\":\"Maryland\",\"zip\":\"21666\",\"phone\":\"2028304056\",\"username\":\"sherazahmed\",\"preferred\":\"Email or Phone\",\"software\":\"Quickbooks\",\"about\":\"I am writing to get to know about how can I get my bookkeeping done is reasonable cost on weekly basis\"}";
		Contact contact = Utils.getContactObjectFromJSON(JSON);
		logger.debug("username -> " + contact.getUsername());
		
		String password = "12345678";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		logger.debug(hashedPassword);
		
		MessageDigestPasswordEncoder pe = new MessageDigestPasswordEncoder("MD5", true);
		salt = Utils.getSalt();
		String encodedPassword = pe.encodePassword(password, salt);
		boolean result = pe.isPasswordValid(encodedPassword, password, salt);
		logger.debug(salt + " " + String.valueOf(salt) + " " + encodedPassword + " " + result);

		String block_time = "12/09/2014";
		String current_time = "05/09/2015";
		//31 + 31 + 27 + 31 + 30
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = format.parse(block_time);
		Date date2 = format.parse(current_time);
		long difference = date2.getTime() - date1.getTime();
		logger.debug("" + difference/1000/60);
		logger.debug("Days " + TimeUnit.MILLISECONDS.toDays(difference) + "   " + (31+31+27+31+30));
		
		

	}

}
