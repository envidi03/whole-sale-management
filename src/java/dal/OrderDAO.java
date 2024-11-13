/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.time.LocalDate;
import model.Account;
import model.Cart;
import model.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Consignment;
import model.Customer;
import model.Item;
import util.DataConvert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.sql.PreparedStatement;

/**
 *
 * @author LamHP
 */
public class OrderDAO extends DBContext {

    DataConvert dc = new DataConvert();

//    public void addOrder(Account a, Cart cart, Customer c, Order o) {
//        LocalDate curDate = java.time.LocalDate.now();
//        //  String date = curDate.toString();
//        try {
//            String sql = "INSERT INTO wholesalemanagement.`order` \n"
//                    + "(customer_id, employee_id_incharge, created_date,"
//                    + " status, exported_date, arrived_date, shipping_unit,"
//                    + " order_value_before_discount, total_discount_percentage, order_value_after_discount, pay_date, order_report_id) \n"
//                    + "VALUES \n"
//                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//            PreparedStatement st1 = connection.prepareStatement(sql);
//            st1.setInt(1, o.getCustomerId());
//            st1.setInt(2, o.getEmployeeIdIncharge());
//            st1.setDate(3, dc.UtilDateToSqlDate(o.getCreatedDate()));
//            st1.setInt(4, o.getStatus());
//            st1.setDate(5, dc.UtilDateToSqlDate(o.getExportedDate()));
//            st1.setDate(6, dc.UtilDateToSqlDate(o.getArrivedDate()));
//            st1.setInt(7, o.getShippingUnit());
//            st1.setInt(8, o.getOrderValueBeforeDiscount());
//            st1.setFloat(9, o.getTotalDiscountPercenTage());
//            st1.setInt(10, o.getOrderValueAfterDiscount());
//            st1.setDate(11, dc.UtilDateToSqlDate(o.getPayDate()));
//            st1.setInt(12, o.getOrderReportId());
//            ResultSet rs = st1.executeQuery();
//            if (rs.next()) {
//                int oid = rs.getInt(1);
//                for (Item i : cart.getItems()) {
//                    String sql2 = "INSERT INTO `wholesalemanagement`.`order_detail` (`order_id`, `consignment_id`, `quantity`, `totoal_price`) "
//                            + "VALUES (?, ?, ?, ?);";
//                    PreparedStatement st2 = connection.prepareStatement(sql2);
//                    st2.setInt(1, oid);
//                    st2.setInt(2, i.getConsignment().getId());
//                    st2.setInt(3, i.getQuantity());
//                    st2.setInt(4, i.getPrice());
//                    st2.executeUpdate();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
    public boolean addOrder(Account a, Cart cart, Order o) {

        try {
            String sql = "INSERT INTO wholesalemanagement.`order` \n"
                    + "(customer_id, employee_id_incharge, created_date,"
                    + " status, exported_date, arrived_date, shipping_unit,"
                    + " order_value_before_discount, total_discount_percentage, order_value_after_discount, pay_date, order_report_id) \n"
                    + "VALUES \n"
                    + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement st1 = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); // Thêm tham số để lấy ID tự động tăng
            st1.setInt(1, o.getCustomerId());
            st1.setInt(2, o.getEmployeeIdIncharge());
            st1.setDate(3, dc.UtilDateToSqlDate(o.getCreatedDate()));
            st1.setInt(4, o.getStatus());
            st1.setDate(5, dc.UtilDateToSqlDate(o.getExportedDate()));
            st1.setDate(6, dc.UtilDateToSqlDate(o.getArrivedDate()));
            st1.setInt(7, o.getShippingUnit());
            st1.setInt(8, o.getOrderValueBeforeDiscount());
            st1.setFloat(9, o.getTotalDiscountPercenTage());
            st1.setInt(10, o.getOrderValueAfterDiscount());
            st1.setDate(11, dc.UtilDateToSqlDate(o.getPayDate()));
            st1.setInt(12, o.getOrderReportId());

            // Sử dụng executeUpdate() để thực hiện lệnh INSERT
            int affectedRows = st1.executeUpdate();
            if (affectedRows > 0) {
                // Nếu có ít nhất 1 hàng bị ảnh hưởng, lấy ID của đơn hàng vừa thêm
                ResultSet rs = st1.getGeneratedKeys();
                if (rs.next()) {
                    int oid = rs.getInt(1); // Lấy ID tự động tăng
                    for (Item i : cart.getItems()) {
                        String sql2 = "INSERT INTO `wholesalemanagement`.`order_detail` (`order_id`, `consignment_id`, `quantity`, `totoal_price`) "
                                + "VALUES (?, ?, ?, ?);";
                        PreparedStatement st2 = connection.prepareStatement(sql2);
                        st2.setInt(1, oid);
                        st2.setInt(2, i.getConsignment().getId());
                        st2.setInt(3, i.getQuantity());
                        st2.setInt(4, i.getPrice());
                        st2.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());

        }
        return true;
    }

    // search order by customer name and pagging with status = 1
    public List<Order> search(String txtSearch, int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "            WITH x AS (\n"
                + "                SELECT ROW_NUMBER() OVER (ORDER BY o.id) AS stt, \n"
                + "                o.*, c.name AS customerName \n"
                + "                FROM wholesalemanagement.order o \n"
                + "                JOIN wholesalemanagement.customer c \n"
                + "                ON o.customer_id = c.id \n"
                + "                WHERE c.name LIKE ? and o.status = 1) \n"
                + "                SELECT * FROM x WHERE stt BETWEEN ? * 5 - 4 AND ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // search order by customer name and pagging with status = 4
    public List<Order> search2(String txtSearch, int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "            WITH x AS (\n"
                + "                SELECT ROW_NUMBER() OVER (ORDER BY o.id) AS stt, \n"
                + "                o.*, c.name AS customerName \n"
                + "                FROM wholesalemanagement.order o \n"
                + "                JOIN wholesalemanagement.customer c \n"
                + "                ON o.customer_id = c.id \n"
                + "                WHERE c.name LIKE ? and o.status = 4) \n"
                + "                SELECT * FROM x WHERE stt BETWEEN ? * 5 - 4 AND ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // search order by customer name and pagging with status = 1
    public List<Order> searchEmployeeOrders(String txtSearch, int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "with x as (select ROW_NUMBER() OVER (ORDER BY o.id) AS stt,\n"
                + "                o.*, CONCAT(a.first_name, ' ', a.last_name) as employeeName\n"
                + "                FROM wholesalemanagement.order o \n"
                + "                JOIN wholesalemanagement.account a ON o.employee_id_incharge = a.id\n"
                + "                WHERE CONCAT(a.first_name, ' ', a.last_name) LIKE ? and o.status = 1) \n"
                + "                select * from x where stt between  ? * 5 - 4 AND ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String employeeName = rs.getString("employeeName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        employeeName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
// search order by customer name and pagging with status = 4

    public List<Order> searchEmployeeOrders2(String txtSearch, int index, int size) {
        List<Order> list = new ArrayList<>();
        String sql = "with x as (select ROW_NUMBER() OVER (ORDER BY o.id) AS stt,\n"
                + "                o.*, CONCAT(a.first_name, ' ', a.last_name) as employeeName\n"
                + "                FROM wholesalemanagement.order o \n"
                + "                JOIN wholesalemanagement.account a ON o.employee_id_incharge = a.id\n"
                + "                WHERE CONCAT(a.first_name, ' ', a.last_name) LIKE ? and o.status = 4) \n"
                + "                select * from x where stt between  ? * 5 - 4 AND ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String employeeName = rs.getString("employeeName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        employeeName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
//        public static void main(String[] args) {
//        OrderDAO orderDAO = new OrderDAO();
//        List<Order> list = orderDAO.searchEmployeeOrders("hoang", 1, 5);
//        for (Order o : list) {
//            System.out.println(o);
//        }
//    }
    // count and pagging

    public int count(String txtSearch) {
        String sql = "select count(*) from wholesalemanagement.order o\n"
                + " join wholesalemanagement.customer c on o.customer_id = c.id where c.name like ?";

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

    // count and pagging emplyee
    public int countEmployee(String txtSearch) {
        String sql = "select count(*) from wholesalemanagement.order o join wholesalemanagement.account a \n"
                + "on o.employee_id_incharge = a.id where a.first_name like ? and a.last_name like ?";

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

    public int getTotalOrder() {
        int total = 0;
        String query = "SELECT COUNT(*) FROM wholesalemanagement.order";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Order> getOrdersByPage(int page, int ordersPerPage) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT o.*, c.name as customerName \n"
                + "FROM wholesalemanagement.order o join wholesalemanagement.customer c \n"
                + "on o.customer_id = c.id where o.status = 1 limit ?, ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * ordersPerPage); // Bắt đầu từ vị trí sản phẩm
            ps.setInt(2, ordersPerPage); // Số sản phẩm cần lấy
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Order> getOrdersByPage2(int page, int ordersPerPage) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT o.*, c.name as customerName \n"
                + "FROM wholesalemanagement.order o join wholesalemanagement.customer c \n"
                + "on o.customer_id = c.id where o.status = 4 limit ?, ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * ordersPerPage); // Bắt đầu từ vị trí sản phẩm
            ps.setInt(2, ordersPerPage); // Số sản phẩm cần lấy
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    public static void main(String[] args) {
//        OrderDAO orderDAO = new OrderDAO();
//        List<Order> list = orderDAO.getOrdersByPage2(1, 5);
//        for (Order o : list) {
//            System.out.println(o);
//        }
//    }
    // get all
    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, c.name AS customerName FROM wholesalemanagement.order o JOIN wholesalemanagement.customer c ON o.customer_id = c.id";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int customerId = rs.getInt(2);
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt(3);
                Date createdDate = rs.getDate(4);
                int status = rs.getInt(5);
                Date exportedDate = rs.getDate(6);
                Date arrivedDate = rs.getDate(7);
                int shippingUnit = rs.getInt(8);
                int orderValueBeforeDiscount = rs.getInt(9);
                float totalDiscountPercenTage = rs.getFloat(10);
                int orderValueAfterDiscount = rs.getInt(11);
                Date payDate = rs.getDate(12);
                int orderReportId = rs.getInt(13);

                Order order = new Order(id,
                        customerId,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT o.*, c.name AS customerName FROM wholesalemanagement.order o JOIN wholesalemanagement.customer c ON o.customer_id = c.id where o.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

//    public static void main(String[] args) {
//        OrderDAO orderDAO = new OrderDAO();
//        int orderId = 1;
//        Order order = orderDAO.getOrderById(orderId);
//        System.out.println(order);
//    }
    // get all order by price with status = 1
    public List<Order> getAllOrderByPrice(int min, int max) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT o.*, c.name AS customerName \n"
                + "FROM wholesalemanagement.order o JOIN wholesalemanagement.customer c ON o.customer_id = c.id\n"
                + "where o.status = 1 and o.order_value_after_discount between ? and ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // get all order by price with status = 4
    public List<Order> getAllOrderByPrice2(int min, int max) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT o.*, c.name AS customerName \n"
                + "FROM wholesalemanagement.order o JOIN wholesalemanagement.customer c ON o.customer_id = c.id\n"
                + "where o.status = 4 and o.order_value_after_discount between ? and ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int customer_id = rs.getInt("customer_id");
                String customerName = rs.getString("customerName"); // Lấy tên khách hàng
                int employeeIdIncharge = rs.getInt("employee_id_incharge");
                Date createdDate = rs.getDate("created_date");
                int status = rs.getInt("status");
                Date exportedDate = rs.getDate("exported_date");
                Date arrivedDate = rs.getDate("arrived_date");
                int shippingUnit = rs.getInt("shipping_unit");
                int orderValueBeforeDiscount = rs.getInt("order_value_before_discount");
                float totalDiscountPercenTage = rs.getFloat("total_discount_percentage");
                int orderValueAfterDiscount = rs.getInt("order_value_after_discount");
                Date payDate = rs.getDate("pay_date");
                int orderReportId = rs.getInt("order_report_id");
                Order order = new Order(id,
                        customer_id,
                        customerName,
                        employeeIdIncharge,
                        createdDate,
                        status,
                        exportedDate,
                        arrivedDate,
                        shippingUnit,
                        orderValueBeforeDiscount,
                        totalDiscountPercenTage,
                        orderValueAfterDiscount,
                        payDate,
                        orderReportId);
                list.add(order);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Method to update the order status
    public void updateOrderStatus(int orderId, int newStatus) {
        String sql = "UPDATE wholesalemanagement.order SET status = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newStatus);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmployeeNameById(int employeeId) {
        AccountDAO accountDAO = new AccountDAO();
        return accountDAO.getEmployeeNameById(employeeId);
    }

    // updateDateOrderById
    public void updateDateOrderById(int id, Date exprortDate, Date payDate) {
        String sql = "update wholesalemanagement.order o set o.exported_date = ?, o.pay_date = ? where o.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, dc.UtilDateToSqlDate(exprortDate));
            ps.setDate(2, dc.UtilDateToSqlDate(payDate));
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void deleteOrderById(int id) {
        String sql1 = "DELETE FROM wholesalemanagement.order_detail WHERE order_id = ?";
        String sql2 = "DELETE FROM wholesalemanagement.order WHERE id = ? AND status = 1";
        try {
            // Thực hiện lệnh đầu tiên để xóa trong order_detail
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            // Thực hiện lệnh thứ hai để xóa trong order
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.executeUpdate();

            // Đóng PreparedStatement sau khi sử dụng
            ps1.close();
            ps2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(int CustomerId) {
        Customer cus = null;
        String sql = "select * from wholesalemanagement.customer where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, CustomerId);
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

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateCusNameById("Hoàng Phúc hihi", 1);
    }

//    public static void main(String[] args) {
//        OrderDAO orderDAO = new OrderDAO();
//        // Định dạng ngày với SimpleDateFormat
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            // Chuyển đổi chuỗi thành java.util.Date
//            java.util.Date utilExportDate = sdf.parse("2024-11-22"); // Định dạng ngày là "yyyy-MM-dd"
//            java.util.Date utilPayDate = sdf.parse("2024-11-11");    // Định dạng ngày là "yyyy-MM-dd"
//
//            // Gọi phương thức cập nhật
//            orderDAO.updateDateOrderById(13, utilExportDate, utilPayDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//                    // Tạo một OrderDAO
//            OrderDAO orderDAO = new OrderDAO();
//            Account account = new Account(); // Tạo đối tượng Account
//            Cart cart = new Cart(); // Tạo đối tượng Cart
//            Customer customer = new Customer(); // Tạo đối tượng Customer
//            Order order = new Order(); // Tạo đối tượng Order
//
//            // Thiết lập thông tin cho đối tượng Order
//            order.setCustomerId(1);
//            order.setEmployeeIdIncharge(1);
//            order.setCreatedDate(new java.util.Date()); // Cài đặt ngày tạo
//            order.setStatus(1);
//            order.setExportedDate(new java.util.Date());
//            order.setArrivedDate(new java.util.Date());
//            order.setShippingUnit(1);
//            order.setOrderValueBeforeDiscount(900000);
//            order.setTotalDiscountPercenTage(0);
//            order.setOrderValueAfterDiscount(900000);
//            order.setPayDate(new java.util.Date());
//            order.setOrderReportId(1);
//
//            // Gọi phương thức thêm đơn hàng vao db
//            orderDAO.addOrder(account, cart, order);
//            System.out.println("Đơn hàng đã được thêm thành công!");
//    }
}
