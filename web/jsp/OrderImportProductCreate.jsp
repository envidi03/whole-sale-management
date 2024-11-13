<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Supplier Order Interface</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/OrderImportProduct.Create.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
        <style>
            #supplierInfo {
                display: block;
            }

            #supplierInfo {
                display: block;
                max-height: 200px; /* Giới hạn chiều cao của danh sách */
                overflow-y: auto; /* Thêm thanh cuộn dọc */
            }

            #productTable {
                display: none;
            }
        </style>
    </head>

    <body>
        <div class="container-fluid main-content">
            <form action="OrderImportProduct" method="post">
                <div>

                    <!-- Lấy giá trị từ session -->
                    <c:set var="selectedSupplierId" value="${sessionScope.selectedSupplierId}" />
                    <c:set var="selectedDate" value="${sessionScope.selectedDate}" />

                    <!-- Top Buttons -->
                    <div class="d-flex justify-content-end mt-3 mb-3">
                        <a href="${pageContext.request.contextPath}/OrderImportProductList" class="btn btn-secondary exit">Quay lại</a>
                        
                        <button class="btn btn-primary" name="service" value="createOrder">Đặt hàng</button>
                    </div>

                    <!-- Supplier Info and Order Info Sections with Equal Height -->
                    <div class="row card-container mb-3">
                        <!-- Supplier Info Section -->
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Thông tin nhà cung cấp</h5>
                                    <a href="#" class="d-flex align-items-center mb-2 text-primary" data-toggle="modal" data-target="#addSupplierModal" style="text-decoration: none;">
                                        <i class="fas fa-plus-circle mr-2"></i> Thêm mới nhà cung cấp
                                    </a>
                                    <div id="supplierInfo" class="mt-2">
                                        <c:forEach items="${listS}" var="sup">
                                            <div class="d-flex align-items-center p-2 bg-light rounded shadow-sm">
                                                <i class="fas fa-user-circle fa-2x mr-3 text-primary"></i>
                                                <div>
                                                    <strong>${sup.supplierName}</strong><br>
                                                    <div>${sup.phone}</div>
                                                    <div>${sup.address}</div>
                                                </div>
                                                <!-- Sử dụng giá trị từ session để giữ radio được chọn -->

                                                <input type="radio" name="supplierId" value="${sup.id}" <c:if test="${sup.id == selectedSupplierId}">checked</c:if> />


                                                </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Order Info Section -->
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Thông tin đơn đặt hàng</h5>
<!--                                    <div class="form-group">
                                        <label>Nhân viên</label>
                                        <select class="form-control">
                                            <option>Envidi</option>
                                        </select>
                                    </div>-->
                                    <div class="form-group">
                                        <label>Ngày nhập</label>
                                        <!-- Sử dụng giá trị ngày từ session -->

                                        <input type="text" class="form-control" placeholder="Chọn ngày nhập hàng" name="deliveryDate" id="dateInput" value="${selectedDate}" >

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Product Info Section -->
                    <div class="card mb-3 position-relative">
                        <div class="card-body">
                            <h5 class="card-title">Thông tin sản phẩm</h5>
                            <button type="button" class="btn btn-primary" 
                                    id="showButton" onclick="toggleTable()">Lịch sử</button>
                            <div id="productTable" class="container mt-4">
                                <table class="table table-bordered table-hover">
                                    <thead class="thead-light text-center">
                                        <tr>
                                            <th scope="col">ID Lô Hàng</th>
                                            <th scope="col">Giá Nhập</th>
                                            <th scope="col">Giá Bán</th>
                                            <th scope="col">Số lượng sản phẩm trong lô</th>
                                            <th scope="col">Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listC}" var="c">
                                            <tr>
                                                <th>${c.id}</th>
                                                <td>${c.importPrice} VNĐ</td>
                                                <td>${c.sellingPrice} VNĐ</td>
                                                <td>${c.numberOfProduct}</td>
                                                <td>
                                                    <!-- Nút Buy Again dùng AJAX -->
                                                    <button type="button" class="btn btn-primary" 
                                                            onclick="buyAgain('${c.id}', '${selectedSupplierId}', '${selectedDate}')">
                                                        Mua Lại
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty listC}">
                                            <tr>
                                                <td colspan="5" class="text-center">Không có sản phẩm nào trong lô hàng</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Product Details Form -->
                            <div id="productDetailsForm">
                                <h5>Chi tiết sản phẩm</h5>
                                <table class="table table-bordered thead-light text-center">
                                    <thead>
                                        <tr>
                                            <th>ID Sản Phẩm</th>
                                            <th>Giá Nhập(VNĐ)</th>
                                            <th>Giá Bán(VNĐ)</th>
                                            <th>Số Lượng Sản Phẩm Trong Lô</th>
                                        </tr>
                                    </thead>
                                    <tbody id="productDetailsBody">
                                        <c:forEach items="${cartItems}" var="item" varStatus="status">
                                            <tr id="row_${status.index}">
                                                <td>${item.consignment.productId}</td>
                                        <input type="hidden" name="product_id_${status.index}" value="${item.consignment.productId}">
                                        <td><input type="text" name="import_price_${item.consignment.productId}" value="${item.consignment.importPrice}" class="form-control" min="1" readonly></td>
                                        <td><input type="text" name="selling_price_${item.consignment.productId}" value="${item.consignment.sellingPrice}" class="form-control" min="0" readonly></td>
                                        <td><input type="text" name="number_of_product_${item.consignment.productId}" value="${item.consignment.numberOfProduct}" class="form-control" min="1" readonly></td>
                                        <!-- Nút Xóa sản phẩm khỏi giỏ hàng -->
                                        <td>
                                            <button type="button" class="btn btn-danger" onclick="removeFromCart('${item.consignment.productId}')">Xóa</button>
                                        </td>

                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty cartItems}">
                                        <tr id="noProductMessage">
                                            <td colspan="12" id="emptyMessage">
                                                <div>Đơn đặt hàng nhập của bạn chưa có sản phẩm nào</div>
                                            </td>
                                        </tr>
                                    </c:if>
                                    </tbody>
                                </table>
                                <button class="btn btn-primary mt-3" id="openAddProductModal">Thêm lô hàng</button>
                            </div>

                        </div>
                    </div>
                </div>
            </form>

<!--             Notes and Summary Section 
            <div class="row">
                <div class="col-md-8">
                    <div class="form-group">
                        <label>Ghi chú đơn</label>
                        <textarea class="form-control" rows="3" placeholder="VD: Hàng tặng gói riêng"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Tags</label>
                        <input type="text" class="form-control" placeholder="Nhập ký tự và ấn enter">
                    </div>
                </div>
                <div class="col-md-4">
                    <ul class="list-unstyled">
                        <li>Số lượng: <span class="float-right">0</span></li>
                        <li>Tổng tiền: <span class="float-right">0</span></li>
                        <li><a href="#">Chiết khấu (F6)</a></li>
                        <li>Thuế: <span class="float-right">0</span></li>
                        <li>Tiền cần trả: <span class="float-right">0</span></li>
                    </ul>
                </div>
            </div >-->

            <!-- Modal for Adding New Supplier -->
            <div class="modal fade" id="addSupplierModal" tabindex="-1" aria-labelledby="addSupplierModalLabel" aria-hidden="true" ${not empty errorMessage ? 'data-show="true"' : ''}>
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addSupplierModalLabel">Thêm mới nhà cung cấp</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="OrderImportProduct" method="post">
                                <input type="hidden" name="service" value="addSupplier">
                                <div class="form-group">
                                    <label for="supplierName">Tên nhà cung cấp *</label>
                                    <input type="text" class="form-control" name="supplierName" id="supplierName" placeholder="Nhập họ tên" required>
                                </div>
                                <div class="form-group">
                                    <label for="supplierPhone">Số điện thoại</label>
                                    <input type="number" class="form-control" name="phone" id="supplierPhone" placeholder="Nhập số điện thoại" required>
                                </div>
                                <div class="form-group">
                                    <label for="supplierAddress">Địa Chỉ</label>
                                    <input type="text" class="form-control" name="address" id="supplierAddress" placeholder="Nhập địa chỉ" required>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Thoát</button>
                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>




            <!-- Modal để thêm consignment -->
            <div class="modal fade" id="addProductModal" tabindex="-1" aria-labelledby="addProductModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addProductModalLabel">Thêm lô hàng mới</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form action="OrderImportProduct" method="post">
                                <input type="hidden" name="service" value="addConsignment">

                                <!-- Thêm hidden input để lưu lại nhà cung cấp và ngày nhập -->
                                <input type="hidden" name="supplierId" value="${selectedSupplierId}">
                                <input type="hidden" name="deliveryDate" value="${selectedDate}">

                                <!-- Các trường nhập dữ liệu lô hàng khác -->
                                <div class="form-group">
                                    <label for="productId">ID sản phẩm *</label>
                                    <select class="form-control" id="productId" name="productId" required>
                                        <option value="">Chọn sản phẩm</option>
                                        <c:forEach var="product" items="${products}">
                                            <option value="${product.id}">${product.productName}</option>
                                        </c:forEach>
                                    </select>
                                    <button type="button" class="btn btn-link p-0" ><a href="${pageContext.request.contextPath}/AddProduct">Thêm Sản Phẩm Mới</a></button>
                                </div>

                                <div class="form-group">
                                    <label for="import_price">Giá nhập</label>
                                    <input type="number" class="form-control" id="import_price" name="import_price" placeholder="Nhập giá nhập" required>
                                </div>

                                <div class="form-group">
                                    <label for="selling_price">Giá bán</label>
                                    <input type="number" class="form-control" id="selling_price" name="selling_price" placeholder="Nhập giá bán" required>
                                </div>

                                <div class="form-group">
                                    <label for="number_of_product">Số lượng sản phẩm trong lô</label>
                                    <input type="number" class="form-control" id="number_of_product" name="number_of_product" placeholder="Nhập số lượng" required>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Thoát</button>
                                    <button type="submit" class="btn btn-primary">Thêm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- jQuery, Bootstrap, and Flatpickr JavaScript -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
        <script>
                                                function buyAgain(consignmentId) {
                                                    // Lấy giá trị từ form hoặc session
                                                    var supplierId = document.querySelector('input[name="supplierId"]:checked').value;
                                                    var deliveryDate = document.querySelector('input[name="deliveryDate"]').value;

                                                    console.log("Sending supplierId:", supplierId, "deliveryDate:", deliveryDate);  // Log kiểm tra

                                                    var xhr = new XMLHttpRequest();
                                                    xhr.onreadystatechange = function () {
                                                        if (xhr.readyState == 4 && xhr.status == 200) {
                                                            var response = xhr.responseText;
                                                            if (response === "success") {
                                                                // Làm mới trang để cập nhật lại giỏ hàng
                                                                location.reload();
                                                            } else {
                                                                alert(response);
                                                            }
                                                        }
                                                    };

                                                    // Mở kết nối và gửi yêu cầu tới servlet
                                                    xhr.open("POST", "OrderImportProduct", true);
                                                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                    // Gửi yêu cầu với dữ liệu cần thiết
                                                    xhr.send("service=buyAgain&consignmentId=" + consignmentId
                                                            + "&supplierId=" + supplierId
                                                            + "&deliveryDate=" + deliveryDate
                                                            );
                                                }


                                                function removeFromCart(consignmentId) {
                                                    console.log("Sending consignmentId: ", consignmentId);  // Log kiểm tra consignmentId

                                                    var xhr = new XMLHttpRequest();
                                                    xhr.onreadystatechange = function () {
                                                        if (xhr.readyState == 4 && xhr.status == 200) {
                                                            var response = xhr.responseText;
                                                            if (response === "success") {
                                                                // Xóa thành công, làm mới bảng hoặc cập nhật giao diện người dùng
                                                                location.reload();
                                                            } else {
                                                                alert("Error: " + response);
                                                            }
                                                        }
                                                    };

                                                    // Mở kết nối và gửi yêu cầu tới servlet
                                                    xhr.open("POST", "OrderImportProduct", true);
                                                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                                                    // Gửi yêu cầu với consignmentId và service=removeFromCart
                                                    xhr.send("service=removeFromCart&consignmentId=" + consignmentId);
                                                }



                                                function toggleTable() {
                                                    var table = document.getElementById("productTable");
                                                    var button = document.getElementById("showButton");

                                                    if (table.style.display === "none") {
                                                        table.style.display = "table"; // Hiển thị bảng
                                                        button.textContent = "Ẩn Lịch Sử"; // Thay đổi text của button
                                                    } else {
                                                        table.style.display = "none"; // Ẩn bảng
                                                        button.textContent = "Lịch Sử"; // Thay đổi text của button
                                                    }
                                                }

                                                // Initialize Flatpickr cho input ngày
                                                flatpickr("#dateInput", {
                                                    enableTime: false,
                                                    dateFormat: "d-m-Y", 
                                                    locale: {
                                                        firstDayOfWeek: 1,
                                                        weekdays: {
                                                            shorthand: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
                                                            longhand: ['Chủ nhật', 'Thứ hai', 'Thứ ba', 'Thứ tư', 'Thứ năm', 'Thứ sáu', 'Thứ bảy'],
                                                        },
                                                        months: {
                                                            shorthand: ['Th1', 'Th2', 'Th3', 'Th4', 'Th5', 'Th6', 'Th7', 'Th8', 'Th9', 'Th10', 'Th11', 'Th12'],
                                                            longhand: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
                                                        },
                                                    }
                                                });


                                                $(document).ready(function () {
                                                    $('#openAddProductModal').click(function (event) {
                                                        event.preventDefault();  // Ngăn form tự động submit
                                                        $('#addProductModal').modal('show');
                                                    });
                                                });



        </script>
    </body>
</html>