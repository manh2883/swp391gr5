/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Product;
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
       for(Product p: getAllProducts()){
            if(p.getProductId() == null ? id == null : p.getProductId().equals(id)){
                return p;
            }
        }
        return null;

    }

    public static String getImgUrlForProductID(String Id){
        for(Product p: getAllProducts()){
            if(p.getProductId() == null ? Id == null : p.getProductId().equals(Id)){
                if(p.getImgUrl() == null || p.getImgUrl().isEmpty()){
                    return "Images/RUN.jpg";
                }else{
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
        }catch (SQLException e){
            
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

    public static void main(String[] args) {
        ProductDAO pDAO = new ProductDAO();
//        for(Product p: pDAO.getAllProducts()){
//            System.out.println(p);
//        }
        System.out.println(pDAO.getImgUrlForProductID("P007"));
    //        System.out.println(pDAO.getProductById("P007"));
        System.out.println(pDAO.getImgUrlForProductID("P006"));
    }
}
