<%-- 
    Document   : ImportProductDetail
    Created on : Oct 22, 2024, 1:42:11 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Đơn Hàng</title>
        <!-- Liên kết tới Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/StorageLocation.css">
    </head>

    <body>
        <div class="container mt-5">
            <h1>Thông tin lô hàng</h1>
            <div class="card mb-4">
                <div class="card-header">
                    Thông tin nhà cung cấp
                </div>
                <div class="card-body">
                    <div class="mb-3">
                        <strong>${listC[0].supplierName}</strong><br>
                        <div>${listC[0].supplierPhone}</div>
                        <div>${listC[0].supplierAddress}</div>
                    </div>
                </div>


            </div>

            <div class="card">
                <div class="card-header">
                    Thông tin sản phẩm
                </div>
                <div class="card-body">
                    <!-- Chi tiết sản phẩm -->
                    <h4>Chi tiết sản phẩm</h4>
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>ID Lô Hàng</th>
                                <th>ID Sản Phẩm</th>
                                <th>ID Hợp Đồng</th>
                                <th>Giá Nhập (VNĐ)</th>
                                <th>Giá Bán (VNĐ)</th>
                                <th>Ngày Nhập</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="con" items="${listC}">
                                <tr>
                                    <td>${con.id}</td>
                                    <td>${con.productId}</td>
                                    <td>${con.contractId}</td>
                                    <td>${con.importPrice}</td>
                                    <td>${con.sellingPrice}</td>
                                    <td>${con.deliveryDate}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Các nút thao tác -->
            <div class="mt-4">
                <form action="ImportProduct" method="post">
                    <input type="hidden" name="service" value="confirmWarehouse">
                    <input type="hidden" name="contractId" value="${listC[0].contractId}"> <!-- Chỉ cần gửi contractId -->

                    <div class="mb-3">
                        <label for="warehouseSelect" class="form-label">Chọn kho</label>
                        <select class="form-select" id="warehouseSelect" name="warehouseId">
                            <c:forEach items="${listW}" var="w">
                                <option value="${w.id}">${w.name}</option> 
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success">Nhập kho</button>
                </form>

                <form action="ImportProduct" method="post" style="display:inline;">
                    <input type="hidden" name="service" value="cancelOrder">
                    <input type="hidden" name="contractId" value="${listC[0].contractId}">
                    <button type="submit" class="btn btn-warning">Hủy đơn</button>
                </form>

                <a href="${pageContext.request.contextPath}/ImportProduct?service=orderList" class="btn btn-secondary">Quay lại</a>
            </div>
        </div>

        <!-- Liên kết tới Bootstrap JS và Popper.js -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
    </body>

</html>
