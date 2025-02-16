/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DBContext.DBContext;
import models.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
                slider.setSliderId(rs.getInt("slider_id"));
                slider.setSliderName(rs.getString("slider_name"));
                slider.setEndDate(rs.getTimestamp("end_date"));
                slider.setStartDate(rs.getTimestamp("start_date"));
                slider.setCreatedDate(rs.getTimestamp("created_date"));
                slider.setCreatedBy(rs.getInt("created_by"));
                slider.setLength(rs.getInt("length"));
                slider.setWidth(rs.getInt("width"));
                slider.setClickCount(rs.getInt("click_count"));
                list.add(slider);
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
        DBContext db = new DBContext();
        String query = "SELECT * FROM slider_detail where slider_id = ? order by slider_order";
        ArrayList<SliderDetail> list = new ArrayList<>();
        try {
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                SliderDetail sDetail = new SliderDetail();
                sDetail.setSliderId(rs.getInt("slider_id"));
                sDetail.setDetailTitle(rs.getString("sliderTitle"));
                sDetail.setSliderOrder(rs.getInt("slider_order"));
                sDetail.setDetailContent(rs.getString("sliderContent"));
                sDetail.setBackLink(rs.getString("back_link"));
                sDetail.setSliderImgLink(rs.getString("slider_img_link"));

                list.add(sDetail);
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static SliderDetail getSliderDetailBySliderIdAndOrder(int sliderId, int order) {
        return null;
    }

    public static int getCurrentSlidetId() {

        DBContext db = new DBContext();
        String query = """
                        SELECT slider_id
                       FROM slider
                       WHERE start_date <= NOW() 
                       AND end_date >= NOW();""";
        int id = -1;
        try {
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                id = rs.getInt("slider_id");
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static ArrayList<SliderDetail> getCurrentSliderList() {
        return getAllSliderDetailBySliderId(getCurrentSlidetId());
    }

    public static Slider getSliderById(int sliderId) {
        DBContext db = new DBContext();
        String query = "SELECT * FROM slider where slider_id = ?";
        Slider slider = new Slider();
        try {
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                slider.setSliderId(rs.getInt("slider_id"));
                slider.setSliderName(rs.getString("slider_name"));
                slider.setEndDate(rs.getTimestamp("end_date"));
                slider.setStartDate(rs.getTimestamp("start_date"));
                slider.setCreatedDate(rs.getTimestamp("creared_date"));
                slider.setCreatedBy(rs.getInt("created_by"));
                slider.setLength(rs.getInt("lenght"));
                slider.setWidth(rs.getInt("width"));
                slider.setClickCount(rs.getInt("click_count"));
            }
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slider;
    }

    public static void createSlider(Slider slider) {

    }

    public static void createSliderDetailForSlider(int sliderId, SliderDetail SliderDetail) {

    }

    public static void updateSlider(Slider slider) {

    }

    public static void updateSliderDetailForSlider(int sliderId, SliderDetail SliderDetail) {

    }

    public static void deleteSlider(Slider slider) {

    }

    public static void delateSliderDetailForSlider(int sliderId, SliderDetail SliderDetail) {

    }

    public static void main(String[] args) {
        SliderDAO sDAO = new SliderDAO();
//        for (Slider s : sDAO.getAllSlider()) {
//            System.out.println(s);
//            for (SliderDetail sd : sDAO.getAllSliderDetailBySliderId(s.getSliderId())) {
//                System.out.println("       " + sd);
//            }
//        }
        System.out.println(SliderDAO.getCurrentSlidetId());
        for (SliderDetail sd : SliderDAO.getCurrentSliderList()) {
            System.out.println(sd);
        }
    }
}
