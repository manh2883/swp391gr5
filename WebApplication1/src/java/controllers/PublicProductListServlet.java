/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import DAO.SettingDAO;
import DAO.SliderDAO;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class PublicProductListServlet extends HttpServlet {

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
        ProductDAO pDAO = new ProductDAO();
        SliderDAO sDAO = new SliderDAO();

        // Left side brand
        List<Object[]> bList = SettingDAO.getPublicBrandList();
        if (bList != null && !bList.isEmpty()) {
            request.setAttribute("brandList", bList);
        }

        // Left side category
        Map<Integer, String> cList = SettingDAO.getPublicProductCategory();
        if (cList != null && !cList.isEmpty()) {
            request.setAttribute("categoryList", cList);
        }

        // Product List
        List<Map.Entry<Product, Map<Boolean, String>>> productList = new ArrayList<>();

        // Xây dựng URL hiện tại
        StringBuilder currentLink = new StringBuilder();

        // filter
        // DungPT code here
        // get Parameter
        String categoryParam = request.getParameter("category");
        String brandParam = request.getParameter("brand");

        // tao category
        Long category = (categoryParam != null && !categoryParam.isEmpty()) ? Long.valueOf(categoryParam) : null;
        Long brand = (brandParam != null && !brandParam.isEmpty()) ? Long.valueOf(brandParam) : null;

        List<Product> productFilter = new ArrayList<>();
        try {
            productFilter = ProductDAO.productFilterList(null, null, brand, category, null, null, null, null, null, null);

        } catch (SQLException ex) {
            Logger.getLogger(PublicProductListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        productList = ProductDAO.productFilterView(productFilter);

        // Truyền currentLink về JSP
        if (category != null) {
            currentLink.append("category=").append(category).append("&");
        }
        if (brand != null) {
            currentLink.append("brand=").append(brand).append("&");
        }

        request.setAttribute("currentLink", currentLink.toString());

        // phan trang start
        // Tính toán số lượng trang
        int totalProducts = productList.size();
        int productPerPage = SettingDAO.getPublicProductPerPage();
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

        // Truyền dữ liệu lên JSP
        request.setAttribute("productList", subProductList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("currentLink", currentLink);

        request.setAttribute("maxQuan", SettingDAO.getMaxQuantityInCart());

        if (productList != null && !productList.isEmpty()) {
            request.setAttribute("productList", subProductList);
        }

        // phan trang end
        // truyen du lieu
        request.getRequestDispatcher("Home/ViewPublicProductList.jsp").forward(request, response);
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
