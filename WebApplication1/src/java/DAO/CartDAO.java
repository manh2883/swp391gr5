/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.CartDetail;
import Models.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    public ArrayList<CartDetail> getAllCartDetailByUserID(int userID) {
        ArrayList<CartDetail> cartDetails = new ArrayList<>();
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
            // Giảm số lượng
            updateQuery = "UPDATE cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "SET cd.quantity = cd.quantity - 1, cd.updated_date = NOW() "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";

            // Xóa nếu số lượng <= 0
            deleteQuery = "DELETE cd FROM cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ? AND cd.quantity <= 0";
        } else {
            return; // Nếu action không hợp lệ, thoát khỏi phương thức
        }
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            try {
                int updateCount;
                try (PreparedStatement updateStm = con.prepareStatement(updateQuery)) {
                    updateStm.setInt(1, cartDetailID);
                    updateStm.setInt(2, userID);
                    updateCount = updateStm.executeUpdate();
                }
                if (updateCount == 0) {
                    // Không có bản ghi nào được cập nhật
                    con.rollback();
                    System.out.println("Cập nhật thất bại. Kiểm tra lại cartDetailID và userID.");
                    return;
                }
                if ("decrement".equals(action) && !deleteQuery.isEmpty()) {
                    // Xóa nếu cần
                    int deleteCount;
                    try (PreparedStatement deleteStm = con.prepareStatement(deleteQuery)) {
                        deleteStm.setInt(1, cartDetailID);
                        deleteStm.setInt(2, userID);
                        deleteCount = deleteStm.executeUpdate();
                    }
                    if (deleteCount > 0) {
                        System.out.println("Sản phẩm đã bị xóa khỏi giỏ hàng do số lượng <= 0.");
                    }
                }
                con.commit();
                System.out.println("Cập nhật giỏ hàng thành công.");
            } catch (Exception e) {
                con.rollback(); // Hoàn tác nếu có lỗi
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tùy theo yêu cầu của ứng dụng, ví dụ ghi log hoặc thông báo lỗi cho người dùng
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
    public static void main(String[] args) {
        CartDAO cDAO = new CartDAO();
//        System.out.println(cDAO.getCartIDByUserID(1));
//        cDAO.createCartForUserID(1);
//        System.out.println(cDAO.getCartIDByUserID(1));
        for (CartDetail c : cDAO.getAllCartDetailByUserID(4)) {
            System.out.println(c);
        }
    }
}
