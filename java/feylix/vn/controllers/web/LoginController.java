package feylix.vn.controllers.web;

import java.io.IOException;

import feylix.vn.model.User;
import feylix.vn.service.UserService;
import feylix.vn.service.impl.UserServiceImpl;
import feylix.vn.utils.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = {"","/login"})
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Kiểm tra Session xem đã đăng nhập chưa
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		// 2. Kiểm tra Cookie ghi nhớ đăng nhập
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					session = req.getSession(true);
					session.setAttribute("username", cookie.getValue());
					resp.sendRedirect(req.getContextPath() + "/waiting");
					return;
				}
			}
		}

		// 3. Chuyển hướng sang trang giao diện login.jsp
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// Lấy dữ liệu từ form login.jsp
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String remember = req.getParameter("remember");

		boolean isRememberMe = "on".equals(remember);
		String alertMsg = "";

		// Kiểm tra rỗng
		if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		// Gọi tầng Service kiểm tra đăng nhập
		UserService service = new UserServiceImpl();
		User user = service.login(username, password);

		if (user != null) {
			// Đăng nhập thành công -> Lưu session
			HttpSession session = req.getSession(true);
			session.setAttribute("account", user);

			// Nếu chọn Remember Me -> Tạo Cookie
			if (isRememberMe) {
				saveRememberMe(resp, username);
			}

			// Chuyển sang Controller chờ phân quyền
			resp.sendRedirect(req.getContextPath() + "/waiting");
		} else {
			// Đăng nhập thất bại -> Trả về lỗi
			alertMsg = "Tài khoản hoặc mật khẩu không đúng!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}

	private void saveRememberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60); // Lưu cookie trong 30 phút
		response.addCookie(cookie);
	}
}