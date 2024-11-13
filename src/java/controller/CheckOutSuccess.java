package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import java.io.IOException;

public class CheckOutSuccess extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Lấy thông tin từ session
        HttpSession session = request.getSession(false);
        Order order = (Order) session.getAttribute("order"); // Giả sử bạn đã lưu thông tin đơn hàng vào session
        
        if (order != null) {
            request.setAttribute("order", order);
            request.getRequestDispatcher("jsp/checkoutSuccess.jsp").forward(request, response);
        } else {
            // Nếu không có thông tin đơn hàng, chuyển hướng về trang chủ hoặc thông báo lỗi
            response.sendRedirect("home.jsp"); // Hoặc trang khác
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
        return "Servlet hiển thị thông tin đơn hàng thành công";
    }
}
