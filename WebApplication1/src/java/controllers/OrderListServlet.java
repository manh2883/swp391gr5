/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.SettingDAO;
import Models.Account;
import Models.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class OrderListServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderListServlet at " + request.getContextPath() + "</h1>");
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
        // Lấy tham số từ request
        String searchQuery = request.getParameter("search"); // Tìm theo order ID, customer name
        String status = request.getParameter("status"); // Lọc theo trạng thái đơn hàng
        String fromDate = request.getParameter("fromDate"); // Lọc từ ngày
        String toDate = request.getParameter("toDate"); // Lọc đến ngày

//        int page = 1;
//        int pageSize = 10;
        ArrayList<Object[]> list;
        OrderDAO oDAO = new OrderDAO();
        SettingDAO sDAO = new SettingDAO();
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String currentUrl = "OrderList";
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("OrderManager", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    try {
                        list = oDAO.getFilterOrderView(searchQuery, status, fromDate, toDate);
                    } catch (Exception ex) {
                        Logger.getLogger(OrderListServlet.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("message", "Có lỗi khi tải danh sách đơn hàng.");
                        request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                        return;
                    }
                    // Lấy tổng số đơn hàng sau khi lọc
                    int totalRecords = oDAO.getTotalOrderCount(searchQuery, status, fromDate, toDate);

//        // Lấy danh sách đơn hàng
//        List<Order> orders = OrderDAO.getFilteredOrders(search, status, fromDate, toDate, page, pageSize);
                    request.setAttribute("orders", list);
                    request.setAttribute("defaultDropdown", "saleDashboard");

                    request.getRequestDispatcher("AdminDashBoard/OrderList.jsp").forward(request, response);
                }
            } else {
                session.setAttribute("prevLink", "OrderList");
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", "OrderList");
            response.sendRedirect("Login");
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
