package dal;

import model.OrderReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import model.Order;

public class OrderReportDAO extends DBContext {

    // Phương thức để lấy tất cả các OrderReport với thông tin từ warehouse_report
    public Vector<OrderReport> getAllOrderReports() {
        Vector<OrderReport> reports = new Vector<>();
        String sql = "SELECT orp.warehouse_report_id, orp.total_number_of_new_success_order, orp.total_value_of_new_success_order, "
                + "orp.total_number_of_new_overdue_debt_order, orp.total_value_of_new_overdue_debt_order, "
                + "orp.total_number_of_new_indebt_order, orp.total_value_of_new_indebt_order, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM order_report orp "
                + "JOIN warehouse_report wr ON orp.warehouse_report_id = wr.id";
        try {
            Statement state = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                OrderReport report = new OrderReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewSuccessOrder(rs.getInt("total_number_of_new_success_order"));
                report.setTotalValueOfNewSuccessOrder(rs.getInt("total_value_of_new_success_order"));
                report.setTotalNumberOfNewOverdueDebtOrder(rs.getInt("total_number_of_new_overdue_debt_order"));
                report.setTotalValueOfNewOverdueDebtOrder(rs.getInt("total_value_of_new_overdue_debt_order"));
                report.setTotalNumberOfNewIndebtOrder(rs.getInt("total_number_of_new_indebt_order"));
                report.setTotalValueOfNewIndebtOrder(rs.getInt("total_value_of_new_indebt_order"));

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

    // Phương thức để xem chi tiết một OrderReport dựa trên warehouse_report_id
    public OrderReport getOrderReportById(int id) {
        String sql = "SELECT orp.warehouse_report_id, orp.total_number_of_new_success_order, orp.total_value_of_new_success_order, "
                + "orp.total_number_of_new_overdue_debt_order, orp.total_value_of_new_overdue_debt_order, "
                + "orp.total_number_of_new_indebt_order, orp.total_value_of_new_indebt_order, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM order_report orp "
                + "JOIN warehouse_report wr ON orp.warehouse_report_id = wr.id "
                + "WHERE orp.warehouse_report_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                OrderReport report = new OrderReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewSuccessOrder(rs.getInt("total_number_of_new_success_order"));
                report.setTotalValueOfNewSuccessOrder(rs.getInt("total_value_of_new_success_order"));
                report.setTotalNumberOfNewOverdueDebtOrder(rs.getInt("total_number_of_new_overdue_debt_order"));
                report.setTotalValueOfNewOverdueDebtOrder(rs.getInt("total_value_of_new_overdue_debt_order"));
                report.setTotalNumberOfNewIndebtOrder(rs.getInt("total_number_of_new_indebt_order"));
                report.setTotalValueOfNewIndebtOrder(rs.getInt("total_value_of_new_indebt_order"));

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

    public Vector<OrderReport> getOrderReportsByFilter(
            Integer warehouseReportId, Integer totalSuccessOrdersMin, Integer totalSuccessOrdersMax,
            Integer totalValueSuccessOrdersMin, Integer totalValueSuccessOrdersMax, Integer totalOverdueDebtOrdersMin,
            Integer totalOverdueDebtOrdersMax, Integer totalValueOverdueDebtOrdersMin, Integer totalValueOverdueDebtOrdersMax,
            Integer totalIndebtOrdersMin, Integer totalIndebtOrdersMax, Integer totalValueIndebtOrdersMin,
            Integer totalValueIndebtOrdersMax, Integer month, Integer year, String sortBy) {

        Vector<OrderReport> reports = new Vector<>();
        StringBuilder sql = new StringBuilder("SELECT orp.warehouse_report_id, orp.total_number_of_new_success_order, orp.total_value_of_new_success_order, "
                + "orp.total_number_of_new_overdue_debt_order, orp.total_value_of_new_overdue_debt_order, "
                + "orp.total_number_of_new_indebt_order, orp.total_value_of_new_indebt_order, "
                + "wr.month, wr.year, wr.report_type "
                + "FROM order_report orp "
                + "JOIN warehouse_report wr ON orp.warehouse_report_id = wr.id WHERE 1=1");

        if (warehouseReportId != null) {
            sql.append(" AND orp.warehouse_report_id = ?");
        }
        if (totalSuccessOrdersMin != null) {
            sql.append(" AND orp.total_number_of_new_success_order >= ?");
        }
        if (totalSuccessOrdersMax != null) {
            sql.append(" AND orp.total_number_of_new_success_order <= ?");
        }
        if (totalValueSuccessOrdersMin != null) {
            sql.append(" AND orp.total_value_of_new_success_order >= ?");
        }
        if (totalValueSuccessOrdersMax != null) {
            sql.append(" AND orp.total_value_of_new_success_order <= ?");
        }
        if (totalOverdueDebtOrdersMin != null) {
            sql.append(" AND orp.total_number_of_new_overdue_debt_order >= ?");
        }
        if (totalOverdueDebtOrdersMax != null) {
            sql.append(" AND orp.total_number_of_new_overdue_debt_order <= ?");
        }
        if (totalValueOverdueDebtOrdersMin != null) {
            sql.append(" AND orp.total_value_of_new_overdue_debt_order >= ?");
        }
        if (totalValueOverdueDebtOrdersMax != null) {
            sql.append(" AND orp.total_value_of_new_overdue_debt_order <= ?");
        }
        if (totalIndebtOrdersMin != null) {
            sql.append(" AND orp.total_number_of_new_indebt_order >= ?");
        }
        if (totalIndebtOrdersMax != null) {
            sql.append(" AND orp.total_number_of_new_indebt_order <= ?");
        }
        if (totalValueIndebtOrdersMin != null) {
            sql.append(" AND orp.total_value_of_new_indebt_order >= ?");
        }
        if (totalValueIndebtOrdersMax != null) {
            sql.append(" AND orp.total_value_of_new_indebt_order <= ?");
        }
        if (month != null) {
            sql.append(" AND wr.month = ?");
        }
        if (year != null) {
            sql.append(" AND wr.year = ?");
        }

        if (sortBy != null) {
            switch (sortBy) {
                case "totalSuccessOrders":
                    sql.append(" ORDER BY orp.total_number_of_new_success_order");
                    break;
                case "totalValueSuccessOrders":
                    sql.append(" ORDER BY orp.total_value_of_new_success_order");
                    break;
                case "totalOverdueDebtOrders":
                    sql.append(" ORDER BY orp.total_number_of_new_overdue_debt_order");
                    break;
                case "totalValueOverdueDebtOrders":
                    sql.append(" ORDER BY orp.total_value_of_new_overdue_debt_order");
                    break;
                case "totalIndebtOrders":
                    sql.append(" ORDER BY orp.total_number_of_new_indebt_order");
                    break;
                case "totalValueIndebtOrders":
                    sql.append(" ORDER BY orp.total_value_of_new_indebt_order");
                    break;
                default:
                    sql.append(" ORDER BY orp.warehouse_report_id");
                    break;
            }
        }

        try {
            PreparedStatement pre = connection.prepareStatement(sql.toString());
            int paramIndex = 1;

            if (warehouseReportId != null) {
                pre.setInt(paramIndex++, warehouseReportId);
            }
            if (totalSuccessOrdersMin != null) {
                pre.setInt(paramIndex++, totalSuccessOrdersMin);
            }
            if (totalSuccessOrdersMax != null) {
                pre.setInt(paramIndex++, totalSuccessOrdersMax);
            }
            if (totalValueSuccessOrdersMin != null) {
                pre.setInt(paramIndex++, totalValueSuccessOrdersMin);
            }
            if (totalValueSuccessOrdersMax != null) {
                pre.setInt(paramIndex++, totalValueSuccessOrdersMax);
            }
            if (totalOverdueDebtOrdersMin != null) {
                pre.setInt(paramIndex++, totalOverdueDebtOrdersMin);
            }
            if (totalOverdueDebtOrdersMax != null) {
                pre.setInt(paramIndex++, totalOverdueDebtOrdersMax);
            }
            if (totalValueOverdueDebtOrdersMin != null) {
                pre.setInt(paramIndex++, totalValueOverdueDebtOrdersMin);
            }
            if (totalValueOverdueDebtOrdersMax != null) {
                pre.setInt(paramIndex++, totalValueOverdueDebtOrdersMax);
            }
            if (totalIndebtOrdersMin != null) {
                pre.setInt(paramIndex++, totalIndebtOrdersMin);
            }
            if (totalIndebtOrdersMax != null) {
                pre.setInt(paramIndex++, totalIndebtOrdersMax);
            }
            if (totalValueIndebtOrdersMin != null) {
                pre.setInt(paramIndex++, totalValueIndebtOrdersMin);
            }
            if (totalValueIndebtOrdersMax != null) {
                pre.setInt(paramIndex++, totalValueIndebtOrdersMax);
            }
            if (month != null) {
                pre.setInt(paramIndex++, month);
            }
            if (year != null) {
                pre.setInt(paramIndex++, year);
            }

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                OrderReport report = new OrderReport();
                report.setWarehouseReportId(rs.getInt("warehouse_report_id"));
                report.setTotalNumberOfNewSuccessOrder(rs.getInt("total_number_of_new_success_order"));
                report.setTotalValueOfNewSuccessOrder(rs.getInt("total_value_of_new_success_order"));
                report.setTotalNumberOfNewOverdueDebtOrder(rs.getInt("total_number_of_new_overdue_debt_order"));
                report.setTotalValueOfNewOverdueDebtOrder(rs.getInt("total_value_of_new_overdue_debt_order"));
                report.setTotalNumberOfNewIndebtOrder(rs.getInt("total_number_of_new_indebt_order"));
                report.setTotalValueOfNewIndebtOrder(rs.getInt("total_value_of_new_indebt_order"));
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

    public Vector<Order> getSuccessOrders() {
        Vector<Order> orders = new Vector<>();
        String sql = "SELECT o.id, o.customer_id, c.name AS customer_name, o.employee_id_incharge, o.created_date, "
                + "o.status, o.exported_date, o.arrived_date, o.shipping_unit, "
                + "o.order_value_before_discount, o.total_discount_percentage, o.order_value_after_discount, "
                + "o.pay_date, o.order_report_id "
                + "FROM `order` o "
                + "JOIN customer c ON o.customer_id = c.id "
                + "WHERE o.status = 4"; // 4 là trạng thái đơn hàng thành công

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmployeeIdIncharge(rs.getInt("employee_id_incharge"));
                order.setCreatedDate(rs.getDate("created_date"));
                order.setStatus(rs.getInt("status"));
                order.setExportedDate(rs.getDate("exported_date"));
                order.setArrivedDate(rs.getDate("arrived_date"));
                order.setShippingUnit(rs.getInt("shipping_unit"));
                order.setOrderValueBeforeDiscount(rs.getInt("order_value_before_discount"));
                order.setTotalDiscountPercenTage(rs.getFloat("total_discount_percentage"));
                order.setOrderValueAfterDiscount(rs.getInt("order_value_after_discount"));
                order.setPayDate(rs.getDate("pay_date"));
                order.setOrderReportId(rs.getInt("order_report_id"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public Vector<Order> getOverdueDebtOrders() {
        Vector<Order> orders = new Vector<>();
        String sql = "SELECT o.id, o.customer_id, c.name AS customer_name, o.employee_id_incharge, o.created_date, "
                + "o.status, o.exported_date, o.arrived_date, o.shipping_unit, "
                + "o.order_value_before_discount, o.total_discount_percentage, o.order_value_after_discount, "
                + "o.pay_date, o.order_report_id "
                + "FROM `order` o "
                + "JOIN customer c ON o.customer_id = c.id "
                + "WHERE o.status = 5"; // 5 là trạng thái nợ quá hạn

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmployeeIdIncharge(rs.getInt("employee_id_incharge"));
                order.setCreatedDate(rs.getDate("created_date"));
                order.setStatus(rs.getInt("status"));
                order.setExportedDate(rs.getDate("exported_date"));
                order.setArrivedDate(rs.getDate("arrived_date"));
                order.setShippingUnit(rs.getInt("shipping_unit"));
                order.setOrderValueBeforeDiscount(rs.getInt("order_value_before_discount"));
                order.setTotalDiscountPercenTage(rs.getFloat("total_discount_percentage"));
                order.setOrderValueAfterDiscount(rs.getInt("order_value_after_discount"));
                order.setPayDate(rs.getDate("pay_date"));
                order.setOrderReportId(rs.getInt("order_report_id"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public Vector<Order> getInDebtOrders() {
        Vector<Order> orders = new Vector<>();
        String sql = "SELECT o.id, o.customer_id, c.name AS customer_name, o.employee_id_incharge, o.created_date, "
                + "o.status, o.exported_date, o.arrived_date, o.shipping_unit, "
                + "o.order_value_before_discount, o.total_discount_percentage, o.order_value_after_discount, "
                + "o.pay_date, o.order_report_id "
                + "FROM `order` o "
                + "JOIN customer c ON o.customer_id = c.id "
                + "WHERE o.status = 6"; // 6 là trạng thái công nợ

        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmployeeIdIncharge(rs.getInt("employee_id_incharge"));
                order.setCreatedDate(rs.getDate("created_date"));
                order.setStatus(rs.getInt("status"));
                order.setExportedDate(rs.getDate("exported_date"));
                order.setArrivedDate(rs.getDate("arrived_date"));
                order.setShippingUnit(rs.getInt("shipping_unit"));
                order.setOrderValueBeforeDiscount(rs.getInt("order_value_before_discount"));
                order.setTotalDiscountPercenTage(rs.getFloat("total_discount_percentage"));
                order.setOrderValueAfterDiscount(rs.getInt("order_value_after_discount"));
                order.setPayDate(rs.getDate("pay_date"));
                order.setOrderReportId(rs.getInt("order_report_id"));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    // Hàm main để kiểm tra các phương thức
    public static void main(String[] args) {
        OrderReportDAO dao = new OrderReportDAO();

        // Kiểm tra phương thức getAllOrderReports
        System.out.println("Testing getAllOrderReports method:");
        Vector<OrderReport> allReports = dao.getAllOrderReports();
        for (OrderReport report : allReports) {
            System.out.println(report);
        }

        // Kiểm tra phương thức getOrderReportById với một id cụ thể
        int testId = 1;
        System.out.println("\nTesting getOrderReportById method with id = " + testId + ":");
        OrderReport report = dao.getOrderReportById(testId);
        if (report != null) {
            System.out.println(report);
        } else {
            System.out.println("Order Report with id = " + testId + " not found.");
        }

        // Lấy chi tiết các đơn hàng thành công mới
        System.out.println("Success Orders:");
        Vector<Order> successOrders = dao.getSuccessOrders();
        for (Order order : successOrders) {
            System.out.println(order);
        }

        // Lấy chi tiết các đơn hàng nợ quá hạn mới
        System.out.println("\nOverdue Debt Orders:");
        Vector<Order> overdueDebtOrders = dao.getOverdueDebtOrders();
        for (Order order : overdueDebtOrders) {
            System.out.println(order);
        }

        // Lấy chi tiết các đơn hàng công nợ mới
        System.out.println("\nIn Debt Orders:");
        Vector<Order> inDebtOrders = dao.getInDebtOrders();
        for (Order order : inDebtOrders) {
            System.out.println(order);
        }
    }
}
