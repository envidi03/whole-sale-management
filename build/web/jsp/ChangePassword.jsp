<%-- 
    Document   : ChangePassword
    Created on : Sep 20, 2024, 5:52:45 AM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css' rel='stylesheet'>
        <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css' rel='stylesheet'>
        <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset-password.css">

    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6 mt-5">
                    <!-- White Container -->
                    <div class="bg-white rounded mt-2 mb-2">
                        <!-- Main Heading -->
                        <div class="row justify-content-center align-items-center pt-3">
                            <h1><strong>Change Password</strong></h1>
                        </div>

                        <!-- Form Section -->
                        <div class="pt-3 pb-3">
                            <form class="form-horizontal" action="${pageContext.request.contextPath}/ChangePasswordController?service=changePassword" method="POST">
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
                                
                                <!-- Old Password Input -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-9 password-wrapper">
                                        <input type="password" id="password" name="oldPassword"
                                               placeholder="&#xf084; &nbsp; Old Password"
                                               class="form-control border-info placeicon" required>
                                        <span toggle="#password" class="fa fa-fw fa-eye toggle-password"></span>
                                    </div>
                                </div>
                                <!-- New Password Input -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-9 password-wrapper">
                                        <input type="password" id="password" name="newPassword"
                                               placeholder="&#xf084; &nbsp; New Password"
                                               class="form-control border-info placeicon" required>
                                        <span toggle="#password" class="fa fa-fw fa-eye toggle-password"></span>
                                    </div>
                                </div>

                                <!-- Confirm Password Input -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-9 password-wrapper">
                                        <input type="password" id="confPassword" name="confPassword"
                                               placeholder="&#xf084; &nbsp; Confirm New Password"
                                               class="form-control border-info placeicon" required>
                                        <span toggle="#confPassword" class="fa fa-fw fa-eye toggle-password"></span>
                                    </div>
                                </div>

                                <!-- Reset Button -->
                                <div class="form-group row justify-content-center">
                                    <div class="col-6">
                                        <input type="submit" name="changePasswordSubmit" value="Change" class="btn btn-info btn-block">
                                    </div>
                                </div>
                            </form>
                        </div>


                    </div>
                </div>
            </div>
        </div>

        <script type='text/javascript'
        src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
        <script>
            $(document).on('click', '.toggle-password', function () {
                // Toggle the eye icon
                $(this).toggleClass("fa-eye fa-eye-slash");

                // Toggle the input type between password and text
                let input = $($(this).attr("toggle"));
                if (input.attr("type") === "password") {
                    input.attr("type", "text");
                } else {
                    input.attr("type", "password");
                }
            });
        </script>
    </body>
</html>
