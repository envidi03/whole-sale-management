<%-- 
    Document   : ForgotPassword
    Created on : Sep 20, 2024, 2:06:26 AM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css' rel='stylesheet'>
        <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/forgot_password.css">
    </head>
    <body>
        <div class="container padding-bottom-3x mb-2 mt-5">
            <div class="row justify-content-center">

                <div class="col-lg-8 col-md-10">
                    <div class="forgot">
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
                        <h2>Forgot your password?</h2>
                        <p>Change your password in three easy steps. This will help you
                            to secure your password!</p>
                        <ol class="list-unstyled">
                            <li><span class="text-primary text-medium">1. </span>Enter
                                your email address below.</li>
                            <li><span class="text-primary text-medium">2. </span>Our
                                system will send you an OTP to your email</li>
                            <li><span class="text-primary text-medium">3. </span>Enter the OTP on the
                                next page</li>
                        </ol>
                    </div>
                    <form class="card mt-4" action="${pageContext.request.contextPath}/ForgotPasswordController?service=sendEmail" method="post">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="email-for-pass">Enter your email address</label>
                                <input class="form-control" type="email" name="email" placeholder="Your email..."
                                       id="email-for-pass" required="">
                                <small class="form-text text-muted">Enter the registered email address . Then we'll
                                    email a OTP to this address.</small>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button class="btn btn-success" type="submit" value="submit" name="forgotPasswordSendEmailSubmit">Submit</button>
                            <a href="${pageContext.request.contextPath}/LoginController" class="btn btn-danger">Back to Login</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script type='text/javascript'
        src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
    </body>
</html>
