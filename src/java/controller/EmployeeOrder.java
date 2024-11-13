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
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cart;
import model.Consignment;
import model.Item;
import model.Product;
import model.ProductCategory;

/**
 *
 * @author Acer
 */
public class EmployeeOrder extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        
        
        Cart cart = null;
        Object o = session.getAttribute("cart");
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }
        String tid = request.getParameter("id");
        String tnum = request.getParameter("num");
        int id, num;

        try {
            id = Integer.parseInt(tid);
            num = Integer.parseInt(tnum);

            ConsignmentDAO consignmentDAO = new ConsignmentDAO();
            Consignment c = consignmentDAO.getConsignmentById(id);
            int price = c.getSellingPrice();
            Item t = new Item(c, num, price);
            cart.addItem(t);
        } catch (NumberFormatException e) {
            num = 1;
        }
        
            

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

        for (Map.Entry<Integer, String> entry : productMap.entrySet()) {
            System.out.println("Product ID: " + entry.getKey() + ", Name: " + entry.getValue());
        }

        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("productMap", productMap);

        List<Item> list = cart.getItems();
        session.setAttribute("cart", cart);
        session.setAttribute("size", list.size());

        // sử dụng để ẩn sản phẩm trong lô hàng khi bấm mua 
        session.setAttribute("cartIds", cart.getItemIds());

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
