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
            System.out.println("cart_id: " + ca);

            int variantId = getVariantByColorAndSize(productId, color, size);
            System.out.println("product_id " + productId);
            System.out.println("color " + color);
            System.out.println("size " + size);
            System.out.println("variant_id " + variantId);

            if (variantId > 0) {
                int cartDetailId = CheckProductExistInCart(productId, variantId, ca);
                System.out.println("cartDetailId " + cartDetailId);
                if (cartDetailId > 0) {
                    cartdao.editCartDetailByID(userId, cartDetailId, "increment");
                } else {
                    System.out.println("getStockForVariantProduct " + getStockForVariantProduct(productId, color, size));
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
        String query = "SELECT brand.brand_id, brand.name, count(product.product_id) as product_count "
                + "FROM tpfshopwearv2.brand "
                + "JOIN product ON product.brand_id = brand.brand_id "
                + "GROUP BY product.brand_id "
                + "ORDER BY product_count DESC";

        List<Object[]> brandList = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Object[] brandData = new Object[3];
                brandData[0] = rs.getInt("brand_id");     // ID
                brandData[1] = rs.getString("name");      // Name
                brandData[2] = rs.getInt("product_count"); // Product count

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

        return price;
    }

    public static Map<Boolean, String> getProcductNotifyInformation(String productId) {
        Map<Boolean, String> map = new HashMap<>();
        boolean isNew = isNewProduct(productId);
        String isSalse = isSaleProduct(productId);
        map.put(isNew, isSalse);
        return map;

    }

    public static boolean isNewProduct(String productId) {
        boolean isNew = false;
        String query = """
                       SELECT * 
                       FROM product 
                       WHERE created_at >= NOW() - INTERVAL 14 DAY
                       and product_id = ?""";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                isNew = true;
            }
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isNew;
    }

    public static String isSaleProduct(String productId) {
        Product pro = getProductById(productId);
        double price = getCurrentPriceByProductId(productId);

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
            productList.put(p, getProcductNotifyInformation(p.getProductId()));
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
            productMap.put(p, getProcductNotifyInformation(p.getProductId()));
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

            productMap.put(p, getProcductNotifyInformation(p.getProductId()));

        }

        // Chuyển Map thành List để dễ phân trang
        return new ArrayList<>(productMap.entrySet());
    }

    public static void main(String[] args) throws SQLException {
        
    }
}
