package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.Product;
import model.ProductCategory;

public class EmployeeHomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy chỉ số trang hiện tại từ request
        String indexString = request.getParameter("index");
        if (indexString == null) {
            indexString = "1"; // Nếu không có trang hiện tại, mặc định là trang 1
        }
        int index = Integer.parseInt(indexString);

        // Số lượng sản phẩm trên mỗi trang
        int productsPerPage = 5;

        ProductDAO productDAO = new ProductDAO();
        

        // Lấy tổng số lượng sản phẩm
        int totalProducts = productDAO.getTotalProducts();

        // Tính toán tổng số trang
        int endPage = totalProducts / productsPerPage;
        if (totalProducts % productsPerPage != 0) {
            endPage++;
        }

        // Lấy danh sách sản phẩm cho trang hiện tại
        List<Product> list = productDAO.getProductsByPage(index, productsPerPage);

        // Lấy danh sách thể loại sản phẩm
        Map<Integer, String> categoryMap = new HashMap<>();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<ProductCategory> categories = categoryDAO.gettAllCategorys();
        for (ProductCategory category : categories) {
            categoryMap.put(category.getId(), category.getName());
        }
        // lấy ra tất cả thể loại
        List<ProductCategory> listC = categoryDAO.gettAllCategorys();

        // Set các thuộc tính cho JSP
        request.setAttribute("listC", listC);
        request.setAttribute("categoryMap", categoryMap);
        request.setAttribute("listP", list);
        request.setAttribute("endPage", endPage);
        request.setAttribute("currentPage", index);

        // Chuyển hướng đến trang JSP
        request.getRequestDispatcher("jsp/EmployeeHome.jsp").forward(request, response);
    }

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
        return "Servlet xử lý hiển thị sản phẩm với phân trang";
    }
}
