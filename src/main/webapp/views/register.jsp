<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
</head>
<body>
	<h2>Tạo tài khoản mới</h2>

	<c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>

	<form action="${pageContext.request.contextPath}/register"
		method="post">
		<div style="margin-bottom: 10px;">
			<label>Tên đăng nhập:</label><br /> <input type="text"
				name="username" required />
		</div>
		<div style="margin-bottom: 10px;">
			<label>Mật khẩu:</label><br /> <input type="password" name="password"
				required />
		</div>
		<div style="margin-bottom: 10px;">
			<label>Họ và tên:</label><br /> <input type="text" name="fullname"
				required />
		</div>
		<div style="margin-bottom: 10px;">
			<label>Số điện thoại:</label><br /> <input type="text" name="phone" />
		</div>
		<button type="submit">Đăng ký</button>
	</form>

	<p style="margin-top: 15px;">
		Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng
			nhập tại đây</a>
	</p>
</body>
</html>