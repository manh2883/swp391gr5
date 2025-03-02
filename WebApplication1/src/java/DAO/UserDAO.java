/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.User;
import Models.UserAddress;
import com.sun.jdi.connect.spi.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Acer
 */
public class UserDAO extends DBContext {

    public static List<User> getAllUser() {
        String query = "SELECT * FROM User";
        List<User> userList = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setDoB(rs.getDate("DoB"));

                user.setGender(rs.getInt("gender"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                userList.add(user);
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static User getUserById(int id) {
        String query = "SELECT * FROM User where user_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setDoB(rs.getDate("DoB"));

                user.setGender(rs.getInt("gender"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
                return user;
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        String query = "SELECT * FROM User where User.email = ? limit 1 ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            User user = new User();
            // Lấy dữ liệu từ resultSet
            while (rs.next()) {

                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setDoB(rs.getDate("DoB"));

                user.setGender(rs.getInt("gender"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserByPhone(String phone) {
        String query = "SELECT * FROM User where User.phone_number = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, phone);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setDoB(rs.getDate("DoB"));

                user.setGender(rs.getInt("gender"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createUser(User user) {

        String sql = "INSERT INTO user ( `DoB`, `gender`, `first_name`, "
                + "`last_name`, `email`, `phone_number`, `created_at`, `updated_at`) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(sql);

            java.util.Date utilDate = user.getDoB();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stm.setDate(1, sqlDate);
            stm.setInt(2, user.getGender());
            stm.setString(3, user.getFirstName());
            stm.setString(4, user.getLastName());
            stm.setString(5, user.getEmail());
            stm.setString(6, user.getPhoneNumber());

            stm.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            stm.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
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

    public void deleteUserAndAccountById(int id) {

        String query = "delete from account where Account.user_id = ?; "
                + " Delete FROM User where User.user_id = ?; ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getUserIDByAccountID(int accountID) {
        String query = "SELECT User.user_id FROM User left join account"
                + " on account.user_id = user.user_id"
                + " where account.account_id = ?";
        int userID = -1;
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, accountID);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                userID = rs.getInt("user_id");

            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();
            return userID;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }
    
    public List<UserAddress> getUserAddresses(int userId) {
        List<UserAddress> addressList = new ArrayList<>();
        String query = "SELECT address_id, user_id, address_content FROM user_address WHERE user_id = ?";
        
        try{
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            ResultSet resultSet = stm.executeQuery();
            
            while (resultSet.next()) {
                UserAddress address = new UserAddress();
                address.setAddressID(resultSet.getInt("address_id"));
                address.setUserID(resultSet.getInt("user_id"));
                address.setAddressLine(resultSet.getString("address_content"));
                addressList.add(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return addressList;
    }
    
    public static UserAddress getUserAddressById(int addressId) {
        UserAddress address = null;
        String query = "SELECT address_id, user_id, address_content FROM user_address WHERE address_id = ?";
        
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, addressId);
            ResultSet resultSet = stm.executeQuery();
            
            if (resultSet.next()) {
                address = new UserAddress();
                address.setAddressID(resultSet.getInt("address_id"));
                address.setUserID(resultSet.getInt("user_id"));
                address.setAddressLine(resultSet.getString("address_content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return address;
    }
    
    public static boolean insertAddress(int userID, String addressContent) {
        String query = "INSERT INTO user_address (user_id, address_content) VALUES (?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            stm.setString(2, addressContent);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static void main(String[] args) {
        UserDAO uDao = new UserDAO();
        System.out.println(uDao.getUserIDByAccountID(4));
        System.out.println(uDao.getUserById(4));
    }
}
