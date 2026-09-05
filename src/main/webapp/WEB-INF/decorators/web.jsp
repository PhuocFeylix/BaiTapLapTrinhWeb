<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title" /></title>
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<sitemesh:write property="head" />
</head>
<body>

	<!-- HEADER & NAVIGATION BAR -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<!-- Nút Trang chủ Logo -->
			<a class="navbar-brand fw-bold text-warning"
				href="${pageContext.request.contextPath}/home"> 🏠 HOME </a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<!-- Menu bên trái -->
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/product">Sản phẩm</a></li>

					<!-- ================= BƯỚC 3 THÊM VÀO ĐÂY ================= -->
					<%-- Chỉ hiển thị menu Quản lý nếu đã đăng nhập VÀ là Admin (role == 1) --%>
					<c:if
						test="${not empty sessionScope.account and sessionScope.account.role == 1}">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle text-warning fw-bold" href="#"
							role="button" data-bs-toggle="dropdown"> ⚙️ Quản lý (Admin) </a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/admin/categories">Quản
										lý Danh mục</a></li>
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/admin/products">Quản
										lý Sản phẩm</a></li>
							</ul></li>
					</c:if>
					<!-- ======================================================== -->
				</ul>

				<!-- Menu bên phải: Login / Register / Profile / Logout -->
				<ul class="navbar-nav ms-auto">
					<c:choose>
						<%-- Nếu chưa đăng nhập --%>
						<c:when test="${empty sessionScope.account}">
							<li class="nav-item"><a class="btn btn-outline-light me-2"
								href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
							<li class="nav-item"><a class="btn btn-warning"
								href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
						</c:when>

						<%-- Nếu đã đăng nhập --%>
						<c:otherwise>
							<li class="nav-item me-3 d-flex align-items-center text-white">
								<span>Xin chào, <strong>${sessionScope.account.username}</strong>!
							</span>
							</li>
							<li class="nav-item me-2"><a
								class="btn btn-sm btn-info text-white"
								href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a>
							</li>
							<li class="nav-item"><a class="btn btn-sm btn-danger"
								href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>

	<!-- NỘI DUNG ĐỘNG CỦA TỪNG TRANG -->
	<main class="container">
		<sitemesh:write property="body" />
	</main>

	<!-- FOOTER -->
	<footer class="bg-light text-center py-3 mt-5 border-top">
		<p class="mb-0 text-muted">&copy; 2026 Bài Tập Lập Trình Web - JPA
			& Servlet</p>
	</footer>

	<!-- Bootstrap 5 JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>