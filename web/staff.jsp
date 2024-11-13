<%-- 
    Document   : staff
    Created on : Sep 16, 2024, 3:21:41 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Staff - Order Management</title>

        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link href="css/login.css" rel="stylesheet">
    </head>

    <body class="bg-gradient-light">

        <div class="container">

            <!-- Outer Row -->
            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Quản lý Đơn hàng</h1>
                                        </div>

                                        <!-- Thông tin nhân viên -->
                                        <div class="mb-4">
                                            <h5>Thông tin Nhân viên</h5>
                                            <p><strong>Tên:</strong> Nguyễn Văn A</p>
                                            <p><strong>Email:</strong> nguyenvana@example.com</p>
                                        </div>

                                        <!-- Danh sách đơn hàng -->
                                        <div class="table-responsive">
                                            <h5 class="mb-3">Danh sách Đơn hàng</h5>
                                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                <thead>
                                                    <tr>
                                                        <th>Mã Đơn hàng</th>
                                                        <th>Khách hàng</th>
                                                        <th>Ngày đặt</th>
                                                        <th>Trạng thái</th>
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>DH001</td>
                                                        <td>Trần Thị B</td>
                                                        <td>15/09/2024</td>
                                                        <td>Đang xử lý</td>
                                                        <td>
                                                            <a href="viewOrderDetails.jsp?orderId=DH001" class="btn btn-primary btn-sm">Xem</a>
                                                            <a href="editOrder.jsp?orderId=DH001" class="btn btn-secondary btn-sm">Chỉnh sửa</a>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>DH002</td>
                                                        <td>Phạm Văn C</td>
                                                        <td>14/09/2024</td>
                                                        <td>Hoàn thành</td>
                                                        <td>
                                                            <a href="viewOrderDetails.jsp?orderId=DH002" class="btn btn-primary btn-sm">Xem</a>
                                                            <a href="editOrder.jsp?orderId=DH002" class="btn btn-secondary btn-sm">Chỉnh sửa</a>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="text-center mt-4">
                                            <a href="addOrder.jsp" class="btn btn-success">Thêm Đơn hàng mới</a>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>

    </body>

</html>

