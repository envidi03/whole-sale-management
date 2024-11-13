<%-- 
    Document   : CheckOut
    Created on : Oct 30, 2024, 1:15:25 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Check Out</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/checkOutSuccess" method="post">
        <label for="customerName">Tên Khách Hàng:</label>
        <input type="text" id="customerName" name="customerName" required>

        <button type="submit">Xác Nhận Thông Tin</button>
    </form>
</body>
</html>

