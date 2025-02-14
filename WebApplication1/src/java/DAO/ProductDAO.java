/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Cart;
import Models.CartDetail;
import Models.Product;
import Models.User;
import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Dell
 */
public class ProductDAO extends DBContext {

    public static Product getProductById(String id) {
        for (Product p : getAllProducts()) {
            if (p.getProductId() == null ? id == null : p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;

    }

    public static String getImgUrlForProductID(String Id) {
        for (Product p : getAllProducts()) {
            if (p.getProductId() == null ? Id == null : p.getProductId().equals(Id)) {
                if (p.getImgUrl() == null || p.getImgUrl().isEmpty()) {
                    return "Images/RUN.jpg";
                } else {
                    return p.getImgUrl();
                }
            }
        }
        return null;
    }

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String query = """
                       SELECT distinct product.product_id,product.description, product.name, price, brand.name, 
                       product_category.category_name,  product.created_at, product_variant.variant_id, product_image.back_link 
                       FROM tpfshopwearv2.product 
                        left join brand on product.brand_id = brand.brand_id 
                        left join product_category on product.category_id = product_category.category_id
                        join product_variant on product.product_id = product_variant.product_id
                        left join product_image on product_variant.variant_id = product_image.product_variant_id
                       order by product.product_id asc;""";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Product product = new Product();

                product.setBrandName(rs.getString(5));
                product.setProductId(rs.getString("product_Id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCategoryName(rs.getString(6));
                product.setCreateAt(rs.getTimestamp("created_at"));
                product.setImgUrl(rs.getString("back_link"));
                products.add(product);
            }
            rs.close();
        } catch (SQLException e) {

        }

        return products;
    }

    public void addProduct(Product product) {
        String query = "INSERT INTO Product (name, price, description, stock, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getDescription());
            stm.setString(5, product.getCategoryName());
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, category_id = ? WHERE product_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getDescription());
//            
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String query = "DELETE FROM Product WHERE product_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllSizebyProductId(String productId) {
        List<String> sizes = new ArrayList<>();
        String query = "SELECT DISTINCT size FROM product_variant WHERE product_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (rs.getString("size") != null && !rs.getString("size").isEmpty()) {
                    sizes.add(rs.getString("size"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sizes;
    }

    public List<String> getAllColorbyProductId(String productId) {

        List<String> colors = new ArrayList<>();
        String query = "SELECT DISTINCT color FROM product_variant WHERE product_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (rs.getString("color") != null && !rs.getString("color").isEmpty()) {
                    colors.add(rs.getString("color"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colors;
    }

//    //check xem cart có tồn tại hay không?; gọi getUserById; getCartIdByUserId;nếu có cart thì chạy tiếp, nếu không có thì tạo cart.  
//// kiểm tra xem có variant hay không?
////kiểm tra product xem có hay không?
////kiểm tra xem còn hàng trong kho hay không(viết một hàm mới).
////nếu có đủ hết các điều kiện trên thì sẽ add to cart
//    public static int getOrCreateCartId(int userId) {
//        
//        
//        int cartId = -1;
//                    String query = "SELECT cart_id FROM cart WHERE user_id = ?";
//
//        try {
//            // Kiểm tra xem user đã có giỏ hàng chưa
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();
//            PreparedStatement stm = con.prepareStatement(query);
//            stm.setInt(1, userId);
//            ResultSet rs = stm.executeQuery();
//
//            if (rs.next()) {
//                cartId = rs.getInt("cart_id");
//            }else {
//                // Nếu chưa có, tạo giỏ hàng mới
//                String insertCart = "INSERT INTO cart (user_id) VALUES (?)";
//                PreparedStatement stmInsert = con.prepareStatement(insertCart, PreparedStatement.RETURN_GENERATED_KEYS);
//                stmInsert.setInt(1, userId);
//                stmInsert.executeUpdate();
//                ResultSet generatedKeys = stmInsert.getGeneratedKeys();
//                if (generatedKeys.next()) {
//                    cartId = generatedKeys.getInt(1);
//                }
//                stmInsert.close();
//            }
//            stm.close();
//            rs.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cartId;
//    }
//
//
    // Kiểm tra biến thể sản phẩm có tồn tại không (theo color, size)
    public static int getVariantByColorAndSize(String productId, String color, String size) {

        String query = "SELECT variant_id from product_variant where color = ? and size = ? and product_id = ? ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(3, productId);
            stm.setString(1, color);
            stm.setString(2, size);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("variant_id");
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Kiểm tra còn hàng trong kho không
    public static int getStockForVariantProduct(String productId, String color, String size) {

        String query = "SELECT stock FROM product_variant WHERE product_id = ? AND color = ? AND size = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setString(2, color);
            stm.setString(3, size);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock");
                return stock; // Trả về true nếu còn hàng
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getColorForVariantProduct(String productId, int variantId) {

        String query = "SELECT color FROM product_variant WHERE product_id = ? AND variant_id = ? ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, variantId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String color = rs.getString("color");
                if (color != null && color.isEmpty()) {
                    return color; // Trả về true nếu còn hàng

                } else {
                    return null;
                }
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSizeForVariantProduct(String productId, int variantId) {

        String query = "SELECT size FROM product_variant WHERE product_id = ? AND variant_id = ? ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, variantId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String size = rs.getString("size");
                if (size != null && size.isEmpty()) {
                    return size; // Trả về true nếu còn hàng

                } else {
                    return null;
                }
            }
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm sản phẩm vào giỏ hàng nếu đủ điều kiện
    public static void addToCart(String productId, String color, String size, int userId) {
        UserDAO userdao = new UserDAO();
        CartDAO cartdao = new CartDAO();
        User us = userdao.getUserById(userId);

        if (us != null) {
            int ca = cartdao.getCartIDByUserID(userId);
            if (ca <= -1) {
                cartdao.createCartForUserID(userId);
            }
            ca = cartdao.getCartIDByUserID(userId);
            System.out.println("cart_id: " +ca);
            int variantId = getVariantByColorAndSize(productId, color, size);
            System.out.println(variantId);
            if (variantId > 0) {
                int cartDetailId = CheckProductExistInCart(productId, variantId, ca);
                System.out.println(cartDetailId);
                if (cartDetailId > 0) {
                    cartdao.editCartDetailByID(userId, cartDetailId, "increment");
                } else {
                    System.out.println(getStockForVariantProduct(productId, color, size));
                    if (getStockForVariantProduct(productId, color, size) > 0) {
                        AddCartDetail(productId, variantId, ca);
                    }
                }
            }
        }
    }

    public static int CheckProductExistInCart(String productId, int variantId, int cartID) {
        String query = "SELECT cart_detail_id FROM cart_detail WHERE product_id = ? and product_variant_id = ? and cart_id = ?";
        int id = -1;

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, variantId);
            stm.setInt(3, cartID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                id = rs.getInt("cart_detail_id");
                System.out.println(id);
            }
            stm.close();
            rs.close();
            return id; // Trả về true nếu còn hàng

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void AddCartDetail(String productId, int variantId, int cartID) {
        String query = "INSERT INTO `cart_detail` (`cart_id`, "
                + "`product_id`, `product_variant_id`, `quantity`, `updated_date`) "
                + "VALUES (?, ?, ?, ?, ?);";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, cartID);
            stm.setString(2, productId);
            stm.setInt(3, variantId);

            int quantity = getStockForVariantProduct(productId, getColorForVariantProduct(productId, variantId), getSizeForVariantProduct(productId, variantId));
            stm.setInt(4, quantity + 1);
            stm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void AddToCart(String productId, String color, String size, int userId) throws SQLException {
//        
//        DBContext db = new DBContext();
//        java.sql.Connection con = db.getConnection();
//       PreparedStatement stm = con.prepareStatement(productId);
//       ResultSet rs = stm.executeQuery();
//        try {
//            con.setAutoCommit(false);
//        }catch (SQLException e) {
//            e.printStackTrace();
//            }
//
//            // Kiểm tra xem sản phẩm có tồn tại không
//            String checkProductQuery = "SELECT COUNT(*) FROM product group by product_id ";
//            try {
//                PreparedStatement stmcheckProduct = con.prepareStatement(checkProductQuery);
//                stm.setString(1, productId);
//            }catch (SQLException e) {
//            e.printStackTrace();
//            }
//                try {
//                    PreparedStatement stm = con.prepareStatement(checkProductQuery);
//                    ResultSet rs = stm.executeQuery();
//                    if (rs.next() && rs.getInt(1) == 0) {
//                        throw new SQLException("Sản phẩm không tồn tại.");
//                    }
//                }catch (SQLException e) {
//            e.printStackTrace();
//            }
//            
//
//            // Kiểm tra biến thể sản phẩm
//            String checkVariantQuery = "SELECT variant_id, stock FROM product_variant WHERE product_id = ? AND color = ? AND size = ?";
//            int variantId = -1, stock = 0;
//            try {
//                 PreparedStatement stmcheckVariant = con.prepareStatement(checkVariantQuery);
//                
//                stmcheckVariant.setString(1, productId);
//                stmcheckVariant.setString(2, color);
//                stmcheckVariant.setString(3, size);
//            }catch (SQLException e) {
//            e.printStackTrace();
//            }
//                try {
//                    ResultSet rs = stm.checkVariant.executeQuery();
//                    
//                    if (rs.next()) {
//                        variantId = rs.getInt("variant_id");
//                        stock = rs.getInt("stock");
//                    } else {
//                        throw new SQLException("Biến thể sản phẩm không tồn tại.");
//                    }
//                }
//            
//
//            // Kiểm tra hàng tồn kho
//            if (stock <= 0) {
//                throw new SQLException("Sản phẩm đã hết hàng.");
//}
//
//            // Lấy hoặc tạo giỏ hàng cho user
//            CartDAO cartDAO = new CartDAO();
//            int cartId = cartDAO.getCartIDByUserID(userId);
//            if (cartId == -1) {
//                cartDAO.createCartForUserID(userId);
//                cartId = cartDAO.getCartIDByUserID(userId);
//            }
//
//            // Thêm sản phẩm vào giỏ hàng
//            String addToCartQuery = "INSERT INTO cart_detail (cart_id, product_id, product_variant_id, quantity, updated_date) "
//                    + "VALUES (?, ?, ?, 1, NOW()) "
//                    + "ON DUPLICATE KEY UPDATE quantity = quantity + 1, updated_date = NOW()";
//            try (PreparedStatement addToCartStmt = con.prepareStatement(addToCartQuery)) {
//                addToCartStmt.setInt(1, cartID);
//                addToCartStmt.setString(2, productId);
//                addToCartStmt.setInt(3, variantId);
//                addToCartStmt.executeUpdate();
//            
//
//            con.commit();
//            }catch(SQLException e) {
//            throw new SQLException("Lỗi khi thêm sản phẩm vào giỏ hàng: " + e.getMessage());
//        }
//    }
    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();
        CartDAO cDAO = new CartDAO();
        for (CartDetail c : cDAO.getAllCartDetailByUserID(4)) {
            System.out.println(c);
        }
        addToCart("P004", "BLACK", "M", 4);
        System.out.println("--------------");
        for (CartDetail c : cDAO.getAllCartDetailByUserID(4)) {
            System.out.println(c);
        }
    }
}
