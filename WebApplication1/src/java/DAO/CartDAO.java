/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.CartDetail;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class CartDAO extends DBContext{
    public static int getCartIDbyUserID(int userID){
        return 0;
    }
    
    public void createCartForUserID(int userID){
        
    }
    public ArrayList<CartDetail> getAllCartDetailByUserID(int userID){
        return null;
    }
    
    public void editCartDetailByID(int userID, int cartDetailID){
        
    }
}
