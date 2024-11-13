<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Storage Location Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .exit{
                margin-left: 10px;
            }
        </style>
    </head>

    <body>
        <div class="main-content" id="main-content">

            <div class="container mt-5">
                <h2 class="mb-4 text-center">Vị Trí Lô Hàng</h2>

                <!-- Thông báo -->
                <div class="container-fluid">
                    <c:if test="${message != null}">
                        <div class="alert alert-info text-center">${message}</div> 
                    </c:if>
                </div>

                <!-- Xuất Excel -->
                <div class="d-flex justify-content-end mb-3">
                    <form action="${pageContext.request.contextPath}/StorageLocationController" method="post">
                        <input type="hidden" name="service" value="exportLocation"> 
                        <button class="btn btn-secondary">Xuất Excel</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/OrderImportProductList" class="btn btn-secondary exit">Quay lại</a>
                </div>

                <!-- Search Section -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <form action="${pageContext.request.contextPath}/StorageLocationController" method="post">
                            <input type="hidden" name="service" value="searchLocation">
                            <div class="input-group">
                                <input type="text" name="search" class="form-control" placeholder="Tìm theo ID lô hàng">
                                <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                            </div>
                        </form>
                    </div>
               
                </div>

                <!-- Filter Section -->
                <form action="${pageContext.request.contextPath}/StorageLocationController" method="post" class="row mb-4">
                    <input type="hidden" name="service" value="locationList">

                    <!-- Dropdown Warehouse -->
                    <div class="col-md-3">
                        <label for="warehouse" class="form-label">Chọn Warehouse</label>
                        <select class="form-select" name="warehouse" id="warehouse">
                            <option value="">Tất cả</option>
                            <c:forEach var="warehouse" items="${warehouseList}">
                                <option value="${warehouse}" ${warehouseParam == warehouse ? "selected" : ""}>Warehouse ${warehouse}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Dropdown Shelf -->
                    <div class="col-md-3">
                        <label for="shelf" class="form-label">Chọn Shelf</label>
                        <select class="form-select" name="shelf" id="shelf">
                            <option value="">Tất cả</option>
                            <c:forEach var="shelf" items="${shelfList}">
                                <option value="${shelf}" ${shelfParam == shelf ? "selected" : ""}>Shelf ${shelf}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Filter Button -->
                    <div class="col-md-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Lọc</button>
                    </div>
                </form>

                <!-- Sort Buttons -->
                <div class="d-flex justify-content-start mb-4">
                    <form action="${pageContext.request.contextPath}/StorageLocationController" method="post" class="me-2">
                        <input type="hidden" name="service" value="locationList">
                        <input type="hidden" name="sortOrder" value="asc">
                        <input type="hidden" name="warehouse" value="${warehouseParam}">
                        <input type="hidden" name="shelf" value="${shelfParam}">
                        <button class="btn btn-success" type="submit">Sắp xếp Consignment ID ↑</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/StorageLocationController" method="post">
                        <input type="hidden" name="service" value="locationList">
                        <input type="hidden" name="sortOrder" value="desc">
                        <input type="hidden" name="warehouse" value="${warehouseParam}">
                        <input type="hidden" name="shelf" value="${shelfParam}">
                        <button class="btn btn-danger" type="submit">Sắp xếp Consignment ID ↓</button>
                    </form>
                </div>

                <!-- Storage Location List -->
                <div class="card mb-4">
                    <div class="card-body">
                        <h5 class="card-title">Vị trí lưu trữ</h5>
                        <table class="table table-bordered table-striped table-hover mt-3">
                            <thead class="table-light">
                                <tr>
                                    <th>ID Kho</th>
                                    <th>Kệ</th>
                                    <th>Cột</th>
                                    <th>Hàng</th>
                                    <th>ID Lô Hàng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listST}" var="st">
                                    <tr>
                                        <td>${st.warehouse_id}</td>
                                        <td>${st.shelf}</td>
                                        <td>${st.row}</td>
                                        <td>${st.collum}</td>
                                        <td>${st.consignment_id}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Pagination -->
                <div class="d-flex justify-content-center mt-3">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/StorageLocationController?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>

</html>
