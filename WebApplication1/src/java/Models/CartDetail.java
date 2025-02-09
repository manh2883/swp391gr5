/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;

/**
 *
 * @author nguye
 */
public class CartDetail {
    private int cartDetailID;
    private int cartID;
    private String productID;
    private int productVariantID;
    private int quantity;
    private Timestamp updatedDate;

    public CartDetail() {
    }

    public CartDetail(int cartDetailID, int cartID, String productID, int productVariantID, int quantity, Timestamp updatedDate) {
        this.cartDetailID = cartDetailID;
        this.cartID = cartID;
        this.productID = productID;
        this.productVariantID = productVariantID;
        this.quantity = quantity;
        this.updatedDate = updatedDate;
    }

    public int getCartDetailID() {
        return cartDetailID;
    }

    public void setCartDetailID(int cartDetailID) {
        this.cartDetailID = cartDetailID;
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
        return "CartDetail{" + "cartDetailID=" + cartDetailID + ", cartID=" + cartID + ", productID=" + productID + ", productVariantID=" + productVariantID + ", quantity=" + quantity + ", updatedDate=" + updatedDate + '}';
    }

    

    
    
}
