/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Acer
 */
public class Item {
    private Consignment consignment;
    private int quantity;
    private int price;

    public Item() {
    }

    public Item(Consignment consignment, int quantity, int price) {
        this.consignment = consignment;
        this.quantity = quantity;
        this.price = price;
    }

    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment(Consignment consignment) {
        this.consignment = consignment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" + "consignment=" + consignment + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
    
}
