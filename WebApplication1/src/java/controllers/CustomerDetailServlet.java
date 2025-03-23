/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.AccountDAO;
import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.UserDAO;
import Models.Account;
import Models.Order;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class CustomerDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet CustomerDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerDetailServlet at " + request.getContextPath() + "</h1>");
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
        int userId = Integer.parseInt(request.getParameter("userId"));
        String currentUrl = "CustomerDetail?userId=" + userId;
        int role = 0;

        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("ViewProfile", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("message", "role not found");
                request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                return;
            }
        } else {
            session.setAttribute("prevLink", currentUrl);
            response.sendRedirect("Login");
            return;
        }

        // Nhận giá trị status từ request
        String activeTabString = request.getParameter("status");
        Long statusId = 1L;
        if (activeTabString != null && !activeTabString.isEmpty()) {
            try {
                statusId = Long.parseLong(activeTabString);
            } catch (NumberFormatException e) {
                statusId = 1L;
            }
        }

        Long abc = Long.valueOf(userId);
        List<Order> orders = null;
        try {
            orders = OrderDAO.filterOrder(abc, null, null, null, statusId, null, null, null, null, null, null, null, null);
        } catch (SQLException ex) {
            Logger.getLogger(MyOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Kiểm tra nếu request là AJAX
//        String ajaxHeader = request.getHeader("X-Requested-With");
        request.setAttribute("orders", orders);

        // Nếu là request từ AJAX, chỉ trả về bảng
        String isAjax = request.getHeader("X-Requested-With");

        // Xử lý request bình thường (không phải AJAX)
        String message = (String) session.getAttribute("orderMessage");
        
        request.setAttribute("orders", orders);
        request.setAttribute("message", message);
       
        request.setAttribute("title", "My Orders");
        request.setAttribute("breadcrumbs", "My Orders");
        request.setAttribute("activeTab", statusId);
        request.setAttribute("userId", userId);

        User user = UserDAO.getUserById(userId);
        Account acc = AccountDAO.getAccountByUserId(userId);

        if (user != null) {
            request.setAttribute("userName", acc.getUsername());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("dob", user.getDoB());

            int gender = user.getGender();
            String genderString = (gender == 1) ? "Male" : (gender == 0) ? "Female" : "Other";
            request.setAttribute("genderString", genderString);
            request.setAttribute("gender", gender);

           
            request.getRequestDispatcher("AdminDashBoard/CustomerDetail.jsp").forward(request, response);
        } else {
            session.setAttribute("prevLink", "MyProfile");
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
