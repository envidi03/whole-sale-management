package dal;

import model.FiredEmployee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FiredEmployeeDAO extends DBContext {

    // Phương thức để lấy danh sách nhân viên đã nghỉ việc dựa trên warehouseReportId
    public List<FiredEmployee> getFiredEmployeesByReportId(int warehouseReportId) {
        List<FiredEmployee> firedEmployees = new ArrayList<>();
        String sql = "SELECT "
                + "a.id AS employee_id, "
                + "a.first_name, "
                + "a.last_name, "
                + "a.email, "
                + "a.phone_number, "
                + "a.role_id, "
                + "r.title AS role_title, "
                + "a.dob AS date_of_birth, "
                + "a.gender, "
                + "w.name AS warehouse_name, "
                + "fr.warehouse_report_id AS fired_employee_report_id, "
                + "wr.month, "
                + "wr.year, "
                + "fr.total_number_of_new_fired_employee AS total_number_of_new_fired_employee "
                + "FROM account a "
                + "JOIN fired_employee_report fr ON a.fired_employee_report_id = fr.warehouse_report_id "
                + "JOIN warehouse_report wr ON fr.warehouse_report_id = wr.id "
                + "JOIN warehouse w ON wr.warehouse_id = w.id "
                + "JOIN role r ON a.role_id = r.id "
                + "WHERE a.fired_employee_report_id IS NOT NULL AND fr.warehouse_report_id = ?";

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, warehouseReportId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                FiredEmployee firedEmployee = new FiredEmployee();
                firedEmployee.setId(rs.getInt("employee_id"));
                firedEmployee.setFirstName(rs.getString("first_name"));
                firedEmployee.setLastName(rs.getString("last_name"));
                firedEmployee.setEmail(rs.getString("email"));
                firedEmployee.setPhoneNumber(rs.getString("phone_number"));
                firedEmployee.setRoleId(rs.getInt("role_id"));
                firedEmployee.setRoleTitle(rs.getString("role_title"));
                firedEmployee.setDob(rs.getDate("date_of_birth"));
                firedEmployee.setGender(rs.getInt("gender"));
                firedEmployee.setWarehouseName(rs.getString("warehouse_name"));
                firedEmployee.setWarehouseReportId(rs.getInt("fired_employee_report_id"));
                firedEmployee.setMonth(rs.getInt("month"));
                firedEmployee.setYear(rs.getInt("year"));
                // Sử dụng totalNumberOfNewFiredEmployee thay vì totalFiredEmployees
                firedEmployee.setTotalNumberOfNewFiredEmployee(rs.getInt("total_number_of_new_fired_employee"));

                firedEmployees.add(firedEmployee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return firedEmployees;
    }

    public List<FiredEmployee> searchFiredEmployees(Integer id, String firstName, String lastName, String email,
            String phoneNumber, Integer roleId, String roleTitle, Date dob,
            Integer gender, Integer warehouseId, String warehouseName,
            Integer warehouseReportId, Integer month, Integer year,
            Integer totalNumberOfNewFiredEmployee, Date terminationDate,
            String reason, String department, String status) {
        List<FiredEmployee> firedEmployees = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT "
                + "a.id AS employee_id, "
                + "a.first_name, "
                + "a.last_name, "
                + "a.email, "
                + "a.phone_number, "
                + "a.role_id, "
                + "r.title AS role_title, "
                + "a.dob AS date_of_birth, "
                + "a.gender, "
                + "w.name AS warehouse_name, "
                + "fr.warehouse_report_id AS fired_employee_report_id, "
                + "wr.month, "
                + "wr.year, "
                + "fr.total_number_of_new_fired_employee AS total_number_of_new_fired_employee, "
                + "fr.termination_date, "
                + "fr.reason, "
                + "fr.department, "
                + "fr.status "
                + "FROM account a "
                + "JOIN fired_employee_report fr ON a.fired_employee_report_id = fr.warehouse_report_id "
                + "JOIN warehouse_report wr ON fr.warehouse_report_id = wr.id "
                + "JOIN warehouse w ON wr.warehouse_id = w.id "
                + "JOIN role r ON a.role_id = r.id "
                + "WHERE a.fired_employee_report_id IS NOT NULL");

        // Xây dựng truy vấn SQL động dựa trên các tham số không null
        if (id != null) {
            sql.append(" AND a.id = ?");
        }
        if (firstName != null && !firstName.isEmpty()) {
            sql.append(" AND a.first_name LIKE ?");
        }
        if (lastName != null && !lastName.isEmpty()) {
            sql.append(" AND a.last_name LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND a.email LIKE ?");
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            sql.append(" AND a.phone_number LIKE ?");
        }
        if (roleId != null) {
            sql.append(" AND a.role_id = ?");
        }
        if (roleTitle != null && !roleTitle.isEmpty()) {
            sql.append(" AND r.title LIKE ?");
        }
        if (dob != null) {
            sql.append(" AND a.dob = ?");
        }
        if (gender != null) {
            sql.append(" AND a.gender = ?");
        }
        if (warehouseId != null) {
            sql.append(" AND w.id = ?");
        }
        if (warehouseName != null && !warehouseName.isEmpty()) {
            sql.append(" AND w.name LIKE ?");
        }
        if (warehouseReportId != null) {
            sql.append(" AND fr.warehouse_report_id = ?");
        }
        if (month != null) {
            sql.append(" AND wr.month = ?");
        }
        if (year != null) {
            sql.append(" AND wr.year = ?");
        }
        if (totalNumberOfNewFiredEmployee != null) {
            sql.append(" AND fr.total_number_of_new_fired_employee = ?");
        }
        if (terminationDate != null) {
            sql.append(" AND fr.termination_date = ?");
        }
        if (reason != null && !reason.isEmpty()) {
            sql.append(" AND fr.reason LIKE ?");
        }
        if (department != null && !department.isEmpty()) {
            sql.append(" AND fr.department LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND fr.status LIKE ?");
        }

        try (PreparedStatement pre = connection.prepareStatement(sql.toString())) {
            int index = 1;

            // Gán giá trị cho các tham số
            if (id != null) {
                pre.setInt(index++, id);
            }
            if (firstName != null && !firstName.isEmpty()) {
                pre.setString(index++, "%" + firstName + "%");
            }
            if (lastName != null && !lastName.isEmpty()) {
                pre.setString(index++, "%" + lastName + "%");
            }
            if (email != null && !email.isEmpty()) {
                pre.setString(index++, "%" + email + "%");
            }
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                pre.setString(index++, "%" + phoneNumber + "%");
            }
            if (roleId != null) {
                pre.setInt(index++, roleId);
            }
            if (roleTitle != null && !roleTitle.isEmpty()) {
                pre.setString(index++, "%" + roleTitle + "%");
            }
            if (dob != null) {
                pre.setDate(index++, new java.sql.Date(dob.getTime()));
            }
            if (gender != null) {
                pre.setInt(index++, gender);
            }
            if (warehouseId != null) {
                pre.setInt(index++, warehouseId);
            }
            if (warehouseName != null && !warehouseName.isEmpty()) {
                pre.setString(index++, "%" + warehouseName + "%");
            }
            if (warehouseReportId != null) {
                pre.setInt(index++, warehouseReportId);
            }
            if (month != null) {
                pre.setInt(index++, month);
            }
            if (year != null) {
                pre.setInt(index++, year);
            }
            if (totalNumberOfNewFiredEmployee != null) {
                pre.setInt(index++, totalNumberOfNewFiredEmployee);
            }
            if (terminationDate != null) {
                pre.setDate(index++, new java.sql.Date(terminationDate.getTime()));
            }
            if (reason != null && !reason.isEmpty()) {
                pre.setString(index++, "%" + reason + "%");
            }
            if (department != null && !department.isEmpty()) {
                pre.setString(index++, "%" + department + "%");
            }
            if (status != null && !status.isEmpty()) {
                pre.setString(index++, "%" + status + "%");
            }

            ResultSet rs = pre.executeQuery();

            // Xử lý kết quả truy vấn
            while (rs.next()) {
                FiredEmployee firedEmployee = new FiredEmployee();
                firedEmployee.setId(rs.getInt("employee_id"));
                firedEmployee.setFirstName(rs.getString("first_name"));
                firedEmployee.setLastName(rs.getString("last_name"));
                firedEmployee.setEmail(rs.getString("email"));
                firedEmployee.setPhoneNumber(rs.getString("phone_number"));
                firedEmployee.setRoleId(rs.getInt("role_id"));
                firedEmployee.setRoleTitle(rs.getString("role_title"));
                firedEmployee.setDob(rs.getDate("date_of_birth"));
                firedEmployee.setGender(rs.getInt("gender"));
                firedEmployee.setWarehouseName(rs.getString("warehouse_name"));
                firedEmployee.setWarehouseReportId(rs.getInt("fired_employee_report_id"));
                firedEmployee.setMonth(rs.getInt("month"));
                firedEmployee.setYear(rs.getInt("year"));
                firedEmployee.setTotalNumberOfNewFiredEmployee(rs.getInt("total_number_of_new_fired_employee"));
                firedEmployee.setTerminationDate(rs.getDate("termination_date"));
                firedEmployee.setReason(rs.getString("reason"));
                firedEmployee.setDepartment(rs.getString("department"));
                firedEmployee.setStatus(rs.getString("status"));

                firedEmployees.add(firedEmployee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return firedEmployees;
    }

    // Hàm main để kiểm tra phương thức getFiredEmployeesByReportId
    public static void main(String[] args) {
        FiredEmployeeDAO firedEmployeeDAO = new FiredEmployeeDAO();
        int warehouseReportId = 1; // Thay đổi giá trị này để kiểm tra với các warehouseReportId khác

        List<FiredEmployee> firedEmployees = firedEmployeeDAO.getFiredEmployeesByReportId(warehouseReportId);
        System.out.println("Danh sách nhân viên đã nghỉ việc cho warehouseReportId = " + warehouseReportId + ":");
        for (FiredEmployee firedEmployee : firedEmployees) {
            System.out.println("ID: " + firedEmployee.getId());
            System.out.println("Tên: " + firedEmployee.getFirstName() + " " + firedEmployee.getLastName());
            System.out.println("Email: " + firedEmployee.getEmail());
            System.out.println("Số điện thoại: " + firedEmployee.getPhoneNumber());
            System.out.println("Vai trò: " + firedEmployee.getRoleTitle());
            System.out.println("Ngày sinh: " + firedEmployee.getDob());
            System.out.println("Giới tính: " + firedEmployee.getGender());
            System.out.println("Kho: " + firedEmployee.getWarehouseName());
            System.out.println("Tháng: " + firedEmployee.getMonth());
            System.out.println("Năm: " + firedEmployee.getYear());
            System.out.println("Tổng số nhân viên bị sa thải: " + firedEmployee.getTotalNumberOfNewFiredEmployee());
            System.out.println("-----");
        }

        if (firedEmployees.isEmpty()) {
            System.out.println("Không có nhân viên nào đã nghỉ việc cho warehouseReportId = " + warehouseReportId);
        }
    }
}
