/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Account;
import Models.User;
import java.lang.annotation.Annotation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.sql.Timestamp;

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
                account.setRoleId(rs.getInt("role_id"));
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
                account.setRoleId(rs.getInt("role_id"));

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
                account.setRoleId(rs.getInt("role_id"));
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

    public int getwrongLoginCount(int id) {
        Account account = getAccountById(id);
        return account.getWrongLoginCount();
    }

    public void changeWrongLoginCount(String userName, boolean method) {
        String selectQuery = "SELECT wrong_login_count FROM Account WHERE username = ?";
        String updateQuery = "UPDATE Account SET wrong_login_count = ? WHERE username = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement selectStm = con.prepareStatement(selectQuery);
            PreparedStatement updateStm = con.prepareStatement(updateQuery);

            // Lấy giá trị hiện tại
            selectStm.setString(1, userName);
            ResultSet rs = selectStm.executeQuery();

            int currentCount = 0;
            if (rs.next()) {
                currentCount = rs.getInt("wrong_login_count");
            }
            rs.close();

            // Tăng lên 1 và cập nhật
            if (method) {
                updateStm.setInt(1, currentCount + 1);
            } else {
                updateStm.setInt(1, 0);
            }

            updateStm.setString(2, userName);
            int rowsUpdated = updateStm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Wrong login count updated successfully for user: " + userName);
            } else {
                System.out.println("No account found for user: " + userName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int changeAccountStatus() {
        return 0;
    }

    public Account login(String userName, String passWord) {
        Account account = verifyAccount(userName, passWord);
        if (account == null) {
            changeWrongLoginCount(userName, true);
            if (getAccountByUserName(userName).getWrongLoginCount() >= 5) {
                updateLastWrongLogin(userName);
            }
        } else {
            changeWrongLoginCount(userName, false);
            updateLastLogin(userName);
        }
        return account;

    }

    public static void createAccount(Account account) {

        String sql = "INSERT INTO `account` ( `user_id`, `role_id`, `username`, "
                + "`password`, `password_reset_token`, `password_last_change`, `last_login_time`, "
                + "`wrong_login_count`, `status`, `last_wrong_login`, `last_opt_send`) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(sql);
            // update query
            stm.setInt(1, account.getUserId());
            stm.setInt(2, account.getRoleId());
            stm.setString(3, account.getUsername());
            stm.setString(4, account.getPassword());
            stm.setString(5, null);
            stm.setTimestamp(6, null);
            stm.setTimestamp(7, null);
            stm.setInt(8, 0);
            stm.setInt(9, 1);
            stm.setTimestamp(10, null);
            stm.setTimestamp(11, null);

            int rowsUpdated = stm.executeUpdate();
            // Đóng kết nối và tài nguyên
            System.out.println(rowsUpdated + " row(s) updated.");
            System.out.println("------------------");

            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editAccount(Account oldAcc, Account newAcc) {

    }

    public Account getAccountByUserName(String userName) {
        String query = "SELECT * from Account "
                + "where Account.username = ?";

        Account account = new Account();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, userName);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setRoleId(rs.getInt("role_id"));
                account.setUsername(rs.getString("username"));
                account.setPasswordResetToken(rs.getString("password_reset_token"));
                account.setLastOptSend(rs.getTimestamp("last_opt_send"));
                account.setLastPasswordChange(rs.getTimestamp("password_last_change"));
                account.setLastLoginTime(rs.getTimestamp("last_login_time"));
                account.setWrongLoginCount(rs.getInt("wrong_login_count"));
                account.setLastWrongLogin(rs.getTimestamp("last_wrong_login"));
                account.setStatus(rs.getInt(10));
                System.out.println("3:" + account);
                break;
            }

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (account.getUsername() != null && !account.getUsername().isEmpty()) {
            System.out.println("1: " + account);
            return account;
        }
        return null;
    }

    public Account getAccountById(int id) {
        String query = "SELECT * from Account "
                + "where Account.user_id = ?";

        Account account = new Account();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                account.setAccountId(rs.getInt("account_id"));
                account.setUserId(rs.getInt("user_id"));
                account.setRoleId(rs.getInt("role_id"));
                account.setUsername(rs.getString("username"));
                account.setPasswordResetToken(rs.getString("password_reset_token"));
                account.setLastOptSend(rs.getTimestamp("last_opt_send"));
                account.setLastPasswordChange(rs.getTimestamp("password_last_change"));
                account.setLastLoginTime(rs.getTimestamp("last_login_time"));
                account.setWrongLoginCount(rs.getInt("wrong_login_count"));
                account.setLastWrongLogin(rs.getTimestamp("last_wrong_login"));
                account.setStatus(rs.getInt(10));
                System.out.println("3:" + account);
                break;
            }

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (account.getUsername() != null && !account.getUsername().isEmpty()) {
            System.out.println("1: " + account);
            return account;
        }
        return null;
    }

    public List<Account> getAccountList() {
        return null;
    }

    public void updateLastLogin(String userName) {
        String updateQuery = "UPDATE Account SET last_login_time = ? WHERE username = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement updateStm = con.prepareStatement(updateQuery);

            // Lấy thời gian hiện tại
//            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            // Cập nhật vào database
            updateStm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

            updateStm.setString(2, userName);

            int rowsUpdated = updateStm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Last login time updated successfully for user: " + userName);
            } else {
                System.out.println("No account found for user: " + userName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLastWrongLogin(String userName) {
        String updateQuery = "UPDATE Account SET last_wrong_login = ? WHERE username = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement updateStm = con.prepareStatement(updateQuery);

            // Lấy thời gian hiện tại
//            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            // Cập nhật vào database
            updateStm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

            updateStm.setString(2, userName);

            int rowsUpdated = updateStm.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Last login time updated successfully for user: " + userName);
            } else {
                System.out.println("No account found for user: " + userName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AccountDAO aDAO = new AccountDAO();
        Account account = new Account(1, 2, "manhhehe", "12345678", 0, 1);
        createAccount(account);
        
    }
}
