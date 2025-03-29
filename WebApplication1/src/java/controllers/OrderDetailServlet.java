/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import Models.OrderDetail;
import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.UserDAO;
import Models.Account;
import Models.Order;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import models.OrderDetailViewDTO;

/**
 *
 * @author Dell
 */
public class OrderDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailServlet at " + request.getContextPath() + "</h1>");
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
        String orderIdStr = request.getParameter("orderId");
        String currentUrl = "OrderDetail?orderId=" + orderIdStr;

        int role = -1;
        if (account != null) {
            role = account.getRoleId();
            UserDAO uDAO = new UserDAO();
            OrderDAO oDAO = new OrderDAO();
            if (role != -1) {
                PermissionDAO pDAO = new PermissionDAO();

                // Kiểm tra orderId có hợp lệ không
                int oId = -1;
                System.out.println("Received orderIdStr: " + orderIdStr);

                if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                    request.setAttribute("message", "orderId not found: " + orderIdStr + ", " + oId);
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);

                }

                // Kiểm tra xem orderId có phải số không
                try {
                    oId = Integer.parseInt(orderIdStr.trim());
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid orderId: " + orderIdStr);
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);

                }

                Order order = null;
                if (oId > 0) {
                    order = OrderDAO.getOrderInformationById(oId);

                }

                if (order != null) {
                    int userId = uDAO.getUserIDByAccountID(account.getAccountId());

                    if (pDAO.checkPermissionForRole("ViewOrderDetail", role) || userId == order.getUserId()) {

                        //Main Process
                        ArrayList<Object[]> obj = oDAO.getOrderDetailViewByOrderId(oId);
                        request.setAttribute("orderDetailList", obj);
                        request.setAttribute("orderInformation", order);

                        request.setAttribute("prevLink", currentUrl);
                        request.setAttribute("title", "Order: " + orderIdStr);

                        if (pDAO.checkPermissionForRole("ViewOrderDetail", role)) {
                            request.setAttribute("breadcumb", "Order List");
                            request.setAttribute("breadcumbLink", "OrderList");
                            request.setAttribute("role", "manager");
                        } else if (userId == order.getUserId()) {
                            request.setAttribute("breadcumb", "My Order");
                            request.setAttribute("breadcumbLink", "MyOrder");
                            request.setAttribute("role", "customer");
                        }

                        request.getRequestDispatcher("Order/OrderDetail.jsp").forward(request, response);
//                        request.getRequestDispatcher("Home/test.jsp").forward(request, response);
                    } else {
                        request.setAttribute("message", "no permission");
                        request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("message", "order not found");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);

                }
            } else {
                request.setAttribute("message", "role not found");
                request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);

            }
        } else {
            //  request.setAttribute("message", "account not found");
            session.setAttribute("prevLink", currentUrl);
            response.sendRedirect("Login");
            //  request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String action = request.getParameter("action");

        OrderDAO orderDAO = new OrderDAO();
        Order order = OrderDAO.getOrderInformationById(orderId);
        boolean success = false;
        String message = "Action failed!";

        switch (action) {
            case "cancel":
                success = orderDAO.cancelOrderByCustomer(orderId);
                message = success ? "Order cancelled successfully!" : message;
                break;
            case "cancelBySeller":
                success = orderDAO.cancelOrderBySeller(orderId);
                message = success ? "Order cancelled by seller!" : message;
                break;
            case "receive":
                success = orderDAO.receiveOrder(orderId);
                message = success ? "Order received!" : message;
                break;
            case "refund":
                success = orderDAO.refundOrder(orderId);
                message = success ? "Refund successful!" : message;
                break;
            case "accept":
                success = orderDAO.acceptOrder(orderId);
                message = success ? "Order accepted!" : message;
                break;
            case "ship":
                success = orderDAO.shipOrder(orderId);
                message = success ? "Order shipped!" : message;
                break;
            case "delivered":
                success = orderDAO.deliverOrder(orderId);
                message = success ? "Order delivered!" : message;
                break;
            case "pay":
                success = orderDAO.paidOrder(orderId);
                message = success ? "Order delivered!" : message;
                break;
        }

        response.sendRedirect("OrderDetail?orderId=" + orderId);

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
