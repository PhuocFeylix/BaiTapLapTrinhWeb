package feylix.vn.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet(urlPatterns = { "/user/profile", "/profile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("account");

		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User currentUser = (User) session.getAttribute("account");

		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");

		// ===== VALIDATION PHIA SERVER =====
		if (fullname == null || fullname.trim().isEmpty()) {
			req.setAttribute("error", "Vui lòng nhập họ và tên!");
			req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			return;
		}
		if (phone != null && !phone.trim().isEmpty() && !phone.trim().matches("[0-9]{9,11}")) {
			req.setAttribute("error", "Số điện thoại không hợp lệ (chỉ gồm 9-11 chữ số)!");
			req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			return;
		}

		// Xử lý upload ảnh (co the khong chon anh moi)
		Part filePart = req.getPart("image");
		String fileName = (filePart != null && filePart.getSubmittedFileName() != null)
				? Paths.get(filePart.getSubmittedFileName()).getFileName().toString()
				: null;

		if (fileName != null && !fileName.isEmpty()) {
			String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists())
				uploadDir.mkdir();

			String newFileName = System.currentTimeMillis() + "_" + fileName;
			filePart.write(uploadPath + File.separator + newFileName);
			currentUser.setImages(newFileName);
		}

		currentUser.setFullname(fullname);
		currentUser.setPhone(phone);

		// Cập nhật Database qua JPA API
		userDao.update(currentUser);

		// Cập nhật lại Session
		session.setAttribute("account", currentUser);
		req.setAttribute("message", "Cập nhật thông tin thành công!");
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}
}