/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import models.Permission;

/**
 *
 * @author Acer Thuc hien tinh nang phan quyen
 */
public class PermissionDAO {

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

    public static void updatePermission(int roldeId, String permission) {

    }

    public static void main(String[] args) {
        PermissionDAO pDAO = new PermissionDAO();
//
//        System.out.println("Cac quyen cua roleId = 1:");
//        for (Permission p : pDAO.getPermissionById(1)) {
//            System.out.println(p);
//        }
//        System.out.println("-------------");
//        for (Permission p : pDAO.getPermissionByName("ViewProducts")) {
//            System.out.println(p);
//        }

        System.out.println(pDAO.checkPermissionForRole("ProductList", 1));
    }
}
