<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title" /></title>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<sitemesh:write property="head" />
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand" href="#">JPA System</a>
			<div class="navbar-nav ms-auto">
				<c:if test="${not empty sessionScope.account}">
					<span class="nav-link text-light"> Xin chào, ${not empty sessionScope.account.fullname ? sessionScope.account.fullname : sessionScope.account.username}
					</span>
					<a class="nav-link text-danger"
						href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
				</c:if>
			</div>
		</div>
	</nav>

	<div class="container">
		<sitemesh:write property="body" />
	</div>
</body>
</html>