<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<div class="container my-4">
	<h2 class="text-center mb-4 text-primary font-weight-bold">SẢN
		PHẨM MỚI NHẤT</h2>

	<div class="row row-cols-1 row-cols-md-3 row-cols-lg-5 g-4">
		<c:forEach items="${top10Products}" var="p">
			<div class="col">
				<div class="card h-100 shadow-sm border-0">
					<!-- Ảnh sản phẩm -->
					<c:choose>
						<c:when test="${not empty p.images}">
							<img src="${pageContext.request.contextPath}/uploads/${p.images}"
								class="card-img-top p-2" alt="${p.productName}"
								style="height: 180px; object-fit: contain;">
						</c:when>
						<c:otherwise>
							<img src="https://via.placeholder.com/180"
								class="card-img-top p-2" alt="No Image">
						</c:otherwise>
					</c:choose>

					<!-- Thông tin sản phẩm -->
					<div class="card-body d-flex flex-column">
						<span class="badge bg-secondary mb-2 w-auto me-auto">${p.category.categoryname}</span>
						<h6 class="card-title text-truncate" title="${p.productName}">${p.productName}</h6>

						<p class="card-text text-danger fw-bold mt-auto mb-2">
							<fmt:formatNumber value="${p.price}" pattern="#,###" />
							VNĐ
						</p>

						<a
							href="${pageContext.request.contextPath}/product/detail?id=${p.productId}"
							class="btn btn-outline-primary btn-sm w-100 mt-2">Xem chi
							tiết</a> 
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>