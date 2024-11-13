/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
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
import model.Product;
import model.ProductCategory;

/**
 *
 * @author Acer
 */
public class SearchAndPaging extends HttpServlet {

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
        try {
            String txtSearch = request.getParameter("txtSearch");
            String indexString = request.getParameter("index");
            int index = Integer.parseInt(indexString);

            ProductDAO productDAO = new ProductDAO();
            int count = productDAO.count(txtSearch);
            //     System.out.println(count);
            // muốn chia số trang thì sẽ lấy kết quả / số trang(5) để phân trang vd 10/5 = 2 trang 
            int pageSize = 5;
            int endPage = 0;
            endPage = count / pageSize;
            // nếu để mặc định sẽ ép kiểu số in -> thêm điều kiện để page + 1 để tạo thêm 1 trang nữa 
            if (count % pageSize != 0) {
                endPage++;
            }
            List<Product> listSearch = productDAO.search(txtSearch, index, pageSize);

            // Lấy danh sách thể loại sản phẩm
            Map<Integer, String> categoryMap = new HashMap<>();
            CategoryDAO categoryDAO = new CategoryDAO();
            List<ProductCategory> categories = categoryDAO.gettAllCategorys();
            List<ProductCategory> listC = categoryDAO.gettAllCategorys();
            for (ProductCategory category : categories) {
                categoryMap.put(category.getId(), category.getName());
            }

            request.setAttribute("categoryMap", categoryMap);
            request.setAttribute("listC", listC);
            request.setAttribute("end", endPage);
            request.setAttribute("listP", listSearch);
            request.setAttribute("txtSearch", txtSearch);
            request.getRequestDispatcher("jsp/EmployeeHome.jsp").forward(request, response);
        } catch (Exception e) {
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
