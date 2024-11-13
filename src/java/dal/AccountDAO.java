/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.sun.jdi.connect.spi.Connection;
import model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import util.DataConvert;
import util.PasswordEncryption;

/**
 *
 * @author 84941
 */
public class AccountDAO extends DBContext {

    DataConvert dc = new DataConvert();

    //insert new account into database. Attribute id is AUTO_INCREMENT 
    public int insertAccount(Account obj) {
        int n = 0;
        String sql = "INSERT INTO Account\n"
                + "           (email\n"
                + "           ,role_id\n"
                + "           ,password\n"
                + "           ,status\n"
                + "           ,created_date\n"
                + "           ,dob\n"
                + "           ,warehouse_id\n"
                + "           ,first_name\n"
                + "           ,last_name\n"
                + "           ,phone_number\n"
                + "           ,image\n"
                + "           ,account_type_id\n"
                + "           ,gender)\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getEmail());
            pre.setInt(2, obj.getRoleId());
            pre.setString(3, obj.getPassword());
            pre.setInt(4, obj.getStatus());
            pre.setDate(5, dc.UtilDateToSqlDate(obj.getCreatedDate()));
            pre.setDate(6, dc.UtilDateToSqlDate(obj.getDob()));
            pre.setInt(7, obj.getWarehouseId());
            pre.setString(8, obj.getFirstName());
            pre.setString(9, obj.getLastName());
            pre.setString(10, obj.getPhoneNumber());
            pre.setString(11, obj.getImage());
            pre.setInt(12, obj.getAccountTypeId());
            pre.setBoolean(13, obj.isGender());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;

    }

    // get an account list based on specific conditions
    public Vector<Account> getAccounts(String sql) {
        Vector<Account> vector = new Vector<Account>();
        try {
            Statement state = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                int roleId = rs.getInt(3);
                String password = rs.getString(4);
                int status = rs.getInt(5);
                Date createdDate = rs.getDate(6);
                Date dob = rs.getDate(7);
                int warehouseId = rs.getInt(8);
                String firstName = rs.getString(9);
                String lastName = rs.getString(10);
                String phoneNumber = rs.getString(11);
                String image = rs.getString(12);
                int accountTypeId = rs.getInt(13);
                boolean gender = rs.getBoolean(14);
                Account obj = new Account(id, email, roleId, password, status, createdDate, dob, warehouseId, firstName, lastName, phoneNumber, image, accountTypeId, gender);
                vector.add(obj);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;

    }

    // get all the accounts in the database except the admin account
    public Vector<Account> getAll() {
        Vector<Account> vector = new Vector<Account>();
        String sql = "SELECT id\n"
                + "      ,email\n"
                + "      ,role_id\n"
                + "      ,password\n"
                + "      ,status\n"
                + "      ,created_date\n"
                + "      ,dob\n"
                + "      ,warehouse_id\n"
                + "      ,first_name\n"
                + "      ,last_name\n"
                + "      ,phone_number\n"
                + "      ,image\n"
                + "      ,account_type_id\n"
                + "      ,gender\n"
                + "  FROM account"
                + "  WHERE role_id != ( select id from role where title like 'admin') ";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                int roleId = rs.getInt(3);
                String password = rs.getString(4);
                int status = rs.getInt(5);
                Date createdDate = rs.getDate(6);
                Date dob = rs.getDate(7);
                int warehouse_id = rs.getInt(8);
                String firstName = rs.getString(9);
                String lastName = rs.getString(10);
                String phoneNumber = rs.getString(11);
                String image = rs.getString(12);
                int accountTypeId = rs.getInt(13);
                boolean gender = rs.getBoolean(14);
                Account obj = new Account(id, email, roleId, password, status, createdDate, dob, warehouse_id, firstName, lastName, phoneNumber, image, accountTypeId, gender);
                vector.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    // update account information based on id
    public int updateAccount(Account obj) {
        int n = 0;
        String sql = "UPDATE Account\n"
                + "   SET email = ?\n"
                + "      ,role_id = ?\n"
                + "      ,password = ?\n"
                + "      ,status = ?\n"
                + "      ,created_date = ?\n"
                + "      ,dob = ?\n"
                + "      ,warehouse_id = ?\n"
                + "      ,first_name = ?\n"
                + "      ,last_name = ?\n"
                + "      ,phone_number = ?\n"
                + "      ,image = ?\n"
                + "      ,account_type_id = ?\n"
                + "      ,gender = ?\n"
                + " WHERE id = ?";
        try {

            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getEmail());
            pre.setInt(2, obj.getRoleId());
            pre.setString(3, obj.getPassword());
            pre.setInt(4, obj.getStatus());
            pre.setDate(5, dc.UtilDateToSqlDate(obj.getCreatedDate()));
            pre.setDate(6, dc.UtilDateToSqlDate(obj.getDob()));
            pre.setInt(7, obj.getWarehouseId());
            pre.setString(8, obj.getFirstName());
            pre.setString(9, obj.getLastName());
            pre.setString(10, obj.getPhoneNumber());
            pre.setString(11, obj.getImage());
            pre.setInt(12, obj.getAccountTypeId());
            pre.setBoolean(13, obj.isGender());
            pre.setInt(14, obj.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    //get account type by id
    public Account getAccountById(int search_id) {
        Vector<Account> vector = new Vector<>();
        String sql = "SELECT id\n"
                + "      ,email\n"
                + "      ,role_id\n"
                + "      ,password\n"
                + "      ,status\n"
                + "      ,created_date\n"
                + "      ,dob\n"
                + "      ,warehouse_id\n"
                + "      ,first_name\n"
                + "      ,last_name\n"
                + "      ,phone_number\n"
                + "      ,image\n"
                + "      ,account_type_id\n"
                + "      ,gender\n"
                + "  FROM account"
                + "  WHERE id = " + search_id;

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                int roleId = rs.getInt(3);
                String password = rs.getString(4);
                int status = rs.getInt(5);
                Date createdDate = rs.getDate(6);
                Date dob = rs.getDate(7);
                int warehouse_id = rs.getInt(8);
                String firstName = rs.getString(9);
                String lastName = rs.getString(10);
                String phoneNumber = rs.getString(11);
                String image = rs.getString(12);
                int accountTypeId = rs.getInt(13);
                boolean gender = rs.getBoolean(14);
                Account obj = new Account(id, email, roleId, password, status, createdDate, dob, warehouse_id, firstName, lastName, phoneNumber, image, accountTypeId, gender);
                return obj;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public Account getAccountByEmail(String search_email) {
        Vector<Account> vector = new Vector<>();
        String sql = "SELECT id\n"
                + "      ,email\n"
                + "      ,role_id\n"
                + "      ,password\n"
                + "      ,status\n"
                + "      ,created_date\n"
                + "      ,dob\n"
                + "      ,warehouse_id\n"
                + "      ,first_name\n"
                + "      ,last_name\n"
                + "      ,phone_number\n"
                + "      ,image\n"
                + "      ,account_type_id\n"
                + "      ,gender\n"
                + "  FROM account"
                + "  WHERE email = " + search_email;

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                int roleId = rs.getInt(3);
                String password = rs.getString(4);
                int status = rs.getInt(5);
                Date createdDate = rs.getDate(6);
                Date dob = rs.getDate(7);
                int warehouse_id = rs.getInt(8);
                String firstName = rs.getString(9);
                String lastName = rs.getString(10);
                String phoneNumber = rs.getString(11);
                String image = rs.getString(12);
                int accountTypeId = rs.getInt(13);
                boolean gender = rs.getBoolean(14);
                Account obj = new Account(id, email, roleId, password, status, createdDate, dob, warehouse_id, firstName, lastName, phoneNumber, image, accountTypeId, gender);
                return obj;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public String getEmployeeNameById(int employeeId) {
        String name = "";
        String sql = "SELECT first_name, last_name FROM account WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return name;
    }

    // Phương thức để thêm tài khoản mới vào cơ sở dữ liệu
    public boolean addAccount(Account account) {
        String sql = "INSERT INTO account (email, role_id, password, status, created_date, dob, warehouse_id, "
                + "first_name, last_name, phone_number, image, account_type_id, gender) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, account.getEmail());
            ps.setInt(2, account.getRoleId());
            ps.setString(3, account.getPassword());  // Thiết lập mật khẩu mặc định hoặc mã hóa trước khi thêm
            ps.setInt(4, account.getStatus());
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));  // Ngày tạo hiện tại
            ps.setDate(6, account.getDob() != null ? new java.sql.Date(account.getDob().getTime()) : null);
            ps.setInt(7, account.getWarehouseId());
            ps.setString(8, account.getFirstName());
            ps.setString(9, account.getLastName());
            ps.setString(10, account.getPhoneNumber());
            ps.setString(11, account.getImage());
            ps.setInt(12, account.getAccountTypeId());
            ps.setBoolean(13, account.isGender());

            return ps.executeUpdate() > 0;  // Trả về true nếu thêm thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi
        }
    }

//    public static void main(String[] args) {
//        // Tạo một đối tượng AccountDAO
//        AccountDAO accountDAO = new AccountDAO();
//
//        // Đặt ID của nhân viên bạn muốn kiểm tra
//        int employeeId = 1; // Bạn có thể thay đổi ID này thành một ID hợp lệ trong cơ sở dữ liệu của bạn
//
//        // Gọi phương thức để lấy tên nhân viên
//        String employeeName = accountDAO.getEmployeeNameById(employeeId);
//
//        // Kiểm tra và in ra kết quả
//        if (employeeName != null && !employeeName.isEmpty()) {
//            System.out.println("Tên nhân viên với ID " + employeeId + ": " + employeeName);
//        } else {
//            System.out.println("Không tìm thấy nhân viên với ID " + employeeId);
//        }
//    }

//    public static void main(String[] args) {
//        // Tạo một đối tượng AccountDAO
//        AccountDAO accountDAO = new AccountDAO();
//        
//        // Đặt ID của nhân viên bạn muốn kiểm tra
//        int employeeId = 1; // Bạn có thể thay đổi ID này thành một ID hợp lệ trong cơ sở dữ liệu của bạn
//
//        // Gọi phương thức để lấy tên nhân viên
//        String employeeName = accountDAO.getEmployeeNameById(employeeId);
//
//        // Kiểm tra và in ra kết quả
//        if (employeeName != null && !employeeName.isEmpty()) {
//            System.out.println("Tên nhân viên với ID " + employeeId + ": " + employeeName);
//        } else {
//            System.out.println("Không tìm thấy nhân viên với ID " + employeeId);
//        }
//    }

    public static void main(String[] args) {
        AccountDAO accountDAO = new AccountDAO();
        //String password = PasswordEncryption.EncryptBySHA256("123");
        Vector<Account> vector = accountDAO.getAccounts("select*from account where id =5");
        Account account = vector.get(0);
        account.setStatus(2);
        int result=accountDAO.updateAccount(account);
        
        System.out.println(result);
    }
}
