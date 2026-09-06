package feylix.vn.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import feylix.vn.entity.User;
import feylix.vn.utils.JpaUtils;

public class UserDao {

	public User findByUsername(String username) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	public User checkLogin(String username, String password) {
		User user = findByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	public void update(User user) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(user);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	public boolean checkExistUsername(String username) {
	    EntityManager em = JpaUtils.getEntityManager();
	    try {
	        TypedQuery<Long> query = em.createQuery(
	            "SELECT COUNT(u) FROM User u WHERE u.username = :uname", Long.class);
	        query.setParameter("uname", username);
	        return query.getSingleResult() > 0;
	    } finally {
	        em.close();
	    }
	}

	public User findByEmail(String email) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	public boolean checkExistEmail(String email) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email",
					Long.class);
			query.setParameter("email", email);
			return query.getSingleResult() > 0;
		} finally {
			em.close();
		}
	}

	// Dung cho man Quen mat khau: cho phep nhap username HOAC email
	public User findByUsernameOrEmail(String value) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery(
					"SELECT u FROM User u WHERE u.username = :v OR u.email = :v", User.class);
			query.setParameter("v", value);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// Thêm user mới vào Database
	public void insert(User user) {
	    EntityManager em = JpaUtils.getEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.persist(user);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        throw e;
	    } finally {
	        em.close();
	    }
	}
}