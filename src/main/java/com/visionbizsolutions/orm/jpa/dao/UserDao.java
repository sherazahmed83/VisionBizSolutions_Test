package com.visionbizsolutions.orm.jpa.dao;

import java.util.Collection;

import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.bean.UserAttempts;
import com.visionbizsolutions.orm.jpa.bean.UserFile;
import com.visionbizsolutions.orm.jpa.bean.UserRole;

public interface UserDao {

	 /**
     * Find User by id.
     */
    public User findUserById(Integer id);

    /**
     * Find users.
     */
    public Collection<User> findUsers();

    /**
     * Find users using a start index and max number of results.
     */
    public Collection<User> findUsers(final int startIndex, final int maxResults);

    /**
     * Find user by last name.
     */
    public Collection<User> findUsersByLastName(String lastName);

    /**
     * Find user by username.
     */
    public Collection<User> findUsersByUsername(String username);

    /**
     * Find user by email.
     */
    public Collection<User> findUsersByEmail(String email);

    /**
     * Find user by verification code.
     */
    public Collection<User> findUsersByVerficationCode(String code);

    /**
     * Saves user.
     */
    public User save(User user);

    /**
     * Deletes user.
     */
    public void delete(User user);

    /**
     * Saves role to user by adding or updating record.
     */
    public User saveRole(Integer id, UserRole role);

    /**
     * Deletes role.
     */
    public User deleteRole(Integer id, Integer roleId);

    /**
     * Saves file to user by adding or updating record.
     */
    public User saveFile(Integer id, UserFile file);

    /**
     * Deletes file.
     */
    public User deleteFile(Integer id, Integer fileId);

	
}
