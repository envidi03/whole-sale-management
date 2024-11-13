<%-- 
    Document   : Home.jsp
    Created on : Oct 7, 2024, 3:40:10 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WSM - Whole Sale Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/Home.css"> <!-- Link to the external CSS file -->
</head>

<body>

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <div class="sidebar-header d-flex align-items-center justify-content-between">
            <h3 class="logo"><a href="${pageContext.request.contextPath}/jsp/Home.jsp">WSM</a></h3>
            <button class="btn btn-light btn-sm toggle-btn" id="toggleBtn">☰</button>
        </div>
        <hr class="divider"> <!-- Divider between logo and menu -->

        <ul class="nav flex-column">
            <!-- Đơn hàng Section -->
            <li class="nav-item">
                <a class="nav-link" data-bs-toggle="collapse" href="#collapseDonHang" role="button"
                    aria-expanded="false" aria-controls="collapseDonHang">
                    <i class="bi bi-receipt"></i>
                    <span class="menu-text">Báo Cáo</span>
                </a>
                <div class="collapse" id="collapseDonHang">
                    <ul class="list-unstyled ps-3">
                        
                    </ul>
                </div>
            </li>

            <!-- Sản phẩm Section -->
            <li class="nav-item">
                <a class="nav-link" data-bs-toggle="collapse" href="#collapseSanPham" role="button"
                    aria-expanded="false" aria-controls="collapseSanPham">
                    <i class="bi bi-box"></i>
                    <span class="menu-text">Sản phẩm</span>
                    <i class="bi bi-caret-down-fill float-end"></i>
                </a>
                <div class="collapse" id="collapseSanPham">
                    <ul class="list-unstyled ps-3">
                        <li><a href="#" class="nav-link">Danh sách sản phẩm</a></li>
                        <li><a href="${pageContext.request.contextPath}/StorageLocationController" class="nav-link">Vị trí Lô hàng</a></li>
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
            <h4>Tổng quan</h4>
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
                <!-- User Profile with Dropdown -->
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
                    <!-- Dropdown Menu -->
                    <ul class="dropdown-menu dropdown-menu-end mt-2" aria-labelledby="profileDropdown">
                        <li><a class="dropdown-item d-flex align-items-center" href="#">
                                <i class="bi bi-person me-2"></i>Tài khoản của tôi</a>
                        </li>
                        <li><a class="dropdown-item d-flex align-items-center" href="#">
                                <img src="path-to-service-icon.png" alt="Service Icon" class="me-2"
                                    style="width: 20px;">Thông tin gói dịch vụ</a>
                        </li>
                        <li><a class="dropdown-item d-flex align-items-center" href="#">
                                <i class="bi bi-folder me-2"></i>Lịch sử xuất nhập file</a>
                        </li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item d-flex align-items-center" href="#">
                                <i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a>
                        </li>
                    </ul>
                </div>
                <!-- Notification Bell -->
                <div class="icon-item ms-3">
                    <i class="bi bi-bell-fill text-primary"></i>
                </div>
            </div>
        </div>
    </div>



    <!-- JavaScript for Sidebar Toggle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Toggle button to expand/collapse sidebar
        document.getElementById('toggleBtn').addEventListener('click', function (e) {
            e.stopPropagation(); // Prevent this click from affecting the sidebar click event

            let sidebar = document.getElementById('sidebar');
            let headerContent = document.getElementById('header-content');
            let header = document.querySelector('.header');

            // Toggle the collapsed class
            sidebar.classList.toggle('collapsed');
            headerContent.classList.toggle('expanded');
            header.classList.toggle('expanded');

            // Collapse all dropdowns if sidebar is collapsing
            if (sidebar.classList.contains('collapsed')) {
                // Close all dropdowns
                let dropdowns = document.querySelectorAll('.collapse.show');
                dropdowns.forEach(function (dropdown) {
                    let collapseInstance = new bootstrap.Collapse(dropdown);
                    collapseInstance.hide();
                });
            }
        });

        // Add event listener to the sidebar itself
        document.getElementById('sidebar').addEventListener('click', function (e) {
            let sidebar = document.getElementById('sidebar');
            let headerContent = document.getElementById('header-content');
            let header = document.querySelector('.header');

            // Check if the sidebar is collapsed
            if (sidebar.classList.contains('collapsed')) {
                // Expand the sidebar on click
                sidebar.classList.remove('collapsed');
                headerContent.classList.remove('expanded');
                header.classList.remove('expanded');
            }
        });
    </script>
</body>

</html>
