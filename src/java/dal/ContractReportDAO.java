package dal;

import model.ContractReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ContractReportDAO extends DBContext {

    // Phương thức để lấy tất cả các ContractReport với thông tin từ warehouse_report
    public Vector<ContractReport> getAllContractReports() {
        Vector<ContractReport> reports = new Vector<>();
        String sql = "SELECT cr.warehouse_report_id, cr.total_number_of_new_contract, cr.total_value_of_new_contract, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM contract_report cr "
                + "JOIN warehouse_report wr ON cr.warehouse_report_id = wr.id";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ContractReport report = new ContractReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewContract(rs.getInt("total_number_of_new_contract"));
                report.setTotalValueOfNewContract(rs.getInt("total_value_of_new_contract"));

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

    // Phương thức để xem chi tiết một ContractReport dựa trên warehouse_report_id với thông tin từ warehouse_report
    public ContractReport getContractReportById(int id) {
        String sql = "SELECT cr.warehouse_report_id, cr.total_number_of_new_contract, cr.total_value_of_new_contract, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM contract_report cr "
                + "JOIN warehouse_report wr ON cr.warehouse_report_id = wr.id "
                + "WHERE cr.warehouse_report_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                ContractReport report = new ContractReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewContract(rs.getInt("total_number_of_new_contract"));
                report.setTotalValueOfNewContract(rs.getInt("total_value_of_new_contract"));

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

    public Vector<ContractReport> getContractReportsByFilter(
            Integer warehouseReportId, Integer totalContractsMin, Integer totalContractsMax,
            Integer totalValueMin, Integer totalValueMax, Integer month, Integer year, String sortBy) {

        Vector<ContractReport> reports = new Vector<>();
        StringBuilder sql = new StringBuilder("SELECT cr.warehouse_report_id, cr.total_number_of_new_contract, cr.total_value_of_new_contract, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM contract_report cr "
                + "JOIN warehouse_report wr ON cr.warehouse_report_id = wr.id WHERE 1=1");

        // Điều kiện lọc
        if (warehouseReportId != null) {
            sql.append(" AND cr.warehouse_report_id = ?");
        }
        if (totalContractsMin != null) {
            sql.append(" AND cr.total_number_of_new_contract >= ?");
        }
        if (totalContractsMax != null) {
            sql.append(" AND cr.total_number_of_new_contract <= ?");
        }
        if (totalValueMin != null) {
            sql.append(" AND cr.total_value_of_new_contract >= ?");
        }
        if (totalValueMax != null) {
            sql.append(" AND cr.total_value_of_new_contract <= ?");
        }
        if (month != null) {
            sql.append(" AND wr.month = ?");
        }
        if (year != null) {
            sql.append(" AND wr.year = ?");
        }

        // Thêm phần sắp xếp
        if (sortBy != null) {
            switch (sortBy) {
                case "totalContracts":
                    sql.append(" ORDER BY cr.total_number_of_new_contract");
                    break;
                case "totalValue":
                    sql.append(" ORDER BY cr.total_value_of_new_contract");
                    break;
                default:
                    sql.append(" ORDER BY cr.warehouse_report_id");
                    break;
            }
        }

        try {
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (warehouseReportId != null) {
                pre.setInt(paramIndex++, warehouseReportId);
            }
            if (totalContractsMin != null) {
                pre.setInt(paramIndex++, totalContractsMin);
            }
            if (totalContractsMax != null) {
                pre.setInt(paramIndex++, totalContractsMax);
            }
            if (totalValueMin != null) {
                pre.setInt(paramIndex++, totalValueMin);
            }
            if (totalValueMax != null) {
                pre.setInt(paramIndex++, totalValueMax);
            }
            if (month != null) {
                pre.setInt(paramIndex++, month);
            }
            if (year != null) {
                pre.setInt(paramIndex++, year);
            }

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ContractReport report = new ContractReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewContract(rs.getInt("total_number_of_new_contract"));
                report.setTotalValueOfNewContract(rs.getInt("total_value_of_new_contract"));
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
        ContractReportDAO dao = new ContractReportDAO();
        System.out.println("Testing getAllContractReports method:");
        Vector<ContractReport> allReports = dao.getAllContractReports();
        for (ContractReport report : allReports) {
            System.out.println(report);
        }
        int testId = 1;
        System.out.println("\nTesting getContractReportById method with id = " + testId + ":");
        ContractReport report = dao.getContractReportById(testId);
        if (report != null) {
            System.out.println(report);
        } else {
            System.out.println("Contract Report with id = " + testId + " not found.");
        }
    }
}
