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

        Account account = (Account) session.getAttribute("account");

        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("MyOrderList", role) && !pDAO.checkPermissionForRole("OrderManager", role)) {
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
                            statusId = new Long("5");
                        }
                    }

                    int userId = UserDAO.getUserIDByAccountID(account.getAccountId());
                    Long abc = Long.valueOf(userId);
                    if (userId != -1) {
//                        List<Order> orders = OrderDAO.getOrderListByUserId(userId);
                        List<Order> orders = null;
                        try {
                            orders = OrderDAO.filterOrder(abc, null, null, null,
                                    statusId, null, null, null, null, null, null);
                        } catch (SQLException ex) {
                            Logger.getLogger(MyOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String message = (String) session.getAttribute("orderMessage");

                        request.setAttribute("orders", orders);
                        request.setAttribute("message", message);
                        
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
            request.setAttribute("message", "account not found");
            request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
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

}
