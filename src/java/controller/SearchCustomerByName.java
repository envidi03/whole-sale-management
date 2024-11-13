/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.CustomerDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.Order;

/**
 *
 * @author Acer
 */
public class SearchCustomerByName extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String txtSearch = request.getParameter("txtSearch");
        String indexString = request.getParameter("index");
        int index = Integer.parseInt(indexString);

        OrderDAO orderDAO = new OrderDAO();
        AccountDAO accountDAO = new AccountDAO();
        int count = orderDAO.count(txtSearch);
        
        int pageSize = 5;
        int endPage = 0;
        endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }

        // lấy ra order số trang đầu tiên và cuối cùng
        List<Order> list = orderDAO.getOrdersByPage(index, index);
        

        //dùng để chuyển hướng qua các trang jsp khác nhau
        String targetPage = request.getParameter("targetPage");

        // tim kiem order voi status = 1
        List<Order> listSearchC = orderDAO.search(txtSearch, index, pageSize);
        // lấy ra tên nhân viên trực ca dựa theo id
        Map<Integer, String> employeeNames = new HashMap<>();
        for (Order order : list) {
            int employeeId = order.getEmployeeIdIncharge();
            if (!employeeNames.containsKey(employeeId)) {
                String employeeName = accountDAO.getEmployeeNameById(employeeId);
                employeeNames.put(employeeId, employeeName);
            }
        }

        List<Order> listSearchCC = orderDAO.search2(txtSearch, index, pageSize);

        request.setAttribute("employeeNames", employeeNames);
        request.setAttribute("txtSearch", txtSearch);
        request.setAttribute("pendingOrders", listSearchC); // của đơn hàng chưa thanh toán
        request.setAttribute("deliveredOrders", listSearchCC); //  của đơn hàng đã thanh toán
        request.setAttribute("end", endPage);

        if ("pending".equals(targetPage)) {
            request.getRequestDispatcher("jsp/orderPending.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("jsp/orderDelivered.jsp").forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
