<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product, Category, and Unit</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Thêm Sản Phẩm</h2>

        <!-- Form to Add Product -->
        <div class="card mb-4">
            <div class="card-header">Thêm Sản Phẩm</div>
            <div class="card-body">
                <form action="AddProduct" method="post">
                    <input type="hidden" name="service" value="addProduct"/>
                    <div class="form-group">
                        <label for="productName">Tên Sản Phẩm</label>
                        <input type="text" class="form-control" id="productName" name="productName" placeholder="Enter product name" required>
                    </div>
                    <div class="form-group">
                        <label for="manufactureDate">Ngày sản xuất</label>
                        <input type="date" class="form-control" id="manufactureDate" name="manufactureDate" required>
                    </div>
                    <div class="form-group">
                        <label for="expireDate">Ngày hết hạn</label>
                        <input type="date" class="form-control" id="expireDate" name="expireDate" required>
                    </div>
                    <div class="form-group">
                        <label for="unitId">Đơn vị</label>
                        <select class="form-control" id="unitId" name="unitId" required>
                            <option value="">Chọn Đơn vị</option>
                            <c:forEach var="unit" items="${units}">
                                <option value="${unit.id}">${unit.name}</option>
                            </c:forEach>
                        </select>
                        <button type="button" class="btn btn-link p-0" data-toggle="modal"
                            data-target="#addUnitModal">Thêm Đơn Vị</button>
                    </div>
                    <div class="form-group">
                        <label for="retailPrice">Giá bán lẻ</label>
                        <input type="number" class="form-control" id="retailPrice" name="retailPrice" placeholder="Enter retail price" required>
                    </div>
                    <div class="form-group">
                        <label for="categoryId">Loại</label>
                        <select class="form-control" id="categoryId" name="categoryId" required>
                            <option value="">Chọn Loại</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                        <button type="button" class="btn btn-link p-0" data-toggle="modal"
                            data-target="#addCategoryModal">Thêm Loại Mới</button>
                    </div>
                    <!--<button type="button"  class="btn btn-primary" >Thoát</button>-->
                    <button type="submit" class="btn btn-primary">Thêm Sản Phẩm</button>
                </form>
                <button class="btn btn-outline-secondary mr-2"><a href="${pageContext.request.contextPath}/OrderImportProduct" style="text-decoration: none;">Thoát</a></button>
            </div>
        </div>
    </div>

    <!-- Modal for Adding New Category -->
    <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel"
        aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addCategoryModalLabel">Thêm Loại Mới</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="AddProduct" method="post">
                        <input type="hidden" name="service" value="addProductCategory"/>
                        <div class="form-group">
                            <label for="newCategoryName">Tên Loại</label>
                            <input type="text" class="form-control" id="newCategoryName" name="newCategoryName"
                                placeholder="Enter new category name" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Thêm</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for Adding New Unit -->
    <div class="modal fade" id="addUnitModal" tabindex="-1" aria-labelledby="addUnitModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addUnitModalLabel">Thêm Đơn vị</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="AddProduct" method="post">
                        <input type="hidden" name="service" value="addUnit"/>
                        <div class="form-group">
                            <label for="newUnitName">Tên Đơn Vị</label>
                            <input type="text" class="form-control" id="newUnitName" name="newUnitName" placeholder="Enter new unit name" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Thêm</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
