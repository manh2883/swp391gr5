/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Models.Account;
import Models.CartDetail;
import Models.Order;
import Models.OrderDetail;
import Models.User;
import Models.UserAddress;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<UserAddress> userAddresses = (List<UserAddress>) session.getAttribute("userAddresses");
        List<CartDetail> checkoutItems = (List<CartDetail>) session.getAttribute("checkoutItems");

        if (user == null || checkoutItems == null || checkoutItems.isEmpty()) {
            response.sendRedirect("ViewCart");
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("userAddresses", userAddresses);
        request.setAttribute("checkoutItems", checkoutItems);

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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        int userId = UserDAO.getUserIDByAccountID(account.getAccountId());
        // 2. Lấy thông tin từ request
        String selectedAddress = request.getParameter("address");
        String newAddress = request.getParameter("newAddress");
        String finalAddress = (selectedAddress.equals("Other")) ? newAddress : selectedAddress;
        String paymentMethod = request.getParameter("paymentMethod");
        String orderNote = request.getParameter("orderNote");
        String userReceive = request.getParameter("name");
        String contact = request.getParameter("contact");
        // 3. Lấy giỏ hàng từ session
        List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("checkoutItems");
        if (cartDetails == null || cartDetails.isEmpty()) {
            request.setAttribute("message", "Giỏ hàng trống! Vui lòng chọn sản phẩm.");
            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
            return;
        }

        // 4. Tạo danh sách OrderDetail và tính tổng tiền
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalAmount = 0;

        for (CartDetail item : cartDetails) {
            String productId = item.getProductID();
            int variantId = (int) item.getProductVariantID();
            int quantity = item.getQuantity();
            ProductDAO productDAO = new ProductDAO();
            int stock = productDAO.getStockByProductAndVariant(productId, variantId);
            // Lấy giá từ bảng product_price
            double price = ProductDAO.getCurrentPriceForProductVariant(productId, variantId);
            if (quantity > stock) {
                request.setAttribute("message", "Không còn sản phẩm!");
                request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
                return;
            }
            //  Thêm vào danh sách OrderDetail
            OrderDetail orderDetail = new OrderDetail(0, 0, productId, variantId, quantity, (int) price);
            orderDetails.add(orderDetail);
            // Tính tổng tiền
            totalAmount += (int) (price * quantity);
        }
        // 5. Tạo Order
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date createAt = new Date(timestamp.getTime()); // Chuyển Timestamp -> Date

        // Nếu Order chấp nhận double totalAmount
        Order order = new Order(0, userId, totalAmount, 1,
                createAt, Integer.parseInt(paymentMethod), finalAddress, orderNote, userReceive, contact);

        // 6. Ghi vào database
        OrderDAO orderDAO = new OrderDAO();
        long orderId = orderDAO.createOrder(order, orderDetails);

        if (orderId > 0) {
            session.removeAttribute("checkoutItems"); // Xóa giỏ hàng sau khi đặt hàng thành công
//            response.sendRedirect(request.getContextPath() + "/Home/test.jsp"); // Chuyển đến My Order
             request.setAttribute("message", "Đặt hàng thành công: "+orderId);
            request.getRequestDispatcher("/Home/test.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Đặt hàng thất bại. Vui lòng thử lại!");
            request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
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
