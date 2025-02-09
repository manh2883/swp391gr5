/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.CartDAO;
import DAO.UserDAO;
import Models.Account;
import Models.CartDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ViewCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewCartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewCartServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
//
//        // Lấy userID từ session (giả sử user đăng nhập rồi)
//        Account account = (Account) session.getAttribute("account");
//        int accountId = -1;
//        if (account == null) {
//            request.setAttribute("userName", "hehe");
//            request.getRequestDispatcher("Login/Login.jsp").forward(request, response);
//            
//        } else {
//            accountId = account.getAccountId();
//        }
//
//        UserDAO uDAO = new UserDAO();
//        int userId = -1;
//        userId = uDAO.getUserIDByAccountID(accountId);
//
//        System.out.println(userId + ", " + accountId);
//// Lấy danh sách giỏ hàng từ database
//        if (userId == -1) {
//            request.setAttribute("userName", "hoho");
//            request.getRequestDispatcher("Login/Login.jsp").forward(request, response);
//        }
//        CartDAO cartDAO = new CartDAO();
//        List<CartDetail> cartDetails = cartDAO.getAllCartDetailByUserID(userId);
//
//        // Gửi danh sách cartDetails lên trang JSP
//        if (cartDetails != null) {
//            request.setAttribute("cartDetails", cartDetails);
//            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
//        } else {
//            request.getRequestDispatcher("Home/Home.jsp").forward(request, response);
//        }
        CartDAO cartDAO = new CartDAO();
        // Lấy account từ session
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "Login/Login.jsp");
            return;
        }

        int accountId = account.getAccountId();

        // Lấy userId từ accountId
        UserDAO uDAO = new UserDAO();
        int userId = uDAO.getUserIDByAccountID(accountId);

        if (userId == -1) {
            response.sendRedirect(request.getContextPath() + "Login/Login.jsp");
            return;
        }

        // Xử lý hành động tăng/giảm số lượng hoặc xóa sản phẩm
        String cartDetailIDParam = request.getParameter("cartDetailID");
        String action = request.getParameter("action");

        if (cartDetailIDParam != null && action != null) {
            try {
                int cartDetailID = Integer.parseInt(cartDetailIDParam);

                // Kiểm tra quyền sở hữu cartDetailID
                if (!cartDAO.isCartDetailOwnedByUser(cartDetailID, userId)) {
                    response.sendRedirect(request.getContextPath() + "/ViewCart");
                    return;
                }

                if ("increment".equals(action)) {
                    // Tăng số lượng
                    cartDAO.editCartDetailByID(userId, cartDetailID, "increment");
                } else if ("decrement".equals(action)) {
                    // Giảm số lượng
                    cartDAO.editCartDetailByID(userId, cartDetailID, "decrement");
                } else if ("delete".equals(action)) {
                    // Xóa sản phẩm khỏi giỏ hàng
                    cartDAO.deleteCartDetailByID(userId, cartDetailID);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/ViewCart");
            return;
        }

        // Lấy danh sách giỏ hàng
        List<CartDetail> cartDetails = cartDAO.getAllCartDetailByUserID(userId);

        // Gửi danh sách cartDetails lên trang JSP
        if (cartDetails != null && !cartDetails.isEmpty()) {
            request.setAttribute("cartDetails", cartDetails);
            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
