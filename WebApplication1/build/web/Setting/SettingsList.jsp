<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
    List<Object[]> settings = (List<Object[]>) request.getAttribute("settings");
    int totalPages = (int) request.getAttribute("totalPages");
    int currentPage = (int) request.getAttribute("currentPage");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Settings List</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h2 class="mt-4">Settings List</h2>

            <a href="add-setting.jsp" class="btn btn-primary mb-3">Add New Setting</a>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Value</th>
                        <th>Order</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Object[] setting : settings) { %>
                    <tr>
                        <td><%= setting[0] %></td>
                        <td><%= setting[3] %></td>
                        <td><%= setting[2] %></td>
                        <td><%= setting[4] %></td>
                        <td><%= ((int) setting[5] == 1) ? "Active" : "Inactive" %></td>
                        <td>
                            <a href="view-setting.jsp?id=<%= setting[0] %>" class="btn btn-info btn-sm">View</a>
                            <a href="edit-setting.jsp?id=<%= setting[0] %>" class="btn btn-warning btn-sm">Edit</a>
                            <a href="toggle-setting.jsp?id=<%= setting[0] %>" class="btn btn-danger btn-sm">
                                <%= ((int) setting[5] == 1) ? "Deactivate" : "Activate" %>
                            </a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <% for (int i = 1; i <= totalPages; i++) { %>
            <a href="?page=<%= i %>" class="btn btn-secondary"><%= i %></a>
            <% } %>
        </div>
    </body>
</html>
