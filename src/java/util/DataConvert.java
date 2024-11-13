/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author 84941
 */
public class DataConvert {
    public java.sql.Date UtilDateToSqlDate(Date utilDate) {
        if (utilDate != null) {
            // Convert java.util.Date to java.sql.Date
            return new java.sql.Date(utilDate.getTime());
        } else {
            return null; // Handle null input
        }
    }
    
     public Date StringToUtilDate(String dateStr) throws ParseException  {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
        return formatter.parse(dateStr);
     }
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // 
        }

        return date;
    }
}

