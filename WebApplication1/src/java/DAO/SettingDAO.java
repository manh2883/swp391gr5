/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;

/**
 *
 * @author Acer
 */
public class SettingDAO {
    
    public static int getPublicProductPerPage(){
        
        return 5;
    }
    
    public static List<Object[]> getPublicBrandList(){
        return ProductDAO.getAllBrand();
    }
    
    
    
    
}
