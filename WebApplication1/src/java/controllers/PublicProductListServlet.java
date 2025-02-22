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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, String> cList = ProductDAO.getAllProductCategory();
        if (cList != null && !cList.isEmpty()) {
            request.setAttribute("categoryList", cList);
        }

        // Product List
        List<Map.Entry<Product, Map<Boolean, String>>> productList = ProductDAO.getProductListPublic(0);
        
        // Xây dựng URL hiện tại
            StringBuilder currentLink = new StringBuilder();
        
        // filter
        if (productList != null && !productList.isEmpty()) {
            // DungPT code here

            Iterator<Map.Entry<Product, Map<Boolean, String>>> iterator = productList.iterator();

            

            // get Parameter
            String categoryParam = request.getParameter("category");
            String brandParam = request.getParameter("brand");

            // tao category
            Integer category = (categoryParam != null && !categoryParam.isEmpty()) ? Integer.valueOf(categoryParam) : null;
            Integer brand = (brandParam != null && !brandParam.isEmpty()) ? Integer.valueOf(brandParam) : null;


            while (iterator.hasNext()) {
                Map.Entry<Product, Map<Boolean, String>> entry = iterator.next();
                Product product = entry.getKey();

                // Lọc theo category
                if (category != null) {
                    String categoryName = ProductDAO.getCategoryNameById(category);
                    if (!categoryName.equals(product.getCategoryName())) {
                        iterator.remove(); // Xóa nếu không khớp
                        continue; // Tiếp tục vòng lặp, tránh kiểm tra brand nếu đã bị xóa
                    }
                    currentLink.append("category=").append(category).append("&");
                }

                // Lọc theo brand
                if (brand != null) {
                    String brandName = ProductDAO.getBrandNameById(brand);
                    if (!brandName.equals(product.getBrandName())) {
                        iterator.remove(); // Xóa nếu không khớp
                    } else {
                        currentLink.append("brand=").append(brand).append("&");
                    }
                }
            }

// Truyền currentLink về JSP
            request.setAttribute("currentLink", currentLink.toString());

        }

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

        if (productList != null && !productList.isEmpty()) {
            request.setAttribute("productList", productList);
        }

        // phan trang end
//        // log: test subList
//        String log = "";
//                for (Map.Entry<Product, Map<Boolean, String>> entry : subProductList) {
//            log += entry.getKey().getProductId();
//        }
//        request.setAttribute("log", log);
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
