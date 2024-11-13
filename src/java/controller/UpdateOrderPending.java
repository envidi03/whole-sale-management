package controller;

import dal.CustomerDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.Order;

public class UpdateOrderPending extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO = new OrderDAO();
    private CustomerDAO customerDAO = new CustomerDAO();  // Khai báo CustomerDAO

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Lấy đơn hàng từ cơ sở dữ liệu LamHP176866
        Order order = orderDAO.getOrderById(orderId);

        request.setAttribute("order", order);

        // Chuyển hướng đến trang JSP cập nhật
        request.getRequestDispatcher("jsp/UpdateOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String cusName = request.getParameter("cusName"); // Lấy tên khách hàng từ form

        String exportDateStr = request.getParameter("exportDate");
        String payDateStr = request.getParameter("payDate");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date exportDate = null;
        Date payDate = null;

        try {
            if (exportDateStr != null && !exportDateStr.isEmpty()) {
                exportDate = new Date(dateFormat.parse(exportDateStr).getTime());
            }
            if (payDateStr != null && !payDateStr.isEmpty()) {
                payDate = new Date(dateFormat.parse(payDateStr).getTime());
            }

            // Cập nhật ngày tháng của đơn hàng
            orderDAO.updateDateOrderById(orderId, exportDate, payDate);

            // Cập nhật tên khách hàng nếu tên không null
            if (cusName != null && !cusName.isEmpty()) {
                int customerId = orderDAO.getOrderById(orderId).getCustomerId(); // Lấy customerId từ order
                customerDAO.updateCusNameById(cusName, customerId);
            }

            // Chuyển hướng về trang danh sách đơn hàng đã cập nhật thành công
            response.sendRedirect("pendingOrder?message=Update successful");
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect("pendingOrder?error=Invalid date format");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating pending orders";
    }
}
