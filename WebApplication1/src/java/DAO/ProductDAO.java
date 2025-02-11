/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Product;
import com.mysql.cj.protocol.Resultset;
import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
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
public class ProductDAO extends DBContext{
    DBContext db = new DBContext();
    public static Product getProductById(String id) throws IOException {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        Product product = new Product();
        try {
            DBContext db = new DBContext();
            
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                product.setProductId(rs.getString("product_Id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCategoryName(rs.getString("category_name"));
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
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
                       order by product.created_at asc;""";
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
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getDetailProduct(String name){
        String query = " SELECT * \n " +
                       " FROM product \n " +
                       " JOIN brand ON product.brand_id = brand.brand_id\n " +
                       " WHERE product.id =? ";
                     Product product = new Product();
                try{
            
           PreparedStatement stm = getConnection().prepareStatement(query);

            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
                product.setBrandName(rs.getString("name"));
                product.setProductId(rs.getString("product_Id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCategoryName(rs.getString(6));
                product.setCreateAt(rs.getTimestamp("created_at"));

                
//                    rs.getString("productId"),       // Lấy tên sản phẩm
//                    rs.getString("description"),// Lấy mô tả sản phẩm
//                    rs.getString("name");
//                    rs.getDouble("price"),      // Lấy giá sản phẩm
//                    rs.getString("brand_name")  // Lấy tên thương hiệu
     
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return product;
      
    }

    public static void addProduct(Product product) {
        String query = "INSERT INTO `tpfshopwearv2`.`product` "
                + "(`product_id`, `name`, `description`, `brand_id`, `price`, `category_id`, `created_at`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, product.getProductId());
            stm.setString(2, product.getName());
            stm.setDouble(3, product.getPrice());
            stm.setString(4, product.getDescription());
            //stm.setInt(5, product.getCategoryId());
            stm.setString(6, product.getBrandName());
            stm.setTimestamp(7, null);
            int row = stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(Product product) {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, category_id = ? WHERE product_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getDescription());
            stm.setString(4, product.getCategoryName());
            stm.setString(5, product.getProductId());
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id) {
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

    public static Product getVariantById(int parseInt) {
        String query = "SELECT * FROM Variant WHERE variant_id = ?";
        Product variant = null; // Assuming a Variant is still a type of Product

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            String variantId = null;
            stm.setString(1, variantId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                variant = new Product();
                variant.setProductId(rs.getString("variant_id"));  // Assuming variant_id is treated as product_id
                variant.setName(rs.getString("name"));
                variant.setPrice(rs.getDouble("price"));
                variant.setDescription(rs.getString("description"));
                variant.setCategoryName(rs.getString("category_id"));
                variant.setCreateAt(rs.getTimestamp("created_at"));
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variant;
    }

    public static List<Product> getAllVariants() {
        List<Product> variants = new ArrayList<>();
        String query = "SELECT * FROM Variant"; // Assuming 'Variant' is the table name

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Product variant = new Product();
                variant.setProductId(rs.getString("variant_id")); // Assuming variant_id is like product_id
                variant.setName(rs.getString("name"));
                variant.setPrice(rs.getDouble("price"));
                variant.setDescription(rs.getString("description"));
                variant.setCategoryName(rs.getString("category_id"));
                variant.setCreateAt(rs.getTimestamp("created_at"));
                variants.add(variant);
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return variants;
    }

    public void insertVariant(Product variant) {
        String query = "INSERT INTO Variant (name, price, description, stock, category_name, product_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, variant.getName());
            stm.setDouble(2, variant.getPrice());
            stm.setString(3, variant.getDescription());
            stm.setString(5, variant.getCategoryName());
            stm.setString(6, variant.getProductId()); // Assuming this links to the main product

            stm.executeUpdate();

            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVariant(Product variant) {
        String query = "UPDATE Variant SET name = ?, price = ?, description = ?, stock = ?, category_name = ?, product_id = ? WHERE variant_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setString(1, variant.getName());
            stm.setDouble(2, variant.getPrice());
            stm.setString(3, variant.getDescription());
            stm.setString(5, variant.getCategoryName());
            stm.setString(6, variant.getProductId());
            stm.setString(7, variant.getProductId()); // Assuming variant_id is the same as product_id

            stm.executeUpdate();

            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVariant(int variantId) {
        String query = "DELETE FROM Variant WHERE variant_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            stm.setInt(1, variantId);
            stm.executeUpdate();

            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();
//        Product product = new Product();
//        product.setBrandName("1");
//        product.setCreateAt(null);
//        product.setCategoryName(1);
//        product.setDescription("1");
//        product.setName("1");
//        product.setPrice(1);
//        product.setProductId("P0013");
//        
//        pDAO.addProduct(product);
            for(Product p: pDAO.getAllProducts()){
                System.out.println(p);
            }

    }
}
