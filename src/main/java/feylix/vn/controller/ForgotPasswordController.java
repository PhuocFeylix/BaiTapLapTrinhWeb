package feylix.vn.controller;

import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;
import feylix.vn.utils.MailUtils;
import feylix.vn.utils.OtpUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Date;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String account = req.getParameter("account"); // co the la username hoac email

		if (account == null || account.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập tên đăng nhập hoặc email!");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		User user = userDao.findByUsernameOrEmail(account.trim());

		if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
			req.setAttribute("error", "Không tìm thấy tài khoản phù hợp hoặc tài khoản chưa có email!");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		String otp = OtpUtils.generateOtp();
		user.setOtpCode(otp);
		user.setOtpExpiry(new Date(System.currentTimeMillis() + OtpUtils.OTP_VALID_MILLIS));
		userDao.update(user);

		try {
			MailUtils.sendOtpMail(user.getEmail(), otp, MailUtils.PURPOSE_RESET);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Gửi email thất bại, vui lòng thử lại sau!");
			req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
			return;
		}

		resp.sendRedirect(req.getContextPath() + "/reset-password?username=" + user.getUsername());
	}
}
