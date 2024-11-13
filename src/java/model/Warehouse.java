/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 * @author 84941
 */
public class Warehouse {

    private int id;
    private String location;
    private int managerId;
    private int number_of_shelf;
    private int number_of_row_per_shelf;
    private int number_of_collum_per_shelf;
    
    private String name;
    
    private int numberOfShelf;
    private int numberOfRowPerShelf;
    private int numberOfColumnPerShelf;

    public Warehouse() {
    }

    public Warehouse(int id, String location, int managerId) {
        this.id = id;
        this.location = location;
        this.managerId = managerId;
    }

    public Warehouse(int id, String location, int managerId, int number_of_shelf, int number_of_row_per_shelf, int number_of_collum_per_shelf) {
        this.id = id;
        this.location = location;
        this.managerId = managerId;
        this.number_of_shelf = number_of_shelf;
        this.number_of_row_per_shelf = number_of_row_per_shelf;
        this.number_of_collum_per_shelf = number_of_collum_per_shelf;
    }

    public Warehouse(int id, String name, String location, int managerId, int numberOfShelf, int numberOfRowPerShelf, int numberOfColumnPerShelf) {
        this.id = id;
        this.location = location;
        this.managerId = managerId;
        this.name = name;
        this.numberOfShelf = numberOfShelf;
        this.numberOfRowPerShelf = numberOfRowPerShelf;
        this.numberOfColumnPerShelf = numberOfColumnPerShelf;
    }
    

    public int getNumber_of_shelf() {
        return number_of_shelf;
    }

    public void setNumber_of_shelf(int number_of_shelf) {
        this.number_of_shelf = number_of_shelf;
    }

    public int getNumber_of_row_per_shelf() {
        return number_of_row_per_shelf;
    }

    public void setNumber_of_row_per_shelf(int number_of_row_per_shelf) {
        this.number_of_row_per_shelf = number_of_row_per_shelf;
    }

    public int getNumber_of_collum_per_shelf() {
        return number_of_collum_per_shelf;
    }

    public void setNumber_of_collum_per_shelf(int number_of_collum_per_shelf) {
        this.number_of_collum_per_shelf = number_of_collum_per_shelf;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getNumberOfShelf() {
        return numberOfShelf;
    }

    public void setNumberOfShelf(int numberOfShelf) {
        this.numberOfShelf = numberOfShelf;
    }

    public int getNumberOfRowPerShelf() {
        return numberOfRowPerShelf;
    }

    public void setNumberOfRowPerShelf(int numberOfRowPerShelf) {
        this.numberOfRowPerShelf = numberOfRowPerShelf;
    }

    public int getNumberOfColumnPerShelf() {
        return numberOfColumnPerShelf;
    }

    public void setNumberOfColumnPerShelf(int numberOfColumnPerShelf) {
        this.numberOfColumnPerShelf = numberOfColumnPerShelf;
    }

}
