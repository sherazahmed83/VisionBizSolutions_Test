package com.visionbizsolutions.orm.jpa.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.visionbizsolutions.orm.jpa.bean.User;
import com.visionbizsolutions.orm.jpa.bean.UserFile;
import com.visionbizsolutions.orm.jpa.bean.UserRole;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

	private EntityManager em = null;

	/**
	 * Sets the entity manager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public User findUserById(Integer id) {
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsers() {
		return em.createQuery(
				"select u from User u order by u.lastName, u.firstName")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsers(int startIndex, int maxResults) {
		return em
				.createQuery(
						"select u from User u order by u.lastName, u.firstName")
				.setFirstResult(startIndex).setMaxResults(maxResults)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsersByLastName(String lastName) {
		return em
				.createQuery(
						"select u from User u where u.lastName = :lastName order by u.lastName, u.firstName")
				.setParameter("lastName", lastName).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsersByUsername(String username) {
		return em
				.createQuery(
						"select u from User u where u.username = :username order by u.lastName, u.firstName")
				.setParameter("username", username).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsersByEmail(String email) {
		return em
				.createQuery(
						"select u from User u where u.email = :email order by u.lastName, u.firstName")
				.setParameter("email", email).getResultList();
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User save(User user) {
		return em.merge(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(User user) {
		em.remove(em.merge(user));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User saveRole(Integer id, UserRole role) {
		User user = findUserById(id);

		if (user.getAuthorities().contains(role)) {
			user.getAuthorities().remove(role);
		}

		user.getAuthorities().add(role);

		return save(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User deleteRole(Integer id, Integer roleId) {
		User user = findUserById(id);

        UserRole role = new UserRole();
        role.setId(roleId);

        if (user.getAuthorities().contains(role)) {
            for (UserRole a : user.getAuthorities()) {
                if (a.getId().equals(roleId)) {
                    em.remove(a);
                    user.getAuthorities().remove(role);
                    
                    break;
                }
            }
        }

        return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findUsersByVerficationCode(String code) {
		return em
				.createQuery(
						"select u from User u where u.verificationHash = :verificationHash order by u.lastName, u.firstName")
				.setParameter("verificationHash", code).getResultList();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User saveFile(Integer id, UserFile file) {
		User user = findUserById(id);

		if (user.getFiles().contains(file)) {
			user.getFiles().remove(file);
		}

		user.getFiles().add(file);

		return save(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public User deleteFile(Integer id, Integer fileId) {
		User user = findUserById(id);

        UserFile file = new UserFile();
        file.setId(fileId);

        if (user.getFiles().contains(file)) {
            for (UserFile a : user.getFiles()) {
                if (a.getId().equals(fileId)) {
                    em.remove(a);
                    user.getFiles().remove(file);
                    
                    break;
                }
            }
        }

        return user;
	}

	


}
