package feylix.vn.dao;

import feylix.vn.entity.Category;
import feylix.vn.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CategoryDao {

	public List<Category> findAll() {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	public Category findById(int id) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			return em.find(Category.class, id);
		} finally {
			em.close();
		}
	}

	public void insert(Category category) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(category);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public void update(Category category) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(category);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public void delete(int id) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Category category = em.find(Category.class, id);
			if (category != null) {
				em.remove(category);
			}
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}