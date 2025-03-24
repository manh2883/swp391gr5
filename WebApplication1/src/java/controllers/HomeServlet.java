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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import models.SliderDetail;

/**
 *
 * @author Acer
 */
public class HomeServlet extends HttpServlet {

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

        ProductDAO pDAO = new ProductDAO();
        SliderDAO sDAO = new SliderDAO();
        SettingDAO setDAO = new SettingDAO();
        // Left side brand
        List<Object[]> bList = SettingDAO.getPublicBrandList();
        if (bList != null && !bList.isEmpty()) {
            request.setAttribute("brandList", bList);
        }

        // Left side category
        Map<Integer, String> cList = setDAO.getPublicProductCategory();
        if (cList != null && !cList.isEmpty()) {
            request.setAttribute("categoryList", cList);
        }

        // Product List
        List<Map.Entry<Product, Map<Boolean, String>>> productList = ProductDAO.getProductListPublic(9);
        if (productList != null && !productList.isEmpty()) {
            request.setAttribute("productList", productList);
        }

        // filter
        // phan trang
        // Slider
        Map<Map<String, String>, Map<String, String>> sList = SliderDAO.getCurrentSliderList();
        if (sList != null && !sList.isEmpty()) {
            // Tạo hai Map để tách dữ liệu
            Map<String, String> sliderContent = new LinkedHashMap<>(); // Giữ thứ tự
            Map<String, String> sliderLink = new HashMap<>();

            // Lặp qua sList để lấy dữ liệu
            for (Map.Entry<Map<String, String>, Map<String, String>> entry : sList.entrySet()) {
                sliderContent.putAll(entry.getKey()); // Lưu title và content
                sliderLink.putAll(entry.getValue()); // Lưu title và image link
            }

            // Đặt vào request
            request.setAttribute("sliderContent", sliderContent);
            request.setAttribute("sliderLink", sliderLink);

        }
        request.getRequestDispatcher("/Home/Home.jsp").forward(request, response);
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
        processRequest(request, response);

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
