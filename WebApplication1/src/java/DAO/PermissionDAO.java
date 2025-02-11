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
        ArrayList<Permission> list = getPermissionByName(permissionName);

        for (Permission p : list) {
            if (roleId == p.getRoleId()) {
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

    public static ArrayList<Permission> getPermissionByName(String permissionName) {
        String query = """
                       SELECT permission.permission_id, permission_name, role_id from permission 
                       join role_permission on permission.permission_id = role_permission.permission_id 
                       where permission_name = ?""";
        ArrayList<Permission> list = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, permissionName);
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

    public static void addNewPermission(int roldeId, String permissionName) {

    }

    public static void deletePermission(int roldeId, String permissionName) {

    }

    public static void updatePermission(int roldeId, String permission) {

    }

    public static void main(String[] args) {
        PermissionDAO pDAO = new PermissionDAO();

        System.out.println("Cac quyen cua roleId = 1:");
        for (Permission p : pDAO.getPermissionById(1)) {
            System.out.println(p);
        }
        System.out.println("-------------");
        for (Permission p : pDAO.getPermissionByName("ViewProducts")) {
            System.out.println(p);
        }
        
        System.out.println(pDAO.checkPermission(1, "ViewProducts"));
    }
}
