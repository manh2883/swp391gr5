/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.AccountDAO;
import DAO.UserDAO;
import Models.Account;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Acer
 */
public class CreateNewPassWordServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateNewPassWordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateNewPassWordServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession(false);

        if (session != null) {
            Account acc = (Account) session.getAttribute("account");

            if (acc != null) {
                response.sendRedirect("ChangePassword");
                return;
            }
        }

        request.getRequestDispatcher("Login/AddNewPassWord.jsp").forward(request, response);
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

        String email = request.getParameter("email");
        String newPassWord = request.getParameter("newPassword");
        System.out.println(email + ", " + newPassWord);
        Boolean changeStatus = false;
        if (email != null && !email.isEmpty()) {
            User user = UserDAO.getUserByEmail(email);

            if (user != null) {
                if (newPassWord != null && !newPassWord.isEmpty()) {
                    int accId = AccountDAO.getAccountByUserId(user.getUserId()).getAccountId();
                    changeStatus = AccountDAO.changePassword(accId, newPassWord);
                    String passWord = AccountDAO.getAccountByUserId(user.getUserId()).getPassword();

                    request.setAttribute("userName", email);
                    request.setAttribute("passWord", passWord);
                    request.getRequestDispatcher("Login/Login.jsp").forward(request, response);

                    if (changeStatus) {
                        request.setAttribute("passError", "Change Password successfully");
                    } else {
                        request.setAttribute("passWord", null);
                        request.setAttribute("changeMessage", "Change Password failed");
                    }
                    request.setAttribute("changeStatus", true);
                    request.getRequestDispatcher("Login/Login.jsp").forward(request, response);

                } else {
                    request.setAttribute("passError", "Enter new password");
                    request.getRequestDispatcher("Login/AddNewPassWord.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("emailError", "Email not found");
                request.setAttribute("availOTP", false);
                request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
            }
            request.setAttribute("emailError", "Email not found");
            request.setAttribute("availOTP", false);
            request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
        } else {
            request.setAttribute("emailError", "Email not found");
            request.setAttribute("availOTP", true);
            request.setAttribute("step", "Send OTP");
            request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
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
