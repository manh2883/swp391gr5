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
import java.util.HashMap;
import java.util.Map;

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

    public List<UserAddress> getUserAddresses(int userId) {
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
    public static List<User> getFilteredUsers(String search, String status, String sortBy, int start, int recordsPerPage) {
        List<User> userList = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT u.user_id, u.first_name, u.last_name, u.gender, u.email, u.phone_number, "
                + "a.username, r.role_id, r.role_name, a.status "
                + "FROM user u "
                + "LEFT JOIN account a ON u.user_id = a.user_id "
                + "LEFT JOIN roles r ON a.role_id = r.role_id "
                + "WHERE 1=1 ");

        List<Object> paramsList = new ArrayList<>();

        // Tìm kiếm theo tên, email, số điện thoại
        if (search != null && !search.trim().isEmpty()) {
            query.append(" AND (u.first_name LIKE ? OR u.last_name LIKE ? OR u.email LIKE ? OR u.phone_number LIKE ?)");
            String searchPattern = "%" + search + "%";
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
        }

        // Lọc theo trạng thái
        if (status != null && !status.isEmpty()) {
            query.append(" AND a.status = ?");
            paramsList.add(status);
        }

        // Sắp xếp
        if (sortBy != null) {
            switch (sortBy) {
                case "name" ->
                    query.append(" ORDER BY u.first_name, u.last_name");
                case "email" ->
                    query.append(" ORDER BY u.email");
                case "phone" ->
                    query.append(" ORDER BY u.phone_number");
                case "status" ->
                    query.append(" ORDER BY a.status");
                case "Role" ->
                    query.append(" ORDER BY r.role_id");
                default ->
                    query.append(" ORDER BY u.user_id");
            }
        } else {
            query.append(" ORDER BY u.created_at DESC");
        }

        query.append(" LIMIT ?, ?");
        paramsList.add(start);
        paramsList.add(recordsPerPage);

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query.toString());

            // Gán tham số vào PreparedStatement
            for (int i = 0; i < paramsList.size(); i++) {
                stm.setObject(i + 1, paramsList.get(i));
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setGender(rs.getInt("gender"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));

                Account account = new Account();
                account.setUsername(rs.getString("username"));
                account.setRoleId(rs.getInt("role_id"));
//                account.setStatus(rs.getInt("status"));

                user.setAccount(account);
                userList.add(user);
            }

            rs.close();
            stm.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

//    public static ArrayList<Object[]> getFilterViewUser(String search, String status, String sortBy, int start, int recordsPerPage) {
////            HashMap<User, Account> ur = new HashMap<>();
////            ur.put("User", Account);
////            System.out.println(ur);
//
//        List<User> users = getFilteredUsers(search, status, sortBy, start, recordsPerPage);
//        ArrayList<Object[]> viewList = new ArrayList<>();
//
//        for (User user : users) {
//            Object[] row = new Object[3];
//            row[0] = user.getEmail();  // Username (ở đây thay bằng email nếu username null)
//            row[1] = user.getPhoneNumber(); // Role ID
//            row[2] = user.getFirstName() + " " + user.getLastName(); // Full name
//
//            viewList.add(row);
//        }
//
//        return viewList;
//    }
    public static int getTotalUserCount(String search, String status) {
        int total = 0;
        StringBuilder query = new StringBuilder(
                "SELECT COUNT(*) FROM user u "
                + "LEFT JOIN account a ON u.user_id = a.user_id "
                + "WHERE 1=1 ");

        List<Object> paramsList = new ArrayList<>();

        // Apply search filter
        if (search != null && !search.trim().isEmpty()) {
            query.append(" AND (u.first_name LIKE ? OR u.last_name LIKE ? OR u.email LIKE ? OR u.phone_number LIKE ?)");
            String searchPattern = "%" + search + "%";
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
            paramsList.add(searchPattern);
        }

        // Apply status filter
        if (status != null && !status.isEmpty()) {
            query.append(" AND a.status = ?");
            paramsList.add(status);
        }

        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query.toString());

            for (int i = 0; i < paramsList.size(); i++) {
                stm.setObject(i + 1, paramsList.get(i));
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public static void main(String[] args) throws SQLException {
        List<User> userList = getFilteredUsers(null, null, null, 0, 10);
        System.out.println(getUserByEmail("manhzxnm057@gmail.com"));
    }
}
