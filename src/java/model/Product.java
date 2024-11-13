/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Product {
    private int id;
    private String productName;
    private Date manufactureDate;
    private Date expireDate;
    private int unitId;
    private int retailPrice;
    private int categoryId;

    public Product() {
    }

    public Product(int id, String productName) {
        this.id = id;
        this.productName = productName;
    }
    
    public Product(int id, String productName, Date manufactureDate, Date expireDate, int unitId, int retailPrice, int categoryId) {
        this.id = id;
        this.productName = productName;
        this.manufactureDate = manufactureDate;
        this.expireDate = expireDate;
        this.unitId = unitId;
        this.retailPrice = retailPrice;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName=" + productName + ", manufactureDate=" + manufactureDate + ", expireDate=" + expireDate + ", unitId=" + unitId + ", retailPrice=" + retailPrice + ", categoryId=" + categoryId + '}';
    }
    
    
    
}
