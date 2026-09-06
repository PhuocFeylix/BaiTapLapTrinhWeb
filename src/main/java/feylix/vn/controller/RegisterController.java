package feylix.vn.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;
import feylix.vn.utils.MailUtils;
import feylix.vn.utils.OtpUtils;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9,11}$");

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
		String email = req.getParameter("email");

		// Giu lai du lieu da nhap de hien thi lai form neu co loi (tru mat khau)
		req.setAttribute("oldUsername", u);
		req.setAttribute("oldFullname", fullname);
		req.setAttribute("oldPhone", phone);
		req.setAttribute("oldEmail", email);

		// ===== VALIDATION PHIA SERVER =====
		if (u == null || u.trim().length() < 4) {
			req.setAttribute("error", "Tên đăng nhập phải có ít nhất 4 ký tự!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (p == null || p.length() < 6) {
			req.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (fullname == null || fullname.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập họ và tên!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (phone == null || !PHONE_PATTERN.matcher(phone.trim()).matches()) {
			req.setAttribute("error", "Số điện thoại không hợp lệ (chỉ gồm 9-11 chữ số)!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
			req.setAttribute("error", "Địa chỉ email không hợp lệ!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (userDao.checkExistUsername(u.trim())) {
			req.setAttribute("error", "Tên đăng nhập đã tồn tại!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}
		if (userDao.checkExistEmail(email.trim())) {
			req.setAttribute("error", "Email này đã được sử dụng để đăng ký tài khoản khác!");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		// ===== TAO USER (CHUA KICH HOAT) + SINH OTP =====
		String otp = OtpUtils.generateOtp();

		User user = new User();
		user.setUsername(u.trim());
		user.setPassword(p);
		user.setFullname(fullname.trim());
		user.setPhone(phone.trim());
		user.setEmail(email.trim());
		user.setActive(0); // Chua kich hoat, cho xac thuc OTP
		user.setOtpCode(otp);
		user.setOtpExpiry(new Date(System.currentTimeMillis() + OtpUtils.OTP_VALID_MILLIS));

		try {
			userDao.insert(user);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "Đã xảy ra lỗi khi đăng ký! Vui lòng thử lại.");
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
			return;
		}

		// Gui OTP qua email. Neu gui that bai, van cho phep tiep tuc
		// (nguoi dung co the bam "Gui lai ma" o trang xac thuc OTP).
		try {
			MailUtils.sendOtpMail(user.getEmail(), otp, MailUtils.PURPOSE_REGISTER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Chuyen sang trang nhap OTP de kich hoat tai khoan
		resp.sendRedirect(req.getContextPath() + "/verify-otp?username=" + user.getUsername());
	}
}
