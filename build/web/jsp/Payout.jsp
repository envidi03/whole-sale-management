<%-- 
    Document   : Payout
    Created on : Oct 21, 2024, 7:46:19 PM
    Author     : ThinkPad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Order" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payout</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .order-details {
                margin: 20px 0;
                padding: 20px;
                border: 1px solid #ddd;
                background-color: #f9f9f9;
            }
            h2 {
                color: #333;
            }
            button {
                background-color: #28a745;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <h1>Payout</h1>
        <div class="order-details">
            <h2>Order Details</h2>
            <p><strong>Order ID:</strong> ${order.id}</p>
            <p><strong>Customer Name:</strong> ${order.customerName}</p>
            <p><strong>Total Before Discount:</strong> ${order.orderValueBeforeDiscount}</p>
            <p><strong>Discount (%):</strong> ${order.totalDiscountPercenTage}</p>
            <p><strong>Total After Discount:</strong> ${order.orderValueAfterDiscount}</p>
            <p><strong>Created Date:</strong> ${order.createdDate}</p>
            <p><strong>Payment Date:</strong> ${order.payDate}</p>
        </div>
        <form action="ProcessPayment" method="POST">
            <input type="hidden" name="orderId" value="${order.id}" />
            <button type="submit">Confirm Payment</button>
        </form>
        <div>
            <button>
                <a href="vnpay_pay.jsp">Thanh toán bằng VNPAY</a>
            </button>
            
        </div>
    </body>
</html>

