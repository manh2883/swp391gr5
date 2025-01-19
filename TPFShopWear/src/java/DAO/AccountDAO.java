/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Account;
import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Acer
 */
public class AccountDAO extends DBContext {

    public String checkUserNameExist(String userName) {

        String query = "SELECT top 1 from Account "
                + "where Account.username = ? ";
        Account account = new Account();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, userName);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                account.setUsername(rs.getString("username"));
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (account.getUsername() != null && !account.getUsername().isEmpty()) {
            return account.getUsername();
        }
        return null;
    }

    public String checkEmailExist(String email) {

        String query = "SELECT top 1 from Account"
                + "join User on Account.user_id = User.user_id "
                + "where User.email = ? ";
        User user = new User();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                user.setEmail(rs.getString("email"));
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            return user.getEmail();
        }
        return null;

    }

    public String checkPhoneNumberExist(String phoneNumber) {
        String query = "SELECT top 1 from Account"
                + "join User on Account.user_id = User.user_id "
                + "where User.email = ? ";
        User user = new User();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, phoneNumber);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                user.setEmail(rs.getString("phone_number"));
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            return user.getPhoneNumber();
        }
        return null;
    }

    public Account loginWithUsername(String userName, String passWord) {
        return null;
    }

    public Account loginWithEmail(String email, String passWord) {
        return null;
    }

    public Account loginWithPhone(String phoneNumber, String passWord) {
        return null;
    }

    public int increaseWrongLoginTime(int accountId) {
        return 0;
    }

    public Account verifyAccount(String userName, String passWord) {
        int loginMethod = 0;
        if (checkEmailExist(userName) != null && !checkEmailExist(userName).isEmpty()) {
            loginMethod = 1;
        }
        if (checkPhoneNumberExist(userName) != null && !checkPhoneNumberExist(userName).isEmpty()) {
            loginMethod = 2;
        }
        if (checkUserNameExist(userName) != null && !checkUserNameExist(userName).isEmpty()) {
            loginMethod = 3;
        }

        Account account = new Account();
        switch (loginMethod) {
            case 1:
                account = loginWithEmail(userName, passWord);
                break;
            case 2:
                account = loginWithPhone(userName, passWord);
                break;
            case 3:
                account = loginWithUsername(userName, passWord);
                break;
            default:
                throw new AssertionError();
        }

        return account;
    }
    
    public int getwrongLoginCount(String id){
        Account account = getAccountById(id);
        return account.getWrongLoginCount();
    }
    public int changeAccountStatus() {
        return 0;
    }

    public Account login(String userName, String passWord) {

        return verifyAccount(userName, passWord);

    }

    public void createAccount(Account account) {

    }

    public void editAccount(Account oldAcc, Account newAcc) {

    }

    public Account getAccountByUserName(String userName) {
        return null;
    }

    public Account getAccountById(String id) {
        return null;
    }

    public List<Account> getAccountList() {
        return null;
    }
}
