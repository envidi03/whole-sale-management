<%-- 
    Document   : AccountProfile
    Created on : Nov 5, 2024, 8:52:19 PM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Account Profile</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            .wrapper {
                display: flex;
                height: 100vh;
            }

            #ViewUserDetail {
                flex-grow: 1;
                overflow-y: auto;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <c:set var="account" value="${requestScope.account}"></c:set>
                <section style="background-color: #eee;" id="ViewUserDetail" >
                    <div class="container py-5">
                        <!-- Display error message if any -->
                    <c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" role="alert">
                            ${pageScope.errorMessage}
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
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="${account.getImage()}" alt="avatar"
                                         class="rounded-circle img-fluid" style="width: 150px;">
                                    <h5 class="my-3">${account.getFirstName()} ${account.getLastName()}</h5>
                                    <c:forEach items="${requestScope.roleVector}" var="role">
                                        <c:if test="${account.getRoleId()==role.getId()}">
                                            <input type="text" class="form-control w-100" value="${role.getTitle()}" readonly>
                                        </c:if>
                                    </c:forEach>

                                    <br/>                                    
                                    <form action="${pageContext.request.contextPath}/AccountProfileController?service=updateAvatar" method="post" enctype="multipart/form-data">
                                        <input type="file" class="form-control" name="avatar" accept="image/*" required>
                                        <input type="hidden" name="accountId" value="${account.getId()}">
                                        <button type="submit" class="btn btn-primary mt-2">Change Avatar</button>
                                    </form>
                                    <!-- Update user end -->
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-8">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <form action="${pageContext.request.contextPath}/AccountProfileController?service=updateAccount" method="post">
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">First Name</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control text-muted mb-0" placeholder="${account.getFirstName()}" name="firstName" value="${account.getFirstName()}" required>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Last Name</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control text-muted mb-0" placeholder="${account.getLastName()}" name="lastName" value="${account.getLastName()}" required>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Gender</label>
                                            </div>
                                            <div class="col-sm-9">                                            
                                                <input type="text" class="form-control text-muted mb-0" placeholder="${account.isGender()==false ? 'Male' : 'Female'}" name="gender" value="${account.isGender()==false ? 'Male' : 'Female'}" readonly="true">
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Email</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="email" class="form-control text-muted mb-0" placeholder="${account.getEmail()}" name="email" value="${account.getEmail()}" readonly="true">
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Phone</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control text-muted mb-0" placeholder="${account.getPhoneNumber()}" name="phone" value="${account.getPhoneNumber()}" required>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Date of Birth</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="date" class="form-control text-muted mb-0" name="dob" value="${account.getDob()}" required>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Created Date</label>
                                            </div>
                                            <div class="col-sm-9">
                                                <input type="date" class="form-control text-muted mb-0" name="createdDate" value="${account.getCreatedDate()}" readonly>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Warehouse</label>
                                            </div>
                                            <div class="col-sm-9">

                                                <c:forEach items="${requestScope.warehouseVector}" var="warehouse">
                                                    <c:if test="${account.getWarehouseId()==warehouse.getId()}">
                                                        <input type="text" class="form-control text-muted mb-0" value="${warehouse.getName()}" readonly>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row">
                                            <div class="col-sm-3">
                                                <label class="mb-0">Account Type</label>
                                            </div>
                                            <div class="col-sm-9">                                               
                                                <c:forEach items="${requestScope.accountTypeVector}" var="accountType">
                                                    <c:if test="${account.getAccountTypeId()==accountType.getId()}">
                                                        <input type="text" class="form-control text-muted mb-0" value="${accountType.getTitle()}" readonly>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <hr>
                                        <input type="hidden" name="accountId" value="${account.getId()}">
                                        <div class="row">
                                            <div class="col-sm-9 offset-sm-3">
                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </section>
</div>
</body>
</html>
