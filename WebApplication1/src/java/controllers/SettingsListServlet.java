/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.AccountDAO;
import DAO.ProductDAO;
import DAO.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Dell
 */
public class SettingsListServlet extends HttpServlet {

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
            out.println("<title>Servlet SettingsListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SettingsListServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int page = 1;
            int pageSize = 3;

            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid page number: " + pageParam);
                }
            }

            String search = request.getParameter("search");
            String type = request.getParameter("type");

            List<Object[]> settings = SettingDAO.getSettings(page, pageSize, search, type);
            int totalRecords = SettingDAO.countSettings(search, type);
            int totalPages = (totalRecords + pageSize - 1) / pageSize; // Tính toán tổng trang chính xác hơn

            List<String> types = SettingDAO.getAllSettingTypes();
            List<Object[]> categories = SettingDAO.getProductCategories();
            List<Object[]> accounts = SettingDAO.getAccounts();

            request.setAttribute("settings", settings);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("search", search);
            request.setAttribute("type", type);
            request.setAttribute("types", types);
            request.setAttribute("categories", categories);
            request.setAttribute("accounts", accounts);

            request.getRequestDispatcher("Setting/SettingsList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Hoặc dùng logger để ghi log
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra!");
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
        String action = request.getParameter("action");

        if ("toggleCategory".equals(action)) {
            String categoryId = request.getParameter("categoryId");
            if (categoryId != null) {
                try {
                    ProductDAO dao = new ProductDAO();
                    dao.toggleCategoryVisibility(Long.parseLong(categoryId));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else if ("toggleAccount".equals(action)) {
            String accountId = request.getParameter("accountId");
            if (accountId != null) {
                try {
                    long id = Long.parseLong(accountId);
                    AccountDAO.toggleAccountStatus(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else if ("saveSetting".equals(action)) {
            String settingId = request.getParameter("settingId");
            String settingValue = request.getParameter("settingValue");

            if (settingId != null && settingValue != null) {
                try {
                    long id = Long.parseLong(settingId);
                    int value = Integer.parseInt(settingValue);
                    SettingDAO.updateSetting(id, value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        response.sendRedirect("SettingsListServlet");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
}
