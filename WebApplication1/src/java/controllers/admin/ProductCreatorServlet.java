/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.admin;

import DAO.PermissionDAO;
import DAO.ProductDAO;
import DAO.SettingDAO;
import Models.Account;
import Models.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String currentUrl = "MyOrder";
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("AddProducts", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    List<String> sizeNList = SettingDAO.getNumbericSizeList(20, 45);
                    List<String> sizeCList = SettingDAO.getLetterSizelist("S", "XXL");

                    String[] nList = new String[sizeNList.size()];
                    for (int i = 0; i < sizeNList.size(); i++) {
                        nList[i] = sizeNList.get(i);
                    }

                    String[] cList = new String[sizeCList.size()];
                    for (int i = 0; i < sizeCList.size(); i++) {
                        cList[i] = sizeCList.get(i);
                    }

                    Map<Integer, String> categoryList = ProductDAO.getAllProductCategory();
                    List<Object[]> brandList = ProductDAO.getAllBrand();
                    List<String> colorList = ProductDAO.getAllColor();

                    request.setAttribute("sizeNList", nList);
                    request.setAttribute("sizeCList", cList);
                    request.setAttribute("categoryDropList", categoryList);
                    request.setAttribute("brandDropList", brandList);
                    request.setAttribute("colorList", colorList);
                    request.setAttribute("defaultDropdown", "productManager");
                    request.getRequestDispatcher("AdminDashBoard/ProductCreator.jsp").forward(request, response);
                }
            }
        } else {
            session.setAttribute("prevLink", "ProductCreator");
            response.sendRedirect("Login");

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
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String currentUrl = "ProductCreator";
        List<Map<String, String>> variantList = new ArrayList<>();
        int role = 0;
        if (account != null) {
            role = account.getRoleId();
            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();
                if (!pDAO.checkPermissionForRole("AddProducts", role)) {
                    request.setAttribute("message", "no permission");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                } else {
                    request.setCharacterEncoding("UTF-8");

                    String testString = "";
                    // Lấy thông tin sản phẩm
                    String name = request.getParameter("name");
                    testString += name + ", ";

                    String description = request.getParameter("description");
                    testString += description + ", ";
                    String brandIdString = request.getParameter("brandInput");

                    String newBrand = request.getParameter("newBrand");

                    String categoryIdString = request.getParameter("categoryInput");

                    String newCategory = request.getParameter("newCategory");

                    int price = Integer.parseInt(request.getParameter("price"));
                    testString += price + ", ";
                    //Size
                    String sizeType = request.getParameter("sizeType");
                    List<String> sizeList = new ArrayList<>();
                    switch (sizeType) {
                        case "number": {
                            int sizeStart = Integer.parseInt(request.getParameter("sizeStart"));
                            int sizeEnd = Integer.parseInt(request.getParameter("sizeEnd"));
                            sizeList = SettingDAO.getSizeList(sizeStart, sizeEnd);
                            break;
                        }
                        case "letter": {
                            String sizeStart = request.getParameter("sizeStart");
                            String sizeEnd = request.getParameter("sizeEnd");
                            sizeList = SettingDAO.getSizeList(sizeStart, sizeEnd);
                            break;
                        }
                        default:
                            sizeList.add("Standard");
                            break;
                    }

                    // Kiểm tra đường dẫn thư mục lưu ảnh
                    String uploadPath = new File(getServletContext().getRealPath("/")).getParentFile().getParent() + "/web/Images/ProductDetail/";
//                    List<String> messageList = new ArrayList<>();
                    System.out.println("Upload Path: " + uploadPath);
//                    messageList.add("Upload Path: " + uploadPath);
                    // Đảm bảo thư mục tồn tại
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        boolean created = uploadDir.mkdirs();
                        System.out.println("Created directory: " + created);
//                        messageList.add("Created directory: " + created);
                    }

                    // Lấy danh sách biến thể
//        List<Map<String, Object>> variantList = new ArrayList<>();
                    List<String> imagePaths = new ArrayList<>();
                    List<String> colorList = new ArrayList<>();
                    String colorTest = "";
                    // Duyệt các biến thể
                    for (int i = 1; request.getParameter("variant[" + i + "][color]") != null; i++) {
                        String color = request.getParameter("variant[" + i + "][color]");
                        String imgUrl = "";
//                        messageList.add(color + "\n");
                        String newColor = request.getParameter("newColor_" + i);

                        if (color.equals("Other")) {
                            if (newColor != null && !newColor.isEmpty()) {
                                color = newColor;
                            } else {
                                color = "Standard";
                            }
                        }
                        colorTest += color + ", ";

                        if (color.length() > 1) {
                            color = color.substring(0, 1).toUpperCase() + color.substring(1).toLowerCase();
                        } else {
                            color = color.toUpperCase();
                        }
                        System.out.println("color: " + color);
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
//                                    messageList.add("File saved at: " + filePath);
                                    // Lưu đường dẫn tương đối vào DB
                                    imgUrl = "Images/ProductDetail/" + newFileName;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("Error saving file: " + e.getMessage());
//                                    messageList.add("Error saving file: " + e.getMessage());
                                }
                            }
                        }

                        if (imgUrl.isEmpty() || imgUrl == null) {
                            imgUrl = "Images/RUN.jpg";
                        }
                        // Thêm biến thể vào danh sách

                        if (!colorList.contains(color)) { // **Kiểm tra xem màu đã tồn tại chưa**
                            colorList.add(color);
                            Map<String, String> variantData = new HashMap<>();
                            variantData.put("color", color);
                            variantData.put("images", imgUrl.isEmpty() ? "Images/RUN.jpg" : imgUrl);
                            variantList.add(variantData);
                        }
                    }
                    Product returnProduct = null;
                    try {
                        returnProduct = ProductDAO.productCreator(name, description, brandIdString, newBrand,
                                price, categoryIdString, newCategory, variantList, sizeList);
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductCreatorServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

//        request.setAttribute("message1", name + description + brandIdString + newBrand
//                + price + categoryIdString + newCategory);
//                    request.setAttribute("message1", colorList);
//                    request.setAttribute("message2", variantList);
//                    request.getRequestDispatcher("Home/test.jsp").forward(request, response);
                    if (returnProduct != null) {
                        response.sendRedirect("ProductDetail?productId=" + returnProduct.getProductId());
                    } else {
                        request.setAttribute("message1", "Unknown Error");
                        request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                    }
                }
            }
        } else {
            session.setAttribute("prevLink", "ProductCreator");
            response.sendRedirect("Login");
        }
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
