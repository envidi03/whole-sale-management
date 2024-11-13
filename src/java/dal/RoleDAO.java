/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Role;

/**
 *
 * @author 84941
 */
public class RoleDAO extends DBContext {

    //insert new role into database. Attribute id is AUTO_INCREMENT 
    public int insertRole(Role obj) {
        int n = 0;
        String sql = "INSERT INTO role\n"
                + "           (title)\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getTitle());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;

    }

    // get role list based on specific conditions
    public Vector<Role> getRoles(String sql) {
        Vector<Role> vector = new Vector<Role>();
        try {
            Statement state = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                Role obj = new Role(id, title);
                vector.add(obj);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;

    }
    //select all the role
    public Vector<Role> getAll() {
        Vector<Role> vector = new Vector<Role>();
        String sql = "SELECT id\n"
                + "      ,title\n"
                + "  FROM role ";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                Role obj = new Role(id, title);
                vector.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }
    //update role base on id
    public int updateRole(Role obj) {
        int n = 0;
        String sql = "UPDATE role\n"
                + "   SET title = ?\n"
                + " WHERE id = ?";
        try {

            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getTitle());
            pre.setInt(2, obj.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    //get role by title
    public Role getRoleByTitle(String search_title) {
        Vector<Role> vector = new Vector<Role>();
        String sql = "SELECT id\n"
                + "      ,title\n"
                + "  FROM role"
                + "  WHERE title like '" + search_title +"'";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                Role obj = new Role(id, title);
                return obj;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }
    public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        Role role = dao.getRoleByTitle("System Admin");
        System.out.println(role.getId());
        
    }
    
}
