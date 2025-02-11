package controllers;

import DAO.UserDAO;
import Models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import jakarta.mail.Authenticator;
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SendOtpServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        LOGGER.info("Bắt đầu xử lý yêu cầu gửi OTP...");
//
//        String email = request.getParameter("email");
//        LOGGER.info("Email nhận được từ request: " + email);
//
//        if (email == null || email.isEmpty()) {
//            LOGGER.warning("Email bị null hoặc rỗng!");
//            request.setAttribute("message", "Vui lòng nhập email hợp lệ.");
//            request.getRequestDispatcher("Home/test.jsp").forward(request, response);
//            return;
//        }
//
//        UserDAO uDAO = new UserDAO();
//        User user = uDAO.getUserByEmail(email);
//        LOGGER.info("Kiểm tra user có tồn tại trong hệ thống: " + (user != null));
//
//        if (user != null) {
//            String otp = generateVerifyCode();
//            LOGGER.info("OTP được tạo: " + otp);
//
//            HttpSession session = request.getSession();
//            session.setAttribute("otp", otp);
//            session.setAttribute("email", email);
//            session.setMaxInactiveInterval(5 * 60);
//            LOGGER.info("OTP đã lưu vào session, thời gian hết hạn: 5 phút");
//
//            boolean isSent = sendEmail(email, otp);
//            if (isSent) {
//                LOGGER.info("Gửi OTP thành công đến email: " + email);
//                System.out.println("Gửi OTP thành công đến email: " + email);
//                request.setAttribute("message", "OTP đã được gửi đến email của bạn.");
//            } else {
//                LOGGER.warning("Gửi OTP thất bại.");
//                request.setAttribute("message", "Gửi OTP thất bại. Vui lòng thử lại.");
//            }
//        } else {
//            LOGGER.warning("Email không tồn tại trong hệ thống.");
//            request.setAttribute("message", "Email không tồn tại trong hệ thống.");
//        }

        request.getRequestDispatcher("Home/test.jsp").forward(request, response);
    }

//    private boolean sendEmail(String recipient, String otp) {
//        final String from = "tpfshopwear@gmail.com";
//        final String password = "123@_123A"; // Nên sử dụng mật khẩu ứng dụng thay vì hardcode
//
//        LOGGER.info("Bắt đầu quá trình gửi email...");
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//        props.put("mail.debug", "true"); // Bật debug để kiểm tra log gửi mail
//
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//            message.setSubject("Mã OTP xác thực tài khoản");
//            message.setText("Mã OTP của bạn là: " + otp + "\nVui lòng không chia sẻ mã này cho ai.");
//
//            LOGGER.info("Đang gửi email tới: " + recipient);
//            Transport.send(message);
//
//            LOGGER.info("Gửi email thành công!");
//            return true;
//        } catch (MessagingException e) {
//            LOGGER.log(Level.SEVERE, "Lỗi khi gửi email: " + e.getMessage(), e);
//            return false;
//        }
//    }
//
//    private String generateVerifyCode() {
//        Random rand = new Random();
//        int number = rand.nextInt(999999);
//        return String.format("%06d", number);
//    }
}
