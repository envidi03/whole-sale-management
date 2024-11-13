<%-- 
    Document   : AddNewAccount
    Created on : Nov 1, 2024, 8:54:08 AM
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
        <title>Add New Account</title>
        <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ViewAccountList.css">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/component/AdminDashboardSidebar.jsp"></jsp:include>
                <div class="main">
                    <main class="container-fluid">
                        <div class="mb-3">

                            <h3 class="fw-bold fs-4 mb-3">Admin Dashboard</h3>
                            <h3 class="fw-bold fs-4 my-3">Add New Account</h3>
                            <form action="AdminAccountDashboardController" method="post">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email:</label>
                                    <input type="text" id="email" name="email" class="form-control" required>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Select Account Type:</label>
                                    <div>
                                        <label class="form-check-label me-2">
                                            <input type="radio" name="accountType" value="system" class="form-check-input" required checked="true"> System Account
                                        </label>
                                    </div>
                                    <div>
                                        <label class="form-check-label">
                                            <input type="radio" name="accountType" value="google" class="form-check-input"> Google Account
                                        </label>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Select Role</label>
                                    <div>
                                        <label class="form-check-label me-2">
                                            <input type="radio" name="accountRole" value="employee" class="form-check-input" required checked="true"> Employee
                                        </label>
                                    </div>
                                    <div>
                                        <label class="form-check-label">
                                            <input type="radio" name="accountRole" value="warehouseManager" class="form-check-input"> Warehouse Manager
                                        </label>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label" for="warehouseId">Warehouse</label>
                                    <select class="form-select form-select-lg" name="warehouseId" aria-label="Select Warehouse">
                                    <c:forEach items="${requestScope.warehouseVector}" var="warehouse">
                                        <option value="${warehouse.getId()}">${warehouse.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="adminNote" class="form-label">System Admin Note:</label>
                                <input type="text" id="adminNote" name="adminNote" class="form-control">
                            </div>
                            <input type="hidden" name="service" value="addNewAccount">
                            <button type="submit" class="btn btn-primary" name="addNewAccountSubmit">Add New Account</button>
                        </form>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
