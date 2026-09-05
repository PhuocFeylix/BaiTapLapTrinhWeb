<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Trang cá nhân</title>
</head>
<body>
	<div class="row justify-content-center">
		<div class="col-md-6">
			<h3 class="text-center mb-4">Cập nhật thông tin cá nhân</h3>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>

			<form action="${pageContext.request.contextPath}/user/profile"
				method="post" enctype="multipart/form-data">
				<div class="mb-3 text-center">
					<c:choose>
						<c:when test="${not empty sessionScope.account.images}">
							<img
								src="${pageContext.request.contextPath}/uploads/${sessionScope.account.images}"
								class="rounded-circle" width="120" height="120"
								style="object-fit: cover;">
						</c:when>
						<c:otherwise>
							<img src="https://via.placeholder.com/120" class="rounded-circle">
						</c:otherwise>
					</c:choose>
				</div>

				<div class="mb-3">
					<label class="form-label">Tên đăng nhập:</label> <input type="text"
						class="form-content form-control"
						value="${sessionScope.account.username}" disabled>
				</div>

				<div class="mb-3">
					<label class="form-label">Họ và tên:</label> <input type="text"
						name="fullname" class="form-control"
						value="${sessionScope.account.fullname}">
				</div>

				<div class="mb-3">
					<label class="form-label">Số điện thoại:</label> <input type="text"
						name="phone" class="form-control"
						value="${sessionScope.account.phone}">
				</div>

				<div class="mb-3">
					<label class="form-label">Ảnh đại diện:</label> <input type="file"
						name="image" class="form-control" accept="image/*">
				</div>

				<button type="submit" class="btn btn-primary w-100">Lưu
					thay đổi</button>
			</form>
		</div>
	</div>
</body>
</html>