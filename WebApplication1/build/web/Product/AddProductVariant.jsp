<%-- 
    Document   : AddProductVariant
    Created on : Feb 9, 2025, 5:41:33 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Product List</h2>
        <a href="add-product.jsp" class="btn btn-primary mb-3">Add New Product</a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Brand</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Product> productList = (List<Product>) request.getAttribute("productList");
                    for (Product product : productList) {
                %>
                <tr>
                    <td><%= product.getProductId() %></td>
                    <td><%= product.getName() %></td>
                    <td><%= product.getDescription() %></td>
                    <td><%= product.getBrandId() %></td>
                    <td>$<%= product.getPrice() %></td>
                    <td><%= product.getCategoryId() %></td>
                    <td>
                        <a href="view-product.jsp?id=<%= product.getProductId() %>" class="btn btn-info btn-sm">View</a>
                        <a href="edit-product.jsp?id=<%= product.getProductId() %>" class="btn btn-warning btn-sm">Edit</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>

