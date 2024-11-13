<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bảng Điều Khiển Báo Cáo Nhân Viên Bị Sa Thải</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f6f9;
                font-family: Arial, sans-serif;
            }
            h1, h2 {
                color: #333;
            }
            .container {
                margin-top: 40px;
            }
            .table th {
                background-color: #343a40;
                color: #fff;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
                border-radius: 4px;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .card {
                border: none;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .form-check-label {
                font-weight: 600;
                color: #333;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Bảng Điều Khiển Báo Cáo Nhân Viên Bị Sa Thải</h1>
            <h2 class="text-center mb-4">Danh Sách Báo Cáo Nhân Viên Bị Sa Thải</h2>

            <!-- Nút Quay Lại -->
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="btn btn-secondary">Quay lại</a>
            </div>

            <!-- Form Lọc Gửi Tham Số Đến Servlet -->
            <form action="WarehouseManagerDashboardController" method="get" class="mb-4">
                <input type="hidden" name="action" value="viewFiredEmployeeReports">

                <div class="card p-4">
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <label for="warehouseReportId">Tìm kiếm theo Mã Báo Cáo Kho</label>
                            <input type="number" name="warehouseReportId" id="warehouseReportId" class="form-control" placeholder="Nhập Mã Báo Cáo Kho">
                        </div>
                        <div class="col-md-4 mb-3">
                            <label>Tổng Số Nhân Viên Bị Sa Thải Mới</label>
                            <div class="input-group">
                                <input type="number" name="totalFiredEmployeesMin" class="form-control" placeholder="Tối thiểu">
                                <input type="number" name="totalFiredEmployeesMax" class="form-control" placeholder="Tối đa">
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="month">Tháng</label>
                            <input type="number" name="month" id="month" class="form-control" placeholder="Nhập Tháng (1-12)">
                        </div>
                        <div class="col-md-4 mb-3">
                            <label for="year">Năm</label>
                            <input type="number" name="year" id="year" class="form-control" placeholder="Nhập Năm (VD: 2023)">
                        </div>
                    </div>

                    <div class="mt-3">
                        <label>Sắp xếp theo:</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sortBy" value="warehouseReportId" checked>
                            <label class="form-check-label">Mã Báo Cáo Kho</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sortBy" value="totalFiredEmployees">
                            <label class="form-check-label">Tổng Số Nhân Viên Bị Sa Thải</label>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary mt-3">Lọc Kết Quả</button>
                </div>
            </form>

            <!-- Hiển Thị Bảng Báo Cáo Nhân Viên Bị Sa Thải -->
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Mã Báo Cáo Kho</th>
                            <th>Tổng Số Nhân Viên Bị Sa Thải Mới</th>
                            <th>Tháng</th>
                            <th>Năm</th>
                            <th>Loại Báo Cáo</th>
                            <th>Xem</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty firedEmployeeReports}">
                                <c:forEach var="report" items="${firedEmployeeReports}">
                                    <tr>
                                        <td>${report.warehouseReportId}</td>
                                        <td>${report.totalNumberOfNewFiredEmployee}</td>
                                        <td>${report.month}</td>
                                        <td>${report.year}</td>
                                        <td>${report.reportType}</td>
                                        <td>
                                            <a href="<c:url value='/WarehouseManagerDashboardController' />?action=viewFiredEmployeeReportDetails&warehouseReportId=${report.warehouseReportId}" 
                                               class="btn btn-sm btn-primary">Xem</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center">Không có báo cáo nhân viên bị sa thải nào được tìm thấy.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Bootstrap JS, Popper.js, và jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
