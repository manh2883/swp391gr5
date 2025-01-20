/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.User;
import com.sun.jdi.connect.spi.Connection;
import java.util.ArrayList;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Acer
 */
public class UserDAO extends DBContext{
    public static List<User> getAllUser() {
        String query = "SELECT * FROM User";
        List<User> userList = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();  // Giả sử DBContext cung cấp phương thức này
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            // Lấy dữ liệu từ resultSet
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvtLink(rs.getString("avt_link"));
                user.setDoB(rs.getDate("DoB"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                userList.add(user);
            }
            // Đóng kết nối và tài nguyên
            rs.close();
            stm.close();
            con.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return userList;
    }
    public boolean checkEmailIsExist(String userName){
        return false;
    }
    public boolean checkPhoneNumberIsExist(String userName){
        return false;
    }
    
    
    
    
    
    
 
    public static void main(String[] args) {
        List<User> userList = getAllUser();
              for (User user  : userList) {
                  System.out.println(user);
            }
    }
}
