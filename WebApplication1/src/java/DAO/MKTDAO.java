/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.Date;

/**
 *
 * @author nguye
 */
public class MKTDAO extends DBContext {

    // Lấy danh sách product dựa trên product_id
    public List<Map<String, Object>> searchProductById(String productId) {
        List<Map<String, Object>> productList = new ArrayList<>();
        String query = "SELECT pv.product_id, pv.variant_id, pv.color, pv.size, "
                + "COALESCE(pp.price, p.price) AS price, pp.start_price_date, pp.end_price_date "
                + "FROM product_variant pv "
                + "LEFT JOIN product_price pp ON pv.variant_id = pp.variant_id "
                + "JOIN product p on pv.product_id = p.product_id "
                + "WHERE pv.product_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("product_id", rs.getString("product_id"));
                product.put("variant_id", rs.getString("variant_id"));
                product.put("color", rs.getString("color"));
                product.put("size", rs.getString("size"));
                product.put("price", rs.getInt("price"));
                product.put("start_price_date", rs.getDate("start_price_date"));
                product.put("end_price_date", rs.getDate("end_price_date"));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Admin cập nhật giá sản phẩm
    public void updateProductPrice(String productId, int variantId, int newPrice, Date startDate, Date endDate) {
        String checkQuery = "SELECT COUNT(*) FROM product_price WHERE variant_id = ?";
        String updateQuery = "UPDATE product_price SET price = ?, start_price_date = ?, end_price_date = ? WHERE variant_id = ?";
        String insertQuery = "INSERT INTO product_price (product_id, variant_id, price, start_price_date, end_price_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement checkStm = con.prepareStatement(checkQuery);
            PreparedStatement updateStm = con.prepareStatement(updateQuery);
            PreparedStatement insertStm = con.prepareStatement(insertQuery);

            // Kiểm tra variant_id đã có trong product_price chưa
            checkStm.setInt(1, variantId);
            ResultSet rs = checkStm.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) { // Nếu đã tồn tại -> Update
                updateStm.setInt(1, newPrice);
                updateStm.setDate(2, startDate);
                updateStm.setDate(3, endDate);
                updateStm.setInt(4, variantId);
                updateStm.executeUpdate();
                System.out.println("Giá đã được cập nhật!");
            } else { // Nếu chưa có -> Insert mới
                insertStm.setString(1, productId);  // ✅ Đảm bảo truyền product_id vào INSERT
                insertStm.setInt(2, variantId);
                insertStm.setInt(3, newPrice);
                insertStm.setDate(4, startDate);
                insertStm.setDate(5, endDate);
                insertStm.executeUpdate();
                System.out.println("Giá mới đã được thêm!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Object> getProductStatsLast7Days(String productId) {
    List<Object> stats = new ArrayList<>();
    String query = "SELECT d.date AS day, " +
                 "COALESCE(SUM(od.quantity), 0) AS total_quantity, " +
                 "COALESCE(SUM(od.quantity * od.price), 0) AS total_revenue " +
                 "FROM (SELECT CURDATE() - INTERVAL n DAY AS date " +
                 "      FROM (SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 " +
                 "            UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) AS days) d " +
                 "LEFT JOIN orders o ON DATE(o.completed_at) = d.date AND o.status_id = 8 " +
                 "LEFT JOIN order_detail od ON o.order_id = od.order_id AND od.product_id = ? " +
                 "GROUP BY d.date ORDER BY d.date ASC";

    try  {
         DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
        stm.setString(1, productId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Date date = rs.getDate("day");
            int quantity = rs.getInt("total_quantity");
            double revenue = rs.getDouble("total_revenue");
            Object[] obj = new Object[3];
            obj[0] = date;
            obj[1] = quantity;
            obj[2] = revenue;
            stats.add(obj);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return stats;
}
    
    public static void main(String[] args) {
        MKTDAO dao = new MKTDAO();
        System.out.println(dao.searchProductById("P001"));
    }
}
