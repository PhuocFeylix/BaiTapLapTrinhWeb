<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="d-flex justify-content-between align-items-center mb-3">
	<h2>Quản lý Sản phẩm</h2>
	<a href="${pageContext.request.contextPath}/admin/product/add"
		class="btn btn-primary">Thêm sản phẩm mới</a>
</div>

<table class="table table-bordered table-hover align-middle">
	<thead class="table-dark">
		<tr>
			<th>ID</th>
			<th>Hình ảnh</th>
			<th>Tên sản phẩm</th>
			<th>Danh mục</th>
			<th>Giá</th>
			<th>Số lượng</th>
			<th>Thao tác</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${listproduct}" var="p">
			<tr>
				<td>${p.productId}</td>
				<td><c:if test="${not empty p.images}">
						<img src="${pageContext.request.contextPath}/uploads/${p.images}"
							width="60" height="60" style="object-fit: cover;" />
					</c:if></td>
				<td>${p.productName}</td>
				<td><span class="badge bg-info text-dark">${p.category.categoryname}</span></td>
				<td>${p.price}VNĐ</td>
				<td>${p.quantity}</td>
				<td><a
					href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productId}"
					class="btn btn-warning btn-sm">Sửa</a> <a
					href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productId}"
					class="btn btn-danger btn-sm"
					onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?')">Xóa</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>