<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<div class="container my-4">
	<h3 class="mb-4 text-center text-uppercase fw-bold text-dark">Danh
		Sách Sản Phẩm</h3>

	<!-- Lưới hiển thị 6 sản phẩm -->
	<div class="row row-cols-1 row-cols-md-3 g-4 mb-4">
		<c:forEach items="${products}" var="p">
			<div class="col">
				<div class="card h-100 shadow-sm border-0">
					<c:choose>
						<c:when test="${not empty p.images}">
							<img src="${pageContext.request.contextPath}/uploads/${p.images}"
								class="card-img-top p-3" alt="${p.productName}"
								style="height: 220px; object-fit: contain;">
						</c:when>
						<c:otherwise>
							<img src="https://via.placeholder.com/220"
								class="card-img-top p-3" alt="No Image">
						</c:otherwise>
					</c:choose>

					<div class="card-body d-flex flex-column">
						<span class="badge bg-secondary mb-2 w-auto me-auto">${p.category.categoryname}</span>
						<h5 class="card-title text-truncate">${p.productName}</h5>
						<p class="card-text text-danger fs-5 fw-bold mt-auto mb-2">
							<fmt:formatNumber value="${p.price}" pattern="#,###" />
							VNĐ
						</p>
						<a
							href="${pageContext.request.contextPath}/product/detail?id=${p.productId}"
							class="btn btn-primary w-100 mt-2">Xem chi tiết</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- Thanh Phân trang (Pagination) -->
	<c:if test="${endPage > 1}">
		<nav aria-label="Page navigation">
			<ul class="pagination justify-content-center">
				<!-- Nút Previous -->
				<li class="page-item ${currentPage == 1 ? 'disabled' : ''}"><a
					class="page-link"
					href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">Trước</a>
				</li>

				<!-- Danh sách các con số trang -->
				<c:forEach begin="1" end="${endPage}" var="i">
					<li class="page-item ${currentPage == i ? 'active' : ''}"><a
						class="page-link"
						href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
					</li>
				</c:forEach>

				<!-- Nút Next -->
				<li class="page-item ${currentPage == endPage ? 'disabled' : ''}">
					<a class="page-link"
					href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">Sau</a>
				</li>
			</ul>
		</nav>
	</c:if>
</div>