/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Acer
 */
public class Permission {

    private int permissionId;
    private int roleId;
    private String permissionName;

    public Permission() {
    }

    public Permission(int roleId, String permissionName) {
        this.roleId = roleId;
        this.permissionName = permissionName;
    }

    public Permission(int permissionId, int roleId, String permissionName) {
        this.permissionId = permissionId;
        this.roleId = roleId;
        this.permissionName = permissionName;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "Permission{" + "permissionId=" + permissionId + ", roleId=" + roleId + ", permissionName=" + permissionName + '}';
    }
    
    

}
