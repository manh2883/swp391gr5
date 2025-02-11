/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import java.util.ArrayList;
import models.Slider;
import models.SliderDetail;

/**
 *
 * @author Acer
 */
public class SliderDAO extends DBContext {

    public static ArrayList<Slider> getAllSlider() {
        return null;
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
        for(Slider s: sDAO.getAllSlider()){
            System.out.println(s);
        }
    }
}
