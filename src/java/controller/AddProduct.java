package controller;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.UnitDAO;
import util.DataConvert;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductCategory;
import model.Unit;

/**
 * Servlet for handling AddProduct requests with a similar structure to OrderImportProductList.
 */
public class AddProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String service = request.getParameter("service");
        if (service == null) {
            service = "addProductForm";
        }

        switch (service) {
            case "addProductForm":
                loadAddProductForm(request, response);
                break;
            case "addProduct":
                addProduct(request, response);
                break;
            case "addProductCategory":
                addCategory(request, response);
                break;
            case "addUnit":
                addUnit(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service parameter.");
                break;
        }
    }

    private void loadAddProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UnitDAO unitDao = new UnitDAO();
        CategoryDAO categoryDao = new CategoryDAO();

        Vector<Unit> units = unitDao.getAllUnit();
        Vector<ProductCategory> categories = categoryDao.getAllProductCategory();

        request.setAttribute("units", units);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("jsp/AddProduct.jsp").forward(request, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String manufactureDateStr = request.getParameter("manufactureDate");
        String expireDateStr = request.getParameter("expireDate");
        int unitId = Integer.parseInt(request.getParameter("unitId"));
        int retailPrice = Integer.parseInt(request.getParameter("retailPrice"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DataConvert dataConvert = new DataConvert();
        Date manufactureDate = null;
        Date expireDate = null;

        try {
            if (manufactureDateStr != null && !manufactureDateStr.isEmpty()) {
                manufactureDate = dataConvert.UtilDateToSqlDate(dateFormat.parse(manufactureDateStr));
            }
            if (expireDateStr != null && !expireDateStr.isEmpty()) {
                expireDate = dataConvert.UtilDateToSqlDate(dateFormat.parse(expireDateStr));
            }
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
            loadAddProductForm(request, response);
            return;
        }

        Product product = new Product(0, productName, manufactureDate, expireDate, unitId, retailPrice, categoryId);
        ProductDAO productDao = new ProductDAO();
        productDao.addProduct(product);
        response.sendRedirect(request.getContextPath() + "/OrderImportProduct");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("newCategoryName");
        ProductCategory category = new ProductCategory(0, categoryName);

        CategoryDAO categoryDao = new CategoryDAO();
        categoryDao.addProductCategory(category);
        response.sendRedirect(request.getContextPath() + "/AddProduct?service=addProductForm");
    }

    private void addUnit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String unitName = request.getParameter("newUnitName");
        Unit unit = new Unit(0, unitName);

        UnitDAO unitDao = new UnitDAO();
        unitDao.addUnit(unit);
        response.sendRedirect(request.getContextPath() + "/AddProduct?service=addProductForm");
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
        return "AddProduct servlet for handling adding new products, categories, and units with service-based structure";
    }
}
