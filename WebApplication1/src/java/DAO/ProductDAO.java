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
import java.util.HashMap;
import java.util.Map;

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

    /**
     *
     * @return
     */
    public static Map<Integer, String> getAllBrand(){
        
              String query = "SELECT * from brand";
              Map<Integer, String> brandList = new HashMap<>(); 

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                brandList.put(rs.getInt("brand_id"), rs.getString("name"));
            }
            stm.close();
            rs.close();
           

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;

    }
    
    public static Map<Integer, String> getAllProductCategory(){
        
              String query = "SELECT * from product_category";
              Map<Integer, String> brandList = new HashMap<>(); 

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                brandList.put(rs.getInt("category_id"), rs.getString("category_name"));
            }
            stm.close();
            rs.close();
           

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;

    }
    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();
        System.out.println(getAllBrand());
         System.out.println(getAllProductCategory());
    }
}
