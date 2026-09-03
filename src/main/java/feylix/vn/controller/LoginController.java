package feylix.vn.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import feylix.vn.dao.impl.UserDao;
import feylix.vn.entity.User;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Đọc Cookie nếu có để tự điền username/password
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    req.setAttribute("username", c.getValue());
                }
            }
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        User user = userDao.checkLogin(username, password);

        if (user != null) {
            // 1. Lưu vào Session
            HttpSession session = req.getSession();
            session.setAttribute("account", user);

            // 2. Xử lý Cookie
            if ("on".equals(remember)) {
                Cookie cUser = new Cookie("username", username);
                cUser.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                resp.addCookie(cUser);
            } else {
                Cookie cUser = new Cookie("username", "");
                cUser.setMaxAge(0);
                resp.addCookie(cUser);
            }

            resp.sendRedirect(req.getContextPath() + "/user/profile");
        } else {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}