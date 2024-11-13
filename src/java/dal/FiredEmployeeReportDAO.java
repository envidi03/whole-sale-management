package dal;

import model.FiredEmployeeReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class FiredEmployeeReportDAO extends DBContext {

    // Phương thức để lấy tất cả các FiredEmployeeReport với thông tin từ warehouse_report
    public Vector<FiredEmployeeReport> getAllFiredEmployeeReports() {
        Vector<FiredEmployeeReport> reports = new Vector<>();
        String sql = "SELECT fer.warehouse_report_id, fer.total_number_of_new_fired_employee, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM fired_employee_report fer "
                + "JOIN warehouse_report wr ON fer.warehouse_report_id = wr.id";
        try {
            Statement state = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                FiredEmployeeReport report = new FiredEmployeeReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewFiredEmployee(rs.getInt("total_number_of_new_fired_employee"));

                // Lấy thông tin từ bảng warehouse_report
                report.setMonth(rs.getInt("month"));
                report.setYear(rs.getInt("year"));
                report.setReportType(rs.getInt("report_type"));

                reports.add(report);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reports;
    }

    // Phương thức để xem chi tiết một FiredEmployeeReport dựa trên warehouse_report_id
    public FiredEmployeeReport getFiredEmployeeReportById(int id) {
        String sql = "SELECT fer.warehouse_report_id, fer.total_number_of_new_fired_employee, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM fired_employee_report fer "
                + "JOIN warehouse_report wr ON fer.warehouse_report_id = wr.id "
                + "WHERE fer.warehouse_report_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                FiredEmployeeReport report = new FiredEmployeeReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewFiredEmployee(rs.getInt("total_number_of_new_fired_employee"));

                // Lấy thông tin từ bảng warehouse_report
                report.setMonth(rs.getInt("month"));
                report.setYear(rs.getInt("year"));
                report.setReportType(rs.getInt("report_type"));

                return report;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Trong FiredEmployeeReportDAO.java
    public Vector<FiredEmployeeReport> getFiredEmployeeReportsByFilter(
            Integer warehouseReportId, Integer totalFiredEmployeesMin, Integer totalFiredEmployeesMax,
            Integer month, Integer year, String sortBy) {

        Vector<FiredEmployeeReport> reports = new Vector<>();
        StringBuilder sql = new StringBuilder("SELECT fer.warehouse_report_id, fer.total_number_of_new_fired_employee, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM fired_employee_report fer "
                + "JOIN warehouse_report wr ON fer.warehouse_report_id = wr.id WHERE 1=1");

        if (warehouseReportId != null) {
            sql.append(" AND fer.warehouse_report_id = ?");
        }
        if (totalFiredEmployeesMin != null) {
            sql.append(" AND fer.total_number_of_new_fired_employee >= ?");
        }
        if (totalFiredEmployeesMax != null) {
            sql.append(" AND fer.total_number_of_new_fired_employee <= ?");
        }
        if (month != null) {
            sql.append(" AND wr.month = ?");
        }
        if (year != null) {
            sql.append(" AND wr.year = ?");
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "totalFiredEmployees":
                    sql.append(" ORDER BY fer.total_number_of_new_fired_employee");
                    break;
                default:
                    sql.append(" ORDER BY fer.warehouse_report_id");
                    break;
            }
        }

        try {
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (warehouseReportId != null) {
                pre.setInt(paramIndex++, warehouseReportId);
            }
            if (totalFiredEmployeesMin != null) {
                pre.setInt(paramIndex++, totalFiredEmployeesMin);
            }
            if (totalFiredEmployeesMax != null) {
                pre.setInt(paramIndex++, totalFiredEmployeesMax);
            }
            if (month != null) {
                pre.setInt(paramIndex++, month);
            }
            if (year != null) {
                pre.setInt(paramIndex++, year);
            }

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                FiredEmployeeReport report = new FiredEmployeeReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewFiredEmployee(rs.getInt("total_number_of_new_fired_employee"));
                report.setMonth(rs.getInt("month"));
                report.setYear(rs.getInt("year"));
                report.setReportType(rs.getInt("report_type"));

                reports.add(report);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reports;
    }

    // Hàm main để kiểm tra các phương thức
    public static void main(String[] args) {
        FiredEmployeeReportDAO dao = new FiredEmployeeReportDAO();

        // Kiểm tra phương thức getAllFiredEmployeeReports
        System.out.println("Testing getAllFiredEmployeeReports method:");
        Vector<FiredEmployeeReport> allReports = dao.getAllFiredEmployeeReports();
        for (FiredEmployeeReport report : allReports) {
            System.out.println(report);
        }

        // Kiểm tra phương thức getFiredEmployeeReportById với một id cụ thể
        int testId = 1;
        System.out.println("\nTesting getFiredEmployeeReportById method with id = " + testId + ":");
        FiredEmployeeReport report = dao.getFiredEmployeeReportById(testId);
        if (report != null) {
            System.out.println(report);
        } else {
            System.out.println("Fired Employee Report with id = " + testId + " not found.");
        }
    }
}
