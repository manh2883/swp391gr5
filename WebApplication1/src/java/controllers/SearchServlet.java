/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import DAO.SettingDAO;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

        String key = request.getParameter("searchKey");

        String searchString = "";
        String searchSQL = "";
        System.out.println(key);
        if (key == null || key.isEmpty()) {
            response.sendRedirect("PublicProductList");
            return;
        }

        if (key != null && !key.isEmpty()) {
            searchString = key.trim().replaceAll("\\s+", "+");
            searchSQL = key.replace("+", "%");  // Thay '+' bằng '%' cho SQL LIKE
        }
//        request.setAttribute("message", key);
//        request.getRequestDispatcher("Home/test.jsp").forward(request, response);
        ProductDAO pDAO = new ProductDAO();
        SettingDAO sDAO = new SettingDAO();
        List<Product> products;
        try {
            products = pDAO.productSearchList(searchSQL);
            List<Map.Entry<Product, Map<Boolean, String>>> productList = pDAO.productFilterView(products);
            //Category, Brand, ProductName, ProductId.

            // Tính toán số lượng trang
            int totalProducts = productList.size();
            int productPerPage = sDAO.getPublicProductPerPage();
            int totalPages = (int) Math.ceil((double) totalProducts / productPerPage);

            // Lấy số trang hiện tại từ tham số yêu cầu
            String pageParam = request.getParameter("page");
            int currentPage = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                    if (currentPage < 1) {
                        currentPage = 1;
                    } else if (currentPage > totalPages) {
                        currentPage = totalPages;
                    }
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }
//        currentLink += "page=" + currentPage + "&";

            // Tính toán chỉ số cho subList
            int startIndex = (currentPage - 1) * productPerPage;
            int endIndex = Math.min(startIndex + productPerPage, totalProducts);

            // Lấy subList cho trang hiện tại 
            List<Map.Entry<Product, Map<Boolean, String>>> subProductList = new ArrayList<>();
            if (!productList.isEmpty()) {
                subProductList = productList.subList(startIndex, endIndex);
            }

            // Left side brand
            List<Object[]> bList = sDAO.getPublicBrandList();
            if (bList != null && !bList.isEmpty()) {
                request.setAttribute("brandList", bList);
            }

            // Left side category
            Map<Integer, String> cList = sDAO.getPublicProductCategory();
            if (cList != null && !cList.isEmpty()) {
                request.setAttribute("categoryList", cList);
            }

            request.setAttribute("productList", subProductList);

            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("currentFunction", "Search");
            request.setAttribute("currentLink", "searchKey=" + searchString + "&");
            request.setAttribute("searchKey", key);
            request.getRequestDispatcher("Home/ViewPublicProductList.jsp").forward(request, response);

//                    request.getRequestDispatcher("Product/ProductListManager.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("Home");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String key = request.getParameter("searchKey");
        String searchString = "";
        String searchSQL = "";

        if (key != null) {
            key = key.trim().replaceAll("\\s+", "+");
            searchString = URLEncoder.encode(key, StandardCharsets.UTF_8.toString()); // Mã hóa URL
            searchSQL = key.replace("+", "%"); // Thay '+' bằng '%' cho SQL LIKE
        }

        response.sendRedirect("Search?searchKey=" + searchString);
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
