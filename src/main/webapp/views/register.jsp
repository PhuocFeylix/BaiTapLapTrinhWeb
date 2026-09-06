<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container" style="max-width: 480px; margin-top: 50px;">
		<div class="card shadow-sm">
			<div class="card-body">
				<h3 class="card-title text-center mb-4">Tạo tài khoản mới</h3>

				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/register" method="post">
					<div class="mb-3">
						<label class="form-label">Tên đăng nhập:</label>
						<input type="text" class="form-control" name="username"
							value="${oldUsername}" required minlength="4" />
					</div>
					<div class="mb-3">
						<label class="form-label">Mật khẩu:</label>
						<input type="password" class="form-control" name="password"
							required minlength="6" />
						<div class="form-text">Ít nhất 6 ký tự.</div>
					</div>
					<div class="mb-3">
						<label class="form-label">Email (dùng để nhận mã OTP kích hoạt):</label>
						<input type="email" class="form-control" name="email"
							value="${oldEmail}" required />
					</div>
					<div class="mb-3">
						<label class="form-label">Họ và tên:</label>
						<input type="text" class="form-control" name="fullname"
							value="${oldFullname}" required />
					</div>
					<div class="mb-3">
						<label class="form-label">Số điện thoại:</label>
						<input type="text" class="form-control" name="phone"
							value="${oldPhone}" pattern="[0-9]{9,11}" required />
					</div>
					<button type="submit" class="btn btn-primary w-100">Đăng ký</button>
				</form>

				<p class="text-center mt-3">
					Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập tại đây</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>
