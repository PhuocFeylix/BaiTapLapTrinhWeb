package feylix.vn.filter;

import feylix.vn.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = { "/admin/*" })
public class AdminSecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		// 1. Lấy thông tin user từ session
		User user = (session != null) ? (User) session.getAttribute("account") : null;

		// 2. Kiểm tra nếu chưa đăng nhập OR không phải là Admin (role != 1)
		if (user == null) {
			// Chuyển hướng sang trang Đăng nhập kèm thông báo
			req.setAttribute("error", "Bạn vui lòng đăng nhập trước!");
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		} else if (user.getRole() != 1) { // Giả sử role 1 là Admin
			// Đã đăng nhập nhưng không có quyền Admin
			resp.sendRedirect(req.getContextPath() + "/home");
			return;
		}

		// 3. Nếu là Admin -> Cho phép truy cập tiếp
		chain.doFilter(request, response);
	}
}