<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Consignment List</title>
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
        </style>
    </head>
    <body>
        <div class="container">
            <c:set var="size" value="${sessionScope.size}"/>

            <a href="${pageContext.request.contextPath}/jsp/MyCart.jsp" class="btn btn-info mb-3">Giỏ Hàng (${size})</a>

            <h1 class="text-center">Danh Sách Lô Hàng</h1>

            <form name="f" action="" method="post">
                <h3>Nhập số lượng hàng hóa muốn mua:</h3>
                <div class="container">
                    <div class="row align-items-center justify-content-between">
                        <!-- Phần tìm kiếm và lọc -->
                        <div class="col-5">
                            <input type="readonly" name="num" value="1" min="1" class="form-control w-25 mb-3"/>
                            <div class="form-group">
                                <input type="text" id="searchInput" onkeyup="searchProducts()" placeholder="Tìm kiếm theo tên sản phẩm hoặc ID lô hàng" class="form-control mb-3">
                            </div>
                            <div class="form-group">
                                <input type="text" id="statusInput" onkeyup="searchProductsByStatus()" placeholder="Tìm kiếm theo trạng thái" class="form-control mb-3">
                            </div>
                            <div class="form-group">
                                <input type="number" id="minPrice" onkeyup="filterByPrice()" placeholder="Giá tối thiểu" class="form-control mb-3">
                                <input type="number" id="maxPrice" onkeyup="filterByPrice()" placeholder="Giá tối đa" class="form-control mb-3">
                            </div>
                        </div>
                        <!-- Phần nút sắp xếp được căn về bên phải -->
                        <div class="col-7 d-flex justify-content-end">
                            <button type="button" class="btn btn-primary mr-2" onclick="sortProducts('asc')">Giá Tăng Dần</button>
                            <button type="button" class="btn btn-primary mr-2" onclick="sortProducts('desc')">Giá Giảm Dần</button>
                            <button type="button" class="btn btn-primary mr-2" onclick="sortDiscounts('asc')">Giảm Giá Tăng Dần</button>
                            <button type="button" class="btn btn-primary" onclick="sortDiscounts('desc')">Giảm Giá Giảm Dần</button>
                        </div>
                    </div>
                </div>



                <table class="table table-striped table-bordered" id="productTable">
                    <thead class="thead-light">
                        <tr>
                            <th>ID Lô Hàng</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Trạng Thái</th>
                            <th>Thể Loại</th>
                            <th>Giảm Giá (%)</th>
                            <th>Giá Bán</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <jsp:useBean id="db" class="dal.ConsignmentDAO" />
                        <c:forEach items="${db.getAvailableConsignments(sessionScope.cartIds)}" var="o">
                            <tr>
                                <td>${o.id}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty productMap[o.productId]}">
                                            ${productMap[o.productId]}
                                        </c:when>
                                        <c:otherwise>
                                            Unknown
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${o.getStatusString()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty categoryMap[o.productCategoryId]}">
                                            ${categoryMap[o.productCategoryId]}
                                        </c:when>
                                        <c:otherwise>
                                            Unknown
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${o.discountPercentage}</td>
                                <td>${o.sellingPrice}</td>
                                <td>
                                    <button type="button" class="btn btn-success" onclick="buy('${o.id}')">Thêm Vào Giỏ Hàng</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form> 
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/homeController" class="btn btn-primary">Quay Lại</a>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script type="text/javascript">
                                        function buy(id) {
                                            document.f.action = "${pageContext.request.contextPath}/buy?id=" + id;
                                            document.f.submit();
                                        }

                                        function sortProducts(order) {
                                            const table = document.getElementById("productTable");
                                            const rows = Array.from(table.rows).slice(1); // Bỏ qua hàng đầu tiên (tiêu đề)

                                            rows.sort((a, b) => {
                                                const priceA = parseFloat(a.cells[5].innerText); // Cột Giá Bán (index 5)
                                                const priceB = parseFloat(b.cells[5].innerText);
                                                return order === 'asc' ? priceA - priceB : priceB - priceA;
                                            });

                                            // Xóa các hàng hiện tại và thêm các hàng đã sắp xếp
                                            rows.forEach(row => table.appendChild(row));
                                        }

                                        function sortDiscounts(order) {
                                            const table = document.getElementById("productTable");
                                            const rows = Array.from(table.rows).slice(1); // Bỏ qua hàng đầu tiên (tiêu đề)

                                            rows.sort((a, b) => {
                                                const discountA = parseFloat(a.cells[4].innerText); // Cột Giảm Giá (index 4)
                                                const discountB = parseFloat(b.cells[4].innerText);
                                                return order === 'asc' ? discountA - discountB : discountB - discountA;
                                            });

                                            // Xóa các hàng hiện tại và thêm các hàng đã sắp xếp
                                            rows.forEach(row => table.appendChild(row));
                                        }

                                        function searchProducts() {
                                            const input = document.getElementById("searchInput");
                                            const filter = input.value.toLowerCase();
                                            const table = document.getElementById("productTable");
                                            const rows = table.getElementsByTagName("tr");

                                            for (let i = 1; i < rows.length; i++) { // Bỏ qua hàng tiêu đề
                                                const cells = rows[i].getElementsByTagName("td");
                                                let found = false;

                                                // Kiểm tra từng ô trong hàng
                                                for (let j = 0; j < cells.length; j++) {
                                                    if (cells[j].innerText.toLowerCase().indexOf(filter) > -1) {
                                                        found = true;
                                                        break;
                                                    }
                                                }

                                                // Hiện/ẩn hàng dựa trên kết quả tìm kiếm
                                                rows[i].style.display = found ? "" : "none";
                                            }
                                        }

                                        function searchProductsByStatus() {
                                            const input = document.getElementById("statusInput");
                                            const filter = input.value.toLowerCase();
                                            const table = document.getElementById("productTable");
                                            const rows = table.getElementsByTagName("tr");

                                            for (let i = 1; i < rows.length; i++) { // Bỏ qua hàng tiêu đề
                                                const cells = rows[i].getElementsByTagName("td");
                                                const statusCell = cells[2]; // Trạng thái nằm ở cột thứ 3 (index 2)
                                                rows[i].style.display = statusCell.innerText.toLowerCase().indexOf(filter) > -1 ? "" : "none";
                                            }
                                        }

                                        function filterByPrice() {
                                            const minPrice = parseFloat(document.getElementById("minPrice").value) || 0;
                                            const maxPrice = parseFloat(document.getElementById("maxPrice").value) || Infinity;
                                            const table = document.getElementById("productTable");
                                            const rows = table.getElementsByTagName("tr");

                                            for (let i = 1; i < rows.length; i++) { // Bỏ qua hàng tiêu đề
                                                const priceCell = rows[i].getElementsByTagName("td")[5]; // Cột Giá Bán (index 5)
                                                const price = parseFloat(priceCell.innerText);

                                                // Hiện/ẩn hàng dựa trên giá
                                                rows[i].style.display = (price >= minPrice && price <= maxPrice) ? "" : "none";
                                            }
                                        }
        </script>
    </body>
</html>
