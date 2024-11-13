<%-- 
    Document   : ImportProduct
    Created on : Oct 7, 2024, 10:01:58 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>WSM - Whole Sale Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/OrderImportProduct.css" rel="stylesheet">
    </head>

    <body>

        <!-- Sidebar -->
        <div class="sidebar" id="sidebar">
            <div class="sidebar-header d-flex align-items-center justify-content-between">
                <h3 class="logo">WSM</h3>
                <button class="btn btn-light btn-sm toggle-btn" id="toggleBtn">☰</button>
            </div>
            <hr class="divider">
            <ul class="nav flex-column">
<!--                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#collapseDonHang" role="button"
                       aria-expanded="false" aria-controls="collapseDonHang">
                        <i class="bi bi-receipt"></i>
                        <span class="menu-text">Báo Cáo</span>
                        <i class="bi bi-caret-down-fill float-end"></i>
                    </a>
                    <div class="collapse" id="collapseDonHang">
                       <ul class="list-unstyled ps-3">
                            <li><a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="nav-link">Báo Cáo Kho</a></li>
                        </ul>
                    </div>
                </li>-->
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="collapse" href="#collapseSanPham" role="button"
                       aria-expanded="false" aria-controls="collapseSanPham">
                        <i class="bi bi-box"></i>
                        <span class="menu-text">Sản phẩm</span>
                        <i class="bi bi-caret-down-fill float-end"></i>
                    </a>
                    <div class="collapse" id="collapseSanPham">
                        <ul class="list-unstyled ps-3">
                            <li><a href="${pageContext.request.contextPath}/StorageLocationController" class="nav-link">Vị trí lô hàng</a></li>
                            <li><a href="${pageContext.request.contextPath}/OrderImportProductList" class="nav-link">Đặt hàng nhập</a></li>
                            <li><a href="${pageContext.request.contextPath}/ImportProduct" class="nav-link">Nhập hàng</a></li>
                        </ul>
                    </div>
                </li>
                
            </ul>
        </div>




        <!-- Header -->
        <div class="header-content" id="header-content">
            <div class="header d-flex justify-content-between align-items-center">

                <h4>Nhập Hàng</h4>
                <div class="header-icons d-flex align-items-center">
                    <div class="icon-item d-flex align-items-center">
                        <i class="bi bi-currency-dollar"></i>
                        <span>Vay vốn kinh doanh</span>
                    </div>
                    <div class="icon-item d-flex align-items-center ms-3">
                        <i class="bi bi-question-circle"></i>
                        <span>Trợ giúp</span>
                    </div>
                    <div class="icon-item d-flex align-items-center ms-3">
                        <i class="bi bi-heart"></i>
                        <span>Góp ý</span>
                    </div>
                    <div class="dropdown ms-3">
                        <button class="btn d-flex align-items-center" id="profileDropdown" data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <div class="avatar rounded-circle bg-success text-white d-flex justify-content-center align-items-center"
                                 style="width: 35px; height: 35px;">
                                E
                            </div>
                            <span class="ms-2">Envidi</span>
                            <i class="bi bi-caret-down-fill ms-1"></i>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end mt-2" aria-labelledby="profileDropdown">
                            <li><a class="dropdown-item d-flex align-items-center" href="#">
                                    <i class="bi bi-person me-2"></i>Tài khoản của tôi</a>
                            </li>
<!--                            <li><a class="dropdown-item d-flex align-items-center" href="#">
                                    <i class="bi bi-folder me-2"></i>Lịch sử xuất nhập file</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>-->
                            <li><a class="dropdown-item d-flex align-items-center" href="#">
                                    <i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a>
                            </li>
                        </ul>
                    </div>
                    <div class="icon-item ms-3">
                        <i class="bi bi-bell-fill text-primary"></i>
                    </div>
                </div>
            </div>
        </div>


        <!-- Main Content Area -->
        <div class="main-content" id="main-content">
            <div class="container-fluid">

                <div class="d-flex justify-content-start align-items-center mb-3">
                    <form action="${pageContext.request.contextPath}/ImportProduct" method="post">
                        <input type="hidden" name="service" value="exportContract"> <!-- Gửi service để xác định hành động -->
                        <button class="btn btn-light me-2 button-filter">Xuất Excel</button>
                    </form>
                </div>

                <!--<button class="btn btn-light me-2 button-filter">Quản lý trả hàng NCC</button>-->
            </div>

            <!-- Navigation Tabs -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <ul class="nav nav-tabs">
                    <li class="nav-item"><a class="nav-link active" href="#">Tất cả đơn nhập hàng</a></li>
                    <!--                        <li class="nav-item"><a class="nav-link" href="#">Đang giao dịch</a></li>
                                            <li class="nav-item"><a class="nav-link" href="#">Hoàn thành</a></li>-->
                </ul>

            </div>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <!-- Form Tìm Kiếm -->
                <form action="${pageContext.request.contextPath}/ImportProduct" method="get" class="d-flex" style="flex: 1;">
                    <input type="hidden" name="service" value="searchContract">
                    <div class="input-group me-2" style="width: 100%;">
                        <span class="input-group-text bg-white border-end-0">
                            <i class="bi bi-search"></i>
                        </span>
                        <input type="text" class="form-control border-start-0" placeholder="Tìm id hợp đồng, tên NCC" name="searchQuery" aria-label="Tìm kiếm">
                        <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                    </div>
                </form>

                <!-- Form Lọc -->
                <form action="${pageContext.request.contextPath}/ImportProduct" method="get" class="d-flex align-items-center ms-3">
                    <input type="hidden" name="service" value="orderList">
                    <select class="form-select me-2" id="statusFilter" name="statusFilter">
                        <option value="">Tất cả</option>
                        <option value="1">Đang xử lý</option>
                        <option value="3">Hoàn thành</option>
                        <option value="4">Hủy</option>
                    </select>
                    <button class="btn btn-primary" type="submit">Lọc</button>
                </form>
            </div>

            <!-- Order Table -->
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th scope="col"><i class="bi bi-gear"></i></th>
                        <th scope="col">ID Hợp đồng</th>
                        <th scope="col">Ngày giao hàng</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Nhà cung cấp</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="contract" items="${listC}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/ImportProduct?service=contractDetail&contractId=${contract.id}" class="nav-link">
                                    <i class="bi bi-eye text-primary"></i>
                                </a>
                            </td>
                            <td name="contractId">
                                ${contract.id}
                            </td>
                            <td name="contractDeliveryDate">${contract.contractDeliveryDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${contract.status == 1}">
                                        <span class="badge bg-warning">Đang xử lý</span>
                                    </c:when>
                                    <c:when test="${contract.status == 3}">
                                        <span class="badge bg-success">Đã hoàn thành</span>
                                    </c:when>
                                    <c:when test="${contract.status == 4}">
                                        <span class="badge bg-danger">Hủy</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Không xác định</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td name="supplierName">${contract.supplierName}</td> 
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


            <!-- Pagination -->
            <div class="d-flex justify-content-between align-items-center mt-3">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/ImportProduct?page=${i}&statusFilter=${statusFilter}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>

        </div>
    </div>

    <!-- JavaScript for Sidebar Toggle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('toggleBtn').addEventListener('click', function (e) {
            e.stopPropagation();

            let sidebar = document.getElementById('sidebar');
            let headerContent = document.getElementById('header-content');
            let mainContent = document.getElementById('main-content');

            sidebar.classList.toggle('collapsed');
            headerContent.classList.toggle('expanded');
            mainContent.classList.toggle('expanded');

            if (sidebar.classList.contains('collapsed')) {
                let dropdowns = document.querySelectorAll('.collapse.show');
                dropdowns.forEach(function (dropdown) {
                    let collapseInstance = new bootstrap.Collapse(dropdown);
                    collapseInstance.hide();
                });
            }
        });

        document.getElementById('sidebar').addEventListener('click', function () {
            let sidebar = document.getElementById('sidebar');
            let headerContent = document.getElementById('header-content');
            let mainContent = document.getElementById('main-content');

            if (sidebar.classList.contains('collapsed')) {
                sidebar.classList.remove('collapsed');
                headerContent.classList.remove('expanded');
                mainContent.classList.remove('expanded');
            }
        });
    </script>
</body>

</html>
