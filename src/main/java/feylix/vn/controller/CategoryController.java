package feylix.vn.controller;

import feylix.vn.dao.CategoryDao;
import feylix.vn.entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet(urlPatterns = { "/admin/categories", "/admin/category/add", "/admin/category/edit",
		"/admin/category/insert", "/admin/category/update", "/admin/category/delete" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDao categoryDao = new CategoryDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		if (url.contains("/admin/categories")) {
			List<Category> list = categoryDao.findAll();
			req.setAttribute("listcate", list);
			req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);

		} else if (url.contains("/admin/category/add")) {
			req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);

		} else if (url.contains("/admin/category/edit")) {
			int id = Integer.parseInt(req.getParameter("id"));
			Category category = categoryDao.findById(id);
			req.setAttribute("cate", category);
			req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);

		} else if (url.contains("/admin/category/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			categoryDao.delete(id);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();

		if (url.contains("/admin/category/insert")) {
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));

			if (categoryname == null || categoryname.trim().isEmpty()) {
				req.setAttribute("error", "Vui lòng nhập tên danh mục!");
				req.getRequestDispatcher("/views/admin/category-add.jsp").forward(req, resp);
				return;
			}

			Category category = new Category();
			category.setCategoryname(categoryname);
			category.setStatus(status);

			// Upload ảnh
			Part filePart = req.getPart("images");
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists())
					uploadDir.mkdir();

				filePart.write(uploadPath + File.separator + fileName);
				category.setImages(fileName);
			}

			categoryDao.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");

		} else if (url.contains("/admin/category/update")) {
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			String categoryname = req.getParameter("categoryname");
			int status = Integer.parseInt(req.getParameter("status"));

			Category category = categoryDao.findById(categoryId);

			if (categoryname == null || categoryname.trim().isEmpty()) {
				req.setAttribute("error", "Vui lòng nhập tên danh mục!");
				req.setAttribute("cate", category);
				req.getRequestDispatcher("/views/admin/category-edit.jsp").forward(req, resp);
				return;
			}
			category.setCategoryname(categoryname);
			category.setStatus(status);

			Part filePart = req.getPart("images");
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists())
					uploadDir.mkdir();

				filePart.write(uploadPath + File.separator + fileName);
				category.setImages(fileName);
			}

			categoryDao.update(category);
			resp.sendRedirect(req.getContextPath() + "/admin/categories");
		}
	}
}