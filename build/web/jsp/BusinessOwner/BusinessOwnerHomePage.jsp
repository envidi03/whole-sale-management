<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng Điều Khiển Báo Cáo Đơn Hàng</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
        <style>
            body {
                background-color: #f0f3f7;
                font-family: 'Arial', sans-serif;
            }
            h1, h2 {
                color: #0044cc;
                font-weight: bold;
                text-align: center;
            }
            h1 {
                font-size: 2.5rem;
                margin-top: 20px;
            }
            .stat-card {
                border-radius: 12px;
                color: #ffffff;
                font-weight: bold;
                padding: 20px;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                text-align: center;
            }
            .stat-card:hover {
                transform: scale(1.05);
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
            }
            .stat-card-info {
                background: linear-gradient(135deg, #17a2b8, #48c6ef);
            }
            .stat-card-success {
                background: linear-gradient(135deg, #28a745, #85d65e);
            }
            .stat-card-warning {
                background: linear-gradient(135deg, #ffc107, #ffd460);
            }
            .stat-card-danger {
                background: linear-gradient(135deg, #dc3545, #ff6f6f);
            }

            .filter-card {
                background-color: #eaf0f9;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 30px;
            }
            .btn-primary {
                border-radius: 20px;
                padding: 10px 25px;
                font-weight: bold;
                background: linear-gradient(90deg, #0044cc, #4a69bd);
                color: #ffffff;
            }
            .btn-primary:hover {
                background: linear-gradient(90deg, #4a69bd, #0044cc);
            }
            .table-hover tbody tr:hover {
                background-color: #f1f7ff;
            }
            .table thead {
                background: linear-gradient(90deg, #0044cc, #6c757d);
                color: white;
            }
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }

            .back-button {
                display: inline-block;
                border-radius: 20px;
                padding: 10px 30px;
                font-weight: bold;
                font-size: 1rem;
                background: linear-gradient(90deg, #0044cc, #007bff);
                color: #ffffff;
                text-align: center;
                text-decoration: none;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
                transition: all 0.3s ease;
                margin-top: 20px;
            }
            .back-button:hover {
                background: linear-gradient(90deg, #007bff, #0044cc);
                transform: translateY(-2px);
                box-shadow: 0 6px 12px rgba(0, 0, 0, 0.25);
            }
            .pagination .page-item.active .page-link {
                background-color: #0044cc;
                border-color: #0044cc;
            }
            .pagination .page-link {
                color: #0044cc;
            }
            .pagination .page-link:hover {
                background-color: #e2e6ea;
                color: #0044cc;
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <h1><i class="bi bi-bar-chart-line-fill"></i> Bảng Điều Khiển Báo Cáo Đơn Hàng</h1>
            <h2 class="mb-5">Danh Sách Báo Cáo Đơn Hàng</h2>

            <!-- Hiển thị Số Liệu Thống Kê -->
            <div class="row text-center mb-5">
                <div class="col-md-3 mb-4">
                    <div class="card stat-card stat-card-info p-3">
                        <h5><i class="bi bi-cart-check-fill"></i> Tổng Số Đơn Hàng Thành Công</h5>
                        <p class="card-text display-6">${statistics.totalSuccessOrders}</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="card stat-card stat-card-success p-3">
                        <h5><i class="bi bi-currency-dollar"></i> Tổng Giá Trị Đơn Hàng Thành Công</h5>
                        <p class="card-text display-6">${statistics.totalSuccessOrderValue}</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="card stat-card stat-card-warning p-3">
                        <h5><i class="bi bi-exclamation-triangle-fill"></i> Tổng Số Đơn Hàng Nợ Quá Hạn</h5>
                        <p class="card-text display-6">${statistics.totalOverdueDebtOrders}</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="card stat-card stat-card-danger p-3">
                        <h5><i class="bi bi-archive-fill"></i> Tổng Số Đơn Hàng Công Nợ</h5>
                        <p class="card-text display-6">${statistics.totalIndebtOrders}</p>
                    </div>
                </div>
            </div>

            <!-- Form Lọc và Sắp Xếp -->
            <div class="filter-card">
                <h3 class="text-center text-primary mb-4">Lọc và Sắp Xếp Báo Cáo</h3>
                <form action="BusinessOwnerDashboardController" method="get">
                    <input type="hidden" name="action" value="filterReports">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="month" class="form-label">Tháng</label>
                            <input type="number" name="month" id="month" class="form-control" placeholder="Nhập Tháng (1-12)">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="year" class="form-label">Năm</label>
                            <input type="number" name="year" id="year" class="form-control" placeholder="Nhập Năm (VD: 2023)">
                        </div>
                    </div>
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary mt-3">Lọc và Sắp Xếp</button>
                    </div>
                </form>
            </div>

            <!-- Hiển Thị Bảng Báo Cáo Đơn Hàng -->
            <div class="table-responsive mt-5">
                <table class="table table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tháng</th>
                            <th>Năm</th>
                            <th>Tổng Giá Trị Đơn Hàng Thành Công</th>
                            <th>Tổng Giá Trị Đơn Hàng Công Nợ</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty orderReports}">
                                <c:forEach var="report" items="${orderReports}">
                                    <tr>
                                        <td>${report.id}</td>
                                        <td>${report.month}</td>
                                        <td>${report.year}</td>
                                        <td>${report.totalValueOfNewSuccessOrder}</td>
                                        <td>${report.totalValueOfNewIndebtOrder}</td>
                                        <td>
                                            <a href="BusinessOwnerDashboardController?action=viewReportDetails&reportId=${report.id}" class="btn btn-info btn-sm"><i class="bi bi-eye-fill"></i> Xem Chi Tiết</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="6" class="text-center text-danger">Không có báo cáo nào được tìm thấy.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- Back Button -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="btn btn-secondary back-button">Quay Lại</a>
            </div>

            <!-- Pagination -->
            <div class="d-flex justify-content-center mt-4">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${noOfPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="BusinessOwnerDashboardController?action=filterReports&page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
