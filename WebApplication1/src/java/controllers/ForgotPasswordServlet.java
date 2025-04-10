/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import DAO.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import jdk.jfr.Timestamp;

/**
 *
 * @author Acer
 */
public class ForgotPasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet ForgotPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath() + "</h1>");
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
        boolean availOTP = true;
        request.setAttribute("step", "Send OTP");
        request.setAttribute("availOTP", availOTP);
        request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
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
        String emailInput = request.getParameter("email");
        String otpInput = request.getParameter("otpInput");

        if (emailInput == null || emailInput.isEmpty()) {
            request.setAttribute("otpError", "Email input is required.");
            forwardToPage(request, response);
            return;
        }
        AccountDAO aDAO = new AccountDAO();
        String realOtp = aDAO.getOtpByEmail(emailInput);
        java.sql.Timestamp time = aDAO.getOtpLastSendTimeByEmail(emailInput);

        Instant now = Instant.now();
        Instant last = time.toInstant();
        Duration dur = Duration.between(last, now);

        if (dur.getSeconds() <= 60) {
            if (otpInput == null || otpInput.isEmpty()) {
                request.setAttribute("otpError", "OTP input is required.");
            } else if (!realOtp.equals(otpInput.trim())) {
                request.setAttribute("email", emailInput);
                request.setAttribute("otpError", "OTP does not match.");
            } else {
                request.setAttribute("email", emailInput);
                request.getRequestDispatcher("Login/AddNewPassWord.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("email", emailInput);
            request.setAttribute("otpError", time + ", " + dur.getSeconds() + ", " + "OTP is expired!");
        }

        forwardToPage(request, response);
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
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
