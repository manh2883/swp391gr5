/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Account;
import Models.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Acer
 */
public class SettingDAO {

    public static Integer getSettingValueByName(String name) {
        String query = "SELECT setting_value FROM setting WHERE setting_name = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("setting_value");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPublicProductPerPage() {

        return getSettingValueByName("publicProductPerPage") != null ? getSettingValueByName("publicProductPerPage") : 6;
    }

    public static int getMaxQuantityInCart() {
        return getSettingValueByName("maxQuantityInCart") != null ? getSettingValueByName("maxQuantityInCart") : 20;
    }

    public static List<String> getSizeList(Object sizeStart, Object sizeEnd) {
        List<String> defaultList = new ArrayList<>();

        if (sizeStart instanceof Integer && sizeEnd instanceof Integer) {
            return getNumbericSizeList((Integer) sizeStart, (Integer) sizeEnd);
        } else if (sizeStart instanceof String && sizeEnd instanceof String) {
            return getLetterSizelist((String) sizeStart, (String) sizeEnd);
        } else {
            defaultList.add("Standard");
            return defaultList;
        }
    }

    public static List<String> getNumbericSizeList(int startSize, int endSize) {
        List<String> list = new ArrayList<>();
        if (startSize > endSize) {
            list.add("Standard");
            return list;
        }

        for (int i = 0; i <= endSize - startSize; i++) {
            list.add(String.valueOf(startSize + i));
        }
        return list;
    }

    public static List<String> getLetterSizelist(String start, String end) {
        String[] sizeCList = {"S", "M", "L", "XL", "XXL", "XXXL"};

        List<String> sizeList = Arrays.asList(sizeCList);

        List<String> sizeListDefault = new ArrayList<>();
        if (!sizeList.contains(start) || !sizeList.contains(end)) {

            sizeListDefault.add("Standard");
            return sizeListDefault;
        }

        int startIndex = sizeList.indexOf(start);
        int endIndex = sizeList.indexOf(end);

        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }

        return sizeList.subList(startIndex, endIndex + 1);
    }

    public static List<Object[]> getPublicBrandList() {
        String query = """
                       SELECT b.brand_id, b.name, b.origin, COUNT(p.product_id) AS product_count
                       FROM brand b
                       JOIN product p ON b.brand_id = p.brand_id
                       JOIN product_category c ON p.category_id = c.category_id
                       WHERE p.is_visible = 1 AND c.is_visible = 1
                       GROUP BY b.brand_id, b.name, b.origin;""";

        List<Object[]> brandList = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Object[] brandData = new Object[3];
                brandData[0] = rs.getInt("brand_id");     // ID
                brandData[1] = rs.getString("name");      // Name
                brandData[2] = rs.getInt("product_count"); // Product count

                brandList.add(brandData);
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brandList;

//        return ProductDAO.getAllBrand();
    }

    public static Map<Integer, String> getPublicProductCategory() {

        String query = """
                       SELECT c.category_id, c.category_name, COUNT(p.product_id) AS product_count
                       FROM product_category c
                       JOIN product p ON c.category_id = p.category_id
                       WHERE (c.is_visible = 1 AND p.is_visible = 1)
                       GROUP BY c.category_id, c.category_name;""";
        Map<Integer, String> brandList = new HashMap<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                brandList.put(rs.getInt("category_id"), rs.getString("category_name"));
            }
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public static int getMaxQuantityItemInCart() {
        return getSettingValueByName("maxQuantityItemInCart") != null ? getSettingValueByName("maxQuantityItemInCart") : 6;
    }

    public static void sendEmail(String to, String subject, String messageContent) throws MessagingException {
        final String senderEmail = "manhnhhe172630@fpt.edu.vn"; // Thay thế bằng email của bạn
        final String senderPassword = "gmec kdnz fnow sqpe"; // Thay thế bằng mật khẩu ứng dụng

        // Cấu hình SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Xác thực tài khoản email
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Tạo email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(messageContent);

        // Gửi email
        Transport.send(message);
    }

    public static boolean sendOTP(String to) throws MessagingException, SQLException {
        //tao otp
        String otp = generateOTP();
        // gui otp len sql
        User user = UserDAO.getUserByEmail(to);

        String subject = "TPF Shopwear| Reset Password Token| Don't share";
        String message = "Your Reset Password OTP is: " + otp
                + ".\nDon't share it to anyone.\n "
                + "Available for 60 seconds.";

        //Send Status
        boolean sendStatus = false;
        if (user != null) {
            Account acc = AccountDAO.getAccountByUserId(user.getUserId());
            Timestamp lastSend = AccountDAO.getOtpLastSendTimeByEmail(to);

            if (lastSend != null) {
                Instant now = Instant.now();
                // Chuyển đổi Timestamp sang Instant
                Instant lastSendInstant = lastSend.toInstant();
                // Tính khoảng cách thời gian
                Duration duration = Duration.between(lastSendInstant, now);
                // duration tinh theo giay

                if (duration.getSeconds() >= 60) {
                    AccountDAO.setOtpByAccountId(acc.getAccountId(), otp);
                    // AccountDAO.setOtpLastSendTimeByEmail(otp);

                    sendEmail(to, subject, message);

                    sendStatus = true;
                } else {
                    sendStatus = false;
                    System.out.println("wait a minute");
                }

            } else {
                AccountDAO.setOtpByAccountId(acc.getAccountId(), otp);

                sendEmail(to, subject, message);

                sendStatus = true;
            }
        }
        // lay otp tu sql
        return sendStatus;
    }

    public static String generateOTP() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int OTP_LENGTH = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0;
                i < OTP_LENGTH;
                i++) {
            int index = random.nextInt(CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }

        return otp.toString();
    }

    // Lấy danh sách settings
    public static List<Object[]> getSettings(int page, int pageSize, String searchValue, String filterType) {
        List<Object[]> settings = new ArrayList<>();
        String sql = "SELECT setting_id, setting_name, setting_value, setting_type FROM setting WHERE 1=1";

        if (searchValue != null && !searchValue.isEmpty()) {
            sql += " AND (setting_value LIKE ? OR setting_name LIKE ?)";
        }
        if (filterType != null && !filterType.isEmpty()) {
            sql += " AND setting_type = ?";
        }

        sql += " LIMIT ? OFFSET ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            int paramIndex = 1;
            if (searchValue != null && !searchValue.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchValue + "%");
                stmt.setString(paramIndex++, "%" + searchValue + "%");
            }
            if (filterType != null && !filterType.isEmpty()) {
                stmt.setString(paramIndex++, filterType);
            }

            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] setting = {
                    rs.getInt("setting_id"),
                    rs.getString("setting_name"),
                    rs.getInt("setting_value"),
                    rs.getString("setting_type")
                };
                settings.add(setting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

    // Cập nhật setting
    public static boolean updateSetting(int settingId, String name, int value) {
        String sql = "UPDATE setting SET setting_name = ?, setting_value = ? WHERE setting_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, value);
            stmt.setInt(3, settingId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy thông tin setting theo ID
    public static Object[] getSettingById(long id) {
        String sql = "SELECT setting_id, setting_name, setting_value, setting_type FROM setting WHERE setting_id = ?";
        try {

            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getLong("setting_id"),
                    rs.getString("setting_name"),
                    rs.getInt("setting_value"),
                    rs.getString("setting_type")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllSettingTypes() {
        List<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT setting_type FROM setting";

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                types.add(rs.getString("setting_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

    public static int countSettings(String searchValue, String filterType) {
        String sql = "SELECT COUNT(*) FROM setting WHERE 1=1";

        if (searchValue != null && !searchValue.isEmpty()) {
            sql += " AND (setting_value LIKE ? OR setting_name LIKE ?)";
        }
        if (filterType != null && !filterType.isEmpty()) {
            sql += " AND setting_type = ?";
        }

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            int paramIndex = 1;
            if (searchValue != null && !searchValue.isEmpty()) {
                stmt.setString(paramIndex++, "%" + searchValue + "%");
                stmt.setString(paramIndex++, "%" + searchValue + "%");
            }
            if (filterType != null && !filterType.isEmpty()) {
                stmt.setString(paramIndex++, filterType);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static void main(String[] args) throws MessagingException, SQLException {
        // Kiểm tra hàm getSettings() với tìm kiếm theo name/value, lọc theo type
        try {
            List<Object[]> settings = getSettings(1, 5, "a", "");

            System.out.println("🔹 Danh sách Settings:");
            for (Object[] setting : settings) {
                System.out.println(
                        "ID: " + setting[0] + ", "
                        + "Name: " + setting[1] + ", "
                        + "Value: " + setting[2] + ", "
                        + "Type: " + setting[3]
                );
            }

            // Kiểm tra tổng số cài đặt tìm được
            int total = countSettings("", "");
            System.out.println("🔹 Tổng số settings tìm được: " + total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
