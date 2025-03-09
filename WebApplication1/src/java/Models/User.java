/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Acer
 */
import java.sql.Timestamp;
import java.util.Date;

//Done
public class User {

    private int userId;
    private String email;
    private String phoneNumber;
    private String avtLink;
    private Date doB;
    private int gender;
    private String firstName;
    private String lastName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String username;
    private int roleId;

    public User() {
    }

    public User(int userId, String email, String phoneNumber, String avtLink, Date doB, int gender, String firstName, String lastName, Timestamp createdAt, Timestamp updatedAt, String username, int roleId) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avtLink = avtLink;
        this.doB = doB;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
        this.roleId = roleId;
    }

    

    public User(String email, String phoneNumber, String avtLink, Date doB,
            int gender, String firstName, String lastName) {
        this.userId = 0;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avtLink = avtLink;
        this.doB = doB;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvtLink() {
        return avtLink;
    }

    public void setAvtLink(String avtLink) {
        this.avtLink = avtLink;
    }

    public Date getDoB() {
        return doB;
    }

    public void setDoB(Date doB) {
        this.doB = doB;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", email=" + email + ", phoneNumber=" + phoneNumber + ", avtLink=" + avtLink + ", doB=" + doB  + ", gender=" + gender + ", firstName=" + firstName + ", lastName=" + lastName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    

}
