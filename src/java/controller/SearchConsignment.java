/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.ConsignmentDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Consignment;
import model.Product;
import model.ProductCategory;

/**
 *
 * @author Acer
 */
public class SearchConsignment extends HttpServlet {

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
        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
        int count = consignmentDAO.count(txtSearch);
        // muốn chia số trang thì sẽ lấy kết quả / số trang(5) để phân trang vd 10/5 = 2 trang 
        int pageSize = 5;
        int endPage = 0;
        endPage = count / pageSize;
        // nếu để mặc định sẽ ép kiểu số in -> thêm điều kiện để page + 1 để tạo thêm 1 trang nữa 
        if (count % pageSize != 0) {
            endPage++;
        }
        List<Consignment> listSearch = consignmentDAO.search(txtSearch, index, pageSize);

        // map chứa tên thể loại
        Map<Integer, String> categoryMap = new HashMap<>();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<ProductCategory> categories = categoryDAO.gettAllCategorys();
        for (ProductCategory category : categories) {
            categoryMap.put(category.getId(), category.getName());
        }

        // Map chứa tên sản phẩm
        Map<Integer, String> productMap = new HashMap<>();
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            productMap.put(product.getId(), product.getProductName());
        }

        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("productMap", productMap);
        request.setAttribute("end", endPage);
        request.setAttribute("getAvailableConsignments", listSearch);
        request.setAttribute("txtSearch", txtSearch);
        
        request.getRequestDispatcher("jsp/EmployeeOrder.jsp").forward(request, response);
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
