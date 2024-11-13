<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cập Nhật Đơn Hàng</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">Cập Nhật Đơn Hàng</h1>

            <form action="${pageContext.request.contextPath}/updateOrderPending" method="post">
                <input type="hidden" name="orderId" value="${order.id}">

                <div class="form-group">
                    <label for="cusName">Tên Khách Hàng</label>
                    <input type="text" id="cusName" name="cusName" class="form-control" value="${order.customerName}">
                </div>

                <div class="form-group">
                    <label>Ngày Tạo</label>
                    <input type="text" class="form-control" value="${order.createdDate}" readonly>
                </div>

                <div class="form-group">
                    <label>Ngày Xuất Hàng</label>
                    <input type="date" name="exportDate" class="form-control" value="${order.exportedDate != null ? order.exportedDate.toLocalDate() : ''}">
                </div>

                <div class="form-group">
                    <label>Ngày Thanh Toán</label>
                    <input type="date" name="payDate" class="form-control" value="${order.payDate != null ? order.payDate.toLocalDate() : ''}">
                </div>

                <button type="submit" class="btn btn-primary">Cập Nhật</button>
                <a href="${pageContext.request.contextPath}/pendingOrder" class="btn btn-secondary">Quay Lại</a>
            </form>
        </div>
    </body>
</html>
