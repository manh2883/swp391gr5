/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.PermissionDAO;
import DAO.ProductDAO;
import Models.Account;
import Models.Product;
import com.mysql.cj.Session;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class ProductListManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductListManagerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductListManagerServlet at " + request.getContextPath() + "</h1>");
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
                if (!pDAO.checkPermissionForRole("ProductList", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    //Main Process
                    ProductDAO pdDAO = new ProductDAO();
                    ArrayList<Product> ProductList = pdDAO.getAllProducts();

                    //side bar open
                    request.setAttribute("defaultDropdown", "permissionManager");
                    // set title
                    request.setAttribute("title", "Admin Dashboard");
                    // set breadcrumbs
                    request.setAttribute("breadcrumbs", "Product List");
                    request.setAttribute("ProductList", ProductList);
                    request.getRequestDispatcher("AdminDashBoard/ProductList.jsp").forward(request, response);

                }
            } else {
                session.setAttribute("prevLink", "ProductListManager");
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", "ProductListManager");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");
        ProductDAO pDAO = new ProductDAO();
        boolean success = pDAO.toggleProductVisibility(productId);
        int newState = pDAO.getIsVisibleForProductId(productId) == true ? 1 : 0;

//            response.setContentType("application/json");
//            response.getWriter().write("{\"success\": " + success + ", \"newState\": " + newState + "}");
        response.sendRedirect("ProductListManager");

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
