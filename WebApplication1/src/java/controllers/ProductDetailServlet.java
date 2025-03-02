/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.ProductDAO;
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
import java.util.List;

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

                }
                List<String> size = productDAO.getAllSizebyProductId(productId);
                if (size != null && !size.isEmpty()) {
                    request.setAttribute("sizeList", size);

                }

                System.out.println(productDAO.getImgUrlForProductID(productId));
                request.getRequestDispatcher("Product/ProductDetail.jsp").forward(request, response);
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
        String color = request.getParameter("colorInput");
        String size = request.getParameter("sizeInput");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.getRequestDispatcher("Login").forward(request, response);
        } else {
            int userId = UserDAO.getUserIDByAccountID(account.getAccountId());
            String idIn = request.getParameter("idInput");
            ProductDAO.addToCart(idIn, color, size, userId);
//            String nextUrl = "ProductDetail?productId=" + idIn;
//            request.getRequestDispatcher("Home").forward(request, response);

            request.setAttribute("colorList", color + ", " + size + ", " + idIn);
            request.setAttribute("sizeList", userId);
            request.setAttribute("message", ProductDAO.getVariantByColorAndSize(idIn, color, size));
            request.getRequestDispatcher("Home/test.jsp").forward(request, response);

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
