<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<h2>Thêm sản phẩm mới</h2>

<c:if test="${not empty error}">
	<div class="alert alert-danger">${error}</div>
</c:if>

<form action="${pageContext.request.contextPath}/admin/product/insert"
	method="post" enctype="multipart/form-data">
	<div class="mb-3">
		<label class="form-label">Tên sản phẩm:</label> <input type="text"
			name="productName" class="form-control" value="${oldProductName}" required />
	</div>
	<div class="mb-3">
		<label class="form-label">Danh mục:</label> <select name="categoryId"
			class="form-select" required>
			<c:forEach items="${categories}" var="c">
				<option value="${c.categoryId}">${c.categoryname}</option>
			</c:forEach>
		</select>
	</div>
	<div class="mb-3">
		<label class="form-label">Giá bán:</label> <input type="number"
			step="0.01" min="0" name="price" class="form-control" value="${oldPrice}" required />
	</div>
	<div class="mb-3">
		<label class="form-label">Số lượng:</label> <input type="number"
			min="0" name="quantity" class="form-control" value="${oldQuantity}" required />
	</div>
	<div class="mb-3">
		<label class="form-label">Mô tả:</label>
		<textarea name="description" class="form-control" rows="3"></textarea>
	</div>
	<div class="mb-3">
		<label class="form-label">Hình ảnh:</label> <input type="file"
			name="images" class="form-control" accept="image/*" />
	</div>
	<div class="mb-3">
		<label class="form-label">Trạng thái:</label> <select name="status"
			class="form-select">
			<option value="1">Hiển thị</option>
			<option value="0">Ẩn</option>
		</select>
	</div>
	<button type="submit" class="btn btn-success">Lưu sản phẩm</button>
	<a href="${pageContext.request.contextPath}/admin/products"
		class="btn btn-secondary">Hủy</a>
</form>