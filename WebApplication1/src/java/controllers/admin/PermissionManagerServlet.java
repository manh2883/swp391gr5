/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.admin;

import DAO.PermissionDAO;
import Models.Account;
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
public class PermissionManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet PermissionManagerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PermissionManagerServlet at " + request.getContextPath() + "</h1>");
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
        String currentUrl = "PermissionManager";
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("PermissionManager", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    int[] roleIds = {1, 2, 3, 4}; // Nhận từ config hoặc DB

                    PermissionDAO perDAO = new PermissionDAO();
//          List<String> roles = perDAo.get
                    List<Object[]> permissions = perDAO.getRolePermissionList(roleIds);

                    List<String> roles = perDAO.getRoleList(roleIds);
                    //side bar open
                    request.setAttribute("defaultDropdown", "permissionManager");

                    request.setAttribute("title", "Permission Manager");
                    request.setAttribute("breadcrumbs", "Permission Manager");
                    request.setAttribute("permissions", permissions);
                    request.setAttribute("roleIds", roles);
                    request.getRequestDispatcher("AdminDashBoard/Permission.jsp").forward(request, response);
                }

            } else {
                session.setAttribute("prevLink", "PermissionManager");
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", "PermissionManager");
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

        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String currentUrl = "MyOrder";
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("PermissionManager", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    int permissionId = Integer.parseInt(request.getParameter("permissionId"));
                    int roleId = Integer.parseInt(request.getParameter("roleId"));

                    try {
                        // Gọi DAO để cập nhật quyền trong database
                        pDAO.togglePermission(permissionId, roleId);
                    } catch (SQLException ex) {
                        Logger.getLogger(PermissionManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // Redirect về trang cũ để cập nhật giao diện
                    response.sendRedirect("PermissionManager");
                }

            } else {
                session.setAttribute("prevLink", "PermissionManager");
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", "PermissionManager");
            response.sendRedirect("Login");
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
