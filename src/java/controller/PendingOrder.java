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
public class PendingOrder extends HttpServlet {

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
        OrderDAO orderDAO = new OrderDAO();
        AccountDAO accountDAO = new AccountDAO();
        // Lấy chỉ số trang hiện tại từ request
        String indexString = request.getParameter("index");
        if (indexString == null) {
            indexString = "1"; // Nếu không có trang hiện tại, mặc định là trang 1
        }
        int index = Integer.parseInt(indexString);

        // Số lượng sản phẩm trên mỗi trang
        int productsPerPage = 5;

        // Lấy tổng số lượng sản phẩm
        int totalOrder = orderDAO.getTotalOrder();

        // Tính toán tổng số trang
        int endPage = totalOrder / productsPerPage;
        if (totalOrder % productsPerPage != 0) {
            endPage++;
        }

        List<Order> pendingOrders = orderDAO.getOrdersByPage(index, productsPerPage);
        // lấy ra tên nhân viên trực ca dựa theo id
        Map<Integer, String> employeeNames = new HashMap<>();
        for (Order order : pendingOrders) {
            int employeeId = order.getEmployeeIdIncharge();
            if (!employeeNames.containsKey(employeeId)) {
                String employeeName = accountDAO.getEmployeeNameById(employeeId);
                employeeNames.put(employeeId, employeeName);
            }
        }
        request.setAttribute("employeeNames", employeeNames);
        request.setAttribute("pendingOrders", pendingOrders);
        request.setAttribute("endPage", endPage);
        request.setAttribute("currentPage", index);
        
        request.getRequestDispatcher("jsp/orderPending.jsp").forward(request, response);
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
