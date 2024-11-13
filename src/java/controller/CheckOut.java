package controller;

import dal.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Cart;
import model.Order;
import java.io.IOException;

public class CheckOut extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Bước 1: Lấy session
        HttpSession session = request.getSession(true);

        // Bước 2: Kiểm tra giỏ hàng
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            // Nếu không có giỏ hàng, tạo giỏ hàng mới
            cart = new Cart();
        }

        // Bước 3: Kiểm tra tài khoản
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra xem session account có tồn tại hay không
        if (account == null) {
            System.out.println("Session Account is null.");
            // Điều hướng người dùng đến trang đăng nhập nếu chưa đăng nhập
            response.sendRedirect("jsp/Login.jsp");
            return;
        } else {
            System.out.println("Session Account exists: " + account.getId());

            
            // Bước 4: Tạo đối tượng Order và th êm vào database
            OrderDAO odb = new OrderDAO();
            Order order = new Order();
            order.setCustomerId(account.getId());
           
            order.setEmployeeIdIncharge(account.getId());
            // Ngày tạo đơn
            order.setCreatedDate(new java.util.Date());
            // Đặt trạng thái là "waiting_for_approval"
            order.setStatus(1);
            order.setExportedDate(new java.util.Date());
            order.setArrivedDate(new java.util.Date());
            order.setShippingUnit(1);
            order.setOrderValueBeforeDiscount(cart.getTotalMoney());
            // Tính toán giảm giá
            float discountPercentage = 0.0f;
            order.setTotalDiscountPercenTage(discountPercentage);
            int discountAmount = (int) (cart.getTotalMoney() * discountPercentage);
            order.setOrderValueAfterDiscount(cart.getTotalMoney() - discountAmount);
            order.setPayDate(new java.util.Date());
            order.setOrderReportId(1);

            // Lưu đơn hàng vào cơ sở dữ liệu
            boolean isInserted = odb.addOrder(account, cart, order);

            if (isInserted) {
                System.out.println("Order inserted successfully!");
            } else {
                System.out.println("Failed to insert order.");
            }

            // Lưu thông tin đơn hàng vào session
            session.setAttribute("order", order);

            // Xóa giỏ hàng sau khi hoàn thành checkout
            session.removeAttribute("cart");

            // Chuyển hướng đến servlet CheckoutSuccess
            response.sendRedirect("checkOutSuccess");
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
        return "Servlet xử lý thanh toán giỏ hàng và tạo đơn hàng mới";
    }
}
