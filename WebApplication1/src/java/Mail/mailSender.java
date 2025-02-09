/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mail;

import com.mysql.cj.Session;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

/**
 *
 * @author Acer
 */
public class mailSender {
    private String email;
    private String passWord;

    public mailSender() {
    }

    public mailSender(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mailSender{");
        sb.append("email=").append(email);
        sb.append(", passWord=").append(passWord);
        sb.append('}');
        return sb.toString();
    }
    
    
  
}
