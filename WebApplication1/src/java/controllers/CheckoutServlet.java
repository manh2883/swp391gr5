/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.CartDAO;
import DAO.UserDAO;
import Models.Account;
import Models.CartDetail;
import Models.User;
import Models.UserAddress;
import jakarta.servlet.RequestDispatcher;
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
 * @author nguye
 */
public class CheckoutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
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
        // Giả sử bạn có cách để lấy thông tin người dùng đã đăng nhập
        // và lưu trữ trong biến `user`
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            response.sendRedirect("Login");
            return;
        }
        User user = UserDAO.getUserById(account.getAccountId());
        // Lấy danh sách địa chỉ đã lưu của người dùng
        List<UserAddress> addressList  = new ArrayList<>();// Bạn cần triển khai phương thức này trong lớp User

        // Lấy địa chỉ được chọn (nếu có)
        String selectedAddressId = request.getParameter("addressId");
            UserAddress selectedAddress = null;
        if (selectedAddressId != null && !selectedAddressId.equals("new")) {
                int addressId = Integer.parseInt(selectedAddressId);
                selectedAddress = UserDAO.getUserAddressById(addressId);
            } else if (!addressList.isEmpty()) {
                selectedAddress = addressList.get(0);
            } else {
                selectedAddress = new UserAddress(); // Địa chỉ trống
            }
        // Lưu địa chỉ đã chọn vào session để giữ lại khi làm mới trang
            session.setAttribute("selectedAddress", selectedAddress);

            // Đặt các thuộc tính cần thiết vào request
            request.setAttribute("user", account);
            request.setAttribute("addressList", addressList);
            request.setAttribute("selectedAddress", selectedAddress);

            // Chuyển tiếp yêu cầu tới JSP
        request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("Cart/Checkout.jsp");
            dispatcher.forward(request, response);
        } else {
            // Không có sản phẩm nào được chọn
            response.sendRedirect("ViewCart");
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
