/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 84941
 */
public class GetTodayDate {

    public static java.sql.Date getTodayDateForMySQL() {
        // Get an instance of Calendar and set it to the current date
        Calendar cal = Calendar.getInstance();

        // Reset the time part of the calendar to zero (midnight)
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Convert util.Date to sql.Date
        Date utilDate = cal.getTime();
        return new java.sql.Date(utilDate.getTime());
    }

    public static Date getTodayDate() {
        LocalDate localDate = LocalDate.now();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(localDate.toString());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        java.sql.Date todayDate = getTodayDateForMySQL();
//        System.out.println("Today's date for MySQL: " + todayDate);
//    }
}
