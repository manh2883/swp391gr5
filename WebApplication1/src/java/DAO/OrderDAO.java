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

    public static List<Order> getAllOrderListByStatus(int status) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE status_id = ? ORDER BY created_at  DESC";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, status);
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
        String query = "SELECT * FROM orders WHERE user_id = ? AND status_id = ? ORDER BY created_at DESC";

        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, userId);
            stm.setString(2, status);
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
    
    public static Order getOrderInformationById(int orderId) {
        
        String query = "SELECT * FROM orders WHERE order_id = ? limit 1";
        Order order = new Order();
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, orderId);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                
                order.setUserId(rs.getInt("user_id"));
                order.setOrderId(rs.getInt("order_id"));
                order.setTotalamount(rs.getInt("total_amount"));
                order.setStatusId(rs.getInt("status_id"));
                order.setPaymentmethod(rs.getInt("payment_method"));
                order.setCreateAt(rs.getTimestamp("created_at"));
                order.setAddress(rs.getString("address"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public static List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM order_detail WHERE order_id = ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, orderId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                OrderDetail orderdt = new OrderDetail();
                
                orderdt.setOrderdetailId(rs.getInt("order_detail_id"));
                orderdt.setOrderId(rs.getInt("order_id"));
                orderdt.setProductId(rs.getString("product_id"));
                orderdt.setProductvariantId(rs.getInt("product_variant_id"));
                orderdt.setQuantity(rs.getInt("quantity"));
                orderdt.setPrice(rs.getInt("price"));
                
                orderDetails.add(orderdt);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    
    public static ArrayList<Object[]> getOrderDetailViewByOrderId(int orderId){
         List<OrderDetail> orderDetails = OrderDAO.getOrderDetailsByOrderId(orderId);
         ArrayList<Object[]> view = new ArrayList<>();
         for(OrderDetail od: orderDetails){
             Object[] oj = new Object[3];
         oj[0] = od;
         oj[1] = ProductDAO.getProductById(od.getProductId());
         oj[2] = ProductDAO.getVariantInformation(od.getProductId(), od.getProductvariantId());
         view.add(oj);
             
         }
         return view;
         
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
        for( Object[] oj: getOrderDetailViewByOrderId(1)){
            System.out.println(oj[0]);
            System.out.println(oj[1]);
            System.out.println(oj[2]);
        }

    }

}
