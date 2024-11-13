<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi Tiết Báo Cáo Hợp Đồng</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: Arial, sans-serif;
            }
            h1 {
                color: #333;
                margin-bottom: 20px;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .table th {
                background-color: #1d3557;
                color: white;
            }
            .table td {
                vertical-align: middle;
            }
            .form-inline .form-group {
                margin-right: 15px;
            }
            .card {
                border: none;
                border-radius: 8px;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #ffffff;
            }
            .filter-label {
                font-weight: 600;
                color: #555;
            }
            .table-responsive {
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <h1 class="text-center">Chi Tiết Báo Cáo Hợp Đồng</h1>
            <hr>

            <!-- Form lọc -->
            <div class="card p-4 mb-4">
                <form action="WarehouseManagerDashboardController" method="get" class="form-inline">
                    <input type="hidden" name="action" value="viewContractReportDetails">
                    <input type="hidden" name="warehouseReportId" value="${contractReport.warehouseReportId}">

                    <div class="form-group">
                        <label for="status" class="filter-label mr-2">Trạng Thái:</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">Tất cả</option>
                            <option value="1" ${statusParam == '1' ? 'selected' : ''}>Chờ phê duyệt</option>
                            <option value="2" ${statusParam == '2' ? 'selected' : ''}>Chưa giao hàng</option>
                            <option value="3" ${statusParam == '3' ? 'selected' : ''}>Đã giao hàng thành công</option>
                            <option value="4" ${statusParam == '4' ? 'selected' : ''}>Đã hủy</option>
                            <option value="5" ${statusParam == '5' ? 'selected' : ''}>Thất bại</option>
                            <option value="6" ${statusParam == '6' ? 'selected' : ''}>Yêu cầu chỉnh sửa</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="supplierName" class="filter-label mr-2">Nhà Cung Cấp:</label>
                        <input type="text" id="supplierName" name="supplierName" class="form-control" 
                               value="${supplierNameParam}" placeholder="Nhập tên nhà cung cấp">
                    </div>

                    <button type="submit" class="btn btn-primary">Lọc</button>
                </form>
            </div>

            <!-- Hiển thị thông tin báo cáo hợp đồng -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <p><strong>Mã Báo Cáo Kho:</strong> ${contractReport.warehouseReportId}</p>
                </div>
                <div class="col-md-4">
                    <p><strong>Tổng Số Hợp Đồng Mới:</strong> ${contractReport.totalNumberOfNewContract}</p>
                </div>
                <div class="col-md-4">
                    <p><strong>Tổng Giá Trị Hợp Đồng Mới:</strong> ${contractReport.totalValueOfNewContract}</p>
                </div>
            </div>

            <!-- Hiển thị danh sách hợp đồng -->
            <h2>Danh Sách Hợp Đồng</h2>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>Mã Hợp Đồng</th>
                            <th>Ngày Giao Hàng</th>
                            <th>Ngày Thực Tế Giao Hàng</th>
                            <th>Giá Trị Hợp Đồng</th>
                            <th>Trạng Thái</th>
                            <th>Mô Tả</th>
                            <th>Nhà Cung Cấp</th>
                            <th>Kho</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contract" items="${contracts}">
                            <tr>
                                <td>${contract.id}</td>
                                <td>${contract.contractDeliveryDate}</td>
                                <td>${contract.actualDeliveryDate != null ? contract.actualDeliveryDate : "Chưa giao hàng"}</td>
                                <td>${contract.contractValue}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${contract.status == 1}">Chờ phê duyệt</c:when>
                                        <c:when test="${contract.status == 2}">Chưa giao hàng</c:when>
                                        <c:when test="${contract.status == 3}">Đã giao hàng thành công</c:when>
                                        <c:when test="${contract.status == 4}">Đã hủy</c:when>
                                        <c:when test="${contract.status == 5}">Thất bại</c:when>
                                        <c:when test="${contract.status == 6}">Yêu cầu chỉnh sửa</c:when>
                                        <c:otherwise>Trạng thái không xác định</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${contract.description != null ? contract.description : "Không có mô tả"}</td>
                                <td>${contract.supplierName}</td>
                                <td>${contract.warehouseName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <a href="WarehouseManagerDashboardController?action=viewContractReports" class="btn btn-primary mt-3">Quay Lại</a>
        </div>

        <!-- Bootstrap JS, Popper.js, and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
