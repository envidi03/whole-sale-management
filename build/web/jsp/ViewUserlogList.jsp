<%-- 
    Document   : ViewUserlogList
    Created on : Nov 5, 2024, 11:56:06 PM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Userlog</title>
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
                            <h3 class="fw-bold fs-4 my-3">Userlog</h3>
                            <form action="AdminUserlogDashboardController" method="post">
                                <div class="row gx-2 gx-md-3 mb-4">
                                    <!-- Search by ID -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="searchById">Search by ID</label>
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-prepend input-group-text">
                                                <i class="bi-search"></i>
                                            </span>
                                            <input type="text" class="form-control form-control-lg" name="searchById" placeholder="Search by ID" aria-label="Search by ID">
                                        </div>
                                    </div>
                                    <!-- End Search by ID -->

                                    <!-- Search by Account ID -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="searchByAccountId">Search by Account ID</label>
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-prepend input-group-text">
                                                <i class="bi-search"></i>
                                            </span>
                                            <input type="text" class="form-control form-control-lg" name="searchByAccountId" placeholder="Search by Account ID" aria-label="Search by Account ID">
                                        </div>
                                    </div>
                                    <!-- End Search by Account ID -->

                                    <!-- Search by Date -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="searchByDate">Search by Date</label>
                                        <div class="input-group input-group-merge">
                                            <span class="input-group-prepend input-group-text">
                                                <i class="bi-calendar"></i>
                                            </span>
                                            <input type="date" class="form-control form-control-lg" name="searchByDate" aria-label="Search by Date">
                                        </div>
                                    </div>
                                    <!-- End Search by Date -->
                                </div>

                                <div class="row gx-2 gx-md-3 mb-4">
                                    <!-- Date Range -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="dateRange">Date Range</label>
                                        <div class="input-group input-group-merge">
                                            <input type="date" class="form-control form-control-lg" name="dateFrom" placeholder="Start date" aria-label="Start date">
                                            <input type="date" class="form-control form-control-lg" name="dateTo" placeholder="End date" aria-label="End date">
                                        </div>
                                    </div>
                                    <!-- End Date Range -->

                                    <!-- Filter by User Log Type -->
                                    <div class="col-md-4 mb-2 mb-md-0">
                                        <label class="form-label" for="filterByUserLogType">Select User Log Type</label>
                                        <select class="form-select form-select-lg" name="filterByUserLogType" aria-label="Select User Log Type">
                                            <option value="all" selected>All types</option>
                                            <option value="0">Login</option>
                                            <option value="1">Logout</option>
                                        </select>
                                    </div>
                                    <!-- End Filter by User Log Type -->
                                </div>

                                <br/>
                                <!-- Start sorting -->
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

                                    <!-- Sort by Account ID -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByAccountId" value="sortByAccountId" ${checked == 'sortByAccountId' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByAccountId">
                                                Account ID
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Sort by Date -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="sortBy" id="sortByDate" value="sortByDate" ${checked == 'sortByDate' ? 'checked' : ''}>
                                            <label class="form-check-label" for="sortByDate">
                                                Date
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- End sorting -->

                                <br/>
                                <!-- Start choosing field -->
                                <h5 class="mb-3">Display fields:</h5>
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

                                    <!-- Account ID -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckAccountId" name="displayAccountId" ${requestScope.displayAccountId == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckAccountId">
                                                Account ID
                                            </label>
                                        </div>
                                    </div>

                                    <!-- Date -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckDate" name="displayDate" ${requestScope.displayDate == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckDate">
                                                Date
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <!-- User Log Type -->
                                    <div class="col-md-4">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="true" id="flexCheckUserLogType" name="displayUserLogType" ${requestScope.displayUserLogType == 'true'?'checked':''}>
                                            <label class="form-check-label" for="flexCheckUserLogType">
                                                User Log Type
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!-- End choosing field -->

                                <br/>
                                <input type="hidden" name="service" value="viewAllUserlog">
                                <button type="submit" name="filterSubmit" value="submit" class="btn btn-outline-primary">Submit</button>
                            </form>


                            <!-- End Form -->
                            <br/>
                            <div class="row">
                                <div class="col-12">
                                    <table class="table table-striped">    
                                        <thead>
                                            <tr class="highlight">
                                                <c:if test="${requestScope.displayId=='true'}"><th scope="col">ID</th></c:if>
                                                <c:if test="${requestScope.displayAccountId=='true'}"><th scope="col">Account ID</th></c:if>
                                                <c:if test="${requestScope.displayDate=='true'}"><th scope="col">Date</th></c:if>
                                                <c:if test="${requestScope.displayUserLogType=='true'}"><th scope="col">User Log Type</th></c:if>     
                                                <th scope="col">View Account Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${requestScope.userlogVector}" var="userlog">
                                                <tr>
                                                    <c:if test="${requestScope.displayId=='true'}"><td>${userlog.getId()}</td></c:if>
                                                    <c:if test="${requestScope.displayAccountId=='true'}"><td>${userlog.getAccountId()}</td></c:if>
                                                    <c:if test="${requestScope.displayDate=='true'}"><td>${userlog.getDate()}</td></c:if>
                                                    <c:if test="${requestScope.displayUserLogType=='true'}">
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${userlog.isUserLogType()==false}">
                                                                    Login
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Logout
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </c:if>
                                                    <td>
                                                        <a class="btn btn-primary" href="AdminAccountDashboardController?service=viewAccountDetails&id=${userlog.getAccountId()}" role="button">View</a>
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
