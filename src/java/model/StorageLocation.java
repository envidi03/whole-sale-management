/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class StorageLocation {

    private int warehouse_id;
    private int shelf;
    private int row;
    private int collum;
    private int consignment_id;

    public StorageLocation() {
    }

    public StorageLocation(int warehouse_id, int shelf, int row, int collum, int consignment_id) {
        this.warehouse_id = warehouse_id;
        this.shelf = shelf;
        this.row = row;
        this.collum = collum;
        this.consignment_id = consignment_id;
    }

    public StorageLocation(int warehouse_id, int shelf, int row, int collum) {
        this.warehouse_id = warehouse_id;
        this.shelf = shelf;
        this.row = row;
        this.collum = collum;
    }

    public StorageLocation(int shelf, int row, int collum) {
        this.shelf = shelf;
        this.row = row;
        this.collum = collum;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCollum() {
        return collum;
    }

    public void setCollum(int collum) {
        this.collum = collum;
    }

    public int getConsignment_id() {
        return consignment_id;
    }

    public void setConsignment_id(int consignment_id) {
        this.consignment_id = consignment_id;
    }

    @Override
    public String toString() {
        return "StorageLocation{" + "warehouse_id=" + warehouse_id + ", shelf=" + shelf + ", row=" + row + ", collum=" + collum + ", consignment_id=" + consignment_id + '}';
    }

   

}
