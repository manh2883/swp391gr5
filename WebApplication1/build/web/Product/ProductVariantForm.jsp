<%-- 
    Document   : ProductVariantForm
    Created on : Feb 9, 2025, 5:39:30 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> language="java" %>
<!DOCTYPE html>
<%@ page import="ProductVariant" %>
<html>
<head>
    <title>Product Variant Form</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Product Variant Form</h2>
        <form action="product-variant" method="post">
            <input type="hidden" name="variantId" value="<%= request.getAttribute("variant") != null ? ((ProductVariant) request.getAttribute("variant")).getVariantId() : "" %>">
            <div class="mb-3">
                <label class="form-label">Product ID:</label>
                <input type="text" name="productId" class="form-control" required
                       value="<%= request.getAttribute("variant") != null ? ((ProductVariant) request.getAttribute("variant")).getProducyId() : "" %>">
            </div>
            <div class="mb-3">
                <label class="form-label">Color:</label>
                <input type="text" name="color" class="form-control" required
                       value="<%= request.getAttribute("variant") != null ? ((ProductVariant) request.getAttribute("variant")).getColor() : "" %>">
            </div>
            <div class="mb-3">
                <label class="form-label">Size:</label>
                <input type="text" name="size" class="form-control" required
                       value="<%= request.getAttribute("variant") != null ? ((ProductVariant) request.getAttribute("variant")).getSize() : "" %>">
            </div>
            <div class="mb-3">
                <label class="form-label">Stock:</label>
                <input type="number" name="stock" class="form-control" required
                       value="<%= request.getAttribute("variant") != null ? ((ProductVariant) request.getAttribute("variant")).getStock() : "" %>">
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="product-variant" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>