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
public class FilterByPriceController extends HttpServlet {

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

        ProductDAO productDAO = new ProductDAO();

        List<Product> list = productDAO.getProductsByPriceRange(minPrice, maxPrice);
        System.out.println(list);
        Map<Integer, String> categoryMap = new HashMap<>();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<ProductCategory> categories = categoryDAO.gettAllCategorys();
        List<ProductCategory> listC = categoryDAO.gettAllCategorys();
        for (ProductCategory category : categories) {
            categoryMap.put(category.getId(), category.getName());
        }
        

        request.setAttribute("listC", listC);
        request.setAttribute("listP", list);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("categoryMap", categoryMap);

        request.getRequestDispatcher("jsp/EmployeeHome.jsp").forward(request, response);

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
//        String txtSearch = request.getParameter("txtSearch");
//        String indexString = request.getParameter("index");
//        int index = Integer.parseInt(indexString);
//
//        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
//        int count = consignmentDAO.count(txtSearch);
//
//        int pageSize = 5;
//        int endPage = 0;
//        endPage = count / pageSize;
//
//        List<Consignment> list = consignmentDAO.search1(txtSearch, index, pageSize);
//
//        request.setAttribute("end", endPage);
//        request.setAttribute("listP", list);
//        request.setAttribute("txtSearch", txtSearch);
//
//        request.getRequestDispatcher("jsp/EmployeeOrder.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//                String txtSearch = request.getParameter("txtSearch");
//        String indexString = request.getParameter("index");
//        int index = Integer.parseInt(indexString);
//
//        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
//        int count = consignmentDAO.count(txtSearch);
//
//        int pageSize = 5;
//        int endPage = 0;
//        endPage = count / pageSize;
//
//        List<Consignment> list = consignmentDAO.search1(txtSearch, index, pageSize);
//        
//        List<Product> listP = consignmentDAO.getAllProductsASC();
////        List<Consignment> listC = consignmentDAO.getAllConsignment();
//        
//        request.setAttribute("listP", listP);
//
//        request.setAttribute("end", endPage);
//        request.setAttribute("listC", list);
//        request.setAttribute("txtSearch", txtSearch);
//
//        request.getRequestDispatcher("jsp/EmployeeOrder.jsp").forward(request, response);
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
