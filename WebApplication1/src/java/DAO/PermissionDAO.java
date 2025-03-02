/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Account;
import com.oracle.wls.shaded.org.apache.bcel.generic.D2F;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import models.Permission;

/**
 *
 * @author Acer Thuc hien tinh nang phan quyen
 */
public class PermissionDAO extends DBContext {

    public static boolean checkPermission(int accountId, String permissionName) {

        AccountDAO aDAO = new AccountDAO();
        Account acc = aDAO.getAccountById(accountId);
        boolean check = false;
        if (acc != null) {
            int roleId = acc.getRoleId();
            check = checkPermissionForRole(permissionName, roleId);
        }
        return check;
    }

    public static boolean checkPermissionForRole(String permissionName, int roleId) {
        ArrayList<Object> list = getPermissionByName(permissionName);

        for (Object p : list) {
            HashMap<String, Object> map = (HashMap<String, Object>) p;
            if (roleId == Integer.valueOf((int) map.get("role_id"))) {
                return true;
            }
        }

        return false;
    }

    public static ArrayList<Permission> getPermissionById(int roleId) {
        String query = """
                       SELECT permission.permission_id, permission_name, role_id from permission 
                       join role_permission on permission.permission_id = role_permission.permission_id 
                       where role_id = ?""";
        ArrayList<Permission> list = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, roleId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Permission per = new Permission();
                per.setPermissionId(rs.getInt("permission_id"));
                per.setPermissionName(rs.getString("permission_name"));
                per.setRoleId(rs.getInt("role_id"));
                list.add(per);
            }

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Object> getPermissionByName(String permissionName) {
        String query = """
                   SELECT permission.permission_id, permission_name, role_id 
                   FROM permission 
                   JOIN role_permission ON permission.permission_id = role_permission.permission_id 
                   WHERE permission_name = ?""";
        ArrayList<Object> list = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, permissionName);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> per = new HashMap<>();
                per.put("permission_id", rs.getInt("permission_id"));
                per.put("permission_name", rs.getString("permission_name"));
                per.put("role_id", rs.getInt("role_id"));
                list.add(per);
            }

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addNewPermission(int roldeId, String permissionName) {

    }

    public static void deletePermission(int roldeId, String permissionName) {

    }

    public static List<Object[]> getRolePermissionList(int[] roleIds) {
        List<Object[]> resultList = new ArrayList<>();
        String query = generateDynamicQuery(roleIds);

        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                Object[] row = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static ArrayList<String> getRoleList(int[] roleIds) {
        ArrayList<String> list = new ArrayList<>();
        for (int id : roleIds) {
            switch (id) {
                case 1:
                    list.add("Admin");
                    break;
                case 2:
                    list.add("Customer");
                    break;
                case 3:
                    list.add("Marketing");
                    break;
                case 4:
                    list.add("Sale");
                    break;
                case 5:
                    list.add("Shipper");
                    break;
                default:
                    list.add("Unknown");
                    break;
            }
        }
        return list;
    }

    private static String generateDynamicQuery(int[] roleIds) {
        StringBuilder sql = new StringBuilder("SELECT p.permission_id as perid, p.permission_name AS permission");

        for (int roleId : roleIds) {
            sql.append(", MAX(CASE WHEN r.role_id = ").append(roleId)
                    .append(" THEN 'true' ELSE 'false' END) AS role_").append(roleId);
        }

        sql.append(" FROM permission p ")
                .append("LEFT JOIN role_permission rp ON p.permission_id = rp.permission_id ")
                .append("LEFT JOIN roles r ON rp.role_id = r.role_id ")
                .append("GROUP BY p.permission_id;");

        return sql.toString();
    }

    public static void updatePermission(int roleId, int permissionId, boolean newState) {
        String query;
        if (newState) {
            query = "INSERT INTO role_permission (role_id, permission_id) VALUES (?, ?)";
        } else {
            query = "DELETE FROM role_permission WHERE role_id = ? AND permission_id = ?";
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, roleId);
            stmt.setInt(2, permissionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void togglePermission(int permissionId, int roleId) throws SQLException {
        DBContext db = new DBContext();
        java.sql.Connection conn = db.getConnection();

        // Kiểm tra xem quyền đã tồn tại chưa
        String checkQuery = "SELECT COUNT(*) FROM role_permission WHERE permission_id = ? AND role_id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, permissionId);
        checkStmt.setInt(2, roleId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            // Nếu có, xóa quyền (toggle OFF)
            String deleteQuery = "DELETE FROM role_permission WHERE permission_id = ? AND role_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, permissionId);
            deleteStmt.setInt(2, roleId);
            deleteStmt.executeUpdate();
        } else {
            // Nếu chưa có, thêm quyền (toggle ON)
            String insertQuery = "INSERT INTO role_permission (permission_id, role_id) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, permissionId);
            insertStmt.setInt(2, roleId);
            insertStmt.executeUpdate();
        }

        conn.close();
    }

    public static void main(String[] args) {
        int[] roleIds = {1, 2, 3, 4, 5}; // Nhận từ tham số bên ngoài
        for(int i: roleIds){
            System.out.println(checkPermissionForRole("ProductList", i));
        }
    }
}
