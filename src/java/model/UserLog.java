/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author 84941
 */
public class Userlog {
    private int id;
    private int accountId;
    Date date;
    boolean userLogType;

    public Userlog() {
    }

    public Userlog(int id, int accountId, Date date, boolean userLogType) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.userLogType = userLogType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isUserLogType() {
        return userLogType;
    }

    public void setUserLogType(boolean userLogType) {
        this.userLogType = userLogType;
    }
    
    
}
