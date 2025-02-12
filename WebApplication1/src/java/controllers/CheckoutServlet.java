/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import DAO.CartDAO;
import Models.CartDetail;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class CheckoutServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet CheckoutServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String[] selectedItems = request.getParameterValues("selectedItems");

    if (selectedItems != null && selectedItems.length > 0) {
        List<CartDetail> checkoutItems = new ArrayList<>();
        for (String id : selectedItems) {
            int cartDetailID = Integer.parseInt(id);
            // Lấy CartDetail từ session hoặc database dựa trên cartDetailID
            CartDAO cDAO = new CartDAO();
            cDAO.getCartDetailByID(cartDetailID);
            if (cDAO.getCartDetailByID(cartDetailID) != null) {
                checkoutItems.add(cDAO.getCartDetailByID(cartDetailID));
            }
        }
        // Lưu checkoutItems vào session hoặc request để sử dụng trong trang thanh toán
        request.setAttribute("checkoutItems", checkoutItems);
        // Chuyển đến trang thanh toán
        RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
        dispatcher.forward(request, response);
    } else {
        // Không có sản phẩm nào được chọn
        response.sendRedirect("viewCart.jsp?message=Please select at least one item to checkout.");
    }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
