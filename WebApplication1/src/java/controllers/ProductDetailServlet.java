/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
import DAO.SettingDAO;
import DAO.UserDAO;
import Models.Account;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class ProductDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailServlet at " + request.getContextPath() + "</h1>");
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
        String productId = request.getParameter("productId");

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
        if (productId != null) {
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                request.setAttribute("message", "product not found");
                request.getRequestDispatcher("Home/test.jsp").forward(request, response);
            } else {
                request.setAttribute("product", product);
                request.setAttribute("imgUrl", productDAO.getImgUrlForProductID(productId));

                List<String> color = productDAO.getAllColorbyProductId(productId);
                if (color != null && !color.isEmpty()) {
                    request.setAttribute("colorList", color);
                } else {
                    request.setAttribute("message", "productId not found");
                    request.getRequestDispatcher("Home/test.jsp").forward(request, response);
                }

                boolean isNew = productDAO.isNewProduct(productId);
                boolean isSale = productDAO.isSaleProduct(productId) != null;

                String tag = null;
                if (isSale) {
                    tag = "isSale";
                } else {
                    if (isNew) {
                        tag = "isNew";
                    }
                }
                request.setAttribute("tag", tag);
                request.setAttribute("netPrice", product.getPrice());

                List<String> size = productDAO.getAllSizebyProductId(productId);
                if (size != null && !size.isEmpty()) {
                    request.setAttribute("sizeList", size);
                } else {
                    request.setAttribute("message", "productId not found");
                    request.getRequestDispatcher("Home/test.jsp").forward(request, response);
                }

                ArrayList<Object[]> imgList = ProductDAO.getImageListByProduct(productId);
                if (imgList != null && !imgList.isEmpty()) {
                    request.setAttribute("imgList", imgList);
                }

                ArrayList<Object[]> varList = ProductDAO.getVariantListForProductId(productId);
                if (varList != null && !varList.isEmpty()) {
                    request.setAttribute("variantList", varList);
                }

                List<Map.Entry<Product, Map<Boolean, String>>> recommendedtList = ProductDAO.getRecommendedProductList(3);
                if (recommendedtList != null && !recommendedtList.isEmpty()) {
//                    request.setAttribute("recommendedtList", recommendedtList);
                }

                request.getRequestDispatcher("Product/ProductDetail.jsp").forward(request, response);
                HttpSession session = request.getSession();
                session.removeAttribute("addMessage");
                session.removeAttribute("addStatus");
            }

        } else {
            request.setAttribute("message", "productId not found");
            request.getRequestDispatcher("Home/test.jsp").forward(request, response);
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
    }
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
