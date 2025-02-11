//package controllers;
//
//import DAO.PostDAO;
//import Models.Post;
//import Models.PostDetail;
//import java.io.IOException;
//import java.sql.Timestamp;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//public class AddPostServlet extends HttpServlet {
//    
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Display the add post form
//        request.getRequestDispatcher("Post/AddPost.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        
//        try {
//            // Get form data
//            String title = request.getParameter("title");
//            String content = request.getParameter("content");
//            
//            // Set userId to 1
//            Integer userId = 1;
//            
//            // Validate input
//            if (title == null || title.trim().isEmpty() || 
//                content == null || content.trim().isEmpty()) {
//                request.setAttribute("error", "Title and content are required");
//                request.getRequestDispatcher("Post/AddPost.jsp").forward(request, response);
//                return;
//            }
//            
//            // Validate userId must be 1
//            if (userId != 1) {
//                request.setAttribute("error", "Invalid user ID. System can only work with user ID 1");
//                request.getRequestDispatcher("Post/AddPost.jsp").forward(request, response);
//                return;
//            }
//            
//            // Create Post object
//            Post post = new Post();
//            post.setTitle(title.trim());
//            post.setStatus(1); // Default to active
//            post.setViewCount(0);
//            post.setCreatedBy(1); // Ensure using 1
//            post.setUpdatedBy(1); // Ensure using 1
//            
//            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//            post.setCreatedAt(currentTime);
//            post.setUpdatedAt(currentTime);
//            post.setPublishedAt(currentTime);
//            
//            // Create PostDetail object
//            PostDetail postDetail = new PostDetail();
//            postDetail.setContent(content.trim());
//            
//            // Save to database
//            PostDAO postDAO = new PostDAO();
//            boolean success = postDAO.createPost(post, postDetail);
//            
//            if (success) {
//                // Redirect to post list or view page
//                response.sendRedirect("AddPostServlet");
//            } else {
//                request.setAttribute("error", "Failed to create post");
//                request.setAttribute("post", post); // Keep form data
//                request.setAttribute("postDetail", postDetail);
//                request.getRequestDispatcher("Post/AddPost.jsp").forward(request, response);
//            }
//            
//        } catch (Exception e) {
//            request.setAttribute("error", "An error occurred: " + e.getMessage());
//            request.getRequestDispatcher("Post/AddPost.jsp").forward(request, response);
//        }
//    }
//}