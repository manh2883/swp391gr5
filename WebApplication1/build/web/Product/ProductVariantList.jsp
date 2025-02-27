<%-- 
    Document   : ProductVariantList
    Created on : Feb 9, 2025, 5:38:42 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> language="java" %>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="ProductVariant" %>
<html>
<head>
    <title>Product Variants</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h2>Product Variants</h2>
        <a href="product-variant?action=new" class="btn btn-success mb-3">Add New Variant</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Variant ID</th>
                    <th>Product ID</th>
                    <th>Color</th>
                    <th>Size</th>
                    <th>Stock</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% List<ProductVariant> variants = (List<ProductVariant>) request.getAttribute("variants");
                   if (variants != null) {
                       for (ProductVariant variant : variants) { %>
                <tr>
                    <td><%= variant.getVariantId() %></td>
                    <td><%= variant.getProducyId() %></td>
                    <td><%= variant.getColor() %></td>
                    <td><%= variant.getSize() %></td>
                    <td><%= variant.getStock() %></td>
                    <td>
                        <a href="product-variant?action=edit&id=<%= variant.getVariantId() %>" class="btn btn-warning btn-sm">Edit</a>
                        <a href="product-variant?action=delete&id=<%= variant.getVariantId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?');">Delete</a>
                    </td>
                </tr>
                <%   }
                   } %>
            </tbody>
        </table>
    </div>
</body>
</html>