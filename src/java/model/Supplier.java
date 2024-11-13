/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Supplier {
    private int id;
    private String supplierName;
    private String phone;
    private String address;
    private int status;

    public Supplier() {
    }

    public Supplier(String supplierName) {
        this.supplierName = supplierName;
    }
    
    

    public Supplier(int id, String supplierName, String phone, String address, int status) {
        this.id = id;
        this.supplierName = supplierName;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }
    
     public Supplier(String supplierName, String phone, String address, int status) {
        this.supplierName = supplierName;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
