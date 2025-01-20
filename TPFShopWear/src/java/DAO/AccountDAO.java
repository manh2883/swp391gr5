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
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Acer
 */
public class AccountDAO extends DBContext {

    public String checkUserNameExist(String userName) {

        String query = "SELECT * from Account "
                + "where Account.username = ?"
                + "limit 1 ";
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

        String query = "SELECT email FROM Account "
                + "JOIN User ON Account.user_id = User.user_id "
                + "WHERE User.email = ? LIMIT 1";

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
        String query = """
                       SELECT phone_number
                       FROM Account 
                       JOIN User ON User.user_id = Account.user_id 
                       WHERE User.phone_number = ?
                       LIMIT 1;""";
        User user = new User();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, phoneNumber);
 
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                user.setPhoneNumber(rs.getString("phone_number"));
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
        String query = "SELECT * from Account "
                + "where Account.username = ? and Account.password = ? "
                + "limit 1";
        Account account = new Account();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, userName);
            stm.setString(2, passWord);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                account.setAccountId(rs.getInt("account_id"));
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
            return account;
        }
        return null;
    }

    public Account loginWithEmail(String email, String passWord) {
        String query = "SELECT * from Account "
                + "join User on User.user_id = Account.user_id "
                + "where User.email = ? and Account.password = ? "
                + "limit 1";
        Account account = new Account();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, email);
            stm.setString(2, passWord);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                account.setAccountId(rs.getInt("account_id"));
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
            return account;
        }
        return null;
    }

    public Account loginWithPhone(String phoneNumber, String passWord) {
        String query = "SELECT * from Account "
                + "join User on User.user_id = Account.user_id "
                + "where User.phone_number = ? and Account.password = ? "
                + "limit 1";
        Account account = new Account();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, phoneNumber);
            stm.setString(2, passWord);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                account.setAccountId(rs.getInt("account_id"));
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
            return account;
        }
        return null;
    }

    public int increaseWrongLoginTime(int accountId) {
        return 0;
    }

    public Account verifyAccount(String userName, String passWord) {
        int loginMethod = 0;
        String checkResult = null;

        // Kiểm tra tồn tại theo thứ tự ưu tiên
        if ((checkResult = checkEmailExist(userName)) != null && !checkResult.isEmpty()) {
            loginMethod = 1;
        } else if ((checkResult = checkPhoneNumberExist(userName)) != null && !checkResult.isEmpty()) {
            loginMethod = 2;
        } else if ((checkResult = checkUserNameExist(userName)) != null && !checkResult.isEmpty()) {
            loginMethod = 3;
        }

        Account account = null;

        // Xử lý đăng nhập theo phương thức tương ứng
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
                account = null;
        }

        // Debugging thông tin
        System.out.println("Login method: " + loginMethod);
        System.out.println("Account: " + account);

        return account;
    }

    public int getwrongLoginCount(String id) {
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

    public static void main(String[] args) {
        AccountDAO aDAO = new AccountDAO();
        Account acc = aDAO.login("0987654321", "12345678");
        if (acc != null) {
            System.out.println(acc);
        } else {
            System.out.println("null");
        }
    }
}
