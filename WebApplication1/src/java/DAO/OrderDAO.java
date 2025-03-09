/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import java.sql.Timestamp;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.OrderDetailViewDTO;

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
                order.setCompletedAt(rs.getTimestamp("completed_at"));
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
                order.setCompletedAt(rs.getTimestamp("completed_at"));
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

    public static ArrayList<Object[]> getOrderDetailViewByOrderId(int orderId) {
        List<OrderDetail> orderDetails = OrderDAO.getOrderDetailsByOrderId(orderId);

        ArrayList<Object[]> view = new ArrayList<>();

        for (OrderDetail oD : orderDetails) {
//            OrderDetailViewDTO viewItem = new OrderDetailViewDTO();
            Object[] viewItem = new Object[3];
            String proId = oD.getProductId();
            Product pro = ProductDAO.getProductById(proId);
            String variant = ProductDAO.getVariantInformation(proId, oD.getProductvariantId());

//            viewItem.setOrderDetail(oD);
//            viewItem.setProduct(pro);
//            viewItem.setVariantStr(variant);
            viewItem[0] = oD;
            viewItem[1] = pro;
            viewItem[2] = variant;
            System.out.println("[" + variant + "]");
            view.add(viewItem);
        }

        return view;

    }

    // Huy don, ship, tra hang, da giao hang
    public boolean updateOrderStatus(int role, int oldStatus, int newStatus, long orderId) throws SQLException {
        boolean success = false;
        if (!isValidStatusUpdate(role, oldStatus, newStatus)) {
            success = false;
        } else {
            String sql = "UPDATE orders SET status_id = ? WHERE order_id = ? AND status_id = ?";
            try {
                DBContext db = new DBContext();
                java.sql.Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, newStatus);
                pstmt.setLong(2, orderId);
                pstmt.setInt(3, oldStatus);
                success = pstmt.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    // kiem tra quyen
    private boolean isValidStatusUpdate(int role, int oldStatus, int newStatus) {
        if (role == 2) { // Customer
            return (oldStatus == 1 && newStatus == 4) || (oldStatus == 6 && newStatus == 4) || (oldStatus == 2 && newStatus == 7);
        } else if (role == 1 || role == 4) { // Admin, Sale
            return (oldStatus == 2 && newStatus == 7) || (oldStatus == 7 && newStatus == 8)
                    || (oldStatus == 1 && newStatus == 5) || (oldStatus == 2 && newStatus == 5)
                    || (oldStatus == 1 && newStatus == 2) || (oldStatus == 2 && newStatus == 3);
        }
        return false;
    }

    public long createOrder(Order order, List<OrderDetail> orderDetails) {
        long orderId = -1;
        String orderSQL = "INSERT INTO orders (user_id, total_amount, status_id, "
                + "payment_method, created_at, address, note, user_receive, contact) VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?)";
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
                orderstm.setString(6, order.getOrderNote());
                orderstm.setString(7, order.getUserReceive());
                orderstm.setString(8, order.getContact());

                orderstm.executeUpdate();
                try (ResultSet rs = orderstm.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getLong(1);
                    } else {
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

    public static List<Order> filterOrder(Long userId, Long orderId, Long mintotalAmount, Long maxtotalAmount, Long statusId,
            Long paymentMethod, String address, String userReceive, String contact,
            Timestamp createdStartDate, Timestamp createdEndDate,
            Timestamp completedStartDate, Timestamp completedEndDate) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        String query = """
    SELECT o.user_id ,o.order_id, o.total_amount, o.status_id, o.created_at, o.payment_Method, o.address, o.user_receive, o.contact, o.note
    FROM orders o
    JOIN order_status s ON o.status_id = s.order_status_id
    WHERE 1=1 """;

        List<Object> params = new ArrayList<>();

        if (userId != null) {
            query += " AND o.user_id = ?";
            params.add(userId);
        }

        // Search by order ID
        if (orderId != null) {
            query += " AND o.order_id = ?";
            params.add(orderId);
        }

        // Search by total amount range
        if (mintotalAmount != null && mintotalAmount >= 0) {
            query += " AND o.total_amount >= ?";
            params.add(mintotalAmount);
        }
        if (maxtotalAmount != null && maxtotalAmount > 0) {
            query += " AND o.total_amount <= ?";
            params.add(maxtotalAmount);
        }

        // Search by status ID
        if (statusId != null) {
            query += " AND o.status_id = ?";
            params.add(statusId);
        }

        // Search by creation date range
        if (createdStartDate != null) {
            query += " AND o.created_at >= ?";
            params.add(createdStartDate);
        }
        if (createdEndDate != null) {
            query += " AND o.created_at <= ?";
            params.add(createdEndDate);
        }

        // Search by creation date range
        if (completedStartDate != null) {
            query += " AND o.completed_at >= ?";
            params.add(completedStartDate);
        }
        if (completedEndDate != null) {
            query += " AND o.completed_at <= ?";
            params.add(completedEndDate);
        }

        if (paymentMethod != null) {
            query += " AND o.payment_method = ?";
            params.add(paymentMethod);
        }

        if (address != null) {
            query += " AND o.address = ?";
            params.add(address);
        }

        if (userReceive != null) {
            query += " AND o.user_receive = ?";
            params.add(userReceive);
        }

        if (contact != null) {
            query += " AND o.contact = ?";
            params.add(contact);
        }

        // Sap xep
        query += " Order by created_at desc";

        DBContext db = new DBContext();
        java.sql.Connection con = db.getConnection();
        PreparedStatement ps = con.prepareStatement(query);

        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setUserId(rs.getInt("user_id"));
                order.setOrderId((int) rs.getLong("order_id"));
                order.setTotalamount(rs.getInt("total_amount"));
                order.setCreateAt(rs.getTimestamp("created_at"));
                order.setStatusId(rs.getInt("status_id")); // Đảm bảo lấy status_id đúng
                order.setAddress(rs.getString("address"));
                order.setPaymentmethod(rs.getInt("payment_method"));
                order.setUserReceive(rs.getString("user_receive"));
                order.setContact(rs.getString("contact"));
                order.setOrderNote(rs.getString("note"));

                orderList.add(order);
            }
        }

        return orderList;
    }

    // Lấy tổng số đơn hàng sau khi lọc (totalRecords)
    public int getTotalOrderCount(String search, String status, String fromDate, String toDate, String saleName) {
        int totalRecords = 0;
        String query = "SELECT COUNT(*) FROM orders WHERE 1=1";

        if (search != null && !search.isEmpty()) {
            query += " AND (order_id LIKE ? OR customer_name LIKE ?)";
        }
        if (status != null && !status.isEmpty()) {
            query += " AND status_id = ?";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            query += " AND created_at >= ?";
        }
        if (toDate != null && !toDate.isEmpty()) {
            query += " AND created_at <= ?";
        }
        if (saleName != null && !saleName.isEmpty()) {
            query += " AND sale_name LIKE ?";
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            int index = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(index++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(index++, toDate);
            }
            if (saleName != null && !saleName.isEmpty()) {
                ps.setString(index++, "%" + saleName + "%");
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalRecords;
    }

    // Lấy danh sách đơn hàng có phân trang
    public List<Order> getFilteredOrders(String search, String status, String fromDate, String toDate, String saleName, String sortBy, int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE 1=1";

//        if (search != null && !search.isEmpty()) {
//            query += " AND (order_id LIKE ? OR customer_name LIKE ?)";
//        }
        if (status != null && !status.isEmpty()) {
            query += " AND status_id = ?";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            query += " AND created_at >= ?";
        }
        if (toDate != null && !toDate.isEmpty()) {
            query += " AND created_at <= ?";
        }
        if (saleName != null && !saleName.isEmpty()) {
            query += " AND sale_name LIKE ?";
        }

        // Sắp xếp theo yêu cầu
        if (sortBy != null) {
            switch (sortBy) {
                case "date":
                    query += " ORDER BY created_at DESC";
                    break;
//                case "customer":
//                    query += " ORDER BY customer_name ASC";
//                    break;
                case "total":
                    query += " ORDER BY total_amount DESC";
                    break;
                case "status":
                    query += " ORDER BY status_id ASC";
                    break;
                default:
                    query += " ORDER BY created_at DESC";
            }
        } else {
            query += " ORDER BY created_at DESC";
        }

        query += " LIMIT ?, ?";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(query);

            int index = 1;
            if (search != null && !search.isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(index++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(index++, toDate);
            }
            if (saleName != null && !saleName.isEmpty()) {
                ps.setString(index++, "%" + saleName + "%");
            }
            ps.setInt(index++, offset);
            ps.setInt(index++, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
//                order.setCustomerName(rs.getString("customer_name"));
                order.setTotalamount(rs.getInt("total_amount"));
                order.setStatusId(rs.getInt("status_id"));
                order.setCreateAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static void main(String[] args) throws SQLException {
//        for( Object[] oj: getOrderDetailViewByOrderId(1)){
//            System.out.println(oj[0]);
//            System.out.println(oj[1]);
//            System.out.println(oj[2]);
//        }

        List<Order> list = filterOrder(Long.valueOf("1"), null, null, null, null, null, null, null, null, null, null, null, null);
        for (Order o : list) {
            System.out.println(o);
        }
    }

}
