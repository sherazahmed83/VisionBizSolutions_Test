package com.visionbizsolutions.security.filter;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.visionbizsolutions.UtilsTest;

public class DashboardSessionManagmentFilterTest {

	private static final Logger logger = LoggerFactory
			.getLogger(UtilsTest.class);

	@Test
	@DirtiesContext
	public void testWithSameSession() throws Exception {
		logger.debug("Method testWithSameSession()\n");
		MockHttpServletRequest request = new MockHttpServletRequest();
//		MockHttpSession session = new MockHttpSession(request.getServletContext());
		HttpSession httpSession = request.getSession();
		logger.debug(httpSession.getId().toString());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

		DashboardSessionManagmentFilter filter = new DashboardSessionManagmentFilter();
		String token = filter.generateSecurityToken(request, encoder);
		
		request = new MockHttpServletRequest();
		logger.debug(httpSession.getId().toString());
		request.setSession(httpSession);
		logger.debug(request.getSession().getId().toString());

		request.addParameter(
				DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_PARAM_NAME,
				token);

		boolean result = filter.isUserSessionExpired(request, encoder);
		logger.debug("User Session verfied result = " + result);
		assertEquals(true, result);
	}

	@Test
	@DirtiesContext
	public void testWithDifferentSession() throws Exception {
		logger.debug("\nMethod testWithDifferentSession()\n");
		MockHttpServletRequest request = new MockHttpServletRequest();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

		DashboardSessionManagmentFilter filter = new DashboardSessionManagmentFilter();
		String token = filter.generateSecurityToken(request, encoder);

		request = new MockHttpServletRequest();

		request.addParameter(
				DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_PARAM_NAME,
				token);
		
		
		boolean result = filter.isUserSessionExpired(request, encoder);
		logger.debug("User Session verfied result = " + result);
		
		assertEquals(false, result);
	}

}
