<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chỉnh sửa sản phẩm</title>
<link
	href="https://cdn.jsdelivr.net/bootstrap/5.3.0/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="container mt-4">

	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header bg-primary text-white">
					<h4 class="mb-0">Cập nhật thông tin sản phẩm</h4>
				</div>
				<div class="card-body">
					<form
						action="${pageContext.request.contextPath}/admin/product/update"
						method="post" enctype="multipart/form-data">

						<!-- Primary Key (Hidden) -->
						<input type="hidden" name="productId" value="${product.productId}" />

						<!-- Ten san pham -->
						<div class="mb-3">
							<label for="productName" class="form-label">Tên sản phẩm:</label>
							<input type="text" class="form-content form-control"
								id="productName" name="productName"
								value="${product.productName}" required />
						</div>

						<!-- Gia -->
						<div class="mb-3">
							<label for="price" class="form-label">Giá (VNĐ):</label> <input
								type="number" step="0.01" class="form-control" id="price"
								name="price" value="${product.price}" required />
						</div>

						<!-- So luong -->
						<div class="mb-3">
							<label for="quantity" class="form-label">Số lượng:</label> <input
								type="number" class="form-control" id="quantity" name="quantity"
								value="${product.quantity}" required />
						</div>

						<!-- Danh muc (Category) -->
						<div class="mb-3">
							<label for="categoryId" class="form-label">Danh mục:</label> <select
								class="form-select" id="categoryId" name="categoryId">
								<c:forEach items="${categories}" var="cat">
									<option value="${cat.categoryId}"
										${cat.categoryId == product.category.categoryId ? 'selected' : ''}>
										${cat.categoryname}</option>
								</c:forEach>
							</select>
						</div>

						<!-- Hinh anh -->
						<div class="mb-3">
							<label class="form-label">Hình ảnh hiện tại:</label><br />
							<c:if test="${not empty product.images}">
								<img
									src="${pageContext.request.contextPath}/uploads/${product.images}"
									width="120" class="img-thumbnail mb-2" />
							</c:if>
							<input type="hidden" name="oldImage" value="${product.images}" />
							<input type="file" class="form-control" name="imageFile"
								accept="image/*" />
						</div>

						<!-- Mo ta -->
						<div class="mb-3">
							<label for="description" class="form-label">Mô tả:</label>
							<textarea class="form-control" id="description"
								name="description" rows="4">${product.description}</textarea>
						</div>

						<!-- Trang thai -->
						<div class="mb-3">
							<label class="form-label">Trạng thái:</label>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="status"
									id="active" value="1" ${product.status == 1 ? 'checked' : ''}>
								<label class="form-check-label" for="active">Kích hoạt</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="status"
									id="inactive" value="0" ${product.status == 0 ? 'checked' : ''}>
								<label class="form-check-label" for="inactive">Khóa</label>
							</div>
						</div>

						<!-- Action Buttons -->
						<div class="d-flex justify-content-between">
							<a href="${pageContext.request.contextPath}/admin/products"
								class="btn btn-secondary">Quay lại</a>
							<button type="submit" class="btn btn-success">Lưu thay
								đổi</button>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>