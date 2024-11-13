/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Acer
 */
public class EmployeeDAO extends DBContext {

    public Account getAccount(String email, String password, int roleId) {

        String sql = "SELECT *FROM wholesalemanagement.account WHERE email = ? AND password = ? AND role_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            st.setInt(3, roleId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13),
                        rs.getBoolean(14));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

//    public static void main(String[] args) {
//        EmployeeDAO employeeDAO = new EmployeeDAO();
//        String email = "hoangplam03@gmail.com";
//        String pass = "123456";
//        int roleId = 1;
//        Account account = employeeDAO.getAccount(email, pass, roleId);
//        if (account != null) {
//            System.out.println("Tìm thấy tài khoản: ");
//            System.out.println("ID: " + account.getId());
//            System.out.println("Email: " + account.getEmail());
//            System.out.println("Role ID: " + account.getRoleId());
//            // In ra thêm thông tin nếu cần
//        } else {
//            System.out.println("Không tìm thấy tài khoản.");
//        }
//    }
}
