<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Sử Đặt Hàng</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa; /* Light background color */
        }
        .container {
            margin-top: 30px;
            background-color: #ffffff; /* White background for the container */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Softer shadow */
        }
        .table th, .table td {
            vertical-align: middle; /* Center content vertically */
        }
        .btn-primary {
            margin-top: 20px; /* Space above the button */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">Lịch Sử Đặt Hàng</h1>
        <table class="table table-striped table-bordered table-hover">
            <thead class="thead-light">
                <tr>
                    <th>Số Thứ Tự</th>
                    <th>Tên Khách Hàng</th>
                    <th>ID Nhân Viên Thực Hiện</th>
                    <th>Ngày Tạo</th>
                    <th>Trạng Thái</th>
                    <th>Ngày Xuất Hàng</th>
                    <th>Tổng Số Tiền Trước Khi Giảm Giá</th>
                    <th>Giảm Giá (%)</th>
                    <th>Tổng Số Tiền Sau Khi Giảm Giá</th>
                    <th>Ngày Thanh Toán</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listO}" var="o">
                    <tr>
                        <td>${o.id}</td>
                        <td>${o.customerName}</td>
                        <td>${employeeNames[o.employeeIdIncharge]}</td>
                        <td>${o.createdDate}</td>
                        <td>${o.getStatusString()}</td>
                        <td>${o.exportedDate}</td>
                        <td>${o.orderValueBeforeDiscount}</td>
                        <td>${o.totalDiscountPercenTage}</td>
                        <td>${o.orderValueAfterDiscount}</td>
                        <td>${o.payDate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/homeController" class="btn btn-primary">Quay Lại</a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
