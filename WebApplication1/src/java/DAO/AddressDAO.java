package DAO;

import DBContext.DBContext;
import Models.UserAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public List<UserAddress> getUserAddresses(int userId) {
        List<UserAddress> addresses = new ArrayList<>();
        String query = "SELECT * FROM user_address WHERE user_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                addresses.add(new UserAddress(rs.getInt("address_id"), userId, rs.getString("address_content")));
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public boolean addAddress(int userId, String addressContent) {
        String query = "INSERT INTO user_address (user_id, address_content) VALUES (?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            stm.setString(2, addressContent);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAddress(int addressId, String addressContent) {
        String query = "UPDATE user_address SET address_content = ? WHERE address_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, addressContent);
            stm.setInt(2, addressId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAddress(int addressId) {
        String query = "DELETE FROM user_address WHERE address_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, addressId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDuplicateAddress(int userId, String addressContent) {
        String query = "SELECT COUNT(*) FROM user_address WHERE user_id = ? AND LOWER(address_content) = LOWER(?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            stm.setString(2, addressContent.trim());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
