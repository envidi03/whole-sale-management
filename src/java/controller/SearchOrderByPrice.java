/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
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
import model.Order;

/**
 *
 * @author Acer
 */
public class SearchOrderByPrice extends HttpServlet {

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

        int minPrice = Integer.parseInt(request.getParameter("minPrice"));
        int maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
        if (minPrice < 0 || maxPrice < 0) {
            request.setAttribute("error", "Vui Lòng Nhập Giá Lớn Hơn Không");
            request.getRequestDispatcher("homeController").forward(request, response);
            return;
        }
        String indexString = request.getParameter("index");
        int index = Integer.parseInt(indexString);
        //dùng để chuyển hướng qua các trang jsp khác nhau
        String targetPage = request.getParameter("action");

        AccountDAO accountDAO = new AccountDAO();
        OrderDAO orderDAO = new OrderDAO();
        // lấy ra order với status = 1
        List<Order> listC = orderDAO.getAllOrderByPrice(minPrice, maxPrice);
        // lấy ra order với status = 4
        List<Order> listCC = orderDAO.getAllOrderByPrice2(minPrice, maxPrice);
        // lấy ra order số trang đầu tiên và cuối cùng
        List<Order> list = orderDAO.getOrdersByPage(index, index);
        // lấy ra tên nhân viên trực ca dựa theo id
        Map<Integer, String> employeeNames = new HashMap<>();
        for (Order order : list) {
            int employeeId = order.getEmployeeIdIncharge();
            if (!employeeNames.containsKey(employeeId)) {
                String employeeName = accountDAO.getEmployeeNameById(employeeId);
                employeeNames.put(employeeId, employeeName);
            }
        }

        request.setAttribute("employeeNames", employeeNames);
        request.setAttribute("pendingOrders", listC); // của đơn hàng chưa thanh toán
        request.setAttribute("deliveredOrders", listCC); //  của đơn hàng đã thanh toán
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);

//        request.getRequestDispatcher("jsp/orderPending.jsp").forward(request, response);
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