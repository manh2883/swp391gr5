<%-- 
    Document   : AddProduct
    Created on : Feb 9, 2025, 5:41:07 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Add Product</h2>
        <form action="AddProduct" method="post" enctype="multipart/form-data">
    <label>Name:</label>
    <input type="text" name="name" required>
    
    <label>Description:</label>
    <input type="text" name="description" required>
    
    <label>Price:</label>
    <input type="number" name="price" step="1" required>
    
    <label>Category:</label>
    <select name="categoryName">
        <option value="1">T-shirt</option>
        <option value="2">Pant</option>
        <option value="3">Shirt</option>
        <option value="4">Underwear</option>
        <option value="5">Short</option>
        <option value="6">Jacket</option>
    </select>
    
    <label>Image:</label>
    <input type="file" name="image" accept="image/*" required>
    
    <button type="submit">Add Product</button>
     <a href="products" class="btn btn-secondary">Cancel</a>

</form>

        <div>
            ${message}
        </div>
    </div>
</body>
</html>