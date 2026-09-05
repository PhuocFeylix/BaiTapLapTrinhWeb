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
			double price = Double.parseDouble(req.getParameter("price"));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String description = req.getParameter("description");
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			int status = Integer.parseInt(req.getParameter("status"));

			Category category = categoryDao.findById(categoryId);

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

		} else if (url.contains("/admin/product/update")) {
			int productId = Integer.parseInt(req.getParameter("productId"));
			String name = req.getParameter("productName");
			double price = Double.parseDouble(req.getParameter("price"));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String description = req.getParameter("description");
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			int status = Integer.parseInt(req.getParameter("status"));

			Product product = productDao.findById(productId);
			Category category = categoryDao.findById(categoryId);

			product.setProductName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setDescription(description);
			product.setCategory(category);
			product.setStatus(status);

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

			productDao.update(product);
			resp.sendRedirect(req.getContextPath() + "/admin/products");
		}
	}
}