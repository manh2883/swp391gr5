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
import Models.CartDetail;
import Models.Product;
import Models.User;
import Models.UserAddress;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class PurchaseProductServlet extends HttpServlet {

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
            out.println("<title>Servlet PurchaseProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PurchaseProductServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Lấy tham số từ request
        String color = request.getParameter("colorInput") != null ? request.getParameter("colorInput").trim() : "";
        String size = request.getParameter("sizeInput") != null ? request.getParameter("sizeInput").trim() : "";
        String idIn = request.getParameter("idInput") != null ? request.getParameter("idInput").trim() : "";

        // Kiểm tra tham số hợp lệ
        if (idIn.isEmpty()) {
            request.setAttribute("message", "Product not found");
            response.sendRedirect("Home/Error404.jsp");
        }
        String currentLink = "ProductDetail?productId=" + idIn;
        // Kiểm tra đăng nhập

        if (account != null) {

            // Lấy userId từ accountId
            int accountId = account.getAccountId();
            int userId = UserDAO.getUserIDByAccountID(accountId);
            if (userId > 0) {
                List<CartDetail> checkoutItems = new ArrayList<>();
                // Kiểm tra color & size trước khi thêm vào giỏ hàng
                List<CartDetail> cartItems = new ArrayList<>();
                if (!color.isEmpty() || !size.isEmpty()) {
                    int variantId = ProductDAO.getVariantByColorAndSize(idIn, color, size);
                    if (variantId > 0) {
                        int stock = ProductDAO.getStockByProductAndVariant(idIn, variantId);
                        if (stock > 0) {
                            Product pro = ProductDAO.getProductById(idIn);
                            pro.setPrice(ProductDAO.getCurrentPriceForProductVariant(idIn, variantId));
                            CartDetail cd = new CartDetail(-1, -1, idIn, variantId, 1, null, pro);
                            checkoutItems.add(cd);

                        }
                    }

                    // Lấy danh sách địa chỉ của user
                    UserDAO userDAO = new UserDAO();
                    List<UserAddress> userAddresses = userDAO.getUserAddresses(userId);
                    User user = UserDAO.getUserById(userId);

                    session.setAttribute("checkoutItems", checkoutItems);
                    session.setAttribute("user", user);
                    session.setAttribute("userAddresses", userAddresses);

                    request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
                } else {
                    session.setAttribute("prevLink", currentLink);
                    response.sendRedirect("Login");
                }
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

}
