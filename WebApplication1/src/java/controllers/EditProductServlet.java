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
import jakarta.servlet.http.Part;
import java.util.List;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class EditProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = new ProductDAO();

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
            out.println("<title>Servlet EditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
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
        
        // Lấy thông tin sản phẩm
        Product product = productDAO.getProductById(productId);
        List<Map<String, Object>> variants = productDAO.getProductVariants(productId);

        request.setAttribute("product", product);
        request.setAttribute("variants", variants);
        request.getRequestDispatcher("Product/EditProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response, String imgUrl)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateProduct".equals(action)) {
            updateProduct(request, response);
        } else if ("updateVariant".equals(action)) {
            updateProductVariant(request, response);
        } else if ("deleteVariant".equals(action)) {
            deleteVariant(request, response);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        String categoryName = request.getParameter("categoryName");

        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + "Images";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String imagePath = null;
        if (!fileName.isEmpty()) {
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            imagePath = "Images" + fileName;
        }

        Product product = new Product(productId, name, description, price, fileName, categoryName, price, null, imagePath);
        productDAO.updateProduct(product);

        response.sendRedirect("Product/EditProduct.jsp?productId=" + productId);
    }

    private void updateProductVariant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String color = request.getParameter("color");
        String size = request.getParameter("size");
        int stock = Integer.parseInt(request.getParameter("stock"));

        boolean success = ProductDAO.updateProductVariant(productId, color, size, stock);

        if (success) {
            response.sendRedirect("EditProduct.jsp?productId=" + productId + "&success=true");
        } else {
            response.sendRedirect("EditProduct.jsp?productId=" + productId + "&error=true");
        }
        
    }

    private void deleteVariant(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String color = request.getParameter("color");
        String size = request.getParameter("size");

        boolean success = ProductDAO.deleteProductVariant(productId, color, size);

        if (success) {
            response.sendRedirect("EditProduct.jsp?productId=" + productId + "&delete_success=true");
        } else {
            response.sendRedirect("EditProduct.jsp?productId=" + productId + "&delete_error=true");
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
