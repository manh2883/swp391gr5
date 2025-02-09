package controllers;

import DAO.PostDAO;
import Models.Post;
import Models.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class ViewPostServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String action = request.getParameter("action");
        PostDAO postDAO = new PostDAO();
        
        if (action == null || action.equals("list")) {
            // Hiển thị danh sách bài viết mới nhất
            try {
                List<Post> latestPosts = postDAO.getLatestPosts(Integer.MAX_VALUE); 
                request.setAttribute("latestPosts", latestPosts);
                request.getRequestDispatcher("Post/ViewPost.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                request.setAttribute("error", "Error loading blog posts: " + e.getMessage());
                request.getRequestDispatcher("Home/Index.jsp").forward(request, response);
                return;
            }
        } else if (action.equals("view")) {
            // Xem chi tiết một bài viết
            try {
                HttpSession session = request.getSession();
                Account account = (Account) session.getAttribute("account");

                String postIdStr = request.getParameter("postId");
                if (postIdStr == null || postIdStr.trim().isEmpty()) {
                    request.setAttribute("error", "Post ID is required");
                    request.getRequestDispatcher("Home/Index.jsp").forward(request, response);
                    return;
                }

                int postId = Integer.parseInt(postIdStr);
                Post post = postDAO.getPostById(postId);

                if (post == null) {
                    request.setAttribute("error", "Post not found");
                    request.getRequestDispatcher("Home/Index.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra quyền xem bài viết
                boolean canView = false;
                if (account != null) {
                    if (account.getRoleId() == 1 || post.getStatus() == 1 || 
                        post.getCreatedBy() == account.getUserId()) {
                        canView = true;
                    }
                } else if (post.getStatus() == 1) {
                    canView = true;
                }

                if (!canView) {
                    request.setAttribute("error", "You don't have permission to view this post");
                    request.getRequestDispatcher("Home/Index.jsp").forward(request, response);
                    return;
                }

                postDAO.incrementViewCount(postId);
                post = postDAO.getPostById(postId);

                request.setAttribute("post", post);
                request.getRequestDispatcher("Post/ViewPost.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Post ID format");
                request.getRequestDispatcher("Home/Index.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}