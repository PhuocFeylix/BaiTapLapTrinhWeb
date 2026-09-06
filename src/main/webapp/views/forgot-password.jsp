<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container" style="max-width: 420px; margin-top: 80px;">
		<div class="card shadow-sm">
			<div class="card-body">
				<h4 class="card-title text-center mb-3">Quên mật khẩu</h4>
				<p class="text-muted text-center">Nhập tên đăng nhập hoặc email đã đăng ký, hệ thống sẽ gửi mã OTP để đặt lại mật khẩu.</p>

				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/forgot-password" method="post">
					<div class="mb-3">
						<label class="form-label">Tên đăng nhập hoặc Email:</label>
						<input type="text" class="form-control" name="account" required autofocus />
					</div>
					<button type="submit" class="btn btn-primary w-100">Gửi mã OTP</button>
				</form>

				<p class="text-center mt-3">
					<a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>
