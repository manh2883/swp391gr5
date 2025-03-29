package controllers;

import Models.Account;
import Models.UserAddress;
import DAO.AddressDAO;
import DAO.UserDAO;
import Models.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AddressDAO addressDAO = new AddressDAO();
        UserDAO uDAO = new UserDAO();
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }
        int userId = UserDAO.getUserIDByAccountID(account.getAccountId());

        List<UserAddress> addressList = addressDAO.getUserAddresses(userId);

        request.setAttribute("user", uDAO.getUserById(userId));
        request.setAttribute("addressList", addressList);
        request.getRequestDispatcher("/Login/AddressManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AddressDAO addressDAO = new AddressDAO();
        UserDAO uDAO = new UserDAO();
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        int userId = uDAO.getUserIDByAccountID(account.getAccountId());

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String newAddress = request.getParameter("newAddress");
            if (newAddress != null && !newAddress.trim().isEmpty()) {
                addressDAO.addAddress(userId, newAddress);
            }
        } else if ("delete".equals(action)) {
            if (request.getParameter("addressId") != null) {
                int addressId = Integer.parseInt(request.getParameter("addressId"));
                addressDAO.deleteAddress(addressId);
            }
        } else if ("update".equals(action)) {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String updatedAddress = request.getParameter("updatedAddress");
            addressDAO.updateAddress(addressId, updatedAddress);
        }

        response.sendRedirect(request.getContextPath() + "/AddressManager");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
