<%@page import="Models.Post"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Post post = (Post) request.getAttribute("post");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Post Details</title>
</head>
<body>
    <% if (post != null) { %>
        <h1><%= post.getTitle() %></h1>
        <p><strong>Status:</strong> <%= post.getStatus() == 1 ? "Visible" : "Hidden" %></p>
        <p><strong>View Count:</strong> <%= post.getViewCount() %></p>
        <p><strong>Created At:</strong> <%= post.getCreatedAt() %></p>
        <p><strong>Created By:</strong> <%= post.getCreatedBy() %></p>
        <p><strong>Updated At:</strong> <%= post.getUpdatedAt() %></p>
        <p><strong>Updated By:</strong> <%= post.getUpdatedBy() %></p>
        <p><strong>Published At:</strong> <%= post.getPublishedAt() %></p>

        <!-- Buttons to Edit or Delete the post -->
        <a href="EditPostServlet?postId=<%= post.getPostId() %>">Edit</a> |
        <a href="DeletePostServlet?postId=<%= post.getPostId() %>" onclick="return confirm('Are you sure?');">Delete</a>
    <% } else { %>
        <p>Post not found!</p>
    <% } %>
</body>
</html>