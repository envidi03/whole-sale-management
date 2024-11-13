<%-- 
    Document   : AddNewUser
    Created on : Sep 20, 2024, 6:47:18 AM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.Account" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View All Account</title>
        <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ViewAccountList.css">
    </head>
    <body>

        <div class="wrapper">  
            <jsp:include page="/component/AdminDashboardSidebar.jsp"></jsp:include>
                <div class="main">
                    <main class="content px-3 py-4">
                        <div class="container-fluid">
                            <div class="mb-3">
                                <!-- Display error message if any -->
                            <c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger" role="alert">
                                    ${requestScope.errorMessage}
                                </div>
                            </c:if>                                           
                            <!-- End error message -->

                            <!-- Display message if any -->
                            <c:set var="message" value="${requestScope.message}"></c:set>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success" role="alert">
                                    ${pageScope.message}
                                </div>
                            </c:if>                                            
                            <!-- End message -->
                            <!-- Profile start -->
                            <nav class="navbar navbar-expand px-4 py-3">
                                <div class="navbar-collapse collapse">
                                    <ul class="navbar-nav ms-auto">
                                        <li class="nav-item dropdown">
                                            <c:set var="account" value="${sessionScope.account}"/>
                                            <a href="AccountProfileController?service=viewAccountProfile&&accountId=${account.getId()}" data-bs-toggle="dropdown" class="nav-icon pe-md-0">
                                                <img src="${account.getImage()}" class="avatar img-fluid" alt="">
                                            </a>
                                            <div class="dropdown-menu dropdown-menu-end rounded">
                                                
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </nav>
                            <!-- Profile end -->

                            <h3 class="fw-bold fs-4 mb-3">Admin Dashboard</h3>
                            <h3 class="fw-bold fs-4 my-3">Account List</h3>
                            <form action="AdminAccountDashboardController" method="post">
                                <div class="row gx-2 gx-md-3 mb-4">
                                    <!-- Search by id -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="searchById">Search by ID</label>
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-prepend input-group-text">
                                                <i class="bi-search"></i>
                                            </span>
                                            <input type="text" class="form-control form-control-lg" name="searchById" placeholder="Search by ID" aria-label="Search by ID">
                                        </div>
                                    </div>
                                    <!-- End Search by id -->

                                    <!-- Search by email -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="searchByEmail">Search by Email</label>
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-prepend input-group-text">
                                                <i class="bi-search"></i>
                                            </span>
                                            <input type="text" class="form-control form-control-lg" name="searchByEmail" placeholder="Search by Email" aria-label="Search by Email">
                                        </div>
                                    </div>
                                    <!-- End Search by email -->

                                    <!-- Filter by role -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="filterByRole">Select Role</label>
                                        <select class="form-select form-select-lg" name="filterByRole" aria-label="Select Role">
                                            <option value="all" selected>All roles</option>
                                            <c:forEach items="${requestScope.roleVector}" var="role">
                                                <option value="${role.getId()}">${role.getTitle()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <!-- End filter by role -->
                                </div>

                                <div class="row gx-2 gx-md-3 mb-4">
                                    <!-- Filter by status -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="filterByStatus">Select Status</label>
                                        <select class="form-select form-select-lg" name="filterByStatus" aria-label="Select Status">
                                            <option value="all" selected>All status</option>
                                            <option value="0">Deactivated</option>
                                            <option value="1">Activated</option>
                                            <option value="2">Default password</option>
                                        </select>
                                    </div>
                                    <!-- End filter by status -->

                                    <!-- Filter by warehouse id -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="filterByWarehouseId">Select Warehouse</label>
                                        <select class="form-select form-select-lg" name="filterByWarehouseId" aria-label="Select Warehouse ID">
                                            <option value="all" selected>All warehouses</option>
                                            <c:forEach items="${requestScope.warehouseVector}" var="warehouse">
                                                <option value="${warehouse.getId()}">${warehouse.getName()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <!-- End filter by warehouse id -->

                                    <!-- Filter by account type  -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="filterByAccountType">Select Account Type</label>
                                        <select class="form-select form-select-lg" name="filterByAccountType" aria-label="Select Account">
                                            <option value="all" selected>All accounts</option>
                                            <c:forEach items="${requestScope.accountTypeVector}" var="accountType">
                                                <option value="${accountType.getId()}">${accountType.getTitle()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <!-- End filter by account type-->
                                </div>
                                <br/>
                                <!-- Start sorting -->
                                <c:set var="checked" value="${requestScope.checked}"></c:set>
                                    <h5 class="mb-3">Sort by:</h5>
                                    <div class="row">
                                        <!-- Sort by ID -->
                                        <div class="col-md-4">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="sortBy" id="sortById" value="sortById" ${checked == 'sortById' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortById">
                                                ID
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Sort by Email -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByEmail" value="sortByEmail" ${checked == 'sortByEmail' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByEmail">
                                                Email
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Sort by Role -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByRole" value="sortByRole" ${checked == 'sortByRole' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByRole">
                                                Role ID
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <!-- Sort by Status -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByStatus" value="sortByStatus" ${checked == 'sortByStatus' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByStatus">
                                                Status
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Sort by Warehouse ID -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByWarehouseId" value="sortByWarehouseId" ${checked == 'sortByWarehouseId' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByWarehouseId">
                                                Warehouse ID
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Sort by Account Type -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByAccountType" value="sortByAccountType" ${checked == 'sortByAccountType' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByAccountType">
                                                Account Type ID
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- End sorting -->

                                <br/>
                                <!-- Start choosing field -->
                                <h5 class="mb-3">Display fields: </h5>
                                <div class="row">
                                    <!-- ID -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckId" name="displayId" ${requestScope.displayId == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckId">
                                                ID
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Email -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckEmail" name="displayEmail" ${requestScope.displayEmail == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckEmail">
                                                Email
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Role -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckRole" name="displayRole" ${requestScope.displayRole == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckRole">
                                                Role
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <!-- Status -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckStatus" name="displayStatus" ${requestScope.displayStatus == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckStatus">
                                                Status
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Warehouse ID -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckWarehouseId" name="displayWarehouseId" ${requestScope.displayWarehouseId == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckWarehouseId">
                                                Warehouse  
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Account Type -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckAccountType" name="displayAccountType" ${requestScope.displayAccountType == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckAccountType">
                                                Account Type
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- End choosing field -->

                                <br/>
                                <input type="hidden" name="service" value="viewAllAccount">
                                <button type="submit" name="fillterSubmit"  value="submit" class="btn btn-outline-primary">Submit</button>

                            </form>
                            <!-- End Form -->
                            <br/>
                            <div class="row">
                                <div class="col-12">
                                    <table class="table table-striped">    
                                        <thead>
                                            <tr class="highlight">
                                                <c:if test="${requestScope.displayId=='true'}"><th scope="col">ID</th></c:if>
                                                <c:if test="${requestScope.displayEmail=='true'}"><th scope="col">Email</th></c:if>
                                                <c:if test="${requestScope.displayRole=='true'}"><th scope="col">Role</th></c:if>
                                                <c:if test="${requestScope.displayStatus=='true'}"><th scope="col">Status</th></c:if>
                                                <c:if test="${requestScope.displayWarehouseId=='true'}"><th scope="col">Warehouse</th></c:if>
                                                <c:if test="${requestScope.displayAccountType=='true'}"><th scope="col">Account Type</th></c:if>
                                                    <th scope="col">View</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${requestScope.accountVector}" var="account">
                                                <tr>
                                                    <c:if test="${requestScope.displayId=='true'}"><td>${account.getId()}</td></c:if>
                                                    <c:if test="${requestScope.displayEmail=='true'}"><td>${account.getEmail()}</td></c:if>
                                                    <c:if test="${requestScope.displayRole=='true'}">
                                                        <td>
                                                            <c:forEach items="${requestScope.roleVector}" var="role">
                                                                <c:if test="${account.getRoleId()==role.getId()}">
                                                                    ${role.getTitle()}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${requestScope.displayStatus=='true'}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${account.getStatus() == 0}">
                                                                    Deactivated
                                                                </c:when>
                                                                <c:when test="${account.getStatus() == 1}">
                                                                    Activated
                                                                </c:when>
                                                                <c:when test="${account.getStatus() == 2}">
                                                                    Default password
                                                                </c:when>
                                                            </c:choose>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${requestScope.displayWarehouseId=='true'}">
                                                        <td>
                                                            <c:forEach items="${requestScope.warehouseVector}" var="warehouse">
                                                                <c:if test="${account.getWarehouseId()==warehouse.getId()}">
                                                                    ${warehouse.getName()}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${requestScope.displayAccountType=='true'}">
                                                        <td>
                                                            <c:forEach items="${requestScope.accountTypeVector}" var="accountType">
                                                                <c:if test="${account.getAccountTypeId()==accountType.getId()}">
                                                                    ${accountType.getTitle()}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                    </c:if>
                                                    <td>
                                                        <a class="btn btn-primary" href="AdminAccountDashboardController?service=viewAccountDetails&id=${account.getId()}" role="button">View</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                            </div>
                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${requestScope.totalPage}" var="i">
                                        <li class="page-item"><a class="page-link" href="AdminAccountDashboardController?service=viewAllAccount&index=${i}">${i}</a></li>
                                        </c:forEach>
                                </ul>
                            </nav>


                        </div>
                    </div>
                </main>
            </div>
        </div>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/add_new_user.js"></script>
    </body>
</html>
