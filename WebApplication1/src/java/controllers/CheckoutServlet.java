/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.CartDAO;
import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.ProductDAO;
import DAO.SettingDAO;
import DAO.UserDAO;
import Models.Account;
import Models.CartDetail;
import Models.Order;
import Models.OrderDetail;
import Models.User;
import Models.UserAddress;
import com.vnpay.common.Config;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import models.Permission;

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
        PermissionDAO perDAO = new PermissionDAO();
        SettingDAO sDAO = new SettingDAO();
        if (account == null) {
            session.setAttribute("prevLink", "ViewCart");
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        } else if (!perDAO.checkPermissionForRole("CreateOrder", account.getRoleId())) {
            request.setAttribute("message", "No permission");
            request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        List<UserAddress> userAddresses = (List<UserAddress>) session.getAttribute("userAddresses");
        List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("checkoutItems");
        List<CartDetail> checkoutItems = new ArrayList<>();
        if (cartDetails != null) {
            for (CartDetail cd : cartDetails) {
                if (cd.getQuantity() <= sDAO.getMaxQuantityInCart()) {
                    checkoutItems.add(cd);
                }
            }
        }

        if (user == null || checkoutItems == null || checkoutItems.isEmpty()) {
            response.sendRedirect("ViewCart");
            return;
        } else {
            request.setAttribute("user", user);
            request.setAttribute("userAddresses", userAddresses);
            request.setAttribute("checkoutItemList", checkoutItems);

            session.setAttribute("checkoutItems", null);
            session.setAttribute("checkoutItems", checkoutItems);

            request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        PermissionDAO perDAO = new PermissionDAO();
        UserDAO uDAO = new UserDAO();
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        } else {
            if (!perDAO.checkPermissionForRole("CreateOrder", account.getRoleId())) {
                request.setAttribute("messagea", "No permission");
                request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                return;
            }
        }

        int userId = uDAO.getUserIDByAccountID(account.getAccountId());
        // 2. Lấy thông tin từ request
        String selectedAddress = request.getParameter("address");
        String newAddress = request.getParameter("newAddress");
        String finalAddress = (selectedAddress.equals("Other")) ? newAddress : selectedAddress;
        String paymentMethod = request.getParameter("paymentMethod");
        String orderNote = request.getParameter("orderNote");
        String userReceive = request.getParameter("name");
        String contact = request.getParameter("contact");
        // Kiểm tra và lưu địa chỉ mới nếu cần
        if (selectedAddress.equals("Other")) {
            

            // Kiểm tra xem địa chỉ mới đã tồn tại chưa
            if (uDAO.checkAddressExist(userId, newAddress)) {
                request.setAttribute("message", "Địa chỉ này đã tồn tại.");
                request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
                return;
            }

            // Lưu địa chỉ mới vào database
            if (!uDAO.saveNewAddress(userId, newAddress)) {
                request.setAttribute("message", "Lỗi khi lưu địa chỉ mới.");
                request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
                return;
            }
        }
        // 3. Lấy giỏ hàng từ session
        List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("checkoutItems");
        if (cartDetails == null || cartDetails.isEmpty()) {
            response.sendRedirect("ViewCart");
            return;
        }

        // 4. Tạo danh sách OrderDetail và tính tổng tiền
        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalAmount = 0;
        ProductDAO productDAO = new ProductDAO();

        for (CartDetail item : cartDetails) {
            String productId = item.getProductID();
            int variantId = item.getProductVariantID();
            int quantity = item.getQuantity();
            int stock = productDAO.getStockByProductAndVariant(productId, variantId);
            double price = productDAO.getCurrentPriceForProductVariant(productId, variantId);
//            int maxQuantity = SettingDAO.getMaxQuantityInCart();

            if (quantity > stock) {
                request.setAttribute("message", "Không còn sản phẩm!");
                request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
                return;
            }

            OrderDetail orderDetail = new OrderDetail(item.getCartDetailID(), 0, productId, variantId, quantity, (int) price);

            orderDetails.add(orderDetail);
            totalAmount += (int) (price * quantity);
            // xoa khoi gio hang
            CartDAO.deleteCartDetailByID(userId, item.getCartDetailID());

        }

        // Create Order object
        Order order = new Order(
                0,
                userId,
                totalAmount,
                1, // status = pending
                new Date(),
                null,
                Integer.parseInt(paymentMethod),
                2, // paymentStatus: 0 = chưa thanh toán
                finalAddress,
                orderNote,
                userReceive,
                contact
        );

        OrderDAO orderDAO = new OrderDAO();
        long orderId = orderDAO.createOrder(order, orderDetails);

        if (orderId <= 0) {
            request.setAttribute("message", "Đặt hàng thất bại. Vui lòng thử lại!");
            request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
            return;
        }

        // ===================== COD ==========================
        if ("2".equals(paymentMethod)) {
            session.removeAttribute("checkoutItems");
            session.setAttribute("orderMessage", "Order successfully: " + orderId);
            response.sendRedirect("MyOrder");
            return;
        }

        // ===================== VNPAY ==========================
        String txnRef = Config.getRandomNumber(8);
//        OrderDAO.addTxnRefToOrder((int) orderId, txnRef); // lưu vào note hoặc cột txn_ref

        String vnp_Url = Config.vnp_PayUrl;
        String vnp_Returnurl = Config.vnp_ReturnUrl + "?orderId=" + orderId;
//        String vnp_Returnurl = Config.vnp_ReturnUrl;
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_HashSecret = Config.vnp_HashSecret;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(totalAmount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = vnp_Params.get(fieldName);
            if ((value != null) && (!value.isEmpty())) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnp_SecureHash = Config.hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnp_Url + "?" + query.toString();

        // Gửi JSON để redirect từ JS
        response.sendRedirect(paymentUrl);
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
