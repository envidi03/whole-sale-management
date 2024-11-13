/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class CustomerDAO extends DBContext {
    // search by name

    public List<Customer> search(String txtSearch, int index, int size) {
        List<Customer> list = new ArrayList<>();
        String sql = "      with x as  (SELECT \n"
                + "    ROW_NUMBER() OVER (ORDER BY c.id) as stt, \n"
                + "    c.*\n"
                + "FROM \n"
                + "    wholesalemanagement.customer c\n"
                + "WHERE \n"
                + "    c.name LIKE ?) select *from x where stt between ?*5-4 and ? * 5;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Customer cus = new Customer(id, customerName, phone, email, address, status);
                list.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // search and pagging
    public int count(String txtSearch) {
        String sql = "SELECT COUNT(*) FROM wholesalemanagement.customer WHERE name LIKE ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);

            }

        } catch (Exception e) {
        }
        return 0;
    }

//    public static void main(String[] args) {
//        CustomerDAO customerDAO = new CustomerDAO();
//        Customer cus = customerDAO.getCustomerById(1);
//        System.out.println(cus);
//
//    }
    // 
    public void updateCusNameById(String name, int id) {
        String sql = "update wholesalemanagement.customer c set c.name = ? where c.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(int customerId) {
        Customer cus = null;
        String sql = "select * from wholesalemanagement.customer where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                cus = new Customer(id, customerName, phone, email, address, status);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cus;
    }
}
