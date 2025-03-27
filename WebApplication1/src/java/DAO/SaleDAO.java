/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public class SaleDAO extends DBContext {

    // Lấy tổng số đơn hàng và số đơn hàng thành công theo ngày trong 7 ngày gần nhất
    public static List<Map<String, Object>> getOrderStatistics(Date startDate, Date endDate, int status) {
        List<Map<String, Object>> statistics = new ArrayList<>();
        String query = "WITH RECURSIVE date_series AS ("
                + "    SELECT ? AS order_date "
                + "    UNION ALL "
                + "    SELECT DATE_ADD(order_date, INTERVAL 1 DAY) "
                + "    FROM date_series "
                + "    WHERE order_date < ? "
                + ") "
                + "SELECT ds.order_date, "
                + "       COALESCE(o.total_orders, 0) AS total_orders, "
                + "       COALESCE(o.success_orders, 0) AS success_orders, "
                + "       COALESCE(o.revenue, 0) AS revenue "
                + "FROM date_series ds "
                + "LEFT JOIN ("
                + "    SELECT DATE(created_at) as order_date, "
                + "           COUNT(order_id) as total_orders, "
                + "           SUM(CASE WHEN status_id = ? THEN 1 ELSE 0 END) as success_orders, "
                + "           SUM(CASE WHEN status_id = ? THEN total_amount ELSE 0 END) as revenue "
                + "    FROM orders "
                + "    WHERE completed_at BETWEEN ? AND ? "
                + "    GROUP BY order_date "
                + ") o ON ds.order_date = o.order_date "
                + "ORDER BY ds.order_date ASC";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setDate(1, startDate);
            stm.setDate(2, endDate);
            stm.setInt(3, status);
            stm.setInt(4, status);
            stm.setDate(5, startDate);
            stm.setDate(6, endDate);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("order_date", rs.getDate("order_date"));
                row.put("total_orders", rs.getInt("total_orders"));
                row.put("success_orders", rs.getInt("success_orders"));
                row.put("revenue", rs.getBigDecimal("revenue"));

                statistics.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statistics;
    }

    // Lấy top n sản phẩm bán chạy hoặc bán ít nhất theo doanh thu hoặc số lượng
    public static List<Map<String, Object>> getTopProducts(Date startDate, Date endDate, int top, String sortBy, boolean asc) {
        List<Map<String, Object>> topProducts = new ArrayList<>();

        // Kiểm tra giá trị hợp lệ của sortBy
        if (!sortBy.equals("revenue") && !sortBy.equals("quantity_sold")) {
            throw new IllegalArgumentException("sortBy chỉ có thể là 'revenue' hoặc 'quantity_sold'");
        }

        String orderDirection = asc ? "ASC" : "DESC"; // Sắp xếp tăng dần hoặc giảm dần

        String query = "SELECT p.product_id, p.name, "
                + "SUM(od.quantity) AS quantity_sold, "
                + "SUM(od.price * od.quantity) AS revenue "
                + "FROM order_detail od "
                + "JOIN orders o ON od.order_id = o.order_id "
                + "JOIN product p ON od.product_id = p.product_id "
                + "WHERE o.completed_at BETWEEN ? AND ? "
                + "GROUP BY p.product_id, p.name "
                + "ORDER BY " + sortBy + " " + orderDirection + " "
                + "LIMIT ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            stm.setDate(1, startDate);
            stm.setDate(2, endDate);
            stm.setInt(3, top);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("product_id", rs.getString("product_id"));
                row.put("product_name", rs.getString("name"));
                row.put("quantity_sold", rs.getInt("quantity_sold"));
                row.put("revenue", rs.getBigDecimal("revenue"));
                topProducts.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topProducts;
    }

    public static void main(String[] args) {
        SaleDAO saleDAO = new SaleDAO();
        Date endDate = new Date(System.currentTimeMillis());
        Date startDate = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // Lùi về 7 ngày trước

        // Test thống kê đơn hàng
        System.out.println("===== Thống kê đơn hàng =====");
        List<Map<String, Object>> stats = saleDAO.getOrderStatistics(startDate, endDate, 3);
        for (Map<String, Object> row : stats) {
            System.out.println("Date: " + row.get("order_date")
                    + ", Total Orders: " + row.get("total_orders")
                    + ", Success Orders: " + row.get("success_orders")
                    + ", Revenue: " + row.get("revenue"));
        }

        // Test lấy top sản phẩm
        System.out.println("\n===== Top 5 sản phẩm bán chạy nhất =====");
        List<Map<String, Object>> topProducts = saleDAO.getTopProducts(startDate, endDate, 3, "revenue", false);
        for (Map<String, Object> row : topProducts) {
            System.out.println("Product ID: " + row.get("product_id")
                    + ", Name: " + row.get("product_name")
                    + ", Quantity Sold: " + row.get("quantity_sold")
                    + ", Revenue: " + row.get("revenue"));
        }
    }
}
