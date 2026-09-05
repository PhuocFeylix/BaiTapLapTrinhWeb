package feylix.vn.controller;

import feylix.vn.dao.ProductDao;
import feylix.vn.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/product/detail")
public class ProductDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao = new ProductDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");

		if (idStr != null && !idStr.isEmpty()) {
			try {
				int id = Integer.parseInt(idStr);
				Product product = productDao.findById(id);

				if (product != null) {
					req.setAttribute("product", product);
					req.getRequestDispatcher("/views/product-detail.jsp").forward(req, resp);
					return;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// Nếu không tìm thấy sản phẩm -> Chuyển về trang danh sách
		resp.sendRedirect(req.getContextPath() + "/product");
	}
}