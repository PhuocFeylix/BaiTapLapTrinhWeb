package feylix.vn.controller;

import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Date;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("username", req.getParameter("username"));
		req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String otp = req.getParameter("otp");
		String newPassword = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmPassword");

		req.setAttribute("username", username);

		User user = userDao.findByUsername(username);

		if (user == null) {
			req.setAttribute("error", "Không tìm thấy tài khoản!");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		if (otp == null || user.getOtpCode() == null || !user.getOtpCode().equals(otp.trim())) {
			req.setAttribute("error", "Mã OTP không chính xác!");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		if (user.getOtpExpiry() == null || user.getOtpExpiry().before(new Date())) {
			req.setAttribute("error", "Mã OTP đã hết hạn, vui lòng yêu cầu gửi lại!");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		if (newPassword == null || newPassword.length() < 6) {
			req.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự!");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		if (!newPassword.equals(confirmPassword)) {
			req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
			req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
			return;
		}

		user.setPassword(newPassword);
		user.setOtpCode(null);
		user.setOtpExpiry(null);
		userDao.update(user);

		req.setAttribute("success", "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.");
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}
}
