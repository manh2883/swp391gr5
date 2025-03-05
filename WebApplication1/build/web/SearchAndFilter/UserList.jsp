<%-- 
    Document   : UserList
    Created on : Mar 5, 2025, 7:01:48 PM
    Author     : Dell
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Models.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="users" scope="request" type="java.util.List" />

<div class="container mt-4">
    <h2>User List</h2>

    <form action="UserList" method="get" class="mb-3">
        <input type="text" name="search" placeholder="Search by name, email, phone" class="form-control" value="${param.search}">
        <select name="status" class="form-control mt-2">
            <option value="">All Status</option>
            <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
            <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
        </select>
        <select name="sortBy" class="form-control mt-2">
            <option value="first_name" ${param.sortBy == 'first_name' ? 'selected' : ''}>Sort by Name</option>
            <option value="email" ${param.sortBy == 'email' ? 'selected' : ''}>Sort by Email</option>
            <option value="phone_number" ${param.sortBy == 'phone_number' ? 'selected' : ''}>Sort by Phone</option>
        </select>
        <button type="submit" class="btn btn-primary mt-2">Search</button>
    </form>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Gender</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.firstName} ${user.lastName}</td>
                    <td>${user.gender == 1 ? "Male" : "Female"}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>
                        <a href="UserDetail.jsp?userId=${user.userId}" class="btn btn-info">View</a>
                        <a href="EditUser.jsp?userId=${user.userId}" class="btn btn-warning">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <nav>
        <ul class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="UserList?page=${i}&search=${param.search}&status=${param.status}&sortBy=${param.sortBy}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>
