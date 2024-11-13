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
public class Consignment {

    int id;
    int productId;
    int wareHouseId;
    int contractId;
    int status;
    Date deliveryDate;
    int productCategoryId;
    int importPrice;
    int sellingPrice;
    int numberOfProduct;
    float discountPercentage;
    private String supplierName;
    private String supplierPhone;
    private String supplierAddress;

    public Consignment() {
    }

    public Consignment(int id, int productId, int wareHouseId, int contractId, int status, Date deliveryDate, int productCategoryId, int importPrice, int sellingPrice, int numberOfProduct, float discountPercentage) {
        this.id = id;
        this.productId = productId;
        this.wareHouseId = wareHouseId;
        this.contractId = contractId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.productCategoryId = productCategoryId;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfProduct = numberOfProduct;
        this.discountPercentage = discountPercentage;
    }

    public Consignment(int id, int productId, int wareHouseId, int contractId, int status, Date deliveryDate, int productCategoryId, int importPrice, int sellingPrice, int numberOfProduct, float discountPercentage, String supplierName) {
        this.id = id;
        this.productId = productId;
        this.wareHouseId = wareHouseId;
        this.contractId = contractId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.productCategoryId = productCategoryId;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfProduct = numberOfProduct;
        this.discountPercentage = discountPercentage;
        this.supplierName = supplierName;
    }

    public Consignment(int id, int productId, int status, Date deliveryDate, int importPrice, int sellingPrice, int numberOfProduct, float discountPercentage) {
        this.id = id;
        this.productId = productId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfProduct = numberOfProduct;
        this.discountPercentage = discountPercentage;
    }

    public Consignment(int productId, int importPrice, int sellingPrice, int numberOfProduct) {
        this.productId = productId;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.numberOfProduct = numberOfProduct;
    }

    public Consignment(int id, int productId, int contractId, Date deliveryDate, int importPrice, int sellingPrice, String supplierPhone, String supplierAddress) {
        this.id = id;
        this.productId = productId;
        this.contractId = contractId;
        this.deliveryDate = deliveryDate;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
    }

    public Consignment(int id, int productId, int contractId, int status, Date deliveryDate, int importPrice, int sellingPrice, String supplierName, String supplierPhone, String supplierAddress) {
        this.id = id;
        this.productId = productId;
        this.contractId = contractId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.importPrice = importPrice;
        this.sellingPrice = sellingPrice;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getWareHouseId() {
        return wareHouseId;
    }

    public int getContractId() {
        return contractId;
    }

    public int getStatus() {
        return status;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public int getImportPrice() {
        return importPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public void setImportPrice(int importPrice) {
        this.importPrice = importPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "Consignment{" + "id=" + id + ", productId=" + productId + ", wareHouseId=" + wareHouseId + ", contractId=" + contractId + ", status=" + status + ", deliveryDate=" + deliveryDate + ", productCategoryId=" + productCategoryId + ", importPrice=" + importPrice + ", sellingPrice=" + sellingPrice + ", numberOfProduct=" + numberOfProduct + ", discountPercentage=" + discountPercentage + ", supplierName=" + supplierName + ", supplierPhone=" + supplierPhone + ", supplierAddress=" + supplierAddress + '}';
    }

    

    public String getStatusString() {
        switch (status) {
            case 0:
                return "Pending";
            case 1:
                return "có sẵn";
            case 2:
                return "hủy bỏ";
            // Thêm các trạng thái khác nếu có
            default:
                return "Unknown";
        }
    }

}
