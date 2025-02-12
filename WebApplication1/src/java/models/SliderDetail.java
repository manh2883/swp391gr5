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
    private String backLink;
    private String detailTitle;
    private String detailContent;

    public SliderDetail() {
    }

    public SliderDetail(int sliderId, int sliderOrder, String sliderImgLink, String BackLink, String detailTitle, String detailContent) {
        this.sliderId = sliderId;
        this.sliderOrder = sliderOrder;
        if (sliderImgLink != null) {
            this.sliderImgLink = sliderImgLink;
        } else {
            this.sliderImgLink = "/Images/RUN.jsg";
        }
        if (BackLink != null) {
            this.backLink = BackLink;
        } else {
            this.backLink = "/Home";
        }

        this.detailTitle = detailTitle;
        this.detailContent = detailContent;
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
        return sliderImgLink;
    }

    public void setSliderImgLink(String sliderImgLink) {
        this.sliderImgLink = sliderImgLink;
    }

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String BackLink) {
        this.backLink = BackLink;
    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    @Override
    public String toString() {
        return "SliderDetail{" + "sliderId=" + sliderId + ", sliderOrder=" + sliderOrder + ", sliderImgLink=" + sliderImgLink + ", BackLink=" + backLink + ", detailTitle=" + detailTitle + ", detailContent=" + detailContent + '}';
    }

}
