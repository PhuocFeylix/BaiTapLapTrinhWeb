package feylix.vn.dao;

import feylix.vn.entity.Product;
import feylix.vn.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductDao {

	// 1. Lay tat ca san pham
	public List<Product> findAll() {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p ORDER BY p.productId DESC",
					Product.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	// 2. Tim san pham theo ID
	public Product findById(int id) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			return em.find(Product.class, id);
		} finally {
			em.close();
		}
	}

	// 3. Them san pham moi
	public void insert(Product product) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.persist(product);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	// 4. Cap nhat san pham
	public void update(Product product) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			em.merge(product);
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	// 5. Xoa san pham
	public void delete(int id) {
		EntityManager em = JpaUtils.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			Product product = em.find(Product.class, id);
			if (product != null) {
				em.remove(product);
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

	// 6. Lấy 10 sản phẩm mới nhất (sắp xếp giảm dần theo ID hoặc Ngày tạo)
	public List<Product> getTop10New() {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Product> query = em
					.createQuery("SELECT p FROM Product p WHERE p.status = 1 ORDER BY p.productId DESC", Product.class);
			query.setMaxResults(10); // Giới hạn đúng 10 sản phẩm
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	// 7. Đếm tổng số sản phẩm active để tính tổng số trang
	public int countAll() {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.status = 1", Long.class);
			return query.getSingleResult().intValue();
		} finally {
			em.close();
		}
	}

	// 8. Lấy danh sách sản phẩm có phân trang (page: trang hiện tại, pageSize: 6)
	public List<Product> findAllPage(int page, int pageSize) {
		EntityManager em = JpaUtils.getEntityManager();
		try {
			TypedQuery<Product> query = em
					.createQuery("SELECT p FROM Product p WHERE p.status = 1 ORDER BY p.productId DESC", Product.class);

			// Tính vị trí bắt đầu lấy bản ghi (Offset)
			int offset = (page - 1) * pageSize;
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);

			return query.getResultList();
		} finally {
			em.close();
		}
	}

}