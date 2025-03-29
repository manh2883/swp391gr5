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
import java.sql.Date;

/**
 *
 * @author Acer
 */
public class MyProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet MyProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyProfileServlet at " + request.getContextPath() + "</h1>");
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

        if (session != null) {
            Account acc = (Account) session.getAttribute("account");
            System.out.println(acc);
            if (acc != null) {
                System.out.println("found acc");
                UserDAO uDAO = new UserDAO();
                AccountDAO aDAO = new AccountDAO();
                int uId = uDAO.getUserIDByAccountID(acc.getAccountId());
                User user = uDAO.getUserById(uId);
                if (user != null) {
                    System.out.println("found user");

                    request.setAttribute("user", user);
                    request.setAttribute("account", acc);

                    request.setAttribute("userName", acc.getUsername());
                    request.setAttribute("firstName", user.getFirstName());
                    request.setAttribute("lastName", user.getLastName());
                    request.setAttribute("dob", user.getDoB());
                    int gender = user.getGender();
                    String genderString = "";
                    switch (gender) {
                        case 1:
                            genderString = "Male";
                            break;
                        case 0:
                            genderString = "Female";
                            break;
                        case 2:
                            genderString = "Other";
                            break;
                        default:
                            genderString = "Other";
                            break;
                    }

                    request.setAttribute("genderString", genderString);
                    request.setAttribute("gender", gender);
                    request.setAttribute("defaultDropdown", "myProfile");

                    request.getRequestDispatcher("Home/Profile.jsp").forward(request, response);
                } else {
                    session.setAttribute("prevLink", "MyProfile");
                    response.sendRedirect("Login");
                    return;
                }
            } else {
                session.setAttribute("prevLink", "MyProfile");
                response.sendRedirect("Login");
                return;
            }
        } else {
            session.setAttribute("prevLink", "MyProfile");
            response.sendRedirect("Login");
            return;
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
        UserDAO uDAO = new UserDAO();
        
        if (session != null) {
            Account acc = (Account) session.getAttribute("account");
            System.out.println(acc);
            if (acc != null) {
                System.out.println("found acc");
                int uId = uDAO.getUserIDByAccountID(acc.getAccountId());
                User user = uDAO.getUserById(uId);
                if (user != null) {
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String dob = request.getParameter("dob");
                    int gender = Integer.parseInt(request.getParameter("gender"));
                    Date dobDate = (dob != null) ? Date.valueOf(dob) : new Date(System.currentTimeMillis());
                    uDAO.editProfile(uId,firstName,lastName,dobDate,gender);
                    response.sendRedirect("MyProfile");
                } else {
                    session.setAttribute("prevLink", "MyProfile");
                    response.sendRedirect("Login");
                }
            } else {
                session.setAttribute("prevLink", "MyProfile");
                response.sendRedirect("Login");
            }
        } else {
            session.setAttribute("prevLink", "MyProfile");
            response.sendRedirect("Login");
            return;
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
