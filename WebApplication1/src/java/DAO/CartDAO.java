/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.CartDetail;
import Models.Product;
import Models.User;
import Models.UserAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;

/**
 *
 * @author nguye
 */
public class CartDAO extends DBContext {

    public static int getCartIDByUserID(int userID) {
        int cartID = -1; // Giá trị mặc định nếu không tìm thấy
        String query = "SELECT cart_id FROM cart WHERE user_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                cartID = rs.getInt("cart_id");
            }
            rs.close();
            con.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartID;
    }

    public void createCartForUserID(int userID) {
        String query = "INSERT INTO cart (user_id) VALUES (?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating cart failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tùy theo yêu cầu của ứng dụng
        }
    }

    public List<CartDetail> getAllCartDetailByUserID(int userID) {
        List<CartDetail> cartDetails = new ArrayList<>();
        String query = "SELECT cd.cart_detail_id, cd.cart_id, cd.product_id, "
                + "cd.product_variant_id, cd.quantity, cd.updated_date,p.price "
                + "FROM cart_detail cd "
                + "JOIN cart c ON cd.cart_id = c.cart_id "
                + "JOIN product p ON cd.product_id = p.product_id "
                + "WHERE c.user_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCartDetailID(rs.getInt("cart_detail_id"));
                cartDetail.setCartID(rs.getInt("cart_id"));
                cartDetail.setProductID(rs.getString("product_id"));
                cartDetail.setProductVariantID(rs.getInt("product_variant_id"));
                cartDetail.setQuantity(rs.getInt("quantity"));
                cartDetail.setUpdatedDate(rs.getTimestamp("updated_date"));

                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
//                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));

                cartDetail.setProduct(product);
                cartDetails.add(cartDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return cartDetails;
    }

    public void editCartDetailByID(int userID, int cartDetailID, String action) {
        if (userID <= 0 || cartDetailID <= 0 || action == null) {
            throw new IllegalArgumentException("Tham số không hợp lệ.");
        }

        String updateQuery = "";
        String deleteQuery = "";
        if ("increment".equals(action)) {
            updateQuery = "UPDATE cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "SET cd.quantity = cd.quantity + 1, cd.updated_date = NOW() "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";
        } else if ("decrement".equals(action)) {
            updateQuery = "UPDATE cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "SET cd.quantity = cd.quantity - 1, cd.updated_date = NOW() "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";

            deleteQuery = "DELETE cd FROM cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ? AND cd.quantity <= 0";
        } else {
            return;
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            con.setAutoCommit(false); // 🔹 Tắt autoCommit để có thể rollback khi cần

            try (PreparedStatement updateStm = con.prepareStatement(updateQuery)) {
                updateStm.setInt(1, cartDetailID);
                updateStm.setInt(2, userID);
                int updateCount = updateStm.executeUpdate();

                if (updateCount == 0) {
                    con.rollback();  // 🔴 Chỉ rollback nếu đã setAutoCommit(false)
                    System.out.println("Cập nhật thất bại. Kiểm tra lại cartDetailID và userID.");
                    return;
                }

                if ("decrement".equals(action) && !deleteQuery.isEmpty()) {
                    try (PreparedStatement deleteStm = con.prepareStatement(deleteQuery)) {
                        deleteStm.setInt(1, cartDetailID);
                        deleteStm.setInt(2, userID);
                        int deleteCount = deleteStm.executeUpdate();
                        if (deleteCount > 0) {
                            System.out.println("Sản phẩm đã bị xóa khỏi giỏ hàng do số lượng <= 0.");
                        }
                    }
                }

                con.commit();  // ✅ Commit nếu mọi thứ thành công
                updateStm.close();
                System.out.println("Cập nhật giỏ hàng thành công.");

            } catch (Exception e) {
                con.rollback();  // 🔴 Rollback nếu có lỗi
                e.printStackTrace();
            } finally {
                con.setAutoCommit(true);  // ✅ Bật lại autoCommit sau khi xong
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteCartDetailByID(int userID, int cartDetailID) {
        if (userID <= 0 || cartDetailID <= 0) {
            throw new IllegalArgumentException("userID và cartDetailID phải lớn hơn 0.");
        }

        String query = "DELETE cd FROM cart_detail cd "
                + "JOIN cart c ON cd.cart_id = c.cart_id "
                + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, cartDetailID);
            stm.setInt(2, userID);

            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Không tìm thấy cart detail cần xóa hoặc không thuộc về userID cung cấp.");
                return false;
            } else {
                System.out.println("Xóa cart detail thành công.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCartDetailOwnedByUser(int cartDetailID, int userID) {
        String query = "SELECT 1 FROM cart_detail cd "
                + "JOIN cart c ON cd.cart_id = c.cart_id "
                + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, cartDetailID);
            stm.setInt(2, userID);

            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu cần
        }
        return false;
    }

    public CartDetail getCartDetailByID(int cartDetailID) {
        CartDetail cartDetail = new CartDetail();
        String query = "SELECT * FROM cart_detail WHERE cart_detail_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, cartDetailID);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                // Khởi tạo đối tượng CartDetail và gán giá trị từ kết quả truy vấn
                //CartDetail cartDetail = new CartDetail();
                cartDetail.setCartDetailID(rs.getInt("cart_detail_id"));
                cartDetail.setCartID(rs.getInt("cart_id"));
                cartDetail.setProductID(rs.getString("product_id"));
                cartDetail.setProductVariantID(rs.getInt("product_variant_id"));
                cartDetail.setQuantity(rs.getInt("quantity"));
                cartDetail.setUpdatedDate(rs.getTimestamp("updated_date"));

                // Lấy thông tin sản phẩm (nếu cần)
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.getProductById(rs.getString("product_id"));
                cartDetail.setProduct(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartDetail;
    }

    public static User getUserByID(int userID) {
        User user = null;
        String query = "SELECT * FROM user WHERE user_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setDoB(rs.getTimestamp("DoB"));
                user.setGender(rs.getInt("gender"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<User> getAllUser() {
        List<User> user = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setDoB(rs.getTimestamp("DoB"));
                u.setGender(rs.getInt("gender"));
                u.setAvtLink(rs.getString("avt_link"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setEmail(rs.getString("email"));
                u.setPhoneNumber(rs.getString("phone_number"));
                u.setCreatedAt(rs.getTimestamp("created_at"));
                u.setUpdatedAt(rs.getTimestamp("updated_at"));

                user.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<UserAddress> getUserAddresses(int userID) {
        List<UserAddress> addr = new ArrayList<>();
        String query = "SELECT * FROM user_address WHERE user_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                UserAddress ua = new UserAddress();
                ua.setUserID(rs.getInt("user_id"));
                ua.setAddressID(rs.getInt("address_id"));
                ua.setAddressLine(rs.getString("address_content"));

                addr.add(ua);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    public static UserAddress getUserAddressById(int addressID) {
        String query = "SELECT * FROM user_address WHERE address_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, addressID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                UserAddress ua = new UserAddress();
                ua.setUserID(rs.getInt("user_id"));
                ua.setAddressID(rs.getInt("address_id"));
                ua.setAddressLine(rs.getString("address_content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
        CartDAO cDAO = new CartDAO();
        UserDAO uDAO = new UserDAO();
        int userID = 2; // Đảm bảo user này tồn tại trong bảng user
        String newAddress = "456 Đường XYZ, Quận 2, TP.HCM";
        //        System.out.println(cDAO.getCartIDByUserID(1));
        //        cDAO.createCartForUserID(1);
        //        System.out.println(cDAO.getCartIDByUserID(1));
//        for (UserAddress u : uDAO.getUserAddresses(2)) {
//            System.out.println(u);
//        }
//        cDAO.getUserByID(1);
//        System.out.println(cDAO.getUserByID(1));
//        boolean isInserted = cDAO.insertAddress(userID, newAddress);
//        if (isInserted) {
//            System.out.println("Thêm địa chỉ thành công!");
//        } else {
//            System.out.println("Thêm địa chỉ thất bại!");
//        }
    }
}
