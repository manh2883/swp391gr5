/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.CartDAO;
import DAO.ProductDAO;
import DAO.SettingDAO;
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
        String color = request.getParameter("colorInput") != null ? request.getParameter("colorInput").trim() : "";
        String size = request.getParameter("sizeInput") != null ? request.getParameter("sizeInput").trim() : "";
        String idIn = request.getParameter("idInput") != null ? request.getParameter("idInput").trim() : "";

        // Kiểm tra tham số hợp lệ
        if (idIn.isEmpty()) {
            request.setAttribute("message", "Product not found");
            response.sendRedirect("Home.Error404.jsp");
        }
        String currentLink = "ProductDetail?productId=" + idIn;
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account != null) {

            // Lấy userId từ accountId
            int accountId = account.getAccountId();
            int userId = UserDAO.getUserIDByAccountID(accountId);
            if (userId > 0) {
                // Kiểm tra color & size trước khi thêm vào giỏ hàng
                String addStatus = null;
                String addMessage = null;
                if (!color.isEmpty() || !size.isEmpty()) {
                    int quanBef = CartDAO.getCartItemQuantityForUserId(userId);
                    int itemNumber = CartDAO.getCartItemNumberForUserId(userId);
                    int maxItemNumber = SettingDAO.getMaxQuantityItemInCart();

                    if (itemNumber < maxItemNumber) {
                        ProductDAO.addToCart(idIn.toUpperCase(), color, size, userId);
                        int quanAf = CartDAO.getCartItemQuantityForUserId(userId);
                        addStatus = (quanAf > quanBef) ? "true" : "false";
                    } else {
                        addStatus = "out";
                    }
                } else {
                    addStatus = "false";
                }

                session.setAttribute("addStatus", addStatus);
                session.setAttribute("addMessage", addMessage);
                response.sendRedirect("ProductDetail?productId=" + idIn);
            } else {
                session.setAttribute("prevLink", currentLink);
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", currentLink);
            response.sendRedirect("Login");

        }
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
