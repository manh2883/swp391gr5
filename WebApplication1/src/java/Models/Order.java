/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;
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
    private int paymentmethod;
    private String address;

    public Order() {
    }

    public Order(int orderId, int userId, int totalamount, int statusId, Date createAt, int paymentmethod, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalamount = totalamount;
        this.statusId = statusId;
        this.createAt = createAt;
        this.paymentmethod = paymentmethod;
        this.address = address;
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

    public int getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(int paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", userId=" + userId + ", totalamount=" + totalamount + ", statusId=" + statusId + ", createAt=" + createAt + ", paymentmethod=" + paymentmethod + ", address=" + address + '}';
    }

    
   
}
