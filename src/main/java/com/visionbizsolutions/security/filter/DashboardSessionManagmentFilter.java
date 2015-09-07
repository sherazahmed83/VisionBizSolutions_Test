package com.visionbizsolutions.security.filter;

import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.visionbizsolutions.Utils;

public final class DashboardSessionManagmentFilter {

	public static final String SESSION_AUTHENTICATION_PARAM_NAME = "DASHBOARD-AUTH-TOKEN";
	public static final String SESSION_AUTHENTICATION_TOKEN_DELIMETER = "-*-%sham$ZEE-";

	private static final Logger logger = LoggerFactory
			.getLogger(DashboardSessionManagmentFilter.class);

	public String generateSecurityToken(HttpServletRequest request,
			BCryptPasswordEncoder encoder) {
		logger.debug("Generating Security Token");
		HttpSession session = request.getSession();

		String tokenString = generateTokenString(session.getId(),
				Utils.genSecureRandom(), new Date().getTime());

		return encoder.encode(tokenString)
				+ SESSION_AUTHENTICATION_TOKEN_DELIMETER + tokenString;
	}

	public boolean isUserSessionExpired(HttpServletRequest request,
			BCryptPasswordEncoder encoder) {
		logger.debug("Verifying User Session");
		HttpSession session = request.getSession();

		String token = request.getParameter(SESSION_AUTHENTICATION_PARAM_NAME);
		if (token == null) {
			return false;
		}

		logger.debug(token);
		String token1 = token
				.substring(
						0,
						token.indexOf(DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_TOKEN_DELIMETER));
		String token2 = token
				.substring(
						token.indexOf(DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_TOKEN_DELIMETER)
								+ DashboardSessionManagmentFilter.SESSION_AUTHENTICATION_TOKEN_DELIMETER
										.length(), token.length());

		String encodedTokenString = token1;
		String tokenStringTokens[] = token2.split("~!-");
		String newTokenString = generateTokenString(session.getId(),
				tokenStringTokens[1], Long.parseLong(tokenStringTokens[2]));

		if (encoder.matches(newTokenString, encodedTokenString)) {
			return true;
		}

		return false;
	}

	private String generateTokenString(String id, String random, long time) {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append("~!-");
		sb.append(random);
		sb.append("~!-");
		sb.append(time);

		return sb.toString();
	}

}
