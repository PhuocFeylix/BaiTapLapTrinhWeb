<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="d-flex justify-content-between align-items-center mb-3">
	<h2>Quản lý Danh mục (Categories)</h2>
	<a href="${pageContext.request.contextPath}/admin/category/add"
		class="btn btn-primary">Thêm danh mục mới</a>
</div>

<table class="table table-bordered table-hover align-middle">
	<thead class="table-dark">
		<tr>
			<th>ID</th>
			<th>Hình ảnh</th>
			<th>Tên danh mục</th>
			<th>Trạng thái</th>
			<th>Thao tác</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${listcate}" var="cate">
			<tr>
				<td>${cate.categoryId}</td>
				<td><c:if test="${not empty cate.images}">
						<img
							src="${pageContext.request.contextPath}/uploads/${cate.images}"
							width="80" height="80" style="object-fit: cover;" />
					</c:if></td>
				<td>${cate.categoryname}</td>
				<td><span
					class="badge ${cate.status == 1 ? 'bg-success' : 'bg-secondary'}">
						${cate.status == 1 ? 'Hoạt động' : 'Khóa'} </span></td>
				<td><a
					href="${pageContext.request.contextPath}/admin/category/edit?id=${cate.categoryId}"
					class="btn btn-warning btn-sm">Sửa</a> <a
					href="${pageContext.request.contextPath}/admin/category/delete?id=${cate.categoryId}"
					class="btn btn-danger btn-sm"
					onclick="return confirm('Bạn có chắc chắn muốn xóa?')">Xóa</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>