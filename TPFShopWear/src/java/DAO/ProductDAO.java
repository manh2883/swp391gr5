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
public class ProductDAO extends DBContext{
    
public Product getProductById(int id) throws IOException {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        Product product = new Product();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                product.setproductId(rs.getInt("product_Id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCategoryId(rs.getInt("category_id"));
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_Id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setCreateAt(rs.getTimestamp("created_at"));
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
    
    public void addProduct(Product product){
        String query = "INSERT INTO Product (name, price, description, stock, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getDescription());
            stm.setInt(5, product.getCategoryId());
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProduct(Product product){
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, stock = ?, category_id = ? WHERE product_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, product.getName());
            stm.setDouble(2, product.getPrice());
            stm.setString(3, product.getDescription());
            stm.setInt(5, product.getCategoryId());
            stm.setInt(6, product.getproductId());
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(int id){
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
        for(Product p: pDAO.getAllProducts()){
            System.out.println(p);
        }
    }
}