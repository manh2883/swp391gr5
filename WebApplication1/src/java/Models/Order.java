/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class Order {
    
    private int orderId;
    private int userId;
    private int totalamount;
    private int statusId;
    private Date createAt;
    private Date completedAt;
    private int paymentMethod;

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    private int paymentStatus;
    private String address;
    private String orderNote;
    private String userReceive;
    private String contact;

    public Order(int orderId, int userId, int totalamount, int statusId, Date createAt, Date completedAt, int paymentMethod, int paymentStatus, String address, String orderNote, String userReceive, String contact) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalamount = totalamount;
        this.statusId = statusId;
        this.createAt = createAt;
        this.completedAt = completedAt;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.address = address;
        this.orderNote = orderNote;
        this.userReceive = userReceive;
        this.contact = contact;
    }

     

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public int getPaymentmethod() {
        return paymentMethod;
    }

    public void setPaymentmethod(int paymentmethod) {
        this.paymentMethod = paymentmethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(String userReceive) {
        this.userReceive = userReceive;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order{");
        sb.append("orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", totalamount=").append(totalamount);
        sb.append(", statusId=").append(statusId);
        sb.append(", createAt=").append(createAt);
        sb.append(", completedAt=").append(completedAt);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", address=").append(address);
        sb.append(", orderNote=").append(orderNote);
        sb.append(", userReceive=").append(userReceive);
        sb.append(", contact=").append(contact);
        sb.append('}');
        return sb.toString();
    }
   

   
    
}
