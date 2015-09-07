package com.visionbizsolutions.orm.jpa.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.bean.UserRole;
import com.visionbizsolutions.orm.jpa.dao.UserDao;
import com.visionbizsolutions.orm.jpa.dao.UserDetailsDao;
import com.visionbizsolutions.orm.jpa.service.bean.UserBean;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserDetailsDao userDetailsDao;
	
	@Autowired
	private UserDao dao;
	
	
	private User getUser(Collection<User> users) {
		User user = null;
		
		if (users != null && users.size() == 1) {
			Iterator<User> iterator = users.iterator();
			if (iterator.hasNext()) {
				user = iterator.next();
			}
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.debug("Fetching User for username " + username);
		Collection<User> users = dao.findUsersByUsername(username);
		User user = getUser(users);

		if (user != null) {
			logger.info("Found user details in database !!");
			
			if (!isUserAccountLocked(user)){
				userDetailsDao.resetFailAttempts(username, true);
				users = dao.findUsersByUsername(username);
				user = getUser(users);
			}
			
			UserBean userBean = new UserBean(
					user.getUsername(), user.getPassword(), user.isEnabled(),
					user.isAccountNonExpired(), user.isCredentialsNonExpired(),
					user.isAccountNonLocked(),
					getGrantedAuthorities(user.getAuthorities()));

			/* accountNonExpired = true */
			/* credentialsNonExpired = true */
			/* accountNonLocked = true */
			return userBean;
		}
		return null;
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(Set<UserRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (UserRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }
	
	private boolean isUserAccountLocked(User user){
		return user.isAccountNonLocked();
	}
	
}
