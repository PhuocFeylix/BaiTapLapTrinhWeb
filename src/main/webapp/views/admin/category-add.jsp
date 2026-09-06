<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Thêm danh mục mới</h2>

<c:if test="${not empty error}">
	<div class="alert alert-danger">${error}</div>
</c:if>

<form action="${pageContext.request.contextPath}/admin/category/insert"
	method="post" enctype="multipart/form-data">
	<div class="mb-3">
		<label class="form-label">Tên danh mục:</label> <input type="text"
			name="categoryname" class="form-control" required />
	</div>
	<div class="mb-3">
		<label class="form-label">Hình ảnh:</label> <input type="file"
			name="images" class="form-control" accept="image/*" />
	</div>
	<div class="mb-3">
		<label class="form-label">Trạng thái:</label> <select name="status"
			class="form-select">
			<option value="1">Hoạt động</option>
			<option value="0">Khóa</option>
		</select>
	</div>
	<button type="submit" class="btn btn-success">Lưu danh mục</button>
	<a href="${pageContext.request.contextPath}/admin/categories"
		class="btn btn-secondary">Hủy</a>
</form>