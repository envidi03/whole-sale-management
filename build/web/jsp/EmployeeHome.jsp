<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Sản Phẩm</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f4f4;
            }
            .container {
                margin-top: 20px;
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            }
            h1, h3 {
                margin-bottom: 20px;
            }
            .input-group input {
                min-width: 150px;
            }
            /* CSS cho hộp thông báo lỗi */
            .error-box {
                display: flex;
                align-items: center;
                justify-content: center;
                color: #721c24;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                border-radius: 5px;
                padding: 10px;
                margin-bottom: 20px;
                font-weight: bold;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                opacity: 1;
                transition: opacity 0.5s ease; /* Thêm transition để hiệu ứng mờ dần mượt mà hơn */
            }
        </style>
    </head>
    <body>




        <div class="container">
            <h1 class="text-center">Danh Sách Sản Phẩm Trong Lô</h1>

            <div class="text-center mb-4">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/homeController">Về Trang Chủ</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/viewConsignment">Xem Danh Sách Lô Hàng</a>
                <a class="btn btn-info" href="${pageContext.request.contextPath}/pendingOrder">Đơn Hàng Đang Chờ Phê Duyệt</a>
                <a class="btn btn-info" href="${pageContext.request.contextPath}/deliveredOrder">Lịch Sử Đơn Hàng</a>
                <a class="btn btn-success" href="${pageContext.request.contextPath}/buy">Tạo Đơn Hàng</a>

                <%-- Hiển thị thông báo lỗi nếu có --%>
                <% if (request.getAttribute("error") != null) { %>
                <div id="errorBox" class="error-box">
                    <span><%= request.getAttribute("error") %></span>
                </div>
                <% } %>
            </div>

            <div class="d-flex align-items-center mb-2"> <!-- Tạo một hàng linh hoạt với căn giữa theo chiều dọc -->
                <form action="searchAndPaging?index=1" method="post" class="mr-2 flex-fill"> <!-- Thêm lề bên phải và làm cho nó có chiều rộng linh hoạt -->
                    <div class="input-group input-group-sm">
                        <input type="text" name="txtSearch" class="form-control form-control-sm" placeholder="Tìm kiếm theo tên sản phẩm" value="${txtSearch}">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary btn-sm" type="submit">Tìm Kiếm</button>
                        </div>
                    </div>
                </form>
                <c:if test="${empty listP}">
                    <p>Không có sản phẩm nào được lọc</p>
                </c:if>
                    
                <form action="searchProductByPrice" method="post" class="mr-2 flex-fill"> <!-- Thêm lề bên phải và làm cho nó có chiều rộng linh hoạt -->
                    <div class="input-group input-group-sm">
                        <input type="number" name="minPrice" class="form-control form-control-sm" placeholder="Giá Tối Thiểu" required>
                        <input type="number" name="maxPrice" class="form-control form-control-sm" placeholder="Giá Tối Đa" required>
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary btn-sm" type="submit">Lọc</button>
                        </div>
                    </div>
                </form>

                <form action="category" method="get" class="flex-fill"> <!-- Làm cho nó có chiều rộng linh hoạt -->
                    <div class="form-group mb-0"> <!-- Sử dụng mb-0 để xóa khoảng cách dưới cho form -->
                        <select class="form-control form-control-sm" id="categorySelect" name="cid"> <!-- Thêm lớp form-control-sm để nhỏ hơn -->
                            <c:forEach items="${listC}" var="c">
                                <option value="${c.id}" ${cateID == c.id ? "selected" : ""}>${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary btn-sm">Xem Sản Phẩm</button> <!-- Thêm lớp btn-sm để nhỏ hơn -->
                </form>
            </div>



            <div class="text-center mb-2"> <!-- Giảm khoảng cách dưới -->
                <button class="btn btn-primary btn-sm" onclick="sortProducts('asc')">Giá Tăng Dần</button>
                <button class="btn btn-danger btn-sm" onclick="sortProducts('desc')">Giá Giảm Dần</button>
            </div>

            <div class="text-center mb-2"> <!-- Giảm khoảng cách dưới -->
                <button class="btn btn-primary btn-sm" onclick="sortByExpiryDate('asc')">Ngày Hết Hạn Tăng Dần</button>
                <button class="btn btn-danger btn-sm" onclick="sortByExpiryDate('desc')">Ngày Hết Hạn Giảm Dần</button>
            </div>



            <table class="table table-striped table-bordered" id="productTable">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">Số Thứ Tự</th>
                        <th scope="col">Tên Sản Phẩm</th>
                        <th scope="col">Ngày Sản Xuất</th>
                        <th scope="col">Ngày Hết Hạn</th>
                        <th scope="col">Giá Bán Lẻ</th>
                        <th scope="col">Thể Loại</th> 
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listP}" var="o">
                        <tr>
                            <td>${o.id}</td>
                            <td>${o.productName}</td>
                            <td>${o.manufactureDate}</td>
                            <td>${o.expireDate}</td>
                            <td>${o.retailPrice}</td>
                            <td>${categoryMap[o.categoryId]}</td> 
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Phân trang cho tìm kiếm sản phẩm -->
            <nav class="my-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${end}" var="i">
                        <li class="page-item">
                            <a class="page-link" href="searchAndPaging?index=${i}&txtSearch=${txtSearch}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>

            <nav class="my-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${endPage}" var="i">
                        <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                            <a class="page-link" href="homeController?index=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>


        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                    // sort sp theo giá tiền tăng - giảm
                    function sortProducts(order) {
                        const table = document.getElementById("productTable");
                        const rows = Array.from(table.rows).slice(1); // Bỏ qua hàng đầu tiên (tiêu đề)

                        rows.sort((a, b) => {
                            const priceA = parseFloat(a.cells[4].innerText); // Giá nằm ở cột 5 (index 4)
                            const priceB = parseFloat(b.cells[4].innerText);
                            return order === 'asc' ? priceA - priceB : priceB - priceA;
                        });

                        // Xóa các hàng hiện tại và thêm các hàng đã sắp xếp
                        rows.forEach(row => table.appendChild(row));
                    }

                    // sort sp theo ngày hết hạn sp tăng giảm
                    function sortByExpiryDate(order) {
                        const table = document.getElementById("productTable");
                        const rows = Array.from(table.rows).slice(1); // Bỏ qua hàng tiêu đề

                        rows.sort((a, b) => {
                            const dateA = new Date(a.cells[3].innerText); // Ngày hết hạn ở cột thứ 4 (index 3)
                            const dateB = new Date(b.cells[3].innerText);
                            return order === 'asc' ? dateA - dateB : dateB - dateA;
                        });

                        // Xóa các hàng hiện tại và thêm các hàng đã sắp xếp
                        rows.forEach(row => table.appendChild(row));
                    }

                    // Tự động ẩn hộp thông báo lỗi sau 5 giây
                    setTimeout(function () {
                        const errorBox = document.getElementById("errorBox");
                        if (errorBox) {
                            errorBox.style.transition = "opacity 0.5s"; // Thêm hiệu ứng mờ dần
                            errorBox.style.opacity = "0"; // Mờ dần trước khi ẩn hoàn toàn
                            setTimeout(() => errorBox.style.display = "none", 500); // Sau khi mờ dần thì ẩn hoàn toàn
                        }
                    }, 5000); // Sau 5000 ms (5 giây)
        </script>
    </body>
</html>
