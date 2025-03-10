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
    
    private int orderdetailId;
    private int orderId;
    private String productId;
    private int productvariantId;
    private int quantity;
    private int price;
//    private Product product;

    public OrderDetail() {
    }

    public OrderDetail(int orderdetailId, int orderId, String productId, int productvariantId, int quantity, int price) {
        this.orderdetailId = orderdetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productvariantId = productvariantId;
        this.quantity = quantity;
        this.price = price;
//        this.product = product;
    }

    public int getOrderdetailId() {
        return orderdetailId;
    }

    public void setOrderdetailId(int orderdetailId) {
        this.orderdetailId = orderdetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductvariantId() {
        return productvariantId;
    }

    public void setProductvariantId(int productvariantId) {
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
