package feylix.vn.controller;

import feylix.vn.dao.ProductDao;
import feylix.vn.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/home", "/index", "" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDao productDao = new ProductDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Lấy danh sách 10 sản phẩm mới nhất
		List<Product> listTop10 = productDao.getTop10New();

		// Truyền dữ liệu sang giao diện JSP
		req.setAttribute("top10Products", listTop10);
		req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
}