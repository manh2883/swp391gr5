package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Models.Post;
import DAO.PostDAO;

public class ViewPostServlet extends HttpServlet {

    private PostDAO postDAO = new PostDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getParameter("postId");
        String errorMessage = null;

        if (postIdParam != null && !postIdParam.isEmpty()) {
            try {
                int postId = Integer.parseInt(postIdParam);
                Post post = postDAO.getPostById(postId);

                if (post != null) {
                    request.setAttribute("post", post);
                } else {
                    errorMessage = "Bài viết không tồn tại.";
                }
            } catch (NumberFormatException e) {
                errorMessage = "ID bài viết không hợp lệ.";
            }
        } else {
            errorMessage = "Không tìm thấy ID bài viết.";
        }

        // Nếu có lỗi, gửi lỗi vào request attribute
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        request.getRequestDispatcher("Post/ViewPost.jsp").forward(request, response);
    }
}
