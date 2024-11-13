<%-- 
    Document   : EnterOTP
    Created on : Sep 20, 2024, 2:55:33 AM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OTP Form</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/enter_otp.css">
    </head>
    <body>
        <div class="form-gap"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-4 offset-md-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="text-center">
                                <h3>
                                    <i class="fa fa-lock fa-4x"></i>
                                </h3>
                                <h2 class="text-center">Enter OTP</h2>
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
                                <p class='text-danger ml-1'>Please enter the OTP sent to your email.</p>
                                <div class="panel-body">
                                    <form id="register-form"  role="form" autocomplete="off" class="form" action="${pageContext.request.contextPath}/ForgotPasswordController?service=verifyOTP" method="post">
                                        <div class="form-group">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <i class="glyphicon glyphicon-envelope"></i>
                                                </span>
                                                <input id="enteredOTP" name="enteredOTP" placeholder="Enter OTP" class="form-control" type="text" required="required">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <input name="ForgotPasswordEnterOTPSubmit" class="btn btn-lg btn-primary btn-block" value="Submit OTP" type="submit">
                                        </div>
                                        <input type="hidden" class="hide" name="token" id="token" value="">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
