/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.Supplier;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author Admin
 */
public class SupplierDAO extends DBContext {

    public Vector<Supplier> getSupplierByName(String search_name) {
        Vector<Supplier> vector = new Vector<>();
        String sql = "SELECT id, name, phone, address, status FROM supplier WHERE name = '" + search_name + "'";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String supplierName = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Supplier sup = new Supplier(id, supplierName, phone, address, status);
                vector.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vector;
    }

    public Vector<Supplier> getAllSupplier() {
        Vector<Supplier> vector = new Vector<>();
        String sql = "SELECT id, name, phone, address, status FROM supplier";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String supplierName = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int status = rs.getInt("status");
                Supplier sup = new Supplier(id, supplierName, phone, address, status);
                vector.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vector;
    }

    public int addSupplier(Supplier supplier) {
        int n = 0;
        String sql = "INSERT INTO supplier (name, phone, address, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, supplier.getSupplierName());
            pre.setString(2, supplier.getPhone());
            pre.setString(3, supplier.getAddress());
            pre.setInt(4, supplier.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public static void main(String[] args) {
        // Tạo một đối tượng SupplierDAO
        SupplierDAO supplierDAO = new SupplierDAO();

        // Gọi phương thức getSupplierByName với một giá trị để tìm kiếm
        String searchName = "Envidi"; // Thay đổi tên này thành tên bạn muốn tìm kiếm
//        Vector<Supplier> suppliers = supplierDAO.getAllSupplier();
        Vector<Supplier> suppliers = supplierDAO.getSupplierByName(searchName);

        // Kiểm tra và hiển thị kết quả
        if (suppliers.isEmpty()) {
            System.out.println("Không tìm thấy nhà cung cấp nào với tên: " + searchName);
        } else {
            System.out.println("Danh sách nhà cung cấp tìm thấy:");
            for (Supplier supplier : suppliers) {
                System.out.println("ID: " + supplier.getId());
                System.out.println("Tên: " + supplier.getSupplierName());
                System.out.println("SĐT: " + supplier.getPhone());
                System.out.println("Địa chỉ: " + supplier.getAddress());
                System.out.println("Trạng thái: " + supplier.getStatus());
                System.out.println("--------------");
            }
        }
    }
}
