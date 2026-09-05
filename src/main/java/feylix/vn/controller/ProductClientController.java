package feylix.vn.controller;

import feylix.vn.dao.ProductDao;
import feylix.vn.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao = new ProductDao();
	private static final int PAGE_SIZE = 6; // Số lượng sản phẩm trên 1 trang

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int page = 1;

		// Lấy tham số page trên URL (ví dụ: /product?page=2)
		String pageStr = req.getParameter("page");
		if (pageStr != null && !pageStr.isEmpty()) {
			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				page = 1;
			}
		}

		// Lấy tổng số sản phẩm và tính tổng số trang
		int totalProducts = productDao.countAll();
		int endPage = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

		// Lấy danh sách 6 sản phẩm của trang hiện tại
		List<Product> products = productDao.findAllPage(page, PAGE_SIZE);

		// Truyền các attribute sang file product-list-client.jsp
		req.setAttribute("products", products);
		req.setAttribute("currentPage", page);
		req.setAttribute("endPage", endPage);

		req.getRequestDispatcher("/views/product-list-client.jsp").forward(req, resp);
	}
}