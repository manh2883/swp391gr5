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
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Acer
 */
public class RegisterServlet extends HttpServlet {

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
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
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

        request.getRequestDispatcher("Login/Register.jsp").forward(request, response);
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

        boolean successful = true;

        UserDAO uDAO = new UserDAO();
        AccountDAO aDAO = new AccountDAO();

        String firstName = request.getParameter("firstNameInput");
        String lastName = request.getParameter("lastNameInput");

        String email = request.getParameter("emailInput");
        String phone = request.getParameter("phoneInput");
        String userName = request.getParameter("userNameInput");
        String passWord = request.getParameter("passWordInput");

        int dobDay = Integer.parseInt(request.getParameter("dobDay"));
        int dobMonth = Integer.parseInt(request.getParameter("dobMonth"));
        int dobYear = Integer.parseInt(request.getParameter("dobYear"));

        Calendar cal = Calendar.getInstance();
        cal.set(dobYear, dobMonth - 1, dobDay); // Tháng bắt đầu từ 0
        Date dob = new Date(cal.getTimeInMillis());

        int gender = Integer.parseInt(request.getParameter("gender"));

        if (aDAO.checkUserNameExist(userName) != null) {
            request.setAttribute("userNameError", "This User Name has been used");
            successful = false;
        }

        if (aDAO.checkPhoneNumberExist(phone) != null) {
            request.setAttribute("phoneError", "This Phone Number has been used");
            successful = false;
        }

        if (aDAO.checkEmailExist(email) != null) {
            request.setAttribute("emailError", "This Email has been used");
            successful = false;
        }

        System.out.println(dob);

        if (successful) {
            User user = new User(email, phone, null, dob, null, gender, firstName, lastName);

            uDAO.createUser(user);
            User user1 = uDAO.getUserByEmail(user.getEmail());

            if (user1 != null) {
                Account newAcc = new Account(user1.getUserId(), 1, userName, passWord, 0, 1);
                newAcc.setPassword(passWord);
                aDAO.createAccount(newAcc);
            } else {
//                uDAO.deleteUserAndAccountById(user1.getUserId());
                request.getRequestDispatcher("Home/test.jsp").forward(request, response);
            }

            request.setAttribute("userName", userName);
            request.setAttribute("passWord", passWord);
            request.getRequestDispatcher("Login/Login.jsp").forward(request, response);
        } else {
            // Giả sử `dob` là java.sql.Date
            if (dob != null) {

                cal.setTime(dob);

                dobDay = cal.get(Calendar.DAY_OF_MONTH);
                dobMonth = cal.get(Calendar.MONTH) + 1; // Tháng trong Java bắt đầu từ 0
                dobYear = cal.get(Calendar.YEAR);

            }
            request.setAttribute("gender", gender);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("userName", userName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("dobDay", dobDay);
            request.setAttribute("dobMonth", dobMonth);
            request.setAttribute("dobYear", dobYear);
            request.getRequestDispatcher("Login/Register.jsp").forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
