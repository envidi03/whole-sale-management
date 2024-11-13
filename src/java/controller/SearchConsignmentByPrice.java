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
public class SearchConsignmentByPrice extends HttpServlet {

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
            request.setAttribute("error2", "Vui Lòng Nhập Giá Lớn Hơn Không");
            request.getRequestDispatcher("viewConsignment").forward(request, response);
            return;
        }

        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
        List<Consignment> list = consignmentDAO.getConsignmentsByPriceRange(minPrice, maxPrice);

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

        List<Product> listP = consignmentDAO.getAllProductsASC();

        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("productMap", productMap);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("listC", list);
        request.setAttribute("listP", listP);

        request.getRequestDispatcher("jsp/EmployeeViewConsignment.jsp").forward(request, response);

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
        
        ConsignmentDAO consignmentDAO = new ConsignmentDAO();

        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (quantity < 0) {
            request.setAttribute("error3", "Vui Lòng Nhập Số Lượng Lớn Hơn Không");
            request.getRequestDispatcher("viewConsignment").forward(request, response);
            return;
        }

        List<Consignment> list = consignmentDAO.getConsignmentsQuantity(quantity);

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

        List<Product> listP = consignmentDAO.getAllProductsASC();

        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("productMap", productMap);
        request.setAttribute("quantity", quantity);

        request.setAttribute("listC", list);
        request.setAttribute("listP", listP);

        request.getRequestDispatcher("jsp/EmployeeViewConsignment.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int discount = Integer.parseInt(request.getParameter("discount"));

        if (discount < 0) {
            request.setAttribute("error4", "Vui Lòng Nhập discount Lớn Hơn Không");
            request.getRequestDispatcher("viewConsignment").forward(request, response);
            return;
        }

        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
        List<Consignment> list = consignmentDAO.getConsignmentsdiscount(discount);

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

        List<Product> listP = consignmentDAO.getAllProductsASC();

        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("productMap", productMap);
        request.setAttribute("discount", discount);
        request.setAttribute("listC", list);
        request.setAttribute("listP", listP);

        request.getRequestDispatcher("jsp/EmployeeViewConsignment.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
