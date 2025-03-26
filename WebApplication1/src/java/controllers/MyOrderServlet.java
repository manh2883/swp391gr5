/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Models.Account;
import Models.Order;
import Models.Product;
import com.vnpay.common.Config;
import static com.vnpay.common.Config.hmacSHA512;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class MyOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet MyOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderServlet at " + request.getContextPath() + "</h1>");
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

        String orderId = request.getParameter("orderId");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        System.out.println("Test: " + validateSignature(request));
        // Xác thực chữ ký từ VNPAY
//        if (validateSignature(request)) {
//            response.getWriter().write("Invalid Signature!");
//            return;
//        }
//
        if ("00".equals(vnp_ResponseCode)) { // "00" = Thanh toán thành công
            OrderDAO.paidOrder(Integer.parseInt(orderId));
            request.setAttribute("message", "Order successfully: " + orderId);
        }else{
             request.setAttribute("message", "Payment failed: " + orderId);
        }

        Account account = (Account) session.getAttribute("account");
        String currentUrl = "MyOrder";
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("MyOrderList", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    //Main Process
                    String activeTabString = request.getParameter("status"); // Nhận giá trị status từ request
                    Long statusId = new Long("1");
                    if (activeTabString != null && !activeTabString.isEmpty()) {
                        try {
                            statusId = Long.parseLong(activeTabString);

                        } catch (NumberFormatException e) {
                            statusId = new Long("1");
                        }
                    }

                    int userId = UserDAO.getUserIDByAccountID(account.getAccountId());
                    Long abc = Long.valueOf(userId);
                    System.out.println("user id: " + userId);
                    if (userId != -1) {
//                        List<Order> orders = OrderDAO.getOrderListByUserId(userId);
                        List<Order> orders = null;
                        try {

                            orders = OrderDAO.filterOrder(abc, null, null, null,
                                    statusId, null, null, null, null, null, null, null, null);
                        } catch (SQLException ex) {
                            Logger.getLogger(MyOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String message = (String) session.getAttribute("orderMessage");

                        request.setAttribute("orders", orders);
                        request.setAttribute("message", message);

                        session.removeAttribute("orderMessage");
                        //side bar open
                        request.setAttribute("defaultDropdown", "saleDashboard");
                        // set title
                        request.setAttribute("title", "My Orders");
                        // set breadcrumbs
                        request.setAttribute("breadcrumbs", "My Orders");
                        // active status tab
                        request.setAttribute("activeTab", statusId);

                        request.getRequestDispatcher("Order/MyOrder.jsp").forward(request, response);

                    }

                }
            } else {
                request.setAttribute("message", "role not found");
                request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
            }
        } else {
            //  request.setAttribute("message", "account not found");
            session.setAttribute("prevLink", currentUrl);
            response.sendRedirect("Login");
            // request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
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

    public static boolean validateSignature(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            String key = e.nextElement();
            if (!key.equals("vnp_SecureHash")) { // Loại bỏ SecureHash khỏi danh sách
                params.put(key, request.getParameter(key));
            }
        }

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = paramNames.nextElement();
            System.out.println(param + " = " + request.getParameter(param));
        }

        // Lấy SecureHash từ request
        String secureHash = request.getParameter("vnp_SecureHash");
        String secretKey = Config.vnp_HashSecret;

        // Tạo chuỗi dữ liệu
        String data = createDataString(params);

        // Debug: In ra dữ liệu trước khi hash
        System.out.println("Data String Before Hash: " + data);
        System.out.println("Secure Hash From VNPAY: " + secureHash);

        // Tạo hash từ chuỗi dữ liệu
        String generatedHash = hmacSHA512(secretKey, data);

        // Debug: In ra hash đã tạo
        System.out.println("Generated Hash: " + generatedHash);

        // So sánh hash, không phân biệt hoa thường
        return generatedHash.equalsIgnoreCase(secureHash);
    }

    private static String createDataString(Map<String, String> params) {
        SortedMap<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (data.length() > 0) {
                data.append("&");
            }
            data.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return data.toString();
    }

}
