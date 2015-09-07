package com.visionbizsolutions.orm.jpa.dao;

import com.visionbizsolutions.orm.jpa.bean.UserAttempts;

public interface UserDetailsDao {
	
	void updateFailAttempts(String username);
	void resetFailAttempts(String username, boolean isActivateTimeCheckRequired);
	UserAttempts getUserAttempts(String username);
}
