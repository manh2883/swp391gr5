/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dell
 */
 public class OrderDetail {
    
    private String orderdetailId;
    private int orderId;
    private int productId;
    private String productvariantId;
    private int quantity;
    private int price;

    public OrderDetail() {
    }

    public OrderDetail(String orderdetailId, int orderId, int productId, String productvariantId, int quantity, int price) {
        this.orderdetailId = orderdetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productvariantId = productvariantId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderdetailId() {
        return orderdetailId;
    }

    public void setOrderdetailId(String orderdetailId) {
        this.orderdetailId = orderdetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductvariantId() {
        return productvariantId;
    }

    public void setProductvariantId(String productvariantId) {
        this.productvariantId = productvariantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderdetailId=" + orderdetailId + ", orderId=" + orderId + ", productId=" + productId + ", productvariantId=" + productvariantId + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
    
    
    
}
