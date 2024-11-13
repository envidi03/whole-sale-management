<%-- 
    Document   : ConsignmentDetail
    Created on : Nov 4, 2024, 12:52:40 PM
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
         <style>
        h1 {
            color: #495057;
            font-weight: 600;
        }
        
        .card-header {
            background-color: #f8f9fa;
            font-weight: bold;
            font-size: 1.1rem;
        }
        
        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        table th {
            text-align: center;
            background-color: #e9ecef;
        }
        
        table td {
            text-align: center;
        }
        
        .btn-secondary {
            margin-top: 20px;
        }
    </style>
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
                        <strong>${con.supplierName}</strong><br>
                        <div>${con.supplierPhone}</div>
                        <div>${con.supplierAddress}</div>
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
                            <tr>
                                <td>${con.id}</td>
                                <td>${con.productId}</td>
                                <td>${con.contractId}</td>
                                <td>${con.importPrice}</td>
                                <td>${con.sellingPrice}</td>
                                <td>${con.deliveryDate}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/OrderImportProductList?service=orderList" class="btn btn-secondary">Quay lại</a>
        </div>

        <!-- Liên kết tới Bootstrap JS và Popper.js -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
    </body>

</html>

