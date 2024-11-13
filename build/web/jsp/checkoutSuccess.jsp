<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Order"%>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thanh toán thành công</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .checkout-success-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #f9f9f9;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="container checkout-success-container">
    <h1 class="text-center text-success">Đặt hàng thành công!</h1>
    
    <%
        Order order = (Order) request.getAttribute("order"); // Nhận đối tượng order từ servlet
        if (order != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    %>

    <div class="order-details mt-4">
        <h2>Chi tiết đơn hàng</h2>
        <ul class="list-group">
            <li class="list-group-item"><strong>Mã đơn hàng:</strong> <%= order.getId() %></li>
            <li class="list-group-item"><strong>Tên khách hàng:</strong> <%= order.getCustomerName() %></li>
            <li class="list-group-item"><strong>Nhân viên phụ trách:</strong> <%= order.getEmployeeIdIncharge() %></li>
            <li class="list-group-item"><strong>Ngày tạo đơn:</strong> <%= dateFormat.format(order.getCreatedDate()) %></li>
            <li class="list-group-item"><strong>Trạng thái:</strong> <%= order.getStatusString() %></li>
            <li class="list-group-item"><strong>Giá trị trước giảm giá:</strong> <%= order.getOrderValueBeforeDiscount() %> VND</li>
            <li class="list-group-item"><strong>Phần trăm giảm giá:</strong> <%= order.getTotalDiscountPercenTage() * 100 %>%</li>
            <li class="list-group-item"><strong>Giá trị sau giảm giá:</strong> <%= order.getOrderValueAfterDiscount() %> VND</li>
        </ul>
    </div>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/homeController" class="btn btn-primary">Quay Lại</a>
    </div>

    <%
        } else {
    %>
        <p class="text-danger text-center">Không tìm thấy thông tin đơn hàng. Vui lòng kiểm tra lại.</p>
    <%
        }
    %>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
