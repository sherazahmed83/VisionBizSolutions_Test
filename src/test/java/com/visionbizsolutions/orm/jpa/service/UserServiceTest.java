package com.visionbizsolutions.orm.jpa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.config.PersistenceJPAConfigXml;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfigXml.class }, loader = AnnotationConfigContextLoader.class)
public class UserServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceTest.class);

	@Autowired
	protected UserServiceImpl userService = null;

	@Test
	@DirtiesContext
	public void testService() {
		assertNotNull("User Service is null.", userService);
		
		String username = "sherazahmed";
		Boolean userNameNotAvailable = new Boolean(Boolean.FALSE);
		
		assertEquals("User username should not be available" + userNameNotAvailable + ".",
				userNameNotAvailable, userService.isUsernameAvailable(username)); 

		String email = "sheraz.ahmed@visionbizsolutions.com";
		Boolean emailNotAvailable = new Boolean(Boolean.FALSE);
		
		assertEquals("User email should not be available" + emailNotAvailable + ".",
				emailNotAvailable, userService.isEmailAvailable(email)); 
		
		
		
	}
}
