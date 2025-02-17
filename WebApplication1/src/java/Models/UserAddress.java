/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author nguye
 */
public class UserAddress {
    private int addressID;
    private int userID;
    private String addressLine;

    public UserAddress() {
    }

    public UserAddress(int addressID, int userID, String addressLine) {
        this.addressID = addressID;
        this.userID = userID;
        this.addressLine = addressLine;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    @Override
    public String toString() {
        return "UserAddress{" + "addressID=" + addressID + ", userID=" + userID + ", addressLine=" + addressLine + '}';
    }
    
    
}
