/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Order;
import Models.OrderDetail;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
//import java.security.Timestamp;

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
                int totalamount = rs.getInt("total_amount");
                int statusId = rs.getInt("status_id");
                String createdAt = rs.getString("created_at");

                orders.add(new Order(orderId, userId, orderId, orderId, null, orderId, query));
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
                int totalAmount = rs.getInt("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

                orders.add(new Order(orderId, userId, totalAmount, statusId, null, statusId, query));
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
                int totalAmount = rs.getInt("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

                orders.add(new Order(orderId, userId, statusId, statusId, null, statusId, query));
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
                int totalAmount = rs.getInt("totalAmount");
                int statusId = rs.getInt("statusId");
                String createdAt = rs.getString("createdAt");

                orders.add(new Order(orderId, userId, totalAmount, statusId, null, statusId, query));
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
                int orderdetailId = rs.getInt("orderdetailId");
                String productId = rs.getString("productId");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                orderDetails.add(new OrderDetail(orderdetailId, orderId, productId, orderdetailId, quantity, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public static void main(String[] args) {
        System.out.println(getOrderListByUserId(1));
    }

}
