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
import java.util.List;

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

            stm.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tùy theo yêu cầu của ứng dụng
        }
    }

    public static List<CartDetail> getAllCartDetailByUserID(int userID, int isVisible) {
//        isVisible = 0 ==  > product is not visible 
//        isVisible = 1 ==  > product is visible
//        other:both

        List<CartDetail> cartDetails = new ArrayList<>();
        String query = "SELECT cd.cart_detail_id, cd.cart_id, cd.product_id, "
                + "cd.product_variant_id, cd.quantity, cd.updated_date,p.price "
                + "FROM cart_detail cd "
                + "JOIN cart c ON cd.cart_id = c.cart_id "
                + "JOIN product p ON cd.product_id = p.product_id "
                + "WHERE c.user_id = ? "
                + "order by cd.updated_date desc ";
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
                switch (isVisible) {
                    case 1:

                        if (ProductDAO.getIsVisibleForProductId(cartDetail.getProductID())) {
                            cartDetails.add(cartDetail);
                        }
                        break;
                    case 0:

                        if (!ProductDAO.getIsVisibleForProductId(cartDetail.getProductID())) {
                            cartDetails.add(cartDetail);
                        }
                        break;
                    default:
                        cartDetails.add(cartDetail);
                        break;
                }

            }
            rs.close();
            con.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return cartDetails;
    }

    public static void editCartDetailByID(int userID, int cartDetailID, String action) {
        if (userID <= 0 || cartDetailID <= 0 || action == null) {
            throw new IllegalArgumentException("Tham số không hợp lệ.");
        }

        String updateQuery = "";
        String deleteQuery = "";

        CartDetail cd = getCartDetailByID(cartDetailID);
        int currentInstock = ProductDAO.getStockByProductAndVariant(cd.getProductID(), cd.getProductVariantID());

        if (action.equals("increment")) {
            updateQuery = "UPDATE cart_detail cd "
                    + "JOIN cart c ON cd.cart_id = c.cart_id "
                    + "SET cd.quantity = cd.quantity + 1, cd.updated_date = NOW() "
                    + "WHERE cd.cart_detail_id = ? AND c.user_id = ?";

        } else if (action.equals("decrement")) {
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
                int updateCount = 0;

                if ((action.equals("increment")
                        && currentInstock > cd.getQuantity())
                        || action.equals("decrement")
                        && currentInstock > 0) {
                    updateStm.setInt(1, cartDetailID);
                    updateStm.setInt(2, userID);
                    updateCount = updateStm.executeUpdate();
                }

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

                con.close();

            } catch (Exception e) {
                con.rollback();  // 🔴 Rollback nếu có lỗi
                e.printStackTrace();
            } finally {
                con.setAutoCommit(true);  // ✅ Bật lại autoCommit sau khi xong
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteCartDetailByID(int userID, int cartDetailID) {
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

    public static void validateCartDetail(CartDetail cartDetail, int userId) {
        int variantId = cartDetail.getProductVariantID();
        String productId = cartDetail.getProductID();
        if (variantId <= 0 || productId.isEmpty() || productId == null) {
            deleteCartDetailByID(userId, cartDetail.getCartDetailID());

        } else {
            int getInstock = ProductDAO.getStockByProductAndVariant(productId, variantId);
            if (getInstock < cartDetail.getQuantity()) {
                for (int i = getInstock; i > cartDetail.getQuantity(); i--) {
                    editCartDetailByID(userId, variantId, "decrement");
                }
            } else if (cartDetail.getQuantity() <= 0) {
                deleteCartDetailByID(userId, cartDetail.getCartDetailID());
            }

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

    public static CartDetail getCartDetailByID(int cartDetailID) {
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
            rs.close();
            stm.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartDetail;
    }

    public static int getCartItemNumberForUserId(int userId) {
        return getAllCartDetailByUserID(userId, 1).size();
    }

    public static int getCartItemQuantityForUserId(int userId) {
        int quantity = 0;
        
        for (CartDetail c : getAllCartDetailByUserID(userId, 1)) {
            quantity += c.getQuantity();
        }
        return quantity;
    }

    public static List<Object[]> getAllCartDetailViewForUser(int userId, int isVisible) {
        List<Object[]> list = new ArrayList<>();
        List<CartDetail> detailList = getAllCartDetailByUserID(userId, isVisible);

        for (CartDetail cd : detailList) {
            Object[] obj = new Object[6];

            String productId = cd.getProductID();
            int variantId = cd.getProductVariantID();
            String variantString = ProductDAO.getVariantInformation(productId, variantId);

            obj[0] = cd;
            obj[1] = ProductDAO.getImgUrlForProductID(cd.getProductID());
            obj[2] = ProductDAO.getCurrentPriceForProductVariant(productId, variantId);
            obj[3] = variantString;
            obj[4] = ProductDAO.getStockByProductAndVariant(productId, variantId);
            obj[5] = ProductDAO.getProductById(productId).getName();
            list.add(obj);

        }
        

        return list;
    }

    public static void main(String[] args) {
        for (Object[] obj : getAllCartDetailViewForUser(1, 1)) {
            for (int i = 0; i < 6; i++) {
                System.out.println(obj[i]);
            }
            System.out.println("=======================");
        }
        System.out.println("--------------------------------------------------------");
    }
}
