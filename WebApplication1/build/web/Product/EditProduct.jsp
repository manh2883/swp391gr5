<%-- 
    Document   : EditProduct
    Created on : Mar 2, 2025, 9:05:10 PM
    Author     : Dell
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Edit Product</h2>

    <!-- Thông báo kết quả -->
    <c:if test="${param.success eq 'true'}">
        <div class="alert alert-success">Update sucessfully!</div>
    </c:if>
    <c:if test="${param.error eq 'true'}">
        <div class="alert alert-danger">Error. Try again.</div>
    </c:if>

    <!-- Form chỉnh sửa sản phẩm -->
    <form action="EditProduct" method="post">
        <input type="hidden" name="product_id" value="${product.productId}">
        
        <div class="mb-3">
            <label class="form-label">Product Name:</label>
            <input type="text" class="form-control" name="name" value="${product.name}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Description:</label>
            <textarea class="form-control" name="description" required>${product.description}</textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">Price:</label>
            <input type="number" class="form-control" name="price" value="${product.price}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Category:</label>
            <input type="text" class="form-control" name="category_id" value="${product.categoryId}" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Product</button>
    </form>

    <hr>

    <h3>Product Variant</h3>

    <!-- Form chỉnh sửa và xóa biến thể sản phẩm -->
    <c:forEach var="variant" items="${variants}">
        <form action="EditProduct" method="post" class="border p-3 mb-2">
            <input type="hidden" name="product_id" value="${variant.productId}">

            <div class="mb-2">
                <label class="form-label">Size:</label>
                <input type="text" class="form-control" name="size" value="${variant.size}" required>
            </div>

            <div class="mb-2">
                <label class="form-label">Color:</label>
                <input type="text" class="form-control" name="color" value="${variant.color}" required>
            </div>

            <div class="mb-2">
                <label class="form-label">Stock:</label>
                <input type="number" class="form-control" name="stock" value="${variant.stock}" required>
            </div>

            <button type="submit" name="action" value="update" class="btn btn-success">Update</button>
            <button type="submit" name="action" value="delete" class="btn btn-danger" onclick="return confirm('Are you sure to delete variant?')">Delete</button>
        </form>
    </c:forEach>
    
</div>
</body>
</html>
