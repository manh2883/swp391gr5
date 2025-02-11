/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Acer
 */
public class SliderDetail {

    private int sliderId;
    private int sliderOrder;
    private String sliderImgLink;
    private String BackLink;

    public SliderDetail() {
    }

    public SliderDetail(int sliderId, int sliderOrder, String sliderImgLink, String BackLink) {
        this.sliderId = sliderId;
        this.sliderOrder = sliderOrder;
        this.sliderImgLink = sliderImgLink;
        this.BackLink = BackLink;
    }

    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public int getSliderOrder() {
        return sliderOrder;
    }

    public void setSliderOrder(int sliderOrder) {
        this.sliderOrder = sliderOrder;
    }

    public String getSliderImgLink() {
        if (sliderImgLink == null) {
            return "RUN.jpg";
        }
        return sliderImgLink;
    }

    public void setSliderImgLink(String sliderImgLink) {
        this.sliderImgLink = sliderImgLink;
    }

    public String getBackLink() {
        if (BackLink == null) {
            return "Home";
        }
        return BackLink;
    }

    public void setBackLink(String BackLink) {
        this.BackLink = BackLink;
    }

    @Override
    public String toString() {
        return "SliderDetail{" + "sliderId=" + sliderId + ", sliderOrder=" + sliderOrder + ", sliderImgLink=" + sliderImgLink + ", BackLink=" + BackLink + '}';
    }

}
