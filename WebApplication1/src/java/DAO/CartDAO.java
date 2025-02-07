/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.CartDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Timestamp;

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
                + "cd.product_variant_id, cd.quantity, cd.updated_date "
                + "FROM cart_detail cd "
                + "JOIN cart c ON cd.cart_id = c.cart_id "
                + "WHERE c.user_id = ?";
        try{
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

                cartDetails.add(cartDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return cartDetails;
    }

    public void editCartDetailByID(int userID, int cartDetailID) {

    }

    public static void main(String[] args) {
        CartDAO cDAO = new CartDAO();
        System.out.println(cDAO.getCartIDByUserID(1));
        cDAO.createCartForUserID(1);
        System.out.println(cDAO.getCartIDByUserID(1));
    }
}
