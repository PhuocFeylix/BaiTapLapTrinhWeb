package feylix.vn.controller;

import feylix.vn.dao.CategoryDao;
import feylix.vn.dao.ProductDao;
import feylix.vn.entity.Category;
import feylix.vn.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/products", "/admin/product/add", "/admin/product/edit", "/admin/product/insert", "/admin/product/delete" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ProductAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao = new ProductDao();
	private CategoryDao categoryDao = new CategoryDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		if (url.contains("/admin/products")) {
			List<Product> list = productDao.findAll();
			req.setAttribute("listproduct", list);
			req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/add")) {
			List<Category> categories = categoryDao.findAll();
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Product product = productDao.findById(id);
			List<Category> categories = categoryDao.findAll();

			req.setAttribute("product", product);
			req.setAttribute("categories", categories);
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);

		} else if (url.contains("/admin/product/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			productDao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		if (url.contains("/admin/product/insert")) {
			String name = req.getParameter("productName");
			String description = req.getParameter("description");
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			int status = Integer.parseInt(req.getParameter("status"));

			double price;
			int quantity;
			try {
				price = Double.parseDouble(req.getParameter("price"));
				quantity = Integer.parseInt(req.getParameter("quantity"));
			} catch (NumberFormatException e) {
				req.setAttribute("error", "Giá bán và số lượng phải là số hợp lệ!");
				req.setAttribute("oldProductName", name);
				req.setAttribute("categories", categoryDao.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}

			// ===== VALIDATION PHIA SERVER =====
			if (name == null || name.trim().isEmpty()) {
				req.setAttribute("error", "Vui lòng nhập tên sản phẩm!");
				req.setAttribute("categories", categoryDao.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}
			if (price < 0) {
				req.setAttribute("error", "Giá bán không được âm!");
				req.setAttribute("oldProductName", name);
				req.setAttribute("categories", categoryDao.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}
			if (quantity < 0) {
				req.setAttribute("error", "Số lượng không được âm!");
				req.setAttribute("oldProductName", name);
				req.setAttribute("oldPrice", price);
				req.setAttribute("categories", categoryDao.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}

			Category category = categoryDao.findById(categoryId);
			if (category == null) {
				req.setAttribute("error", "Danh mục không hợp lệ!");
				req.setAttribute("oldProductName", name);
				req.setAttribute("oldPrice", price);
				req.setAttribute("oldQuantity", quantity);
				req.setAttribute("categories", categoryDao.findAll());
				req.getRequestDispatcher("/views/admin/product-add.jsp").forward(req, resp);
				return;
			}

			Product product = new Product();
			product.setProductName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setDescription(description);
			product.setCategory(category);
			product.setStatus(status);

			// Upload hinh anh
			Part filePart = req.getPart("images");
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists())
					uploadDir.mkdir();

				filePart.write(uploadPath + File.separator + fileName);
				product.setImages(fileName);
			}

			productDao.insert(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
			// Ghi chu: "/admin/product/update" duoc xu ly rieng boi ProductUpdateController
		}
	}
}