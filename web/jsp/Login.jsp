<%-- 
    Document   : login
    Created on : Sep 19, 2024, 11:10:04 AM
    Author     : 84941
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Login</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
    </head>
    <body class="bg-gradient-primary">

        <div class="container">

            <!-- Outer Row -->
            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row">
                                <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                        </div>
                                        <form class="user" action="${pageContext.request.contextPath}/LoginController" method="post">
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

                                            <!-- Email input start -->
                                            <div class="form-group">
                                                <input type="email" name="enteredEmail" value="${cookie.cookieEmail.value}" class="form-control form-control-user"
                                                       id="exampleInputEmail" aria-describedby="emailHelp"
                                                       placeholder="Enter Email Address..." required>
                                            </div>
                                            <!-- Email input end -->
                                            <!-- Password input start -->
                                            <div class="form-group">
                                                <input type="password" name="enteredPassword" value="${cookie.cookiePassword.value}" class="form-control form-control-user"
                                                       id="exampleInputPassword" placeholder="Password" required>
                                            </div>
                                            <!-- Paswword input end -->     
                                            <!-- Remember me input start -->
                                            <div class="form-group">
                                                <div class="custom-control custom-checkbox small">
                                                    <input type="checkbox" class="custom-control-input" id="customCheck" name="rememberMe" ${(cookie.cookieRememberMe!=null?'checked':'')}value="ON">
                                                    <label class="custom-control-label" for="customCheck">Remember Me</label>
                                                </div>
                                            </div>
                                            <!-- Remember me input end -->
                                            <button type="submit" name="loginSubmit" value="submit" class="btn btn-primary btn-user btn-block">Login</button>
                                            <input type="hidden" name="service" value="login">
                                            <hr>
                                            <!-- Login with Google -->
                                            <a href="https://accounts.google.com/o/oauth2/auth?scope=profile&redirect_uri=http://localhost:9999/SWP391/LoginGoogleHandler&response_type=code&client_id=771685583949-c4feh0pcpot4p3fmlnofcoq5af3tuqc6.apps.googleusercontent.com&approval_prompt=force" class="btn btn-google btn-user btn-block">
                                                <i class="fab fa-google fa-fw"></i> Login with Google
                                            </a>                                               
                                        </form>
                                        <hr>
                                        <div class="text-center">
                                            <a class="small" href="${pageContext.request.contextPath}/ForgotPasswordController">Forgot Password?</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>

    </body>
</html>
