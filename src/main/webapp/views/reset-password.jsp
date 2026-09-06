<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đặt lại mật khẩu</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container" style="max-width: 420px; margin-top: 80px;">
		<div class="card shadow-sm">
			<div class="card-body">
				<h4 class="card-title text-center mb-3">Đặt lại mật khẩu</h4>
				<p class="text-muted text-center">Nhập mã OTP đã gửi tới email của tài khoản <b>${username}</b> và mật khẩu mới.</p>

				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/reset-password" method="post">
					<input type="hidden" name="username" value="${username}" />
					<div class="mb-3">
						<label class="form-label">Mã OTP:</label>
						<input type="text" class="form-control" name="otp" maxlength="6"
							pattern="[0-9]{6}" required autofocus />
					</div>
					<div class="mb-3">
						<label class="form-label">Mật khẩu mới:</label>
						<input type="password" class="form-control" name="newPassword"
							minlength="6" required />
					</div>
					<div class="mb-3">
						<label class="form-label">Xác nhận mật khẩu mới:</label>
						<input type="password" class="form-control" name="confirmPassword"
							minlength="6" required />
					</div>
					<button type="submit" class="btn btn-primary w-100">Đặt lại mật khẩu</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
