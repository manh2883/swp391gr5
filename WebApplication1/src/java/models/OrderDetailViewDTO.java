/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import Models.OrderDetail;
import Models.Product;

/**
 *
 * @author Acer
 */
public class OrderDetailViewDTO {

    private OrderDetail orderDetail = new OrderDetail();
    private Product product = new Product();
    private String variantStr;

    public OrderDetailViewDTO(OrderDetail orderDetail, Product product, String variant) {
        this.orderDetail = orderDetail;
        this.product = product;
        this.variantStr = variant;
    }
    
    public OrderDetailViewDTO(){
        
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getVariantStr() {
        return variantStr;
    }

    public void setVariantStr(String variantStr) {
        this.variantStr = variantStr;
    }

    @Override
    public String toString() {
        return "OrderDetailViewDTO{" + "orderDetail=" + orderDetail + ", product=" + product + ", variantStr=" + variantStr + '}';
    }
    
    
    
}
