<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đơn Hàng Đang Chờ Phê Duyệt</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .container {
                margin-top: 30px;
                background-color: #ffffff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .btn {
                font-size: 0.8rem; /* Giảm kích thước nút */
            }
            .input-group input {
                font-size: 0.8rem; /* Giảm kích thước input */
            }
            .filter-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .filter-container form {
                flex: 1;
                margin-right: 10px;
                margin-bottom: 10px; /* Để có khoảng cách giữa các form */
            }
            .filter-container form:last-child {
                margin-right: 0; /* Không có khoảng cách ở form cuối */
            }

        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">Đơn Hàng Đang Chờ Phê Duyệt</h1>

            <div class="filter-container">
                <!-- Tìm kiếm theo khách hàng và nhân viên -->
                <form action="searchCustomerByName?index=1&targetPage=pending" method="post" class="mb-4">
                    <div class="input-group">
                        <input type="text" name="txtSearch" class="form-control" placeholder="Tìm kiếm tên khách hàng" value="${txtSearch}">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="submit">Tìm Kiếm</button>
                        </div>
                    </div>
                </form>
                <form action="searchEmployeeByName?index=1&action=pending" method="post" class="mb-4">
                    <div class="input-group">
                        <input type="text" name="txtSearch" class="form-control" placeholder="Tìm kiếm tên nhân viên" value="${txtSearch}">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="submit">Tìm Kiếm</button>
                        </div>
                    </div>
                </form>
                <!-- Lọc đơn hàng theo giá -->
                <form action="searchOrderByPrice?index=1&action=pending" method="post" class="mb-4">
                    <div class="input-group">
                        <input type="number" name="minPrice" class="form-control" placeholder="Giá Tối Thiểu" required>
                        <input type="number" name="maxPrice" class="form-control" placeholder="Giá Tối Đa" required>
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="submit">Lọc</button>
                        </div>
                    </div>
                </form>
                <!-- Lọc đơn hàng theo khoảng ngày -->
                <form id="dateFilterForm" onsubmit="return validateDateRange()" class="mb-4">
                    <div class="input-group">
                        <input type="date" id="startDate" class="form-control" required placeholder="Ngày Bắt Đầu">
                        <input type="date" id="endDate" class="form-control" required placeholder="Ngày Kết Thúc">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="button" onclick="filterOrders()">Lọc Theo Ngày Tạo</button>
                        </div>
                    </div>
                </form>

            </div>

            <!-- Sắp xếp đơn hàng theo giá -->
            <div class="text-center mb-3">

                <button class="btn btn-primary" onclick="sortProducts('asc')">Tổng Số Tiền Tăng Dần</button>
                <button class="btn btn-danger" onclick="sortProducts('desc')">Tổng Số Tiền Giảm Dần</button>

                <button class="btn btn-primary" onclick="sortByDate('asc')">Ngày Tạo Tăng Dần</button>
                <button class="btn btn-danger" onclick="sortByDate('desc')">Ngày Tạo Giảm Dần</button>
            </div>

            <!-- Bảng đơn hàng -->
            <table class="table table-striped table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>Số Thứ Tự</th>
                        <th>Tên Khách Hàng</th>
                        <th>Tên Nhân Viên Thực Hiện</th>
                        <th>Ngày Tạo</th>
                        <th>Trạng Thái</th>
                        <th>Ngày Xuất Hàng</th>
                        <th>Tổng Số Tiền Trước Khi Giảm Giá</th>
                        <th>Giảm Giá (%)</th>
                        <th>Tổng Số Tiền Sau Khi Giảm Giá</th>
                        <th>Ngày Thanh Toán</th>
                        <th>Cập Nhập</th>
                        <th>Hủy Đơn Đặt Hàng</th>
                        <th>Thanh Toán</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pendingOrders}" var="o">
                        <tr>
                            <td>${o.id}</td>
                            <td>${o.customerName}</td>
                            <td>${employeeNames[o.employeeIdIncharge]}</td>
                            <td>${o.createdDate}</td>
                            <td>${o.getStatusString()}</td>
                            <td>${o.exportedDate}</td>
                            <td>${o.orderValueBeforeDiscount}</td>
                            <td>${o.totalDiscountPercenTage}</td>
                            <td>${o.orderValueAfterDiscount}</td>
                            <td>${o.payDate}</td>
                            
                            <td>
                                <form action="${pageContext.request.contextPath}/updateOrderPending" method="get">
                                    <input type="hidden" name="orderId" value="${o.id}">
                                    <button type="submit" class="btn btn-primary">Cập Nhật</button>
                                </form>
                            </td>

                            <<td>
                                <form action="${pageContext.request.contextPath}/deleteOrder" method="post" onsubmit="return confirmDelete();">
                                    <input type="hidden" name="orderId" value="${o.id}">
                                    <button type="submit" class="btn btn-primary">Xóa</button>
                                </form>
                            </td>


                            <td>
                                <form action="ajaxServlet" method="post">
                                    <input type="hidden" name="orderId" value="${o.id}"/>
                                    <input type="hidden" name="amount" value="${o.orderValueAfterDiscount}"/>
                                    <button type="submit" class="btn btn-primary" >Thanh toán</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Phân trang cho tìm kiếm sản phẩm -->
            <nav class="my-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${end}" var="i">
                        <li class="page-item">
                            <a class="page-link" href="searchCustomerByName?index=${i}&targetPage=pending&txtSearch=${txtSearch}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
            
            

            <!-- Phân trang -->
            <nav class="my-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${endPage}" var="i">
                        <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                            <a class="page-link" href="pendingOrder?index=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>

            <!-- Nút quay lại -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/homeController" class="btn btn-primary">Quay Lại</a>
            </div>
        </div>

        <!-- JavaScript -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                    // Kiểm tra hợp lệ cho khoảng ngày
                                    function validateDateRange() {
                                        const startDate = new Date(document.getElementById("startDate").value);
                                        const endDate = new Date(document.getElementById("endDate").value);
                                        if (startDate > endDate) {
                                            alert("Ngày bắt đầu không thể lớn hơn ngày kết thúc.");
                                            return false;
                                        }
                                        return true;
                                    }

                                    // Lọc đơn hàng theo ngày tạo
                                    function filterOrders() {
                                        if (!validateDateRange())
                                            return;

                                        const startDate = new Date(document.getElementById("startDate").value);
                                        const endDate = new Date(document.getElementById("endDate").value);
                                        const rows = document.querySelectorAll(".table tbody tr");

                                        rows.forEach(row => {
                                            const orderDate = new Date(row.cells[3].innerText); // Cột ngày tạo (index 3)
                                            if (orderDate >= startDate && orderDate <= endDate) {
                                                row.style.display = ""; // Hiển thị hàng
                                            } else {
                                                row.style.display = "none"; // Ẩn hàng
                                            }
                                        });
                                    }

                                    // Sắp xếp sản phẩm theo giá
                                    function sortProducts(order) {
                                        const table = document.querySelector(".table tbody");
                                        const rows = Array.from(table.rows);

                                        rows.sort((a, b) => {
                                            const priceA = parseFloat(a.cells[8].innerText.replace(/,/g, '').trim()) || 0;
                                            const priceB = parseFloat(b.cells[8].innerText.replace(/,/g, '').trim()) || 0;

                                            return order === 'asc' ? priceA - priceB : priceB - priceA;
                                        });

                                        rows.forEach(row => table.appendChild(row));
                                    }

                                    // Sắp xếp sản phẩm theo ngày tạo
                                    function sortByDate(order) {
                                        const table = document.querySelector(".table tbody");
                                        const rows = Array.from(table.rows);

                                        rows.sort((a, b) => {
                                            const dateA = new Date(a.cells[3].innerText.trim()); // Cột ngày tạo (index 3) + 1
                                            const dateB = new Date(b.cells[3].innerText.trim());

                                            return order === 'asc' ? dateA - dateB : dateB - dateA;
                                        });

                                        rows.forEach(row => table.appendChild(row));
                                    }
                                    
                                    // xoa order
                                    function confirmDelete() {
                                        return confirm("Bạn có chắc muốn xóa order?");
                                    }

        </script>
    </body>
</html>
