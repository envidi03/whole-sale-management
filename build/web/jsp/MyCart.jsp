<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 40px;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #495057;
            font-size: 2rem;
            margin-bottom: 20px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .btn {
            margin: 0 5px;
        }
        .total-row td {
            font-weight: bold;
            font-size: 1.1rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center">Giỏ Hàng</h1>
        <table class="table table-hover table-bordered" id="cartTable">
            <thead class="thead-light">
                <tr class="text-center">
                    <th>Số Thứ Tự</th>
                    <th>ID Sản Phẩm</th>
                    <th>Số Lượng</th>
                    <th>Giá Đơn Vị</th>
                    <th>Tổng Giá</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="o" value="${sessionScope.cart}" />
                <c:forEach items="${o.items}" var="i" varStatus="status">
                    <tr class="text-center">
                        <td>${status.index + 1}</td>
                        <td>${i.consignment.id}</td>
                        <td>${i.quantity}</td>
                        <td><fmt:formatNumber pattern="#,##0.##" value="${i.price}" /></td>
                        <td><fmt:formatNumber pattern="#,##0.##" value="${i.price * i.quantity}" /></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/process" method="post" class="d-inline">
                                <input type="hidden" name="id" value="${i.consignment.id}">
                                <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty o.items}">
                    <tr>
                        <td colspan="6" class="text-center text-muted">Không có sản phẩm nào trong giỏ hàng</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="row mt-4">
            <div class="col text-center">
                <form action="${pageContext.request.contextPath}/checkout" method="post" class="d-inline">
                    <button type="submit" class="btn btn-primary btn-lg">Đặt Đơn Hàng</button>
                </form>
                <a href="${pageContext.request.contextPath}/buy" class="btn btn-secondary btn-lg">Quay Lại</a>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

