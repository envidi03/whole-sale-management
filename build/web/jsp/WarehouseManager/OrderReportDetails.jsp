<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Báo Cáo Đơn Hàng</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
            }
            h2, h3 {
                color: #333;
                margin-top: 20px;
            }
            .table th, .table td {
                text-align: center;
            }
            .section {
                margin-bottom: 40px;
            }
            .table-summary {
                margin-bottom: 30px;
                background-color: #e9ecef;
                padding: 15px;
                border-radius: 8px;
            }
            .table-summary h3 {
                color: #495057;
            }
        </style>
    </head>
    <body class="container mt-5">
        <h2 class="text-center mb-4">Chi Tiết Báo Cáo Đơn Hàng</h2>

        <!-- Bảng tóm tắt báo cáo đơn hàng -->
<!--        <div class="section table-summary">
            <h3 class="mb-3">Tóm Tắt Báo Cáo</h3>
            <table class="table table-bordered">
                <tbody>
                    <tr><th>ID Báo Cáo Kho</th><td>${orderReport.warehouseReportId}</td></tr>
                    <tr><th>Tổng Số Đơn Hàng Thành Công</th><td>${orderReport.totalNumberOfNewSuccessOrder}</td></tr>
                    <tr><th>Tổng Giá Trị Đơn Hàng Thành Công</th><td>${orderReport.totalValueOfNewSuccessOrder}</td></tr>
                    <tr><th>Tổng Số Đơn Hàng Nợ Quá Hạn</th><td>${orderReport.totalNumberOfNewOverdueDebtOrder}</td></tr>
                    <tr><th>Tổng Giá Trị Đơn Hàng Nợ Quá Hạn</th><td>${orderReport.totalValueOfNewOverdueDebtOrder}</td></tr>
                    <tr><th>Tổng Số Đơn Hàng Công Nợ</th><td>${orderReport.totalNumberOfNewIndebtOrder}</td></tr>
                    <tr><th>Tổng Giá Trị Đơn Hàng Công Nợ</th><td>${orderReport.totalValueOfNewIndebtOrder}</td></tr>
                </tbody>
            </table>
        </div>-->

        <!-- Hiển thị các đơn hàng thành công -->
        <div class="section">
            <h3 class="mb-3">Đơn Hàng Thành Công</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-primary">
                    <tr>
                        <th>ID Đơn Hàng</th>
                        <th>Tên Khách Hàng</th>
                        <th>Nhân Viên Phụ Trách</th>
                        <th>Ngày Tạo</th>
                        <th>Giá Trị Đơn Hàng (Sau Giảm Giá)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${successOrders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.customerName}</td>
                            <td>${order.employeeIdIncharge}</td>
                            <td>${order.createdDate}</td>
                            <td>${order.orderValueAfterDiscount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Hiển thị các đơn hàng nợ quá hạn -->
        <div class="section">
            <h3 class="mb-3">Đơn Hàng Nợ Quá Hạn</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-warning">
                    <tr>
                        <th>ID Đơn Hàng</th>
                        <th>Tên Khách Hàng</th>
                        <th>Nhân Viên Phụ Trách</th>
                        <th>Ngày Tạo</th>
                        <th>Giá Trị Đơn Hàng (Sau Giảm Giá)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${overdueDebtOrders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.customerName}</td>
                            <td>${order.employeeIdIncharge}</td>
                            <td>${order.createdDate}</td>
                            <td>${order.orderValueAfterDiscount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Hiển thị các đơn hàng công nợ -->
        <div class="section">
            <h3 class="mb-3">Đơn Hàng Công Nợ</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead class="table-danger">
                    <tr>
                        <th>ID Đơn Hàng</th>
                        <th>Tên Khách Hàng</th>
                        <th>Nhân Viên Phụ Trách</th>
                        <th>Ngày Tạo</th>
                        <th>Giá Trị Đơn Hàng (Sau Giảm Giá)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${inDebtOrders}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.customerName}</td>
                            <td>${order.employeeIdIncharge}</td>
                            <td>${order.createdDate}</td>
                            <td>${order.orderValueAfterDiscount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
