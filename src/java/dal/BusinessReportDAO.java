package dal;

import model.BusinessReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessReportDAO extends DBContext {

    // Phương thức lấy tất cả các báo cáo kinh doanh
    public List<BusinessReport> getAllBusinessReports() {
        List<BusinessReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM business_report";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                BusinessReport report = new BusinessReport(
                        rs.getInt("id"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("total_number_of_new_success_order"),
                        rs.getInt("total_value_of_new_success_order"),
                        rs.getInt("total_number_of_new_overdue_debt_order"),
                        rs.getInt("total_value_of_new_overdue_debt_order"),
                        rs.getInt("total_number_of_new_indebt_order"),
                        rs.getInt("total_value_of_new_indebt_order"),
                        rs.getInt("total_number_of_new_contract"),
                        rs.getInt("total_value_of_new_contract"),
                        rs.getInt("total_number_of_new_fired_employee")
                );
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    // Phương thức lấy báo cáo kinh doanh theo ID
    public BusinessReport getBusinessReportById(int id) {
        String sql = "SELECT * FROM business_report WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BusinessReport(
                        rs.getInt("id"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("total_number_of_new_success_order"),
                        rs.getInt("total_value_of_new_success_order"),
                        rs.getInt("total_number_of_new_overdue_debt_order"),
                        rs.getInt("total_value_of_new_overdue_debt_order"),
                        rs.getInt("total_number_of_new_indebt_order"),
                        rs.getInt("total_value_of_new_indebt_order"),
                        rs.getInt("total_number_of_new_contract"),
                        rs.getInt("total_value_of_new_contract"),
                        rs.getInt("total_number_of_new_fired_employee")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức thêm báo cáo kinh doanh mới
    public boolean addBusinessReport(BusinessReport report) {
        String sql = "INSERT INTO business_report (month, year, total_number_of_new_success_order, total_value_of_new_success_order, "
                + "total_number_of_new_overdue_debt_order, total_value_of_new_overdue_debt_order, total_number_of_new_indebt_order, "
                + "total_value_of_new_indebt_order, total_number_of_new_contract, total_value_of_new_contract, total_number_of_new_fired_employee) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, report.getMonth());
            stmt.setInt(2, report.getYear());
            stmt.setInt(3, report.getTotalNumberOfNewSuccessOrder());
            stmt.setInt(4, report.getTotalValueOfNewSuccessOrder());
            stmt.setInt(5, report.getTotalNumberOfNewOverdueDebtOrder());
            stmt.setInt(6, report.getTotalValueOfNewOverdueDebtOrder());
            stmt.setInt(7, report.getTotalNumberOfNewIndebtOrder());
            stmt.setInt(8, report.getTotalValueOfNewIndebtOrder());
            stmt.setInt(9, report.getTotalNumberOfNewContract());
            stmt.setInt(10, report.getTotalValueOfNewContract());
            stmt.setInt(11, report.getTotalNumberOfNewFiredEmployee());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức cập nhật báo cáo kinh doanh
    public boolean updateBusinessReport(BusinessReport report) {
        String sql = "UPDATE business_report SET month = ?, year = ?, total_number_of_new_success_order = ?, total_value_of_new_success_order = ?, "
                + "total_number_of_new_overdue_debt_order = ?, total_value_of_new_overdue_debt_order = ?, total_number_of_new_indebt_order = ?, "
                + "total_value_of_new_indebt_order = ?, total_number_of_new_contract = ?, total_value_of_new_contract = ?, total_number_of_new_fired_employee = ? "
                + "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, report.getMonth());
            stmt.setInt(2, report.getYear());
            stmt.setInt(3, report.getTotalNumberOfNewSuccessOrder());
            stmt.setInt(4, report.getTotalValueOfNewSuccessOrder());
            stmt.setInt(5, report.getTotalNumberOfNewOverdueDebtOrder());
            stmt.setInt(6, report.getTotalValueOfNewOverdueDebtOrder());
            stmt.setInt(7, report.getTotalNumberOfNewIndebtOrder());
            stmt.setInt(8, report.getTotalValueOfNewIndebtOrder());
            stmt.setInt(9, report.getTotalNumberOfNewContract());
            stmt.setInt(10, report.getTotalValueOfNewContract());
            stmt.setInt(11, report.getTotalNumberOfNewFiredEmployee());
            stmt.setInt(12, report.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức xóa báo cáo kinh doanh theo ID
    public boolean deleteBusinessReport(int id) {
        String sql = "DELETE FROM business_report WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức tìm kiếm động theo từng thuộc tính
    public List<BusinessReport> searchBusinessReports(Integer month, Integer year, Integer minSuccessOrders, Integer maxSuccessOrders,
            Integer minSuccessOrderValue, Integer maxSuccessOrderValue,
            Integer minOverdueDebtOrders, Integer maxOverdueDebtOrders,
            Integer minOverdueDebtValue, Integer maxOverdueDebtValue,
            Integer minIndebtOrders, Integer maxIndebtOrders,
            Integer minIndebtValue, Integer maxIndebtValue,
            Integer minContracts, Integer maxContracts,
            Integer minContractValue, Integer maxContractValue,
            Integer minFiredEmployees, Integer maxFiredEmployees) {
        List<BusinessReport> reports = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM business_report WHERE 1=1");

        // Xây dựng câu truy vấn động dựa trên các tham số không null
        if (month != null) {
            sql.append(" AND month = ?");
        }
        if (year != null) {
            sql.append(" AND year = ?");
        }
        if (minSuccessOrders != null) {
            sql.append(" AND total_number_of_new_success_order >= ?");
        }
        if (maxSuccessOrders != null) {
            sql.append(" AND total_number_of_new_success_order <= ?");
        }
        if (minSuccessOrderValue != null) {
            sql.append(" AND total_value_of_new_success_order >= ?");
        }
        if (maxSuccessOrderValue != null) {
            sql.append(" AND total_value_of_new_success_order <= ?");
        }
        if (minOverdueDebtOrders != null) {
            sql.append(" AND total_number_of_new_overdue_debt_order >= ?");
        }
        if (maxOverdueDebtOrders != null) {
            sql.append(" AND total_number_of_new_overdue_debt_order <= ?");
        }
        if (minOverdueDebtValue != null) {
            sql.append(" AND total_value_of_new_overdue_debt_order >= ?");
        }
        if (maxOverdueDebtValue != null) {
            sql.append(" AND total_value_of_new_overdue_debt_order <= ?");
        }
        if (minIndebtOrders != null) {
            sql.append(" AND total_number_of_new_indebt_order >= ?");
        }
        if (maxIndebtOrders != null) {
            sql.append(" AND total_number_of_new_indebt_order <= ?");
        }
        if (minIndebtValue != null) {
            sql.append(" AND total_value_of_new_indebt_order >= ?");
        }
        if (maxIndebtValue != null) {
            sql.append(" AND total_value_of_new_indebt_order <= ?");
        }
        if (minContracts != null) {
            sql.append(" AND total_number_of_new_contract >= ?");
        }
        if (maxContracts != null) {
            sql.append(" AND total_number_of_new_contract <= ?");
        }
        if (minContractValue != null) {
            sql.append(" AND total_value_of_new_contract >= ?");
        }
        if (maxContractValue != null) {
            sql.append(" AND total_value_of_new_contract <= ?");
        }
        if (minFiredEmployees != null) {
            sql.append(" AND total_number_of_new_fired_employee >= ?");
        }
        if (maxFiredEmployees != null) {
            sql.append(" AND total_number_of_new_fired_employee <= ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int index = 1;

            // Gán giá trị cho các tham số không null
            if (month != null) {
                stmt.setInt(index++, month);
            }
            if (year != null) {
                stmt.setInt(index++, year);
            }
            if (minSuccessOrders != null) {
                stmt.setInt(index++, minSuccessOrders);
            }
            if (maxSuccessOrders != null) {
                stmt.setInt(index++, maxSuccessOrders);
            }
            if (minSuccessOrderValue != null) {
                stmt.setInt(index++, minSuccessOrderValue);
            }
            if (maxSuccessOrderValue != null) {
                stmt.setInt(index++, maxSuccessOrderValue);
            }
            if (minOverdueDebtOrders != null) {
                stmt.setInt(index++, minOverdueDebtOrders);
            }
            if (maxOverdueDebtOrders != null) {
                stmt.setInt(index++, maxOverdueDebtOrders);
            }
            if (minOverdueDebtValue != null) {
                stmt.setInt(index++, minOverdueDebtValue);
            }
            if (maxOverdueDebtValue != null) {
                stmt.setInt(index++, maxOverdueDebtValue);
            }
            if (minIndebtOrders != null) {
                stmt.setInt(index++, minIndebtOrders);
            }
            if (maxIndebtOrders != null) {
                stmt.setInt(index++, maxIndebtOrders);
            }
            if (minIndebtValue != null) {
                stmt.setInt(index++, minIndebtValue);
            }
            if (maxIndebtValue != null) {
                stmt.setInt(index++, maxIndebtValue);
            }
            if (minContracts != null) {
                stmt.setInt(index++, minContracts);
            }
            if (maxContracts != null) {
                stmt.setInt(index++, maxContracts);
            }
            if (minContractValue != null) {
                stmt.setInt(index++, minContractValue);
            }
            if (maxContractValue != null) {
                stmt.setInt(index++, maxContractValue);
            }
            if (minFiredEmployees != null) {
                stmt.setInt(index++, minFiredEmployees);
            }
            if (maxFiredEmployees != null) {
                stmt.setInt(index++, maxFiredEmployees);
            }

            ResultSet rs = stmt.executeQuery();

            // Xử lý kết quả truy vấn
            while (rs.next()) {
                BusinessReport report = new BusinessReport(
                        rs.getInt("id"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("total_number_of_new_success_order"),
                        rs.getInt("total_value_of_new_success_order"),
                        rs.getInt("total_number_of_new_overdue_debt_order"),
                        rs.getInt("total_value_of_new_overdue_debt_order"),
                        rs.getInt("total_number_of_new_indebt_order"),
                        rs.getInt("total_value_of_new_indebt_order"),
                        rs.getInt("total_number_of_new_contract"),
                        rs.getInt("total_value_of_new_contract"),
                        rs.getInt("total_number_of_new_fired_employee")
                );
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public BusinessReport getWarehouseReportDetails(int warehouseId, int warehouseReportId) {
        String sql = "SELECT "
                + "    wr.id AS warehouse_report_id, "
                + "    wr.month, wr.year, wr.report_type, "
                + "    br.total_number_of_new_success_order AS total_success_orders_in_month, "
                + "    br.total_value_of_new_success_order AS total_success_order_value, "
                + "    br.total_number_of_new_overdue_debt_order AS total_overdue_orders_in_month, "
                + "    br.total_value_of_new_overdue_debt_order AS total_overdue_order_value, "
                + "    br.total_number_of_new_indebt_order AS total_indebt_orders_in_month, "
                + "    br.total_value_of_new_indebt_order AS total_indebt_order_value, "
                + "    orr.total_number_of_new_success_order AS order_report_success_orders, "
                + "    orr.total_value_of_new_success_order AS order_report_success_order_value, "
                + "    orr.total_number_of_new_overdue_debt_order AS order_report_overdue_orders, "
                + "    orr.total_value_of_new_overdue_debt_order AS order_report_overdue_order_value, "
                + "    orr.total_number_of_new_indebt_order AS order_report_indebt_orders, "
                + "    orr.total_value_of_new_indebt_order AS order_report_indebt_order_value, "
                + "    cr.total_number_of_new_contract AS total_contracts_in_month, "
                + "    cr.total_value_of_new_contract AS total_contract_value, "
                + "    fer.total_number_of_new_fired_employee AS total_fired_employees_in_month "
                + "FROM "
                + "    warehouse_report wr "
                + "LEFT JOIN business_report br ON wr.business_report_id = br.id "
                + "LEFT JOIN order_report orr ON wr.id = orr.warehouse_report_id "
                + "LEFT JOIN contract_report cr ON wr.id = cr.warehouse_report_id "
                + "LEFT JOIN fired_employee_report fer ON wr.id = fer.warehouse_report_id "
                + "WHERE "
                + "    wr.warehouse_id = ? AND wr.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, warehouseId);
            stmt.setInt(2, warehouseReportId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new BusinessReport(
                        rs.getInt("warehouse_report_id"),
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getInt("total_success_orders_in_month"),
                        rs.getInt("total_success_order_value"),
                        rs.getInt("total_overdue_orders_in_month"),
                        rs.getInt("total_overdue_order_value"),
                        rs.getInt("total_indebt_orders_in_month"),
                        rs.getInt("total_indebt_order_value"),
                        rs.getInt("total_contracts_in_month"),
                        rs.getInt("total_contract_value"),
                        rs.getInt("total_fired_employees_in_month")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Integer> getStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        String sql = "SELECT "
                + "SUM(total_number_of_new_success_order) AS totalSuccessOrders, "
                + "SUM(total_value_of_new_success_order) AS totalSuccessOrderValue, "
                + "SUM(total_number_of_new_overdue_debt_order) AS totalOverdueDebtOrders, "
                + "SUM(total_number_of_new_indebt_order) AS totalIndebtOrders "
                + "FROM business_report";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                statistics.put("totalSuccessOrders", rs.getInt("totalSuccessOrders"));
                statistics.put("totalSuccessOrderValue", rs.getInt("totalSuccessOrderValue"));
                statistics.put("totalOverdueDebtOrders", rs.getInt("totalOverdueDebtOrders"));
                statistics.put("totalIndebtOrders", rs.getInt("totalIndebtOrders"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statistics;
    }

    public static void main(String[] args) {
        BusinessReportDAO dao = new BusinessReportDAO();
        // Test the methods here, e.g., getAllBusinessReports, addBusinessReport, etc.
    }
}
