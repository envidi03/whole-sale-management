<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bảng Điều Khiển Báo Cáo Hợp Đồng</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f6f9;
                font-family: Arial, sans-serif;
            }
            h1, h2 {
                color: #333;
            }
            .table th {
                background-color: #1d3557;
                color: #fff;
            }
            .btn-primary, .submit-btn {
                background-color: #457b9d;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                color: #fff;
            }
            .btn-primary:hover, .submit-btn:hover {
                background-color: #1d3557;
            }
            .card {
                border: none;
                border-radius: 8px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #ffffff;
            }
            .form-control, .form-check-input {
                border-radius: 4px;
            }
            .form-check-label {
                color: #555;
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <h1 class="text-center mb-4">Bảng Điều Khiển Báo Cáo Hợp Đồng</h1>
            <h2 class="text-center mb-4">Danh Sách Báo Cáo Hợp Đồng</h2>

            <!-- Nút Back -->
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="btn btn-secondary">Quay lại</a>
            </div>

            <!-- Form Lọc Gửi Tham Số Đến Servlet -->
            <form action="WarehouseManagerDashboardController" method="get" class="mb-4">
                <input type="hidden" name="action" value="viewContractReports">
                <div class="card p-4 mb-4">
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <label for="warehouseReportId">Tìm kiếm theo Mã Báo Cáo Kho</label>
                            <input type="number" name="warehouseReportId" id="warehouseReportId" class="form-control" placeholder="Nhập Mã Báo Cáo Kho">
                        </div>
                        <div class="col-md-4 mb-3">
                            <label>Tổng Số Hợp Đồng Mới</label>
                            <div class="input-group">
                                <input type="number" name="totalContractsMin" class="form-control" placeholder="Tối thiểu">
                                <input type="number" name="totalContractsMax" class="form-control" placeholder="Tối đa">
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <label>Tổng Giá Trị Hợp Đồng Mới</label>
                            <div class="input-group">
                                <input type="number" name="totalValueMin" class="form-control" placeholder="Tối thiểu">
                                <input type="number" name="totalValueMax" class="form-control" placeholder="Tối đa">
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
                            <input class="form-check-input" type="radio" name="sortBy" value="totalContracts">
                            <label class="form-check-label">Tổng Số Hợp Đồng Mới</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="sortBy" value="totalValue">
                            <label class="form-check-label">Tổng Giá Trị Hợp Đồng Mới</label>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Gửi</button>
                </div>
            </form>

            <!-- Hiển Thị Bảng Báo Cáo Hợp Đồng -->
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Mã Báo Cáo Kho</th>
                            <th>Tổng Số Hợp Đồng Mới</th>
                            <th>Tổng Giá Trị Hợp Đồng Mới</th>
                            <th>Tháng</th>
                            <th>Năm</th>
                            <th>Loại Báo Cáo</th>
                            <th>Xem</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty contractReports}">
                                <c:forEach var="report" items="${contractReports}">
                                    <tr>
                                        <td>${report.warehouseReportId}</td>
                                        <td>${report.totalNumberOfNewContract}</td>
                                        <td>${report.totalValueOfNewContract}</td>
                                        <td>${report.month}</td>
                                        <td>${report.year}</td>
                                        <td>${report.reportType}</td>
                                        <td><a href="WarehouseManagerDashboardController?action=viewContractReportDetails&warehouseReportId=${report.warehouseReportId}" class="btn btn-sm btn-primary">Xem</a></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7" class="text-center">Không có báo cáo hợp đồng nào được tìm thấy.</td>
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
