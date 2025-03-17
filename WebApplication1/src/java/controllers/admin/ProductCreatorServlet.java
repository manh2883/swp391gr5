/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.admin;

import DAO.ProductDAO;
import DAO.SettingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Acer
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ProductCreatorServlet extends HttpServlet {

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
            out.println("<title>Servlet ProductCreatorServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductCreatorServlet at " + request.getContextPath() + "</h1>");
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

        String[] sizeNList = SettingDAO.getNumbericSizeList(20, 45);
        String[] sizeCList = SettingDAO.getLetterSizelist("S", "XXXL");

        Map<Integer, String> categoryList = ProductDAO.getAllProductCategory();
        List<Object[]> brandList = ProductDAO.getAllBrand();
        List<String> colorList = ProductDAO.getAllColor();

        request.setAttribute("sizeNList", sizeNList);
        request.setAttribute("sizeCList", sizeCList);
        request.setAttribute("categoryDropList", categoryList);
        request.setAttribute("brandDropList", brandList);
        request.setAttribute("colorList", colorList);

        request.getRequestDispatcher("AdminDashBoard/ProductCreator.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");

        String testString = "";
        // Lấy thông tin sản phẩm
        String name = request.getParameter("name");
        testString += name + ", ";

        String description = request.getParameter("description");
        testString += description + ", ";
        String brandIdString = request.getParameter("brandInput");
        if (brandIdString != null && !brandIdString.isEmpty()) {
            if (brandIdString.equals("Other")) {
                String newBrand = request.getParameter("newBrand");
                if (newBrand != null && !newBrand.isEmpty()) {
                    brandIdString = String.valueOf(ProductDAO.createNewBrand(newBrand));
                } else {
                    brandIdString = "-1";
                }
            }
        } else {
            brandIdString = "-1";
        }
        testString += brandIdString + ", ";

        String categoryIdString = request.getParameter("categoryInput");
        if (categoryIdString != null && !categoryIdString.isEmpty()) {
            if (categoryIdString.equals("Other")) {
                String newCategory = request.getParameter("newCategory");
                if (newCategory != null && !newCategory.isEmpty()) {
                    categoryIdString = String.valueOf(ProductDAO.createNewCategory(newCategory));
                } else {
                    categoryIdString = "-1";
                }
            } 
        } else {
            categoryIdString = "-1";
        }
        testString += categoryIdString + ", ";

        double price = Double.parseDouble(request.getParameter("price"));
        testString += price + ", ";
        //Size
        String sizeType = request.getParameter("sizeType");
        String[] sizeList = null;
        if (sizeType.equals("number")) {
            int sizeStart = Integer.valueOf(request.getParameter("sizeStart"));
            int sizeEnd = Integer.valueOf(request.getParameter("sizeEnd"));
            sizeList = (String[]) SettingDAO.getSizeList(sizeStart, sizeEnd);
        } else if (sizeType.equals("letter")) {
            String sizeStart = request.getParameter("sizeStart");
            String sizeEnd = request.getParameter("sizeEnd");
            sizeList = (String[]) SettingDAO.getSizeList(sizeStart, sizeEnd);
        } else {
            sizeList = new String[]{"Standard"};
        }

        // Kiểm tra đường dẫn thư mục lưu ảnh
        String uploadPath = new File(getServletContext().getRealPath("/")).getParentFile().getParent() + "/web/Images/ProductDetail/";
        List<String> messageList = new ArrayList<>();
        System.out.println("Upload Path: " + uploadPath);
        messageList.add("Upload Path: " + uploadPath);
        // Đảm bảo thư mục tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("Created directory: " + created);
            messageList.add("Created directory: " + created);
        }

        // Lấy danh sách biến thể
        List<Map<String, Object>> variantList = new ArrayList<>();

        List<String> imagePaths = new ArrayList<>();
        // Duyệt các biến thể
        for (int i = 1; request.getParameter("variant[" + i + "][color]") != null; i++) {
            String color = request.getParameter("variant[" + i + "][color]");
            messageList.add(color + "\n");
            String newColor = request.getParameter("newColor_" + i);

            if (color.equals("Other")) {
                if (newColor != null && !newColor.isEmpty()) {
                    color = newColor;
                } else {
                    color = "Standard";
                }
            }

            // Danh sách ảnh của biến thể
            for (Part part : request.getParts()) {
                if (part.getName().equals("variant[" + i + "][images]") && part.getSize() > 0) {

                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String fileExtension = ""; // Phần mở rộng (ví dụ: .png, .jpg)
                    String fileBaseName = fileName; // Tên file không có phần mở rộng

                    int dotIndex = fileName.lastIndexOf(".");
                    if (dotIndex != -1) {
                        fileBaseName = fileName.substring(0, dotIndex);
                        fileExtension = fileName.substring(dotIndex);
                    }

                    String newFileName = fileName;
                    File file = new File(uploadPath + File.separator + newFileName);
                    int count = 1;

                    // Nếu file đã tồn tại, thêm số vào tên file
                    while (file.exists()) {
                        newFileName = fileBaseName + "(" + count + ")" + fileExtension;
                        file = new File(uploadPath + File.separator + newFileName);
                        count++;
                    }

                    String filePath = uploadPath + File.separator + newFileName;

                    try {
                        part.write(filePath);
                        System.out.println("File saved at: " + filePath);
                        messageList.add("File saved at: " + filePath);
                        imagePaths.add("Images/ProductDetail/" + newFileName); // Lưu đường dẫn tương đối vào DB
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error saving file: " + e.getMessage());
                        messageList.add("Error saving file: " + e.getMessage());
                    }
                }
            }

            // Nếu không có ảnh, thêm ảnh mặc định
            if (imagePaths.isEmpty()) {
                imagePaths.add("Images/RUN.jpg");
            }

            // Thêm biến thể vào danh sách
            Map<String, Object> variantData = new HashMap<>();
            variantData.put("color", color);
            variantData.put("images", imagePaths);
            variantList.add(variantData);
        }

        request.setAttribute("message1", testString);
        request.setAttribute("message2", sizeList);
        request.getRequestDispatcher("Home/test.jsp").forward(request, response);
    }

// Hàm lấy tên file từ Part
    private String extractFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
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
