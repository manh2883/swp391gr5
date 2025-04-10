/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.PermissionDAO;
import DAO.SaleDAO;
import Models.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public class SaleStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet SaleStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleStatisticServlet at " + request.getContextPath() + "</h1>");
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
        SaleDAO orderDAO = new SaleDAO();
         HttpSession session = request.getSession();
        
        String currentLink = "SaleDashBoard";
        // Lấy account từ session
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            session.setAttribute("prevLink", currentLink);
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        } else if (!PermissionDAO.checkPermissionForRole("SaleDashBoard", account.getRoleId())) {
            request.setAttribute("message", "No Permission");

            request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
        }
        try {
            // Lấy thông tin filter từ request
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String sortBy = request.getParameter("sortBy");
            String order = request.getParameter("order");
            String topStr = request.getParameter("top");

            // Giá trị mặc định nếu người dùng không chọn
            Date startDate = (startDateStr != null) ? Date.valueOf(startDateStr) : new Date(System.currentTimeMillis() - 6L * 24 * 60 * 60 * 1000);
            Date endDate = (endDateStr != null) ? Date.valueOf(endDateStr) : new Date(System.currentTimeMillis());
            int top = (topStr != null) ? Integer.parseInt(topStr) : 3;
            boolean asc = "asc".equalsIgnoreCase(order);
            sortBy = (sortBy != null && (sortBy.equals("revenue") || sortBy.equals("quantity_sold"))) ? sortBy : "revenue";

            // Lấy dữ liệu từ DAO
            int successStatus = 8;
            List<Map<String, Object>> orderStats = orderDAO.getOrderStatistics(startDate, endDate, successStatus);
            List<Map<String, Object>> topProducts = orderDAO.getTopProducts(startDate, endDate, top, sortBy, asc);

            // Gửi dữ liệu sang JSP
            request.setAttribute("orderStats", orderStats);
            request.setAttribute("topProducts", topProducts);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("order", order);
            request.setAttribute("top", top);
            //side bar open
            request.setAttribute("defaultDropdown", "saleDashboard");
            // Forward sang JSP
            request.getRequestDispatcher("AdminDashBoard/SaleStatistic.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dữ liệu dashboard: " + e.getMessage());
            request.getRequestDispatcher("AdminDashBoard/SaleStatistic.jsp").forward(request, response);
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
