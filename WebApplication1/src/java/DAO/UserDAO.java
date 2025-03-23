/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import static DAO.AccountDAO.getAccountByUserId;
import DBContext.DBContext;
import Models.Account;
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
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class UserDAO extends DBContext {

    public static boolean editProfile(int userId, String firstName, String lastName, Date dob, int gender) {

        String queUser = "UPDATE user SET first_name = ?, last_name = ?, dob = ?, "
                + "gender = ?, updated_at = ? WHERE user_id = ?";
        try {
            DBContext db = new DBContext();

            java.sql.Connection conn = db.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmtUser = conn.prepareStatement(queUser);

            pstmtUser.setString(1, firstName);
            pstmtUser.setString(2, lastName);
            pstmtUser.setDate(3, new java.sql.Date(dob.getTime()));
            pstmtUser.setInt(4, gender);
            pstmtUser.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // updated_date
            pstmtUser.setInt(6, userId);
            pstmtUser.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public static List<Object[]> getCustomerList() {
        List<Object[]> customers = new ArrayList<>();
        String query = "SELECT u.user_id, "
                + "CONCAT(u.first_name, ' ', u.last_name) AS full_name, "
                + "a.username, "
                + "COALESCE(COUNT(o.order_id), 0) AS order_count, "
                + "COALESCE(SUM(o.total_amount), 0) AS total_spent "
                + "FROM user u "
                + "JOIN account a ON u.user_id = a.user_id "
                + "LEFT JOIN orders o ON u.user_id = o.user_id "
                + "WHERE a.role_id = 2 "
                + // Chỉ lấy customer (role_id = 2)
                "GROUP BY u.user_id, full_name, a.username";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                customers.add(new Object[]{
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("username"),
                    rs.getInt("order_count"),
                    rs.getDouble("total_spent")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
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
        User user = null;
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            User user1 = new User();
            // Lấy dữ liệu từ resultSet
            while (rs.next()) {

                user1.setUserId(rs.getInt("user_id"));
                user1.setEmail(rs.getString("email"));
                user1.setPhoneNumber(rs.getString("phone_number"));
                user1.setAvtLink(rs.getString("avt_link"));
                user1.setDoB(rs.getDate("DoB"));

                user1.setGender(rs.getInt("gender"));
                user1.setFirstName(rs.getString("first_name"));
                user1.setLastName(rs.getString("last_name"));
                user1.setCreatedAt(rs.getTimestamp("created_at"));
                user1.setUpdatedAt(rs.getTimestamp("updated_at"));

            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();
            user = user1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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

    public static List<UserAddress> getUserAddresses(int userId) {
        List<UserAddress> addressList = new ArrayList<>();
        String query = "SELECT address_id, user_id, address_content FROM user_address WHERE user_id = ?";

        try {
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

//     public static List<Object[]> getCustomerList() {
//        List<Object[]> customers = new ArrayList<>();
//        String query = "SELECT u.user_id, "
//                + "CONCAT(u.first_name, ' ', u.last_name) AS full_name, "
//                + "a.username, "
//                + "COALESCE(COUNT(o.order_id), 0) AS order_count, "
//                + "COALESCE(SUM(o.total_amount), 0) AS total_spent "
//                + "FROM user u "
//                + "JOIN account a ON u.user_id = a.user_id "
//                + "LEFT JOIN orders o ON u.user_id = o.user_id "
//                + "WHERE a.role_id = 2 "
//                + // Chỉ lấy customer (role_id = 2)
//                "GROUP BY u.user_id, full_name, a.username";
//        try {
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
//            PreparedStatement stm = con.prepareStatement(query.toString());
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                customers.add(new Object[]{
//                    rs.getInt("user_id"),
//                    rs.getString("full_name"),
//                    rs.getString("username"),
//                    rs.getInt("order_count"),
//                    rs.getDouble("total_spent")
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return customers;
//    }
    // Phương thức kiểm tra địa chỉ đã tồn tại chưa
    public boolean checkAddressExist(int userId, String address) {
        String query = "SELECT COUNT(*) FROM user_address WHERE user_id = ? AND address_content = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query.toString());
            stm.setInt(1, userId);
            stm.setString(2, address);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức lưu địa chỉ mới
    public boolean saveNewAddress(int userId, String newAddress) {
        String query = "INSERT INTO user_address (user_id, address_content) VALUES (?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query.toString());
            stm.setInt(1, userId);
            stm.setString(2, newAddress);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<User> getFilteredUser(String search, String status) {
        List<User> users = new ArrayList<>();

        // Truy vấn JOIN từ user + account + roles để có username & role
        String query = """
        SELECT DISTINCT u.user_id, u.first_name, u.last_name, u.email, u.phone_number, 
                        a.username, a.status, r.role_name
        FROM user u
        LEFT JOIN account a ON u.user_id = a.user_id
        LEFT JOIN roles r ON a.role_id = r.role_id
        WHERE 1=1
    """;

        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            query += " AND (LOWER(u.first_name) LIKE ? OR LOWER(u.last_name) LIKE ? "
                    + " OR LOWER(u.email) LIKE ? OR u.phone_number LIKE ? OR LOWER(a.username) LIKE ?)";
            params.add("%" + search.toLowerCase() + "%");
            params.add("%" + search.toLowerCase() + "%");
            params.add("%" + search.toLowerCase() + "%");
            params.add("%" + search + "%");
            params.add("%" + search.toLowerCase() + "%");
        }

        if (status != null && !status.isEmpty()) {
            query += " AND a.status = ?";
            params.add(status);
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            // Set các tham số vào PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Object[]> getFilterUserView(String search, String name, String email, String phone, String username, String roleName, Integer roleId) {
        ArrayList<Object[]> list = new ArrayList<>();

        // SQL Query với JOIN để lấy thông tin user, account, role
        String query = """
        SELECT DISTINCT 
            u.user_id, 
            u.first_name, 
            u.last_name, 
            u.email, 
            u.phone_number, 
            a.username, 
            r.role_id, 
            r.role_name 
        FROM user u
        LEFT JOIN account a ON u.user_id = a.user_id
        LEFT JOIN roles r ON a.role_id = r.role_id
        WHERE 1=1
    """;

        List<Object> params = new ArrayList<>();

        // Search trên tất cả các trường
        if (search != null && !search.isEmpty()) {
            query += """
            AND (
                LOWER(u.first_name) LIKE ? 
                OR LOWER(u.last_name) LIKE ? 
                OR LOWER(u.email) LIKE ? 
                OR u.phone_number LIKE ? 
                OR LOWER(a.username) LIKE ? 
                OR LOWER(r.role_name) LIKE ?
            )
        """;
            String searchPattern = "%" + search.toLowerCase() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }

        // Lọc theo từng trường cụ thể
        if (name != null && !name.isEmpty()) {
            query += " AND (LOWER(u.first_name) LIKE ? OR LOWER(u.last_name) LIKE ?)";
            params.add("%" + name.toLowerCase() + "%");
            params.add("%" + name.toLowerCase() + "%");
        }
        if (email != null && !email.isEmpty()) {
            query += " AND LOWER(u.email) LIKE ?";
            params.add("%" + email.toLowerCase() + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            query += " AND u.phone_number LIKE ?";
            params.add("%" + phone + "%");
        }
        if (username != null && !username.isEmpty()) {
            query += " AND LOWER(a.username) LIKE ?";
            params.add("%" + username.toLowerCase() + "%");
        }
        if (roleId != null) {
            query += " AND a.role_id = ?";
            params.add(roleId);
        }
        if (roleName != null && !roleName.isEmpty()) {
            query += " AND LOWER(r.role_name) LIKE ?";
            params.add("%" + roleName.toLowerCase() + "%");
        }

        // Kết nối Database
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            // Gán tham số vào câu SQL
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[7];
                    row[0] = rs.getInt("user_id");
                    row[1] = rs.getString("first_name") + " " + rs.getString("last_name");
                    row[2] = rs.getString("email");
                    row[3] = rs.getString("phone_number");
                    row[4] = rs.getString("username");
                    row[5] = rs.getString("role_name");
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int getTotalUserCount(String search, String name, String email, String phone, String username, String roleName, Integer roleId) {
        int count = 0;

        // SQL Query với JOIN để lấy số lượng user
        String query = """
        SELECT COUNT(DISTINCT u.user_id) AS total
        FROM user u
        LEFT JOIN account a ON u.user_id = a.user_id
        LEFT JOIN roles r ON a.role_id = r.role_id
        WHERE 1=1
    """;

        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query += " AND (LOWER(u.first_name) LIKE ? OR LOWER(u.last_name) LIKE ?)";
            params.add("%" + name.toLowerCase() + "%");
            params.add("%" + name.toLowerCase() + "%");
        }
        if (email != null && !email.isEmpty()) {
            query += " AND LOWER(u.email) LIKE ?";
            params.add("%" + email.toLowerCase() + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            query += " AND u.phone_number LIKE ?";
            params.add("%" + phone + "%");
        }
        if (username != null && !username.isEmpty()) {
            query += " AND LOWER(a.username) LIKE ?";
            params.add("%" + username.toLowerCase() + "%");
        }
        if (roleId != null) {
            query += " AND a.role_id = ?";
            params.add(roleId);
        }
        if (roleName != null && !roleName.isEmpty()) {
            query += " AND LOWER(r.role_name) LIKE ?";
            params.add("%" + roleName.toLowerCase() + "%");
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println(getUserById(1));
        editProfile(1,"Manh", "Customer",new Date(0), 1);
        System.out.println(getUserById(1));
    }

}
