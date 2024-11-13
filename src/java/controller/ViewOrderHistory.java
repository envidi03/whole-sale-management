package controller;

import dal.AccountDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import model.Order;
import java.util.HashMap;
import java.util.Map;

public class ViewOrderHistory extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        OrderDAO orderDAO = new OrderDAO();
        AccountDAO accountDAO = new AccountDAO();
        List<Order> list = orderDAO.getAllOrder();
//        List<Order> pendingOrders = new ArrayList<>();
//        List<Order> deliveredOrders = new ArrayList<>();

        // Lấy chỉ số trang hiện tại từ request
        String indexString = request.getParameter("index");
        if (indexString == null) {
            indexString = "1"; // Nếu không có trang hiện tại, mặc định là trang 1
        }
        int index = Integer.parseInt(indexString);

        // Số lượng sản phẩm trên mỗi trang
        int productsPerPage = 5;

        // Lấy tổng số lượng sản phẩm
        int totalOrder = orderDAO.getTotalOrder();

        // Tính toán tổng số trang
        int endPage = totalOrder / productsPerPage;
        if (totalOrder % productsPerPage != 0) {
            endPage++;
        }

        List<Order> pendingOrders = orderDAO.getOrdersByPage(index, productsPerPage);
        List<Order> deliveredOrders = orderDAO.getOrdersByPage2(index, productsPerPage);

        for (Order order : list) {
            if ("Đang chờ phê duyệt".equalsIgnoreCase(order.getStatusString())) {
                pendingOrders.add(order);
            } else if ("Đã giao hàng (Khách hàng thanh toán)".equalsIgnoreCase(order.getStatusString())) {
                deliveredOrders.add(order);
            }
        }

        // lấy ra tên nhân viên trực ca dựa theo id
        Map<Integer, String> employeeNames = new HashMap<>();
        for (Order order : list) {
            int employeeId = order.getEmployeeIdIncharge();
            if (!employeeNames.containsKey(employeeId)) {
                String employeeName = accountDAO.getEmployeeNameById(employeeId);
                employeeNames.put(employeeId, employeeName);
            }
        }
        request.setAttribute("pendingOrders", pendingOrders);
        request.setAttribute("deliveredOrders", deliveredOrders);
        request.setAttribute("listO", list);
        request.setAttribute("employeeNames", employeeNames);
        request.setAttribute("endPage", endPage);
        request.setAttribute("currentPage", index);

        String action = request.getParameter("action");
        if ("delivered".equals(action)) {
            request.getRequestDispatcher("jsp/orderDelivered.jsp").forward(request, response);
        } else {
            //request.getRequestDispatcher("jsp/orderPending.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
