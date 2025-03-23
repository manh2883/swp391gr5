/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Product;
import Models.User;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

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

    public static String getVariantInformation(String productId, int variantId) {
        Object[] obj = null; // Trả về null nếu không tìm thấy dữ liệu
        String query = "SELECT color, size FROM product_variant WHERE product_id = ? AND variant_id = ?";
        String str = null;
        try {
            DBContext db = new DBContext();

            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, productId);
            stm.setInt(2, variantId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                obj = new Object[2];
                obj[0] = rs.getString("color");
                obj[1] = rs.getString("size");

//                System.out.println("Color: " + obj[0]);
//                System.out.println("Size: " + obj[1]);
                // Tạo chuỗi kết quả, bỏ qua giá trị null
                StringJoiner joiner = new StringJoiner(", ");
                if (obj[0] != null) {
                    joiner.add(obj[0].toString());
                }
                if (obj[1] != null) {
                    joiner.add(obj[1].toString());
                }

                String st = joiner.toString(); // Nếu cả 2 đều null, str sẽ là chuỗi rỗng ""
                str = st;

//                System.out.println("Result String: " + st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static ArrayList<Object[]> getVariantListForProductId(String productId) {
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] obj = null; // Trả về null nếu không tìm thấy dữ liệu
        String query = "SELECT * FROM product_variant WHERE product_id = ?";

        try {
            DBContext db = new DBContext();

            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, productId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int varId = rs.getInt("variant_id");
                obj = new Object[5];
                obj[0] = varId;
                obj[1] = rs.getString("color");
                obj[2] = rs.getString("size");
                obj[3] = rs.getInt("stock");
                obj[4] = getCurrentPriceForProductVariant(productId, varId);
                list.add(obj);
            }

            con.close();
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String getImgUrlForProductID(String Id) {
        String imgUrl = "Images/RUN.jpg";
        String query = """
                       SELECT *
                       FROM (
                           SELECT *,
                                  ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY img_id ASC) AS rn
                           FROM product_image
                       ) AS ranked
                       where rn = 1 and product_id = ?""";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, Id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                if (rs.getString("back_link") != null && !rs.getString("back_link").isEmpty()) {
                    imgUrl = rs.getString("back_link");
                }
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imgUrl;
    }

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String query = """
                       SELECT distinct product.product_id,product.description, product.name, price, brand.name, 
                       product_category.category_name,  product.created_at 
                       FROM tpfshopwearv2.product 
                        left join brand on product.brand_id = brand.brand_id 
                        left join product_category on product.category_id = product_category.category_id
                        left join product_variant on product.product_id = product_variant.product_id
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
                product.setImgUrl(getImgUrlForProductID(rs.getString("product_Id")));

                products.add(product);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {

        }
        return products;
    }

    public void addProduct(Product product, String color, String size, int stock, String backLink) throws SQLException {
        String query = "INSERT INTO Product (product_Id, name, price, description, category_id) VALUES (?, ?, ?, ?, ?)";
        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, getNextProductCode());
            stm.setString(2, product.getName());
            stm.setDouble(3, product.getPrice());
            stm.setString(4, product.getDescription());
            stm.setString(5, product.getCategoryName());
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Product pr = getProductById(getLastProductId());
        if (pr != null) {
            createVariantForProductId(pr.getProductId(), color, size, stock);
            int variantId = getVariantByColorAndSize(pr.getProductId(), color, size);
            if (variantId != -1) {
                createImgForVariantId(pr.getProductId(), variantId, backLink);
            }
        }

    }

    public static void createImgForVariantId(String productId, int productVariantId, String backLink) {

        String query = "INSERT INTO `tpfshopwearv2`.`product_image` ( `product_id`, `product_variant_id`, `description`, `back_link`) VALUES (?, ?, ?, ?);";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, productVariantId);
            stm.setString(3, "");
            stm.setString(4, backLink);

            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createVariantForProductId(String productId, String color, String size, int stock) {
        String query = "INSERT INTO `tpfshopwearv2`.`product_variant` ( `product_id`, `color`, `size`, `stock`) VALUES (?, ?, ?, ?);";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setString(2, color);
            stm.setString(3, size);
            stm.setInt(4, stock);

            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getLastProductId() throws SQLException {
        String maxProductId = null;
        String sql = "SELECT MAX(product_id) AS max_id FROM product";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                maxProductId = rs.getString("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxProductId;
    }

    public static String getNextProductCode() throws SQLException {
        String lastCode = getLastProductId();
        if (lastCode == null || lastCode.isEmpty()) {
            return "P001"; // Mã sản phẩm đầu tiên nếu database chưa có sản phẩm nào
        }

        // Tách phần chữ (P, Q, R...) và phần số (001, 002...)
        String prefix = lastCode.substring(0, 1);  // Lấy ký tự đầu tiên
        int number = Integer.parseInt(lastCode.substring(1)); // Lấy số và chuyển thành int

        // Tăng số
        number++;

        // Nếu vượt quá 999, đổi prefix sang ký tự tiếp theo
        if (number > 999) {
            prefix = String.valueOf((char) (prefix.charAt(0) + 1)); // Chuyển sang chữ tiếp theo (P → Q)
            number = 1; // Reset số về 001
        }

        // Format số thành 3 chữ số (001, 002, ...)
        return prefix + String.format("%03d", number);
    }

    public static boolean updateProductVariant(String productId, String color, String size, int stock) {
        String query = "UPDATE ProductVariant SET size = ?, color = ?, stock = ? WHERE variant_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

//            getVariantByColorAndSize(productId, color, size);
            int variantId = getVariantByColorAndSize(productId, color, size);
            if (variantId == -1) {
                return false;
            }

            stm.setString(1, size);
            stm.setString(2, color);
            stm.setInt(3, stock);
            stm.setInt(4, variantId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateStockForProductVariantId(int variantId, int stock) {
        String query = "UPDATE ProductVariant SET  stock = ? WHERE variant_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            stm.setInt(2, stock);
            stm.setInt(1, variantId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean decreaseStockAfterCreateOrder(String productId, int variantId, int quantity) {
        String query = "UPDATE Product_Variant SET  stock = ? WHERE variant_id = ?";
        boolean success = false;
        int newStock = -1;
        int oldStock = getStockByProductAndVariant(productId, variantId);

        if (quantity > oldStock || quantity <= 0 || oldStock <= 0) {
            return false;
        } else {
            newStock = oldStock - quantity;
        }

        if (newStock != oldStock && newStock >= 0) {
            try {
                DBContext db = new DBContext();
                java.sql.Connection con = db.getConnection();
                PreparedStatement stm = con.prepareStatement(query);

                stm.setInt(2, variantId);
                stm.setInt(1, newStock);
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean increaseStockAfterCancelOrder(String productId, int variantId, int quantity) {
        String query = "UPDATE Product_Variant SET  stock = ? WHERE variant_id = ?";
        boolean success = false;
        int newStock = -1;
        int oldStock = getStockByProductAndVariant(productId, variantId);

        if (quantity <= 0 || oldStock <= 0) {
            return false;
        } else {
            newStock = oldStock + quantity;
        }

        if (newStock != oldStock && newStock >= 0) {
            try {
                DBContext db = new DBContext();
                java.sql.Connection con = db.getConnection();
                PreparedStatement stm = con.prepareStatement(query);

                stm.setInt(2, variantId);
                stm.setInt(1, newStock);

                stm.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteProductVariant(String productId, String color, String size) {
        int variantId = getVariantByColorAndSize(productId, color, size);
        if (variantId == -1) {
            return false; // Không tìm thấy biến thể
        }

        String query = "DELETE FROM product_variant WHERE variant_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setInt(1, variantId);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, Object>> getProductVariants(String productId) {
        List<Map<String, Object>> variants = new ArrayList<>();
        String query = "SELECT variant_id, color, size, stock FROM product_variant WHERE product_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Map<String, Object> variant = new HashMap<>();
                variant.put("variant_id", rs.getInt("variant_id"));
                variant.put("color", rs.getString("color"));
                variant.put("size", rs.getString("size"));
                variant.put("stock", rs.getInt("stock"));
                variants.add(variant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variants;
    }

    public boolean updateProduct(Product product) {
        String query = "UPDATE Product SET name = ?, description = ?, brand_id = ?, price = ?, category_id = ? WHERE product_id = ?";
        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, product.getName());
            stm.setString(2, product.getDescription());
            stm.setString(3, product.getBrandName());
            stm.setInt(4, (int) product.getPrice());
            stm.setString(5, product.getCategoryName());
            stm.setString(6, product.getProductId());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sizes;
    }

    public List<String> getAllColorbyProductId(String productId) {

        List<String> colors = new ArrayList<>();
        String query = "SELECT distinct color FROM product_variant "
                + "WHERE LOWER(color) IN (SELECT LOWER(color) FROM product_variant GROUP BY LOWER(color))"
                + "And product_id = ? "
                + "ORDER BY color";

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

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colors;
    }

    public static int getVariantByColorAndSize(String productId, String color, String size) {
        int id = -1;
        String query;
        boolean hasColor = (color != null && !color.isEmpty());
        boolean hasSize = (size != null && !size.isEmpty());

        if (!hasColor && !hasSize) {
            query = "SELECT variant_id FROM product_variant WHERE product_id = ?";
        } else if (hasColor && hasSize) {
            query = "SELECT variant_id FROM product_variant WHERE product_id = ? AND color = ? AND size = ?";
        } else if (hasColor) {
            query = "SELECT variant_id FROM product_variant WHERE product_id = ? AND color = ?";
        } else {
            query = "SELECT variant_id FROM product_variant WHERE product_id = ? AND size = ?";
        }

        try (java.sql.Connection con = new DBContext().getConnection(); PreparedStatement stm = con.prepareStatement(query)) {
            System.out.println(con);
            // Thiết lập tham số
            stm.setString(1, productId.toUpperCase());
            if (hasColor && hasSize) {
                stm.setString(2, color.toUpperCase());
                stm.setString(3, size.toUpperCase());
            } else if (hasColor) {
                stm.setString(2, color.toUpperCase());
            } else if (hasSize) {
                stm.setString(2, size.toUpperCase());
            }

            System.out.println("Executing query: " + query);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("variant_id");
                    System.out.println("Found: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Executing query with values: " + productId + ", " + color + ", " + size);

        return id;
    }

    // Kiểm tra còn hàng trong kho không
    public static int getStockForVariantProduct(String productId, String color, String size) {

        String query1 = "SELECT stock FROM product_variant WHERE product_id = ? AND color = ? AND size = ?";
        String query2 = "SELECT stock FROM product_variant WHERE product_id = ?";
        int stock = -1;
        String query = null;
        if (color == null && size == null) {
            query = query2;
        } else {
            query = query1;
        }
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            if (color != null && !color.isEmpty() && size != null && !size.isEmpty()) {
                stm.setString(2, color);

                stm.setString(3, size);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("stock");

            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
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
            rs.close();
            stm.close();
            con.close();
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
            rs.close();
            stm.close();
            con.close();
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
//            System.out.println("cart_id: " + ca);

            int variantId = getVariantByColorAndSize(productId, color, size);
//            System.out.println("product_id " + productId);
//            System.out.println("color " + color);
//            System.out.println("size " + size);
//            System.out.println("variant_id " + variantId);

            if (variantId > 0) {
                int cartDetailId = CheckProductExistInCart(productId, variantId, ca);
//                System.out.println("cartDetailId " + cartDetailId);
                if (cartDetailId > 0) {
                    cartdao.editCartDetailByID(userId, cartDetailId, "increment");
                } else {
//                    System.out.println("getStockForVariantProduct " + getStockForVariantProduct(productId, color, size));
                    if (getStockForVariantProduct(productId, color, size) > 0) {
                        AddCartDetail(productId, variantId, ca);
                    }
                }
            }
            return;
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
            rs.close();
            stm.close();
            con.close();
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
            stm.setInt(4, 1);
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
    public static List<Object[]> getAllBrand() {
        String query = "SELECT brand.brand_id, brand.name  "
                + "FROM brand ";

        List<Object[]> brandList = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Object[] brandData = new Object[2];
                brandData[0] = rs.getInt("brand_id");     // ID
                brandData[1] = rs.getString("name");      // Name

                brandList.add(brandData);
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;
    }

    public static Map<Integer, String> getAllProductCategory() {

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
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public static double getNetPriceByProductId(String productId) {
        double price = 0;
        String query = "SELECT * from product where product_id = ? ";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                price = rs.getDouble("price");
            }
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static double getCurrentPriceByProductId(String productId) {
        double price = -1;
        List<Double> priceList = new ArrayList<>();
        String query = """
                       SELECT price
                       FROM product_price 
                       WHERE NOW() BETWEEN start_price_date AND end_price_date and product_id = ?""";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                priceList.add(rs.getDouble("price"));
            }
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (priceList != null && !priceList.isEmpty()) {
            price = Collections.min(priceList);
        } else {
            price = getProductById(productId).getPrice();
        }
        return price;
    }

    public static double getCurrentPriceForProductVariant(String productId, int variantId) {
        double price = 0;
        List<Double> priceList = new ArrayList<>();
        String query = """
                       SELECT price
                       FROM product_price 
                       WHERE NOW() BETWEEN start_price_date AND end_price_date and product_id = ? and variant_id = ? """;
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, variantId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                price = rs.getDouble("price");
            }
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (price == 0) {
            price = getProductById(productId).getPrice();
        }

        return price;
    }

    public static Map<Boolean, String> getProcductNotifyInformation(Product pro) {
        Map<Boolean, String> map = new HashMap<>();
        boolean isNew = isNewProduct(pro);
        String isSalse = isSaleProduct(pro);
        map.put(isNew, isSalse);
        return map;

    }

    public static boolean isNewProduct(Product pro) {
        boolean isNew = false;
        // Lấy ngày hiện tại
        if (pro.getCreateAt() != null) {
            isNew = Duration.between(pro.getCreateAt().toInstant(), Instant.now()).getSeconds() <= 60 * 60 * 24 * 14;
        }

        return isNew;
    }

    public static String isSaleProduct(Product pro) {

        double price = getCurrentPriceByProductId(pro.getProductId());

        if (price > -1) {
            if (pro.getPrice() > price) {
                return String.valueOf(price);
            }
        }
        return null;
    }

    public static Map<Product, Map<Boolean, String>> getProductView() {
        Map<Product, Map<Boolean, String>> productList = new HashMap<>();
        for (Product p : getAllProducts()) {
            productList.put(p, getProcductNotifyInformation(p));
        }
        return productList;
    }

    public static List<Map.Entry<Product, Map<Boolean, String>>> getProductListPublic(int quantity) {
        Map<Product, Map<Boolean, String>> productMap = new LinkedHashMap<>();
        List<Product> allProducts = getAllProducts(); // Lấy toàn bộ sản phẩm
        int count = 0;

        // Nếu quantity <= 0, lấy tất cả sản phẩm
        for (Product p : allProducts) {
            if (quantity > 0 && count >= quantity) {
                break;
            }
            productMap.put(p, getProcductNotifyInformation(p));
            count++;
        }

        // Chuyển Map thành List để dễ phân trang
        return new ArrayList<>(productMap.entrySet());
    }

    public static List<Map.Entry<Product, Map<Boolean, String>>> getRecommendedProductList(int quantity) {
        Map<Product, Map<Boolean, String>> productMap = new LinkedHashMap<>();
        List<Product> allProducts = getAllProducts(); // Lấy toàn bộ sản phẩm
        int count = 0;

        // Nếu quantity <= 0, lấy tất cả sản phẩm
        for (Product p : allProducts) {
            if (quantity > 0 && count >= quantity) {
                break;
            }
            productMap.put(p, getProcductNotifyInformation(p));
            count++;
        }

        // Chuyển Map thành List để dễ phân trang
        return new ArrayList<>(productMap.entrySet());
    }

    public static List<Map.Entry<Product, Map<Boolean, String>>> getProductListAfterFilter() {
        List<Map.Entry<Product, Map<Boolean, String>>> productList = getProductListPublic(0);
        return productList;
    }

    public static String getBrandNameById(int id) {

        String query = "SELECT brand.name from brand where brand.brand_id = ?";
        String name = null;
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getCategoryNameById(int id) {
        String query = "SELECT category_name from product_category where category_id = ?";
        String name = null;
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("category_name");
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static List<Product> productFilterList(String productId, String productName, Long brandId, Long categoryId,
            Double minPrice, Double maxPrice, Integer isVisible, Integer categoryVisible,
            Timestamp startDate, Timestamp endDate) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String query = """
            SELECT p.product_id, p.name AS product_name, p.description, b.name AS brand_name, 
                               pc.category_name, 
                               COALESCE((SELECT MIN(pp.price) FROM product_price pp 
                                        WHERE pp.product_id = p.product_id 
                                        AND NOW() BETWEEN pp.start_price_date AND pp.end_price_date), p.price) AS current_price, 
                               p.created_at, p.price as net_price
                        FROM product p
                        JOIN product_category pc ON p.category_id = pc.category_id
                        JOIN brand b ON p.brand_id = b.brand_id
                        WHERE 1=1 """;

        List<Object> params = new ArrayList<>();

        // Search by product ID
        if (productId != null && !productId.isEmpty()) {
            query += " AND p.product_id = ?";
            params.add(productId);
        }

        // Search by product name
        if (productName != null && !productName.isEmpty()) {
            query += " AND p.name LIKE ?";
            params.add("%" + productName + "%");
        }

        // Search by brand ID
        if (brandId != null) {
            query += " AND p.brand_id = ?";
            params.add(brandId);
        }

        // Search by category ID
        if (categoryId != null) {
            query += " AND p.category_id = ?";
            params.add(categoryId);
        }

        // Search by price range
        if (minPrice != null) {
            query += " AND (pp.price >= ? OR (pp.price IS NULL AND p.price >= ?))";
            params.add(minPrice);
            params.add(minPrice);
        }
        if (maxPrice != null) {
            query += " AND (pp.price <= ? OR (pp.price IS NULL AND p.price <= ?))";
            params.add(maxPrice);
            params.add(maxPrice);
        }

        // Filter by product visibility
        if (isVisible != null) {
            query += " AND p.is_visible = ?";
            params.add(isVisible);
        }

        // Filter by category visibility
        if (categoryVisible != null) {
            query += " AND pc.is_visible = ?";
            params.add(categoryVisible);
        }

        // Search by creation date range
        if (startDate != null) {
            query += " AND p.created_at >= ?";
            params.add(startDate);
        }
        if (endDate != null) {
            query += " AND p.created_at <= ?";
            params.add(endDate);
        }
        DBContext db = new DBContext();
        java.sql.Connection con = db.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("product_name"));
                product.setDescription(rs.getString("description"));
                product.setBrandName(rs.getString("brand_name"));
                product.setCategoryName(rs.getString("category_name"));
                product.setPrice(rs.getDouble("net_price"));
                product.setCreateAt(rs.getTimestamp("created_at"));
                product.setImgUrl(getImgUrlForProductID(rs.getString("product_id")));
                productList.add(product);
            }
        }
        return productList;
    }

    public static List<Map.Entry<Product, Map<Boolean, String>>> productFilterView(List<Product> productList) {
        Map<Product, Map<Boolean, String>> productMap = new LinkedHashMap<>();

        for (Product p : productList) {

            productMap.put(p, getProcductNotifyInformation(p));

        }

        // Chuyển Map thành List để dễ phân trang
        return new ArrayList<>(productMap.entrySet());
    }

    public static int getStockByProductAndVariant(String productId, int variantId) {
        String query = "SELECT stock FROM product_variant WHERE product_id = ? AND variant_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            stm.setInt(2, variantId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    public static ArrayList<Object[]> getImageListByProduct(String productId) {
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] obj = null; // Trả về null nếu không tìm thấy dữ liệu
        String query = """
                       SELECT pi.product_id, color, back_link
                       FROM product_image pi
                       left JOIN product_variant pv ON pi.product_variant_id = pv.variant_id
                       WHERE pv.product_id = ?
                       """;
        try {
            DBContext db = new DBContext();

            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, productId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                obj = new Object[3];

                obj[0] = rs.getString("product_id");
                obj[1] = rs.getString("color");
                String link = rs.getString("back_link");
                if (link != null && !link.isEmpty()) {
                    obj[2] = link;
                } else {
                    obj[2] = "Images/RUN.jpg";
                }
                list.add(obj);
            }

            con.close();
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    public static ArrayList<Object[]> getImageListByProduct(String productId, String color) {
        ArrayList<Object[]> list = new ArrayList<>();
        Object[] obj = null; // Trả về null nếu không tìm thấy dữ liệu
        String query = """
                       SELECT pi.product_id, color, back_link
                       FROM product_image pi
                       left JOIN product_variant pv ON pi.product_variant_id = pv.variant_id
                       WHERE pv.product_id = ?
                       And color = ? 
                       """;
        try {
            DBContext db = new DBContext();

            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, productId);
            stm.setString(2, color);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                obj = new Object[3];

                obj[0] = rs.getString("product_id");
                obj[1] = rs.getString("color");
                obj[2] = rs.getString("back_link");

                list.add(obj);
            }

            con.close();
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean getIsVisibleForProductId(String productId) {
        boolean status = false;

        String query = """
                       select * from product join product_category on product.category_id = product_category.category_id
                       where product.is_visible = 1 and product_category.is_visible = 1 and product_id = ?
                       """;
        try {
            DBContext db = new DBContext();

            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, productId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                status = true;
            }

            con.close();
            stm.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;

    }

    public static Product productCreator(String name, String des,
            String brandIdString, String newBrand, int price, String categoryIdString, String newCategory,
            List< Map<String, String>> variantList, List<String> sizeList
    ) throws SQLException {
        if (variantList == null || variantList.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("color", "Standard");
            variantList.add(map);
        }

        if (brandIdString != null && !brandIdString.isEmpty()) {
            if (brandIdString.equals("Other")) {
                if (newBrand != null && !newBrand.isEmpty()) {
                    brandIdString = String.valueOf(createNewBrand(newBrand));
                } else {
                    brandIdString = "-1";
                }
            }
        } else {
            brandIdString = "-1";
        }

        if (categoryIdString != null && !categoryIdString.isEmpty()) {
            if (categoryIdString.equals("Other")) {
                if (newCategory != null && !newCategory.isEmpty()) {
                    categoryIdString = String.valueOf(createNewCategory(newCategory));
                } else {
                    categoryIdString = "-1";
                }
            }
        } else {
            categoryIdString = "-1";
        }

        Product pro = null;
        String productId = getNextProductCode();

        if (!brandIdString.equals("-1") && !categoryIdString.equals("-1")) {

            String sql = "INSERT INTO product (product_id, name, description, brand_id, "
                    + "price, category_id, created_at, is_visible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                DBContext db = new DBContext();
                java.sql.Connection conn = db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, productId);
                stmt.setString(2, name);
                stmt.setString(3, des);
                stmt.setString(4, brandIdString);
                stmt.setInt(5, price);
                stmt.setString(6, categoryIdString);
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                stmt.setBoolean(8, true);

                stmt.execute();

                System.out.println("sql: " + stmt);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            pro = getProductById(productId);

            System.out.println("next: " + productId);
            if (pro != null) {
                addImagesToVariant(productId,
                        createProductVariantsAndGetIds(productId, variantList, sizeList),
                        variantList,
                        sizeList.size());
            }
        }

        return pro;
    }

    public static List<Integer> createProductVariantsAndGetIds(String productId, List<Map<String, String>> colorList,
            List<String> sizeList) {
        String sql = "INSERT INTO product_variant (product_id, color, size, stock) VALUES (?, ?, ?, ?)";
        List<Integer> insertedIds = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            for (Map<String, String> map : colorList) {
                String color = map.get("color");
                boolean isFirstVariantOfColor = true;
                for (String size : sizeList) {
                    stmt.setString(1, productId);
                    stmt.setString(2, color);
                    stmt.setString(3, size);
                    stmt.setInt(4, 10); // Stock có thể được cập nhật sau
                    stmt.addBatch();

                    if (isFirstVariantOfColor) {
                        isFirstVariantOfColor = false;
                    }
                }
            }

            stmt.executeBatch();
            ResultSet rs = stmt.getGeneratedKeys();

            while (rs.next()) {
                insertedIds.add(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedIds;
    }

    public static void addImagesToVariant(String productId,
            List<Integer> variantIds, List<Map<String, String>> variantList, int sizeListSize) {
        String sql = "INSERT INTO product_image (product_id, product_variant_id, description, back_link) VALUES (?, ?, ?, ?)";

        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            List<Integer> varIdReal = new ArrayList<>();
            for (int i = 0; i < variantIds.size(); i += sizeListSize) {
                varIdReal.add(variantIds.get(i));
            }

            for (int i = 0; i < varIdReal.size(); i++) {

                int variantId = varIdReal.get(i);
                Map<String, String> variantData = variantList.get(i);

                stmt.setString(1, productId);
                stmt.setLong(2, variantId);
                stmt.setString(3, "Product Image");
                stmt.setString(4, variantData.get("images"));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLastInsertedId(String tableName, String idColumn) {
        String query = "SELECT " + idColumn + " FROM " + tableName + " ORDER BY " + idColumn + " DESC LIMIT 1";
        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int createNewCategory(String category) {
        String query = "INSERT INTO product_category (category_name, is_visible) VALUES (?, 1)";

        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, category);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return getLastInsertedId("product_category", "category_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int createNewBrand(String brand) {
        String query = "INSERT INTO brand (name) VALUES (?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, brand);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return getLastInsertedId("brand", "brand_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<String> getAllColor() {
        List<String> list = new ArrayList<>();

        String query = "SELECT distinct color FROM product_variant "
                + "WHERE LOWER(color) IN (SELECT LOWER(color) FROM product_variant GROUP BY LOWER(color)) "
                + "ORDER BY color";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();

            PreparedStatement stm = con.prepareStatement(query);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String color = rs.getString("color");
                if (color != null && !color.isEmpty()) {
                    list.add('"'
                            + color + '"');
                }
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) throws SQLException {
        List<Map<String, String>> varListList = new ArrayList<>();

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("color", "red");
        map1.put("images", "red.png");
        System.out.println(map1);
        varListList.add(map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("color", "blue");
        map2.put("images", "blue.png");
        System.out.println(map2);
        varListList.add(map2);

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("color", "yellow");
        map3.put("images", "yellow.png");
        System.out.println(map3);
        varListList.add(map3);

        List<String> sizeList = new ArrayList<>();
        sizeList.add("X");
        sizeList.add("XXL");
        sizeList.add("XXXL");

        System.out.println(varListList);
//          System.out.println(createProductVariantsAndGetIds("P012", varListList, sizeList));
        addImagesToVariant("P012",
                createProductVariantsAndGetIds("P012", varListList, sizeList),
                varListList,
                sizeList.size());

        System.out.println(productCreator("Test Product", "des test", "1", "testBrand", 1000, "1", null, varListList, sizeList));
    }
}
