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
    public static void main(String[] args) {
        System.out.println(getOrderDetailsByOrderId(1));
    }

}
