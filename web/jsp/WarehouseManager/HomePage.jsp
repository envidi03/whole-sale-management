<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Warehouse Manager Home</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
        <style>
            body {
                background-color: #f7f8fa;
            }
            h1, h2 {
                color: #333;
            }
            .card {
                border: none;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .btn-primary {
                background-color: #007bff;
                border: none;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .pagination-btn {
                margin-top: 5px;
            }
            #calendar .ui-datepicker {
                width: 100%;
            }
        </style>
    </head>
    <body class="bg-light">

        <div class="container py-5">
            <!-- Nút Back -->
            <div class="mb-3">
                <a href="${pageContext.request.contextPath}/WarehouseManagerDashboardController" class="btn btn-secondary exit">Quay lại</a>
            </div>

            <div class="row">
                <!-- Content Section -->
                <div class="col-lg-8">
                    <h1 class="mb-4">Ghi Chú Công Việc</h1>

                    <!-- Form thêm hoặc cập nhật ghi chú -->
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h2 class="h4">${note != null ? "Cập Nhật Ghi Chú" : "Thêm Ghi Chú Mới"}</h2>
                            <form action="WarehouseManagerHome" method="post">
                                <input type="hidden" name="action" value="${note != null ? 'updateNote' : 'addNote'}">
                                <input type="hidden" name="id" value="${note != null ? note.id : ''}">
                                <div class="mb-3">
                                    <label for="title" class="form-label">Tiêu đề:</label>
                                    <input type="text" id="title" name="title" class="form-control" value="${note != null ? note.title : ''}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="description" class="form-label">Mô tả công việc:</label>
                                    <textarea id="description" name="description" rows="3" class="form-control" required>${note != null ? note.description : ''}</textarea>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="startTime" class="form-label">Thời gian bắt đầu:</label>
                                        <input type="text" id="startTime" name="startTime" class="form-control datetimepicker" value="${note != null ? note.startTime : ''}" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="endTime" class="form-label">Thời gian kết thúc:</label>
                                        <input type="text" id="endTime" name="endTime" class="form-control datetimepicker" value="${note != null ? note.endTime : ''}" required>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">${note != null ? "Cập Nhật" : "Thêm"}</button>
                            </form>
                        </div>
                    </div>

                    <!-- Form tìm kiếm ghi chú -->
                    <div class="card mb-4 shadow-sm">
                        <div class="card-body">
                            <h2 class="h4">Tìm Kiếm Ghi Chú</h2>
                            <div class="mb-3">
                                <label for="searchTitle" class="form-label">Tìm theo Tiêu đề:</label>
                                <input type="text" id="searchTitle" class="form-control" placeholder="Nhập tiêu đề">
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="startDateFilter" class="form-label">Thời gian bắt đầu:</label>
                                    <input type="text" id="startDateFilter" class="form-control datetimepicker" placeholder="Chọn ngày bắt đầu">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="endDateFilter" class="form-label">Thời gian kết thúc:</label>
                                    <input type="text" id="endDateFilter" class="form-control datetimepicker" placeholder="Chọn ngày kết thúc">
                                </div>
                            </div>
                            <button type="button" class="btn btn-primary" onclick="filterAndPaginateNotes()">Tìm Kiếm</button>
                        </div>
                    </div>

                    <!-- Hiển thị danh sách ghi chú -->
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h2 class="h4">Danh Sách Ghi Chú</h2>
                            <c:if test="${not empty notes}">
                                <ul class="list-group list-group-flush" id="noteList">
                                    <c:forEach var="note" items="${notes}">
                                        <li class="list-group-item d-flex justify-content-between align-items-start note-item" data-start="${note.startTime}" data-end="${note.endTime}">
                                            <div class="note-details">
                                                <h5 class="mb-1">${note.title}</h5>
                                                <small class="text-muted">${note.startTime} - ${note.endTime}</small>
                                                <p class="mb-1">${note.description}</p>
                                                <span class="badge bg-${note.status == 0 ? 'secondary' : 'success'}">${note.status == 0 ? 'Chưa hoàn thành' : 'Đã hoàn thành'}</span>
                                            </div>
                                            <div>
                                                <form action="WarehouseManagerHome" method="post" class="d-inline">
                                                    <input type="hidden" name="action" value="editNote">
                                                    <input type="hidden" name="id" value="${note.id}">
                                                    <button type="submit" class="btn btn-warning btn-sm">Sửa</button>
                                                </form>
                                                <form action="WarehouseManagerHome" method="post" class="d-inline">
                                                    <input type="hidden" name="action" value="deleteNote">
                                                    <input type="hidden" name="id" value="${note.id}">
                                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                                </form>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>

                                <!-- Phân trang -->
                                <div id="pagination" class="mt-3 text-center"></div>
                            </c:if>
                            <c:if test="${empty notes}">
                                <p class="text-muted">Chưa có ghi chú nào.</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <!-- Sidebar Calendar -->
                <div class="col-lg-4">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h2 class="h4">Lịch Công Việc</h2>
                            <div id="calendar"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

        <script>
                                $(function () {
                                    $(".datetimepicker").datepicker({
                                        dateFormat: "yy-mm-dd",
                                        showAnim: "fadeIn",
                                        showButtonPanel: true,
                                        changeMonth: true,
                                        changeYear: true
                                    });
                                    $("#calendar").datepicker({
                                        onSelect: function (dateText) {
                                            filterAndPaginateNotes();
                                        }
                                    });
                                });

                                const itemsPerPage = 5;
                                let currentPage = 1;

                                function filterAndPaginateNotes() {
                                    const searchTitle = $("#searchTitle").val().toLowerCase();
                                    const startDateFilter = new Date($("#startDateFilter").val());
                                    const endDateFilter = new Date($("#endDateFilter").val());

                                    $("#noteList .note-item").each(function () {
                                        const title = $(this).find(".note-details h5").text().toLowerCase();
                                        const start = new Date($(this).data("start"));
                                        const end = new Date($(this).data("end"));

                                        const matchesTitle = searchTitle === "" || title.includes(searchTitle);
                                        const matchesStartDate = isNaN(startDateFilter.getTime()) || start >= startDateFilter;
                                        const matchesEndDate = isNaN(endDateFilter.getTime()) || end <= endDateFilter;

                                        $(this).toggle(matchesTitle && matchesStartDate && matchesEndDate);
                                    });

                                    currentPage = 1;
                                    paginateNotes();
                                }

                                function paginateNotes() {
                                    const visibleItems = $("#noteList .note-item:visible");
                                    const totalItems = visibleItems.length;
                                    const totalPages = Math.ceil(totalItems / itemsPerPage);

                                    let start = (currentPage - 1) * itemsPerPage;
                                    let end = start + itemsPerPage;

                                    visibleItems.hide().slice(start, end).show();
                                    updatePagination(totalPages);
                                }

                                function updatePagination(totalPages) {
                                    let paginationHtml = '';
                                    for (let i = 1; i <= totalPages; i++) {
                                        paginationHtml += `<button class="pagination-btn btn btn-primary btn-sm mx-1" data-page="${i}"` + (i === currentPage ? ' disabled' : '') + `>${i}</button>`;
                                    }
                                    $("#pagination").html(paginationHtml);
                                    $(".pagination-btn").click(function () {
                                        currentPage = parseInt($(this).data("page"));
                                        paginateNotes();
                                    });
                                }
        </script>
    </body>
</html>
