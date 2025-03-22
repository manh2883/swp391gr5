/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.CartDAO;
import DAO.ProductDAO;
import DAO.SettingDAO;
import DAO.UserDAO;
import Models.Account;
import Models.CartDetail;
import Models.User;
import Models.UserAddress;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nguye
 */
public class ViewCartServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewCartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewCartServlet at " + request.getContextPath() + "</h1>");
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
        CartDAO cartDAO = new CartDAO();
        String currentLink = "ViewCart";
        // Lấy account từ session
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            session.setAttribute("prevLink", currentLink);
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        int accountId = account.getAccountId();

        // Lấy userId từ accountId
        UserDAO uDAO = new UserDAO();
        int userId = uDAO.getUserIDByAccountID(accountId);

        if (userId == -1) {
            session.setAttribute("prevLink", currentLink);
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // Xử lý hành động tăng/giảm số lượng hoặc xóa sản phẩm
        String cartDetailIDParam = request.getParameter("cartDetailID");
        String action = request.getParameter("action");

        if (cartDetailIDParam != null && action != null) {
            try {
                int cartDetailID = Integer.parseInt(cartDetailIDParam);

                // Kiểm tra quyền sở hữu cartDetailID
                if (!cartDAO.isCartDetailOwnedByUser(cartDetailID, userId)) {
                    response.sendRedirect(request.getContextPath() + "/ViewCart");
                    return;
                }

                if ("increment".equals(action)) {
                    // Tăng số lượng
                    cartDAO.editCartDetailByID(userId, cartDetailID, "increment");
                } else if ("decrement".equals(action)) {
                    // Giảm số lượng
                    cartDAO.editCartDetailByID(userId, cartDetailID, "decrement");
                } else if ("delete".equals(action)) {
                    // Xóa sản phẩm khỏi giỏ hàng
                    cartDAO.deleteCartDetailByID(userId, cartDetailID);
                }
                // Cập nhật lại giỏ hàng trong session
                List<CartDetail> cartDetails = cartDAO.getAllCartDetailByUserID(userId, 1);
                session.setAttribute("cartDetails", cartDetails);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect(request.getContextPath() + "/ViewCart");
            return;
        }

        // Lấy danh sách giỏ hàng
        List<CartDetail> cartDetails = cartDAO.getAllCartDetailByUserID(userId, 1);
        List<Object[]> cartDetailList = cartDAO.getAllCartDetailViewForUser(userId, 1);

        // Gửi danh sách cartDetails lên trang JSP
        if (cartDetails != null && !cartDetails.isEmpty()) {
            request.setAttribute("maxQuan", SettingDAO.getMaxQuantityInCart());
            request.setAttribute("cartDetails", cartDetails);
            request.setAttribute("cartDetailList", cartDetailList);
            
            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Cart/Cart.jsp").forward(request, response);
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

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        int accountId = account.getAccountId();
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserIDByAccountID(accountId);
        User user = userDAO.getUserById(userId);
        if (user == null) {
            request.setAttribute("message", "User not found.");
            request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
            return;
        }
        // Lấy danh sách địa chỉ của user
        List<UserAddress> userAddresses = userDAO.getUserAddresses(userId);

        // Lấy danh sách các sản phẩm đã chọn từ request
        String[] selectedItems = request.getParameterValues("selectedItems");

        if (selectedItems != null && selectedItems.length > 0) {
            List<CartDetail> checkoutItems = new ArrayList<>();
            CartDAO cDAO = new CartDAO();

            for (String id : selectedItems) {
                try {
                    int cartDetailID = Integer.parseInt(id.trim());
                    CartDetail cartDetail = cDAO.getCartDetailByID(cartDetailID); // Lấy 1 lần duy nhất
                    if (cartDetail != null && cartDetail.getProduct() != null) {
                        int variantId = cartDetail.getProductVariantID();
                        String productId = cartDetail.getProductID();
                        //  System.out.println(cartDetail.getProduct().getPrice());
                        cartDetail.getProduct().setPrice(ProductDAO.getCurrentPriceForProductVariant(productId, variantId));
                        //  System.out.println(cartDetail.getProduct().getPrice());
                        checkoutItems.add(cartDetail);

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // Debug lỗi nếu có ID không hợp lệ
                }
            }

            // Lưu vào session để sử dụng ở checkout.jsp
            session.setAttribute("checkoutItems", checkoutItems);
            session.setAttribute("user", user);
            session.setAttribute("userAddresses", userAddresses);

            request.getRequestDispatcher("Cart/Checkout.jsp").forward(request, response);
        } else {
            // Nếu không có sản phẩm nào được chọn, quay lại giỏ hàng
            response.sendRedirect("ViewCart");
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
