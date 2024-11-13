<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi Tiết Báo Cáo Đơn Hàng</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f0f4f8;
                font-family: Arial, sans-serif;
            }
            h1 {
                color: #007bff;
                font-weight: bold;
                font-size: 2.5rem;
            }
            .card {
                border-radius: 12px;
                box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.15);
                background-color: #ffffff;
                margin-top: 20px;
            }
            .card-header {
                background: linear-gradient(90deg, #007bff, #6c757d);
                color: #ffffff;
                font-size: 1.8rem;
                font-weight: bold;
                text-align: center;
                border-top-left-radius: 12px;
                border-top-right-radius: 12px;
            }
            .card-body {
                padding: 2rem;
            }
            .card-body p {
                font-size: 1.2rem;
                color: #333;
                margin-bottom: 1rem;
            }
            .highlight {
                font-size: 1.3rem;
                font-weight: bold;
            }
            .icon {
                color: #007bff;
                margin-right: 10px;
            }
            hr {
                border-top: 2px dashed #007bff;
                margin: 1rem 0;
            }
            .btn-back {
                border-radius: 30px;
                padding: 12px 25px;
                font-weight: bold;
                background: linear-gradient(90deg, #007bff, #6c757d);
                color: #ffffff;
                border: none;
                transition: 0.3s;
            }
            .btn-back:hover {
                background: linear-gradient(90deg, #6c757d, #007bff);
                color: #ffffff;
            }
        </style>
    </head>
    <body>
        <div class="container my-5">
            <h1 class="text-center mb-5"><i class="icon bi bi-file-earmark-text"></i>Chi Tiết Báo Cáo Đơn Hàng</h1>

            <div class="card">
                <div class="card-header">
                    Báo Cáo Đơn Hàng - ID: ${report.id}
                </div>
                <div class="card-body">
                    <p><i class="icon bi bi-calendar-event-fill"></i><strong>Tháng:</strong> <span class="highlight">${report.month}</span></p>
                    <p><i class="icon bi bi-calendar-check-fill"></i><strong>Năm:</strong> <span class="highlight">${report.year}</span></p>
                    <hr>
                    <p><i class="icon bi bi-check-circle-fill text-success"></i><strong>Tổng Số Đơn Hàng Thành Công:</strong> <span class="highlight text-success">${report.totalNumberOfNewSuccessOrder}</span></p>
                    <p><i class="icon bi bi-currency-dollar"></i><strong>Tổng Giá Trị Đơn Hàng Thành Công:</strong> <span class="highlight text-success">${report.totalValueOfNewSuccessOrder}</span></p>
                    <hr>
                    <p><i class="icon bi bi-exclamation-triangle-fill text-warning"></i><strong>Tổng Số Đơn Hàng Nợ Quá Hạn:</strong> <span class="highlight text-warning">${report.totalNumberOfNewOverdueDebtOrder}</span></p>
                    <p><i class="icon bi bi-currency-exchange"></i><strong>Tổng Giá Trị Đơn Hàng Nợ Quá Hạn:</strong> <span class="highlight text-warning">${report.totalValueOfNewOverdueDebtOrder}</span></p>
                    <hr>
                    <p><i class="icon bi bi-archive-fill text-danger"></i><strong>Tổng Số Đơn Hàng Công Nợ:</strong> <span class="highlight text-danger">${report.totalNumberOfNewIndebtOrder}</span></p>
                    <p><i class="icon bi bi-cash-stack"></i><strong>Tổng Giá Trị Đơn Hàng Công Nợ:</strong> <span class="highlight text-danger">${report.totalValueOfNewIndebtOrder}</span></p>
                </div>
            </div>

            <div class="text-center mt-4">
                <a href="BusinessOwnerDashboardController?action=filterReports" class="btn btn-back"><i class="bi bi-arrow-left-circle"></i> Quay lại</a>
            </div>
        </div>

        <!-- Bootstrap Icons and JS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.5.0/font/bootstrap-icons.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
