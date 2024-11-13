/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Account {

    private int id;
    private String email;
    private int roleId;
    private String password;
    private int status; //0-deactivated, 1-activated, 2-default 
    private Date createdDate;
    private Date dob;
    private int warehouseId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String image;
    private int accountTypeId;
    private boolean gender;
    private int firedEmployeeReportId;

    public Account() {
    }

    public Account(int id, String email, int roleId, String password, int status, Date createdDate, Date dob, int warehouseId, String firstName, String lastName, String phoneNumber, String image, int accountTypeId, boolean gender) {
        this.id = id;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
        this.status = status;
        this.createdDate = createdDate;
        this.dob = dob;
        this.warehouseId = warehouseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.accountTypeId = accountTypeId;
        this.gender = gender;
    }

    public Account(int id, String email, int roleId, String password, int status, Date createdDate, Date dob, int warehouseId, String firstName, String lastName, String phoneNumber, String image, int accountTypeId, boolean gender, int firedEmployeeReportId) {
        this.id = id;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
        this.status = status;
        this.createdDate = createdDate;
        this.dob = dob;
        this.warehouseId = warehouseId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.accountTypeId = accountTypeId;
        this.gender = gender;
        this.firedEmployeeReportId = firedEmployeeReportId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getFiredEmployeeReportId() {
        return firedEmployeeReportId;
    }

    public void setFiredEmployeeReportId(int firedEmployeeReportId) {
        this.firedEmployeeReportId = firedEmployeeReportId;
    }

}
