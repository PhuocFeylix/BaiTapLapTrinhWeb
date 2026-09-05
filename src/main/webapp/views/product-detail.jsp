<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<div class="container my-5">
	<!-- Nút quay lại -->
	<a href="javascript:history.back()"
		class="btn btn-outline-secondary mb-4">&larr; Quay lại</a>

	<div class="row g-4 bg-white p-4 rounded shadow-sm">
		<!-- Ảnh sản phẩm bên trái -->
		<div class="col-md-5 text-center">
			<c:choose>
				<c:when test="${not empty product.images}">
					<img
						src="${pageContext.request.contextPath}/uploads/${product.images}"
						class="img-fluid rounded border" alt="${product.productName}"
						style="max-height: 380px; object-fit: contain;">
				</c:when>
				<c:otherwise>
					<img src="https://via.placeholder.com/380"
						class="img-fluid rounded border" alt="No Image">
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Thông tin chi tiết bên phải -->
		<div class="col-md-7 d-flex flex-column">
			<span class="badge bg-primary w-auto me-auto mb-2 fs-6">${product.category.categoryname}</span>
			<h2 class="fw-bold mb-3">${product.productName}</h2>

			<div class="mb-3">
				<span class="text-danger fs-3 fw-bold me-3"> <fmt:formatNumber
						value="${product.price}" pattern="#,###" /> VNĐ
				</span> <span
					class="badge ${product.quantity > 0 ? 'bg-success' : 'bg-danger'}">
					${product.quantity > 0 ? 'Còn hàng' : 'Hết hàng'} </span>
			</div>

			<p class="text-muted mb-2">
				<strong>Mã sản phẩm:</strong> #${product.productId}
			</p>
			<p class="text-muted mb-3">
				<strong>Số lượng kho:</strong> ${product.quantity} sản phẩm
			</p>

			<hr>

			<div class="mb-4">
				<h5 class="fw-bold mb-2">Mô tả sản phẩm:</h5>
				<p class="text-secondary" style="white-space: pre-line;">${not empty product.description ? product.description : 'Chưa có mô tả chi tiết cho sản phẩm này.'}
				</p>
			</div>

			<div class="mt-auto d-flex gap-2">
				<button class="btn btn-success btn-lg px-4"
					${product.quantity == 0 ? 'disabled' : ''}>Thêm vào giỏ
					hàng</button>
				<a href="${pageContext.request.contextPath}/product"
					class="btn btn-outline-primary btn-lg">Xem sản phẩm khác</a>
			</div>
		</div>
	</div>
</div>