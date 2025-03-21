/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.mail;

import DAO.AccountDAO;
import DAO.SettingDAO;
import DAO.UserDAO;
import Models.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Acer
 */
@WebServlet(name = "SendOtpServlett", urlPatterns = {"/SendOTP"})
public class SendOtpServlet extends HttpServlet {

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
            out.println("<title>Servlet SendOtpServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendOtpServlet at " + request.getContextPath() + "</h1>");
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
//        request.setAttribute("step", "Send OTP");
//        request.setAttribute("availOTP", null);
//        request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
        response.sendRedirect("ForgotPassword");
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

         HttpSession session = request.getSession();
        if (emailInput != null && !emailInput.isEmpty() && emailInput.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            User user = UserDAO.getUserByEmail(emailInput);
            System.out.println(user);
            if (user != null) {
                if (canRequestOTP(emailInput)) {
                    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                        try {   
                            boolean otpSent = SettingDAO.sendOTP(user.getEmail());
                            request.setAttribute("email", emailInput);
                            request.setAttribute("availOTP", !otpSent);

                            session.setAttribute("otp", otpSent);
                            session.setAttribute("otpExpiry", System.currentTimeMillis() + 60000); // Hết hạn sau 60s

                            // Gửi OTP qua email (giả sử đã có hàm sendEmail)
                       

                            response.getWriter().write("OTP Sent");

                        } catch (MessagingException | SQLException ex) {
                            Logger.getLogger(SendOtpServlet.class.getName()).log(Level.SEVERE, "Error sending OTP", ex);
                            request.setAttribute("emailError", "System error. Please try again later.");
                            request.setAttribute("availOTP", false);
                        }
                    } else {
                        request.setAttribute("email", emailInput);
                        request.setAttribute("emailError", "User not found.");
                        request.setAttribute("availOTP", true);
                    }
                } else {
                    return;
                }
            } else {
                request.setAttribute("email", emailInput);
                request.setAttribute("emailError", "User not found.");
                request.setAttribute("availOTP", true);
            }
        } else {
            request.setAttribute("email", emailInput);
            request.setAttribute("emailError", "Invalid email format.");
            request.setAttribute("availOTP", true);
        }
        request.setAttribute("step", "Continue");
        forwardToPage(request, response);
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Login/ForgotPassWord.jsp").forward(request, response);
    }

    private boolean canRequestOTP(String email) {
        Timestamp lastSend = AccountDAO.getOtpLastSendTimeByEmail(email);
        return lastSend == null || Duration.between(lastSend.toInstant(), Instant.now()).getSeconds() > 60;
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
