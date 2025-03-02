/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class SettingDAO {

    public static int getPublicProductPerPage() {

        return 5;
    }

    public static List<Object[]> getPublicBrandList() {
        String query = """
                       SELECT b.brand_id, b.name, b.origin, COUNT(p.product_id) AS product_count
                       FROM brand b
                       JOIN product p ON b.brand_id = p.brand_id
                       JOIN product_category c ON p.category_id = c.category_id
                       WHERE p.is_visible = 1 AND c.is_visible = 1
                       GROUP BY b.brand_id, b.name, b.origin;""";

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

//        return ProductDAO.getAllBrand();
    }

    public static Map<Integer, String> getPublicProductCategory() {

        String query = """
                       SELECT c.category_id, c.category_name, COUNT(p.product_id) AS product_count
                       FROM product_category c
                       JOIN product p ON c.category_id = p.category_id
                       WHERE (c.is_visible = 1 AND p.is_visible = 1)
                       GROUP BY c.category_id, c.category_name;""";
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

}
