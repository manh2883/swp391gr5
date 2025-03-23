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

    public static int getPublicProductPerPage() {

        return 5;
    }
    
    public static int getMaxQuantityInCart(){
        return 20;
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
        return 5;
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

    public static void main(String[] args) throws MessagingException, SQLException {
        System.out.println( getSizeList(1, 12)); // [2, 3, 4, 5]
        System.out.println(getSizeList("M", "XXL")); // [M, L, XL, XXL]
        System.out.println( getSizeList("XS", "L")); // [Standard]
        System.out.println( getSizeList(2, "L")); // [Standard]
    }

}
