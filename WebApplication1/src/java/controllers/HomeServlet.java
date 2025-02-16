/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import DAO.SliderDAO;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        Map<Integer, String> bList = ProductDAO.getAllBrand();
        Map<Integer, String> cList = ProductDAO.getAllProductCategory();
        Map<Product, Map<Boolean,Boolean>> productList = ProductDAO.getProductListHome(9);
//       List<SliderDetail> sList = SliderDAO.getAllSliderDetailBySliderId(2);
          // ArrayList<SliderDetail> sList = null;
        if (bList != null && !bList.isEmpty()) {
            request.setAttribute("brandList", bList);
        }
        if (cList != null && !cList.isEmpty()) {
            request.setAttribute("categoryList", cList);
        }
        if(productList != null && !productList.isEmpty()){
            request.setAttribute("productList", productList);
        }
//        if(sList != null && !sList.isEmpty()){
//            request.setAttribute("sliderDetailList", sList);
//        }
        
        request.getRequestDispatcher("Home/Home.jsp").forward(request, response);
        
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
        Map<Integer, String> bList = ProductDAO.getAllBrand();
        Map<Integer, String> cList = ProductDAO.getAllProductCategory();
        Map<Product, Map<Boolean,Boolean>> productList = ProductDAO.getProductListHome(9);
//       List<SliderDetail> sList = SliderDAO.getAllSliderDetailBySliderId(2);
          // ArrayList<SliderDetail> sList = null;
        if (bList != null && !bList.isEmpty()) {
            request.setAttribute("brandList", bList);
        }
        if (cList != null && !cList.isEmpty()) {
            request.setAttribute("categoryList", cList);
        }
        if(productList != null && !productList.isEmpty()){
            request.setAttribute("productList", productList);
        }
//        if(sList != null && !sList.isEmpty()){
//            request.setAttribute("sliderDetailList", sList);
//        }
        
        request.getRequestDispatcher("Home/Home.jsp").forward(request, response);
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
