<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Bảng Điều Khiển Quản Lý Kho</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f6f9;
        }
        .container {
            display: flex;
        }
        .sidebar {
            width: 250px;
            background-color: #1d1f21;
            color: #f8f9fa;
            padding: 15px;
            height: 100vh;
            box-shadow: 2px 0 10px rgba(0, 0, 0, 0.2);
        }
        .sidebar h2 {
            text-align: center;
            color: #00b4d8;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .sidebar ul {
            list-style: none;
            padding: 0;
        }
        .sidebar ul li {
            margin: 15px 0;
        }
        .sidebar ul li a {
            text-decoration: none;
            color: #f8f9fa;
            display: flex;
            align-items: center;
            padding: 12px;
            border-radius: 8px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        .sidebar ul li a i {
            margin-right: 10px;
            font-size: 18px;
        }
        .sidebar ul li a:hover {
            background-color: #00b4d8;
            color: white;
        }
        .main-content {
            flex: 1;
            padding: 40px;
            background-color: #ffffff;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 15px;
            margin: 20px;
        }
        .main-content h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 15px;
        }
        .main-content p {
            font-size: 16px;
            color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h2>Quản Lý Kho</h2>
            <ul>
                <li><a href="WarehouseManagerDashboardController?action=viewContractReports"><i class="fas fa-file-contract"></i>Xem Báo Cáo Hợp Đồng</a></li>
                <li><a href="WarehouseManagerDashboardController?action=viewFiredEmployeeReports"><i class="fas fa-user-slash"></i>Xem Báo Cáo Nhân Viên Bị Sa Thải</a></li>
                <li><a href="WarehouseManagerDashboardController?action=viewOrderReports"><i class="fas fa-box"></i>Xem Báo Cáo Đơn Hàng</a></li>
                <li><a href="WarehouseManagerDashboardController?action=takeNote"><i class="fas fa-sticky-note"></i>Ghi Chú Công Việc</a></li>
                <li><a href="${pageContext.request.contextPath}/BusinessOwnerDashboardController" class="btn btn-secondary exit"><i class="fas fa-chart-line"></i>Số Liệu Thống Kê</a></li>
            </ul>
        </div>
        <div class="main-content">
            <h1>Chào Mừng Đến Bảng Điều Khiển Quản Lý Kho</h1>
            <p>Vui lòng chọn một báo cáo từ menu bên trái.</p>
        </div>
    </div>
</body>
</html>
