/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Order;
import Models.OrderDetail;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Dell
 */
public class OrderDAO {

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, total_amount, status_id, created_at FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String totalamount = rs.getString("total_amount");
                String statusId = rs.getString("status_id");

//                orders.add(orderId, userId, totalamount, statusId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, user_id, total_amount, status_id, created_at FROM orders ORDER BY created_at DESC";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int userId = rs.getInt("userId");
                double totalAmount = rs.getDouble("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

//                orders.add(new Order(orderId, userId, totalAmount, statusId, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, user_id, total_amount, status_id, created_at FROM orders WHERE status_id = ? ORDER BY created_at DESC";

        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, status);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int userId = rs.getInt("userId");
                double totalAmount = rs.getDouble("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

//                orders.add(new Order(orderId, userId, totalAmount, statusId, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public static List<Order> getOrderListByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setUserId(rs.getInt("user_id"));
                order.setOrderId(rs.getInt("order_id"));
                order.setTotalamount(rs.getInt("total_amount"));
                order.setStatusId(rs.getInt("status_id"));
                order.setPaymentmethod(rs.getInt("payment_method"));
                order.setCreateAt(rs.getTimestamp("created_at"));
                order.setAddress(rs.getString("address"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getOrdersByUserIdAndStatus(int userId, String status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT order_id, total_amount, status_id, created_at FROM orders WHERE user_id = ? AND status_id = ? ORDER BY created_at DESC";

        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            stm.setString(2, status);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                double totalAmount = rs.getDouble("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

//                orders.add(new Order(orderId, userId, totalAmount, statusId, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT order_detail_id, order_id, product_id, quantity, price FROM order_detail WHERE order_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, orderId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String orderdetailId = rs.getString("orderdetailId");
                int productId = rs.getInt("productId");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

//                orderDetails.add(new OrderDetail(orderdetailId, orderId, productId, quantity, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public long createOrder(Order order, List<OrderDetail> orderDetails) {
        long orderId = -1;
        String orderSQL = "INSERT INTO orders (user_id, total_amount, status_id, "
                + "payment_method, created_at, address) VALUES (?, ?, ?, ?, NOW(), ?)";
        String detailSQL = "INSERT INTO order_detail (order_id, product_id, "
                + "product_variant_id, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            con.setAutoCommit(false); // Tắt auto-commit để quản lý giao dịch thủ công
            try (PreparedStatement orderstm = con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS); PreparedStatement detailstm = con.prepareStatement(detailSQL)) {
                
                // 1. Chèn vào bảng orders
                orderstm.setLong(1, order.getUserId());
                orderstm.setInt(2, order.getTotalamount());
                orderstm.setLong(3, 1); // 1 là trạng thái "Pending"
                orderstm.setLong(4, order.getPaymentmethod());
                orderstm.setString(5, order.getAddress());

                orderstm.executeUpdate();
                try (ResultSet rs = orderstm.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getLong(1);
                    }else{
                        throw new Exception("Creating order failed, no ID obtained.");
                    }
                }

                // 2. Chèn vào bảng order_detail
                for (OrderDetail detail : orderDetails) {
                    detailstm.setLong(1, orderId);
                    detailstm.setString(2, detail.getProductId());
                    detailstm.setLong(3, detail.getProductvariantId());
                    detailstm.setInt(4, detail.getQuantity());
                    detailstm.setInt(5, detail.getPrice());
                    detailstm.addBatch();
                }
                detailstm.executeBatch();
                con.commit();// Nếu không có lỗi, commit giao dịch
            } catch (Exception e) {
                con.rollback();// Nếu có lỗi, rollback dữ liệu
                throw e;
            } finally {
                con.setAutoCommit(true);// Bật lại auto-commit
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public static void main(String[] args) {
//          // 1. Tạo một đơn hàng giả lập
//        Order order = new Order();
//        order.setUserId(4); // ID user có sẵn trong database
//        order.setTotalamount(new BigDecimal("500.00")); // Tổng tiền
//        order.setPaymentmethod(1); // Giả sử 2 là "QR Payment"
//        order.setAddress("123 Test Street, City");
//
//        // 2. Tạo danh sách chi tiết đơn hàng (List<OrderDetail>)
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        
//        OrderDetail item1 = new OrderDetail();
//        item1.setProductId("P001"); // Mã sản phẩm có sẵn
//        item1.setProductvariantId(1); // ID biến thể sản phẩm
//        item1.setQuantity(2);
//        item1.setPrice(250); // Giá mỗi sản phẩm
//        
//        orderDetails.add(item1);
//
//        // 3. Gọi hàm createOrder() từ DAO
//        OrderDAO orderDAO = new OrderDAO();
//        long orderId = orderDAO.createOrder(order, orderDetails);
//        
//        // 4. Kiểm tra kết quả
//        if (orderId != -1) {
//            System.out.println("✅ Đơn hàng được tạo thành công! Order ID: " + orderId);
//        } else {
//            System.out.println("❌ Tạo đơn hàng thất bại.");
//        }
    }

}
