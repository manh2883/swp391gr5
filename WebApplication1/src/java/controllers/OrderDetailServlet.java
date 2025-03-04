/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import Models.OrderDetail;
import DAO.OrderDAO;
import DAO.PermissionDAO;
import DAO.UserDAO;
import Models.Account;
import Models.Order;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class OrderDetailServlet extends HttpServlet {

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
            out.println("<title>Servlet OrderDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderDetailServlet at " + request.getContextPath() + "</h1>");
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

        int role = 0;
        if (account != null) {
            role = account.getRoleId();

            if (role != 0) {
                PermissionDAO pDAO = new PermissionDAO();

//                String orderIdStr = request.getParameter("orderId");
//                int orderId = Integer.valueOf(orderIdStr);
                Order order = OrderDAO.getOrderInformationById(1);
                
                if (order != null) {
                    int userId = UserDAO.getUserIDByAccountID(account.getAccountId());

                    if (!pDAO.checkPermissionForRole("ViewOrderDetail", role) || userId != order.getUserId()) {
                        request.setAttribute("message", "no permission");
                        request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                    } else {
                        //Main Process
                        
                        ArrayList<Object[]> obj = OrderDAO.getOrderDetailViewByOrderId(1);
                        request.setAttribute("orderDetailList", obj);
                        request.setAttribute("OrderInfomation", order);
                        request.getRequestDispatcher("Order/OrderDetail.jsp").forward(request, response);
                        

                        //                    //side bar open
                        //                    request.setAttribute("defaultDropdown", "productManager");
                        //                    // set title
                        //                    request.setAttribute("title", "Admin Dashboard");
                        //                    // set breadcrumbs
                        //                    request.setAttribute("breadcrumbs", "Product List");
                        //                    request.setAttribute("ProductList", ProductList);
                        //                    request.getRequestDispatcher("AdminDashBoard/ProductList.jsp").forward(request, response);
                        
                    }

                } else {
                    request.setAttribute("message", "order not found");
                    request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("message", "role not found");
                request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "account not found");
            request.getRequestDispatcher("Home/Error404.jsp").forward(request, response);
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
