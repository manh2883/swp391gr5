/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

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
        // Nhận tham số từ request
        String searchValue = request.getParameter("searchValue");
        String filterType = request.getParameter("filterType");
        String pageParam = request.getParameter("page");

        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int pageSize = 10; // Số bản ghi mỗi trang

        // Lấy danh sách settings từ DAO
        List<Object[]> settings = SettingDAO.getSettings(page, pageSize, searchValue, filterType);

        // Lấy danh sách setting_type để hiển thị trong dropdown
        List<String> settingTypes = SettingDAO.getAllSettingTypes();

        // Đếm tổng số settings để phục vụ phân trang
        int totalSettings = SettingDAO.countSettings(searchValue, filterType);
        int totalPages = (int) Math.ceil((double) totalSettings / pageSize);

        // Gửi dữ liệu sang JSP
        request.setAttribute("settings", settings);
        request.setAttribute("settingTypes", settingTypes);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("filterType", filterType);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("Setting/SettingsList.jsp").forward(request, response);
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

        if ("edit".equals(action)) {
            long settingId = Long.parseLong(request.getParameter("settingId"));
            String settingName = request.getParameter("settingName");
            int settingValue = Integer.parseInt(request.getParameter("settingValue"));
            String settingType = request.getParameter("settingType");

            boolean success = SettingDAO.updateSetting(settingId, settingName, settingValue, settingType);
            response.getWriter().write(success ? "success" : "error");
        }
        
        
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
