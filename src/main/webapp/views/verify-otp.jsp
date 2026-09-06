<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Xác thực OTP</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container" style="max-width: 420px; margin-top: 80px;">
		<div class="card shadow-sm">
			<div class="card-body">
				<h4 class="card-title text-center mb-3">Kích hoạt tài khoản</h4>
				<p class="text-muted text-center">
					Mã OTP đã được gửi đến email đăng ký của bạn.<br/> Vui lòng nhập mã để kích hoạt tài khoản
					<b>${username}</b>.
				</p>

				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>
				<c:if test="${not empty message}">
					<div class="alert alert-success">${message}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/verify-otp" method="post">
					<input type="hidden" name="username" value="${username}" />
					<div class="mb-3">
						<label class="form-label">Mã OTP (6 số):</label>
						<input type="text" class="form-control text-center" name="otp"
							maxlength="6" pattern="[0-9]{6}" required autofocus />
					</div>
					<button type="submit" name="action" value="verify"
						class="btn btn-primary w-100 mb-2">Xác thực</button>
					<button type="submit" name="action" value="resend"
						class="btn btn-outline-secondary w-100">Gửi lại mã OTP</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
