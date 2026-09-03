package feylix.vn.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String u = req.getParameter("username");
		String p = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		// Kiểm tra xem trùng tài khoản không
		if (userDao.checkExistUsername(u)) {
			req.setAttribute("error", "Tên đăng nhập đã tồn tại!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		// Tạo đối tượng User mới
		User user = new User();
		user.setUsername(u);
		user.setPassword(p);
		user.setFullname(fullname);
		user.setPhone(phone);

		try {
			userDao.insert(user);
			// Đăng ký thành công -> Chuyển hướng sang trang đăng nhập
			resp.sendRedirect(req.getContextPath() + "/login");
		} catch (Exception e) {
			req.setAttribute("error", "Đã xảy ra lỗi khi đăng ký! Vui lòng thử lại.");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
		}
	}
}
