<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Cập nhật danh mục</h2>

<c:if test="${not empty error}">
	<div class="alert alert-danger">${error}</div>
</c:if>

<form action="${pageContext.request.contextPath}/admin/category/update"
	method="post" enctype="multipart/form-data">
	<input type="hidden" name="categoryId" value="${cate.categoryId}" />

	<div class="mb-3">
		<label class="form-label">Tên danh mục:</label> <input type="text"
			name="categoryname" value="${cate.categoryname}" class="form-control"
			required />
	</div>
	<div class="mb-3">
		<label class="form-label">Hình ảnh hiện tại:</label><br />
		<c:if test="${not empty cate.images}">
			<img src="${pageContext.request.contextPath}/uploads/${cate.images}"
				width="100" class="mb-2" />
		</c:if>
		<input type="file" name="images" class="form-control" accept="image/*" />
	</div>
	<div class="mb-3">
		<label class="form-label">Trạng thái:</label> <select name="status"
			class="form-select">
			<option value="1" ${cate.status == 1 ? 'selected' : ''}>Hoạt
				động</option>
			<option value="0" ${cate.status == 0 ? 'selected' : ''}>Khóa</option>
		</select>
	</div>
	<button type="submit" class="btn btn-primary">Cập nhật</button>
	<a href="${pageContext.request.contextPath}/admin/categories"
		class="btn btn-secondary">Hủy</a>
</form>