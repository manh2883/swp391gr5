package controllers;

import DAO.PostDAO;
import Models.Post;
import Models.PostDetail;
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
            try {
                String postIdStr = request.getParameter("postId");
                System.out.println("PostID received: " + postIdStr);

                int postId = Integer.parseInt(postIdStr);
                Post post = postDAO.getPostById(postId);
                PostDetail postDetail = postDAO.getPostDetailByPostId(postId);

                System.out.println("Post found: " + (post != null));
                System.out.println("PostDetail found: " + (postDetail != null));
                if (postDetail != null) {
                    System.out.println("Content: " + postDetail.getContent());
                }

                request.setAttribute("post", post);
                request.setAttribute("postContent", postDetail != null ? postDetail.getContent() : null);
                request.getRequestDispatcher("Post/PostDetail.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
