/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;
    private String brandName;
    private String categoryName;
    private String categoryId;
    private String imagesPath;
    private Date createAt;
    private String imgUrl;
    
    public Product() {
    }

    public Product(String productId, String name, String description, double price, String brandName, String categoryName,String categoryId,String imagesPath, Timestamp createAt, String imgUrl) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.imagesPath = imagesPath;
        this.createAt = createAt;
        if(imgUrl == null || imgUrl.isEmpty()) {
            this.imgUrl = "Images/RUN.jpg";
        }else{
            this.imgUrl = imgUrl;
        }
    }

   

    public Product(String name, String description, double price, String brandName, String categoryName, String categoryId, String imagesPath, Timestamp createAt, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.imagesPath = imagesPath;
        this.createAt = createAt;
        this.imgUrl = imgUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name=" + name + ", description=" + description + ", price=" + price + ", brandName=" + brandName + ", categoryName=" + categoryName + ", categoryId=" + categoryId + ", imagesPath=" + imagesPath + ", createAt=" + createAt + ", imgUrl=" + imgUrl + '}';
    }
    
    

    

    
}
