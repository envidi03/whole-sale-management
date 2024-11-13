<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Bảng Điều Khiển Báo Cáo Đơn Hàng</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            h1, h2 {
                color: #333;
            }
            .table th {
                background-color: #004080;
                color: #fff;
            }
            .card-header {
                background-color: #004080;
                color: #fff;
            }
            .submit-btn, .btn-primary {
                background-color: #007bff;
                color: #fff;
            }
            .btn-secondary.exit {
                margin-bottom: 20px;
            }
            .input-group > input {
                border-radius: 0.25rem;
            }
            .form-check-label {
                font-weight: normal;
            }
        </style>
    </head>
    <body>
        <div class="container my-4">
            <h1 class="text-center">Bảng Điều Khiển Báo Cáo Đơn Hàng</h1>
            <h2 class="text-center mb-4">Danh Sách Báo Cáo Đơn Hàng</h2>

            <!-- Nút Back -->
            <a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="btn btn-secondary exit">Quay lại</a>

            <!-- Form Lọc Gửi Tham Số Đến Servlet -->
            <form action="WarehouseManagerDashboardController" method="get" class="mb-4">
                <input type="hidden" name="action" value="viewOrderReports">

                <div class="card shadow-sm">
                    <div class="card-header">
                        <h5>Lọc Báo Cáo Đơn Hàng</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="warehouseReportId">Tìm kiếm theo Mã Báo Cáo Kho</label>
                                <input type="number" name="warehouseReportId" id="warehouseReportId" class="form-control" placeholder="Nhập Mã Báo Cáo Kho">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label>Tổng Số Đơn Hàng Thành Công Mới</label>
                                <div class="input-group">
                                    <input type="number" name="totalSuccessOrdersMin" class="form-control" placeholder="Tối thiểu">
                                    <input type="number" name="totalSuccessOrdersMax" class="form-control" placeholder="Tối đa">
                                </div>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label>Tổng Giá Trị Đơn Hàng Thành Công Mới</label>
                                <div class="input-group">
                                    <input type="number" name="totalValueSuccessOrdersMin" class="form-control" placeholder="Tối thiểu">
                                    <input type="number" name="totalValueSuccessOrdersMax" class="form-control" placeholder="Tối đa">
                                </div>
                            </div>
                            <!-- Các bộ lọc bổ sung tương tự -->
                            <div class="col-md-4 mb-3">
                                <label for="month">Tháng</label>
                                <input type="number" name="month" id="month" class="form-control" placeholder="Nhập Tháng (1-12)">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="year">Năm</label>
                                <input type="number" name="year" id="year" class="form-control" placeholder="Nhập Năm (VD: 2023)">
                            </div>
                        </div>

                        <!-- Sắp xếp theo -->
                        <div class="mt-3">
                            <label>Sắp xếp theo:</label>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="sortBy" value="warehouseReportId" checked>
                                <label class="form-check-label">Mã Báo Cáo Kho</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="sortBy" value="totalSuccessOrders">
                                <label class="form-check-label">Tổng Số Đơn Hàng Thành Công</label>
                            </div>
                            <!-- Các lựa chọn sắp xếp bổ sung tương tự -->
                        </div>
                        <button type="submit" class="btn btn-primary mt-3">Gửi</button>
                    </div>
                </div>
            </form>

            <!-- Hiển Thị Bảng Báo Cáo Đơn Hàng -->
            <div class="table-responsive">
                <table class="table table-striped table-bordered mt-4">
                    <thead>
                        <tr>
                            <th>Mã Báo Cáo Kho</th>
                            <th>Tổng Số Đơn Hàng Thành Công Mới</th>
                            <th>Tổng Giá Trị Đơn Hàng Thành Công Mới</th>
                            <th>Tổng Số Đơn Hàng Nợ Quá Hạn Mới</th>
                            <th>Tổng Giá Trị Đơn Hàng Nợ Quá Hạn Mới</th>
                            <th>Tổng Số Đơn Hàng Công Nợ Mới</th>
                            <th>Tổng Giá Trị Đơn Hàng Công Nợ Mới</th>
                            <th>Tháng</th>
                            <th>Năm</th>
                            <th>Loại Báo Cáo</th>
                            <th>Xem</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty orderReports}">
                                <c:forEach var="report" items="${orderReports}">
                                    <tr>
                                        <td>${report.warehouseReportId}</td>
                                        <td>${report.totalNumberOfNewSuccessOrder}</td>
                                        <td>${report.totalValueOfNewSuccessOrder}</td>
                                        <td>${report.totalNumberOfNewOverdueDebtOrder}</td>
                                        <td>${report.totalValueOfNewOverdueDebtOrder}</td>
                                        <td>${report.totalNumberOfNewIndebtOrder}</td>
                                        <td>${report.totalValueOfNewIndebtOrder}</td>
                                        <td>${report.month}</td>
                                        <td>${report.year}</td>
                                        <td>${report.reportType}</td>
                                        <td>
                                            <a href="WarehouseManagerDashboardController?action=viewOrderReportDetails&warehouseReportId=${report.warehouseReportId}" class="btn btn-sm btn-primary">Xem</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="11" class="text-center">Không có báo cáo đơn hàng nào được tìm thấy.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
