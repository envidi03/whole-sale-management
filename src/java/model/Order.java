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
public class Order {

    private int id;
    private int customerId;
    private String customerName;
    private int employeeIdIncharge;
    private Date createdDate;
    private int status;
    private Date exportedDate;
    private Date arrivedDate;
    private int shippingUnit;
    private int orderValueBeforeDiscount;
    private float totalDiscountPercenTage;
    private int orderValueAfterDiscount;
    private Date payDate;
    private int orderReportId;

    public Order() {
    }

    public Order(int id, int customerId, String customerName, int employeeIdIncharge, Date createdDate, int status, Date exportedDate, Date arrivedDate, int shippingUnit, int orderValueBeforeDiscount, float totalDiscountPercenTage, int orderValueAfterDiscount, Date payDate, int orderReportId) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.employeeIdIncharge = employeeIdIncharge;
        this.createdDate = createdDate;
        this.status = status;
        this.exportedDate = exportedDate;
        this.arrivedDate = arrivedDate;
        this.shippingUnit = shippingUnit;
        this.orderValueBeforeDiscount = orderValueBeforeDiscount;
        this.totalDiscountPercenTage = totalDiscountPercenTage;
        this.orderValueAfterDiscount = orderValueAfterDiscount;
        this.payDate = payDate;
        this.orderReportId = orderReportId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getEmployeeIdIncharge() {
        return employeeIdIncharge;
    }

    public void setEmployeeIdIncharge(int employeeIdIncharge) {
        this.employeeIdIncharge = employeeIdIncharge;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExportedDate() {
        return exportedDate;
    }

    public void setExportedDate(Date exportedDate) {
        this.exportedDate = exportedDate;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public int getShippingUnit() {
        return shippingUnit;
    }

    public void setShippingUnit(int shippingUnit) {
        this.shippingUnit = shippingUnit;
    }

    public int getOrderValueBeforeDiscount() {
        return orderValueBeforeDiscount;
    }

    public void setOrderValueBeforeDiscount(int orderValueBeforeDiscount) {
        this.orderValueBeforeDiscount = orderValueBeforeDiscount;
    }

    public float getTotalDiscountPercenTage() {
        return totalDiscountPercenTage;
    }

    public void setTotalDiscountPercenTage(float totalDiscountPercenTage) {
        this.totalDiscountPercenTage = totalDiscountPercenTage;
    }

    public int getOrderValueAfterDiscount() {
        return orderValueAfterDiscount;
    }

    public void setOrderValueAfterDiscount(int orderValueAfterDiscount) {
        this.orderValueAfterDiscount = orderValueAfterDiscount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getOrderReportId() {
        return orderReportId;
    }

    public void setOrderReportId(int orderReportId) {
        this.orderReportId = orderReportId;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", customerId=" + customerId + ", customerName=" + customerName + ", employeeIdIncharge=" + employeeIdIncharge + ", createdDate=" + createdDate + ", status=" + status + ", exportedDate=" + exportedDate + ", arrivedDate=" + arrivedDate + ", shippingUnit=" + shippingUnit + ", orderValueBeforeDiscount=" + orderValueBeforeDiscount + ", totalDiscountPercenTage=" + totalDiscountPercenTage + ", orderValueAfterDiscount=" + orderValueAfterDiscount + ", payDate=" + payDate + ", orderReportId=" + orderReportId + '}';
    }

    public String getStatusString() {
        switch (this.status) {
            case 0:
                return "Hủy (Đang chờ phê duyệt)";
            case 1:
                return "Đang chờ phê duyệt";
            case 2:
                return "Chưa thanh toán (Người quản lý kho đã được phê duyệt)";
            case 3:
                return "Delivered (Customer Paid)";
            case 4:
                return "Đã giao hàng (Khách hàng thanh toán)";
            case 5:
                return "Quá hạn";
            case 6:
                return "Đang nợ";
            default:
                return "Trạng thái không xác định";
        }
    }

}
