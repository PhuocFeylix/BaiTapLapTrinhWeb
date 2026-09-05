package feylix.vn.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import feylix.vn.dao.CategoryDao;
import feylix.vn.dao.ProductDao;
import feylix.vn.entity.Category;
import feylix.vn.entity.Product;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(urlPatterns = { "/admin/product/update" })
public class ProductUpdateController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Khởi tạo trực tiếp đối tượng DAO (Không qua Impl)
	private ProductDao productDao = new ProductDao();
	private CategoryDao categoryDao = new CategoryDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		try {
			int productId = Integer.parseInt(req.getParameter("productId"));
			String productName = req.getParameter("productName");
			double price = Double.parseDouble(req.getParameter("price"));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String description = req.getParameter("description");
			int status = Integer.parseInt(req.getParameter("status"));
			int categoryId = Integer.parseInt(req.getParameter("categoryId"));
			String oldImage = req.getParameter("oldImage");

			Product product = productDao.findById(productId);
			if (product == null) {
				resp.sendRedirect(req.getContextPath() + "/admin/products");
				return;
			}

			Part part = req.getPart("imageFile");
			String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

			if (fileName != null && !fileName.isEmpty()) {
				String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
				String uploadPath = req.getServletContext().getRealPath("") + File.separator + "uploads";
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}

				part.write(uploadPath + File.separator + uniqueFileName);
				product.setImages(uniqueFileName);
			} else {
				product.setImages(oldImage);
			}

			product.setProductName(productName);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setDescription(description);
			product.setStatus(status);

			Category category = categoryDao.findById(categoryId);
			product.setCategory(category);

			productDao.update(product);

			resp.sendRedirect(req.getContextPath() + "/admin/products");

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm!");
			req.getRequestDispatcher("/views/admin/product-edit.jsp").forward(req, resp);
		}
	}
}