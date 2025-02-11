/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author Acer
 */
public class Slider {

    private int sliderId;
    private String sliderName;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdDate;
    private Timestamp createdBy;
    private int length;
    private int width;
    private int clickCount;

    public Slider() {
    }

    public Slider(int sliderId, String sliderName, Timestamp startDate, Timestamp endDate, Timestamp createdDate, Timestamp createdBy, int length, int width, int clickCount) {
        this.sliderId = sliderId;
        this.sliderName = sliderName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.length = length;
        this.width = width;
        this.clickCount = clickCount;
    }

    public Slider(String sliderName, Timestamp startDate, Timestamp endDate, Timestamp createdDate, Timestamp createdBy, int length, int width) {
        this.sliderName = sliderName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.length = length;
        this.width = width;
        this.clickCount = 0;
    }

    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Timestamp createdBy) {
        this.createdBy = createdBy;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "Slider{" + "sliderId=" + sliderId + ", sliderName=" + sliderName + ", startDate=" + startDate + ", endDate=" + endDate + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", length=" + length + ", width=" + width + ", clickCount=" + clickCount + '}';
    }

 
    
    
    
}
