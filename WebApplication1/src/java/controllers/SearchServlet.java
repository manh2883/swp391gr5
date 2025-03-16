/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class SearchServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
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
        response.sendRedirect("Home");
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
        String key = request.getParameter("searchKey");
//        request.setAttribute("message", key);
//        request.getRequestDispatcher("Home/test.jsp").forward(request, response);
        ProductDAO pDAO = new ProductDAO();
        List<Product> products;
        try {
            products = ProductDAO.productFilterList(key, key, null,
                    null, null, null, null,
                    null, null, null);
            List<Map.Entry<Product, Map<Boolean, String>>> productList = pDAO.productFilterView(products);
            //Category, Brand, ProductName, ProductId.
            if (products == null || products.isEmpty()) {
                request.setAttribute("message", "khong co san pham");
                request.getRequestDispatcher("Home/test.jsp").forward(request, response);
            } else {

                Iterator<Product> iterator = products.iterator();
                while (iterator.hasNext()) {
                    Product p = iterator.next();
                    if (!p.getCategoryName().contains(key)
                            && !p.getBrandName().toLowerCase().contains(key)
                            && !p.getProductId().toLowerCase().contains(key)
                            && !p.getName().toLowerCase().contains(key)) {
                        iterator.remove();
                    }
                }

                if (products == null || products.isEmpty()) {
                    request.setAttribute("message", "khong co san pham");
                    request.getRequestDispatcher("Home/test.jsp").forward(request, response);
                } else {
                    request.setAttribute("ProductList", products);
                    request.getRequestDispatcher("Product/ProductListManager.jsp").forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
