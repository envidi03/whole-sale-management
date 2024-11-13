/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Acer
 */
public class OrderDetail {
    int orderId;
    int consignmentId;
    int quantity;
    int totalPrice;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int consignmentId, int quantity, int totalPrice) {
        this.orderId = orderId;
        this.consignmentId = consignmentId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getConsignmentId() {
        return consignmentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setConsignmentId(int consignmentId) {
        this.consignmentId = consignmentId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderId=" + orderId + ", consignmentId=" + consignmentId + ", quantity=" + quantity + ", totalPrice=" + totalPrice + '}';
    }


    
    
    
}
