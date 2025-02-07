/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.security.Timestamp;

/**
 *
 * @author nguye
 */
public class CartDetail {
    private int cartDetialID;
    private int cartID;
    private String productID;
    private int productVariantID;
    private int quantity;
    private Timestamp updatedDate;

    public CartDetail() {
    }

    public CartDetail(int cartDetialID, int cartID, String productID, int productVariantID, int quantity, Timestamp updatedDate) {
        this.cartDetialID = cartDetialID;
        this.cartID = cartID;
        this.productID = productID;
        this.productVariantID = productVariantID;
        this.quantity = quantity;
        this.updatedDate = updatedDate;
    }

    public int getCartDetialID() {
        return cartDetialID;
    }

    public void setCartDetialID(int cartDetialID) {
        this.cartDetialID = cartDetialID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getProductVariantID() {
        return productVariantID;
    }

    public void setProductVariantID(int productVariantID) {
        this.productVariantID = productVariantID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "CartDetail{" + "cartDetialID=" + cartDetialID + ", cartID=" + cartID + ", productID=" + productID + ", productVariantID=" + productVariantID + ", quantity=" + quantity + ", updatedDate=" + updatedDate + '}';
    }
    
    
}
