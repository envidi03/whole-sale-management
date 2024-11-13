/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Unit;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;


/**
 *
 * @author Admin
 */
public class UnitDAO extends DBContext{
    
    public int addUnit(Unit unit){
        int n = 0;
        String sql = "INSERT INTO unit(name)"
                + "VALUE (?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, unit.getName());
            n = pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }
    
    public boolean checkUnitExist(int unitId){
        boolean exist = false;
        try {
            String sql = "SELECT COUNT(*) FROM unit WHERE id = ?";
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if(rs.next()){
                exist = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
        }
        return exist;
    }
    
      public Vector<Unit> getAllUnit() {
        Vector<Unit> unit = new Vector<Unit>();
        String sql = "SELECT * FROM unit";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Unit u = new Unit(id, name);
                unit.add(u);
            }
            rs.close();
            pre.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unit;

    }
}
