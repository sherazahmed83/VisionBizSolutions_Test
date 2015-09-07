package com.visionbizsolutions.orm.jpa.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

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
import com.visionbizsolutions.orm.jpa.bean.UserRole;
import com.visionbizsolutions.orm.jpa.config.PersistenceJPAConfigXml;

//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-database.xml")

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfigXml.class }, loader = AnnotationConfigContextLoader.class)
public class UserDaoTest {

	private static final Logger logger = LoggerFactory
			.getLogger(UserDaoTest.class);

	@Autowired
	protected UserDao userDao = null;

	@Test
	@DirtiesContext
	public void testHibernateTemplate() {
		assertNotNull("User DAO is null.", userDao);

		Collection<User> lUsers = userDao.findUsers();

		int expected = 1;

		assertNotNull("Users list is null.", lUsers);
		assertEquals("Number of users should be " + expected + ".", expected,
				lUsers.size());

		Integer firstId = new Integer(1);

		for (User user : lUsers) {
			assertNotNull("User is null.", user);

			if (firstId.equals(user.getId())) {
				String firstName = "Sheraz";
				String lastName = "Ahmed";
				String userName = "sherazahmed";
				String company = "VISION BUSINESS SOLUTIONS";
				String country = "United States";
				String address = "400 Thompson Creek Rd";
                String province = "Maryland";
                Integer zip = new Integer(21666);
                String phone = "2028304056";
                String email = "sheraz.ahmed@visionbizsolutions.com";
                String preferred = "Email or Phone";
                String stage = "Decided to outsource";
                String software = "Quickbooks";
                String frequency = "Weekly";
                String about = "I am writing to get to know about how can I get my bookkeeping done is reasonable cost on weekly basis";
                Boolean enabled = new Boolean(Boolean.TRUE);

                
				int expectedRoles = 2;

				assertEquals("User first name should be " + firstName + ".",
						firstName, user.getFirstName());
				assertEquals("User last name should be " + lastName + ".",
						lastName, user.getLastName());

				assertEquals("User Username should be " + userName + ".",
						userName, user.getUsername());
				
				assertEquals("User company should be " + company + ".",
						company, user.getCompany());

				assertEquals("User country should be " + country + ".",
						country, user.getCountry());

				assertEquals("Address address should be '" + address + "'.", address, user.getAddress());
            	
            	assertEquals("Address province should be '" + province + "'.", province, user.getProvince());
            	
            	assertEquals("Address zip should be '" + zip + "'.", zip, user.getZip());
            	
				assertEquals("User phone should be " + phone + ".",
						phone, user.getPhone());

				assertEquals("User email should be " + email + ".",
						email, user.getEmail());

				assertEquals("User preferred should be " + preferred + ".",
						preferred, user.getPreferred());

				assertEquals("User stage should be " + stage + ".",
						stage, user.getStage());

				assertEquals("User software should be " + software + ".",
						software, user.getSoftware());

				assertEquals("User frequency should be " + frequency + ".",
						frequency, user.getFrequency());
			
				assertEquals("User about should be " + about + ".",
						about, user.getAbout());

				assertEquals("User enabled should be " + enabled + ".",
						enabled, user.isEnabled());

				
				assertNotNull("User's roles list is null.", user.getAuthorities());
						assertEquals("Number of user's roles list should be "
						+ expectedRoles + ".", expectedRoles, user.getAuthorities()
						.size());
				
				Integer firstRoleId = new Integer(1);
				Integer secondRoleId = new Integer(2);
				
				String userRole = "ROLE_USER";
				String adminRole = "ROLE_ADMIN";
				
				for(UserRole role : user.getAuthorities()){
					assertNotNull("Role is null.", role);
					
					if(firstRoleId.equals(role.getId())){
						assertEquals("Role should be " + userRole + ".",
								userRole, role.getAuthority());
					}

					if(secondRoleId.equals(role.getId())){
						assertEquals("Role should be " + adminRole + ".",
								adminRole, role.getAuthority());
					}
				}// end of roles loop

			}
			logger.debug(user.toString());
		}// end of users loop
		
		User user = userDao.findUserById(firstId);
		userDao.delete(user);
	}
}
