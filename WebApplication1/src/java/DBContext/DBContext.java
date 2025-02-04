package DBContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */
public class DBContext {

    public Connection getConnection() {
        String db = "TPFShopWearv2";
        String url = "jdbc:mysql://localhost:3306/" + db;
        String user = "root";
        String password = "12345678";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            System.out.println("loi" + ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        Connection connection = dbContext.getConnection();

        if (connection != null) {
            System.out.println("Kết nối thành côkoling!");
        } else {
            System.out.println("Kết nối thất bạkoli!");
        }
    }
}
