<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Consignment</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f4f4;
            }
            .container {
                margin-top: 20px;
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .form-group input[type="text"],
            .form-group input[type="number"],
            .form-group input[type="date"],
            .form-group button.sort-button {
                height: 30px;
                font-size: 0.9em;
                padding: 0 10px;
                margin: 5px 0;
            }
            .sort-button, .btn-primary {
                height: 30px;
                font-size: 0.9em;
                padding: 0 10px;
                margin: 5px 2px;
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
        <script>
            // sort theo gia tien ban
            function sortProducts(order) {
                const table = document.getElementById("consignmentTable");
                const rows = Array.from(table.rows).slice(1); // Bỏ qua hàng tiêu đề
                rows.sort((a, b) => {
                    const priceA = parseFloat(a.cells[4].innerText);
                    const priceB = parseFloat(b.cells[4].innerText);
                    return order === 'asc' ? priceA - priceB : priceB - priceA;
                });
                rows.forEach(row => table.appendChild(row));
            }
            
            // search theo ten sp 
            function searchProducts() {
                const input = document.getElementById("searchInput");
                const filter = input.value.toLowerCase();
                const table = document.getElementById("consignmentTable");
                const rows = table.getElementsByTagName("tr");
                for (let i = 1; i < rows.length; i++) {
                    const cells = rows[i].getElementsByTagName("td");
                    const productNameCell = cells[1];
                    rows[i].style.display = productNameCell.innerText.toLowerCase().indexOf(filter) > -1 ? "" : "none";
                }
            }

            // sort theo ngay het han sp
            function sortByExpiryDate(order) {
                const table = document.getElementById("consignmentTable");
                const rows = Array.from(table.rows).slice(1);
                rows.sort((a, b) => {
                    const dateA = new Date(a.cells[6].innerText);
                    const dateB = new Date(b.cells[6].innerText);
                    return order === 'asc' ? dateA - dateB : dateB - dateA;
                });
                rows.forEach(row => table.appendChild(row));
            }

            // tim kiem theo between ngay san xuat 
            function filterByManufactureDate() {
                const minDateInput = document.getElementById("minManufactureDate").value;
                const maxDateInput = document.getElementById("maxManufactureDate").value;

                const minDate = minDateInput ? new Date(minDateInput) : null;
                const maxDate = maxDateInput ? new Date(maxDateInput) : null;

                const table = document.getElementById("consignmentTable");
                const rows = table.getElementsByTagName("tr");

                for (let i = 1; i < rows.length; i++) {
                    const manufactureDateCell = rows[i].getElementsByTagName("td")[5];
                    const manufactureDate = new Date(manufactureDateCell.innerText);

                    const isWithinRange =
                            (minDate ? manufactureDate >= minDate : true) &&
                            (maxDate ? manufactureDate <= maxDate : true);

                    rows[i].style.display = isWithinRange ? "" : "none";
                }
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
    </head>
    <body>
        <div class="container">
            <h1 class="text-center">Danh Sách Lô Hàng Trong Kho</h1>

            <%-- Hiển thị thông báo lỗi nếu có --%>
            <% if (request.getAttribute("error2") != null) { %>
            <div id="errorBox" class="error-box">
                <span><%= request.getAttribute("error2") %></span>
            </div>
            <% } %>

            <%-- Hiển thị thông báo lỗi nếu có --%>
            <% if (request.getAttribute("error3") != null) { %>
            <div id="errorBox" class="error-box">
                <span><%= request.getAttribute("error3") %></span>
            </div>
            <% } %>

            <%-- Hiển thị thông báo lỗi nếu có --%>
            <% if (request.getAttribute("error4") != null) { %>
            <div id="errorBox" class="error-box">
                <span><%= request.getAttribute("error4") %></span>
            </div>
            <% } %>

            <div class="form-group row">

                <div class="col-3">
                    <input type="text" id="searchInput" onkeyup="searchProducts()" placeholder="Tìm kiếm theo tên sản phẩm" class="form-control">
                </div>

                <!--Search By Quantity-->
                <form action="searchConsignmentByPrice" method="get" class="mr-2 flex-fill"> <!-- Thêm lề bên phải và làm cho nó có chiều rộng linh hoạt -->
                    <div class="input-group input-group-sm">
                        <input type="number" name="quantity" class="form-control form-control-sm" placeholder="Số Lượng Sản Phẩm" required>
                        <div class="input-group-append">
                            <button class="btn btn-primary w-100" type="submit">Lọc</button>
                        </div>
                    </div>
                </form>

                <!--Search By Price Range-->
                <form action="searchConsignmentByPriceFix" method="" class="mr-2 flex-fill"> <!-- Thêm lề bên phải và làm cho nó có chiều rộng linh hoạt -->
                    <div class="input-group input-group-sm">
                        <input type="number" name="minPrice" class="form-control form-control-sm" placeholder="Giá Tối Thiểu" required>
                        <input type="number" name="maxPrice" class="form-control form-control-sm" placeholder="Giá Tối Đa" required>
                        <div class="input-group-append">
                            <button class="btn btn-primary w-100" type="submit">Lọc</button>
                        </div>
                    </div>
                </form>

                <!--Search By Discount-->
                <form action="searchConsignmentByPrice" method="post" class="mr-2 flex-fill"> <!-- Thêm lề bên phải và làm cho nó có chiều rộng linh hoạt -->
                    <div class="input-group input-group-sm">
                        <input type="number" name="discount" class="form-control form-control-sm" placeholder="Giảm Giá Tối Thiểu (%)" required>
                        <div class="input-group-append">
                            <button class="btn btn-primary w-100" type="submit">Lọc</button>
                        </div>
                    </div>
                </form>

                <div class="col-3">
                    <input type="date" id="minManufactureDate" placeholder="Ngày Sản Xuất Từ" class="form-control">
                </div>
                <div class="col-3">
                    <input type="date" id="maxManufactureDate" placeholder="Ngày Sản Xuất Đến" class="form-control">
                </div>
                <div class="form-group row justify-content-center">

                    <div class="col-12">
                        <button class="btn btn-primary w-100" onclick="filterByManufactureDate()">Lọc Theo Ngày Sản Xuất</button>
                    </div>
                </div>
            </div>
            <!--Sort-->
            <div class="text-center mb-3">
                <button class="btn btn-primary sort-button" onclick="sortProducts('asc')">Giá Bán Tăng Dần</button>
                <button class="btn btn-danger sort-button" onclick="sortProducts('desc')">Giá Bán Giảm Dần</button>
            </div>
            <div class="text-center mb-3">
                <button class="btn btn-primary sort-button" onclick="sortByExpiryDate('asc')">Ngày Hết Hạn Tăng Dần</button>
                <button class="btn btn-danger sort-button" onclick="sortByExpiryDate('desc')">Ngày Hết Hạn Giảm Dần</button>
            </div>
            
            <table class="table table-striped" id="consignmentTable">
                <thead class="thead-light">
                    <tr>
                        <th>ID Lô Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng Sản Phẩm</th>
                        <th>Giá Tiền Nhập</th>
                        <th>Giá Tiền Bán</th>
                        <th>Ngày Sản Xuất</th>
                        <th>Ngày Hết Hạn</th>
                        <th>Giảm Giá</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listC}" var="c">
                        <c:forEach items="${listP}" var="p">
                            <c:if test="${c.productId == p.id}">
                                <tr>
                                    <td>${c.id}</td>
                                    <td>${p.productName}</td>
                                    <td>${c.numberOfProduct}</td>
                                    <td>${c.importPrice}</td>
                                    <td>${c.sellingPrice}</td>
                                    <td>${p.manufactureDate}</td>
                                    <td>${p.expireDate}</td>
                                    <td>${c.discountPercentage}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>

            <nav class="my-4">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${endPage}" var="i">
                        <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                            <a class="page-link" href="viewConsignment?index=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>

            <div class="text-center">
                <a href="${pageContext.request.contextPath}/homeController" class="btn btn-primary">Về Trang Chủ</a>
            </div>
        </div>
    </body>
</html>
