/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import DAO.UserDAO;
import Models.Account;
import Models.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
public class AddToCartServlet extends HttpServlet {

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
            out.println("<title>Servlet AddToCartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToCartServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        // Lấy tham số từ request
        String color = request.getParameter("colorInput");
        String size = request.getParameter("sizeInput");
        String idIn = request.getParameter("idInput");

        // Kiểm tra tham số hợp lệ
        if (idIn == null || idIn.isEmpty()) {
            request.setAttribute("error", "Invalid product ID.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            request.getRequestDispatcher("Login").forward(request, response);
            return;
        }

        // Lấy userId từ accountId
        int userId = UserDAO.getUserIDByAccountID(account.getAccountId());
        if (userId <= 0) {
            request.setAttribute("error", "User not found.");
//            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Kiểm tra color & size trước khi thêm vào giỏ hàng
        if ((color != null && !color.isEmpty()) || (size != null && !size.isEmpty())) {
            ProductDAO.addToCart(idIn.toUpperCase(), color, size, userId);
        }

        // Lấy thông tin user
        User u = UserDAO.getUserById(userId);
        if (u == null) {
            request.setAttribute("error", "User not found.");
//            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // Lấy variant_id
        int vI = ProductDAO.getVariantByColorAndSize(idIn.toUpperCase(), color.toUpperCase(), size.toUpperCase());
        if (vI == -1) {
            request.setAttribute("message", "Variant not found.");
        } else {
            request.setAttribute("message", vI);
        }

        // Gửi dữ liệu đến JSP
        request.setAttribute("colorList", color + ", " + size + ", " + idIn);
        request.setAttribute("sizeList", u);
        response.sendRedirect("ProductDetail?productId=" + idIn);
//        request.getRequestDispatcher("ProductDetail?productId="+idIn).forward(request, response);
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

    public void contextDestroyed(ServletContextEvent sce) {
        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
    }

}
