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

@WebServlet("/verify-otp")
public class VerifyOtpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("username", req.getParameter("username"));
		req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String otpInput = req.getParameter("otp");
		String action = req.getParameter("action"); // "resend" hoac null/"verify"

		User user = userDao.findByUsername(username);

		if (user == null) {
			req.setAttribute("error", "Không tìm thấy tài khoản!");
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		if (user.getActive() == 1) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		// ===== Gui lai ma OTP =====
		if ("resend".equals(action)) {
			String newOtp = OtpUtils.generateOtp();
			user.setOtpCode(newOtp);
			user.setOtpExpiry(new Date(System.currentTimeMillis() + OtpUtils.OTP_VALID_MILLIS));
			userDao.update(user);

			try {
				MailUtils.sendOtpMail(user.getEmail(), newOtp, MailUtils.PURPOSE_REGISTER);
				req.setAttribute("message", "Đã gửi lại mã OTP, vui lòng kiểm tra email!");
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("error", "Gửi email thất bại, vui lòng thử lại sau!");
			}
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		// ===== Xac thuc OTP =====
		if (otpInput == null || otpInput.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập mã OTP!");
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		if (user.getOtpCode() == null || !user.getOtpCode().equals(otpInput.trim())) {
			req.setAttribute("error", "Mã OTP không chính xác!");
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		if (user.getOtpExpiry() == null || user.getOtpExpiry().before(new Date())) {
			req.setAttribute("error", "Mã OTP đã hết hạn, vui lòng bấm \"Gửi lại mã\"!");
			req.setAttribute("username", username);
			req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
			return;
		}

		// OTP hop le -> kich hoat tai khoan
		user.setActive(1);
		user.setOtpCode(null);
		user.setOtpExpiry(null);
		userDao.update(user);

		req.setAttribute("success", "Kích hoạt tài khoản thành công! Vui lòng đăng nhập.");
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}
}
