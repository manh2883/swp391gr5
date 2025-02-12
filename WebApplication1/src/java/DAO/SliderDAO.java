/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import Models.Product;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Slider;
import models.SliderDetail;

/**
 *
 * @author Acer
 */
public class SliderDAO extends DBContext {

    public static ArrayList<Slider> getAllSlider() {
        DBContext db = new DBContext();
        String query = "SELECT * FROM slider";
        ArrayList<Slider> list = new ArrayList<>();
        try {
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
               Slider slider = new Slider();
               slider.setSliderId(0);Id(rs.getInt(rs.getInt("slider_id")));
               slider.setClickCount(rs.getInt("click_count"));
               slider.setCreatedBy(createdBy);
               slider.setCreatedDate(createdDate);
               slider.setEndDate(endDate);
               slider.setSliderName()
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<SliderDetail> getAllSliderDetailBySliderId(int id) {
        return null;
    }

    public static SliderDetail getSliderDetailBySliderIdAndOrder(int sliderId, int order) {
        return null;
    }

    public static Slider getSliderById(int sliderId) {
        return null;
    }

    public static void createSlider(Slider slider) {

    }

    public static void createSliderDetailForSlider(int sliderId, SliderDetail sliderDetail) {

    }

    public static void updateSlider(Slider slider) {

    }

    public static void updateSliderDetailForSlider(int sliderId, SliderDetail sliderDetail) {

    }

    public static void deleteSlider(Slider slider) {

    }

    public static void delateSliderDetailForSlider(int sliderId, SliderDetail sliderDetail) {

    }

    public static void main(String[] args) {
        SliderDAO sDAO = new SliderDAO();
        for (Slider s : sDAO.getAllSlider()) {
            System.out.println(s);
        }
    }
}
