<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">User List</h2>

    <!-- Search and Filter Form -->
    <form method="GET" action="UserList" class="row mb-3">
        <div class="col-md-4">
            <input type="text" name="search" class="form-control" placeholder="Search by name, email, phone" value="${param.search}">
        </div>
        <div class="col-md-3">
            <select name="status" class="form-control">
                <option value="">All Status</option>
                <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
            </select>
        </div>
        <div class="col-md-3">
            <select name="sortBy" class="form-control">
                <option value="">Sort By</option>
                <option value="name" ${param.sortBy == 'name' ? 'selected' : ''}>Name</option>
                <option value="email" ${param.sortBy == 'email' ? 'selected' : ''}>Email</option>
                <option value="phone" ${param.sortBy == 'phone' ? 'selected' : ''}>Phone</option>
                <option value="status" ${param.sortBy == 'status' ? 'selected' : ''}>Status</option>
                <option value="role" ${param.sortBy == 'role' ? 'selected' : ''}>Role</option>
            </select>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <!-- User Table -->
    <table class="table table-bordered">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Username</th>
                <th>Role</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.account.username}</td>
                    <td>${user.account.roleId}</td>
                    <td>${user.account.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <nav>
        <ul class="pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item"><a class="page-link" href="UserList?page=${currentPage - 1}">Previous</a></li>
            </c:if>

            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="UserList?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item"><a class="page-link" href="UserList?page=${currentPage + 1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
