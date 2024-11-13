package controller;

import dal.ContractDAO;
import dal.ContractReportDAO;
import dal.FiredEmployeeReportDAO;
import dal.FiredEmployeeDAO;
import dal.OrderReportDAO;
import model.ContractReport;
import model.FiredEmployeeReport;
import model.OrderReport;
import model.Contract;
import java.io.IOException;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.FiredEmployee;
import model.Order;

/**
 * Controller for handling Warehouse Manager Dashboard actions.
 */
public class WarehouseManagerDashboardController extends HttpServlet {

    private final ContractReportDAO contractReportDAO = new ContractReportDAO();
    private final FiredEmployeeReportDAO firedEmployeeReportDAO = new FiredEmployeeReportDAO();
    private FiredEmployeeDAO firedEmployeeDAO = new FiredEmployeeDAO();
    private final OrderReportDAO orderReportDAO = new OrderReportDAO();
    private final ContractDAO contractDAO = new ContractDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Action received: " + action);

        if (action == null) {
            System.out.println("No action provided, redirecting to WarehouseManagerHomePage.jsp");
            request.getRequestDispatcher("jsp/WarehouseManager/WarehouseManagerHomePage.jsp").forward(request, response);
            return;
        }

        try {
            switch (action) {
                case "viewContractReports":
                    viewContractReports(request, response);
                    break;
                case "viewFiredEmployeeReports":
                    viewFiredEmployeeReports(request, response);
                    break;
                case "viewOrderReports":
                    viewOrderReports(request, response);
                    break;
                case "viewContractReportDetails":
                    viewContractReportDetails(request, response);
                    break;
                case "viewFiredEmployeeReportDetails":
                    viewFiredEmployeeReportDetails(request, response);
                    break;
                case "viewOrderReportDetails":
                    viewOrderReportDetails(request, response);
                    break;
                case "takeNote":
                    takeNote(request, response);
                    break;
                default:
                    System.out.println("Invalid action: " + action);
                    request.setAttribute("errorMessage", "Invalid action specified.");
                    request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    private void viewContractReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer warehouseReportId = parseInteger(request.getParameter("warehouseReportId"));
            Integer totalContractsMin = parseInteger(request.getParameter("totalContractsMin"));
            Integer totalContractsMax = parseInteger(request.getParameter("totalContractsMax"));
            Integer totalValueMin = parseInteger(request.getParameter("totalValueMin"));
            Integer totalValueMax = parseInteger(request.getParameter("totalValueMax"));
            Integer month = parseInteger(request.getParameter("month"));
            Integer year = parseInteger(request.getParameter("year"));

            String sortBy = request.getParameter("sortBy");

            System.out.println("Fetching contract reports with applied filters and sorting");

            Vector<ContractReport> contractReports = contractReportDAO.getContractReportsByFilter(
                    warehouseReportId, totalContractsMin, totalContractsMax,
                    totalValueMin, totalValueMax, month, year, sortBy
            );

            request.setAttribute("contractReports", contractReports);
            request.getRequestDispatcher("jsp/WarehouseManager/ContractReports.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving contract reports.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    private void viewFiredEmployeeReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer warehouseReportId = parseInteger(request.getParameter("warehouseReportId"));
            Integer totalFiredEmployeesMin = parseInteger(request.getParameter("totalFiredEmployeesMin"));
            Integer totalFiredEmployeesMax = parseInteger(request.getParameter("totalFiredEmployeesMax"));
            Integer month = parseInteger(request.getParameter("month"));
            Integer year = parseInteger(request.getParameter("year"));

            String sortBy = request.getParameter("sortBy"); // Lấy tham số sắp xếp

            System.out.println("Fetching fired employee reports with applied filters and sorting");

            Vector<FiredEmployeeReport> firedEmployeeReports = firedEmployeeReportDAO.getFiredEmployeeReportsByFilter(
                    warehouseReportId, totalFiredEmployeesMin, totalFiredEmployeesMax, month, year, sortBy
            );

            request.setAttribute("firedEmployeeReports", firedEmployeeReports);
            request.getRequestDispatcher("jsp/WarehouseManager/FiredEmployeeReports.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving fired employee reports.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    private void viewOrderReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer warehouseReportId = parseInteger(request.getParameter("warehouseReportId"));
            Integer totalSuccessOrdersMin = parseInteger(request.getParameter("totalSuccessOrdersMin"));
            Integer totalSuccessOrdersMax = parseInteger(request.getParameter("totalSuccessOrdersMax"));
            Integer totalValueSuccessOrdersMin = parseInteger(request.getParameter("totalValueSuccessOrdersMin"));
            Integer totalValueSuccessOrdersMax = parseInteger(request.getParameter("totalValueSuccessOrdersMax"));
            Integer totalOverdueDebtOrdersMin = parseInteger(request.getParameter("totalOverdueDebtOrdersMin"));
            Integer totalOverdueDebtOrdersMax = parseInteger(request.getParameter("totalOverdueDebtOrdersMax"));
            Integer totalValueOverdueDebtOrdersMin = parseInteger(request.getParameter("totalValueOverdueDebtOrdersMin"));
            Integer totalValueOverdueDebtOrdersMax = parseInteger(request.getParameter("totalValueOverdueDebtOrdersMax"));
            Integer totalIndebtOrdersMin = parseInteger(request.getParameter("totalIndebtOrdersMin"));
            Integer totalIndebtOrdersMax = parseInteger(request.getParameter("totalIndebtOrdersMax"));
            Integer totalValueIndebtOrdersMin = parseInteger(request.getParameter("totalValueIndebtOrdersMin"));
            Integer totalValueIndebtOrdersMax = parseInteger(request.getParameter("totalValueIndebtOrdersMax"));
            Integer month = parseInteger(request.getParameter("month"));
            Integer year = parseInteger(request.getParameter("year"));

            String sortBy = request.getParameter("sortBy");

            Vector<OrderReport> orderReports = orderReportDAO.getOrderReportsByFilter(
                    warehouseReportId, totalSuccessOrdersMin, totalSuccessOrdersMax, totalValueSuccessOrdersMin,
                    totalValueSuccessOrdersMax, totalOverdueDebtOrdersMin, totalOverdueDebtOrdersMax,
                    totalValueOverdueDebtOrdersMin, totalValueOverdueDebtOrdersMax, totalIndebtOrdersMin,
                    totalIndebtOrdersMax, totalValueIndebtOrdersMin, totalValueIndebtOrdersMax, month, year, sortBy
            );

            request.setAttribute("orderReports", orderReports);
            request.getRequestDispatcher("jsp/WarehouseManager/OrderReports.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving order reports.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    // New method for viewing Contract Report Details with filters
    private void viewContractReportDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int warehouseReportId = Integer.parseInt(request.getParameter("warehouseReportId"));

        // Lấy thông tin báo cáo hợp đồng
        ContractReport report = contractReportDAO.getContractReportById(warehouseReportId);

        // Lấy các tham số lọc từ request
        String statusParam = request.getParameter("status");
        String supplierNameParam = request.getParameter("supplierName");

        Integer status = null;
        if (statusParam != null && !statusParam.isEmpty()) {
            status = Integer.parseInt(statusParam);
        }

        // Lấy danh sách các hợp đồng chi tiết dựa trên warehouseReportId và các tham số lọc
        List<Contract> contracts = contractDAO.searchContracts(null, null, null, null, status, null, supplierNameParam, null);

        // Đặt các thuộc tính để truyền sang JSP
        request.setAttribute("contractReport", report);
        request.setAttribute("contracts", contracts);

        // Truyền các giá trị lọc vào request để hiển thị lại trên form lọc
        request.setAttribute("statusParam", statusParam);
        request.setAttribute("supplierNameParam", supplierNameParam);

        // Chuyển tiếp đến trang hiển thị chi tiết báo cáo hợp đồng
        request.getRequestDispatcher("jsp/WarehouseManager/ContractReportDetails.jsp").forward(request, response);
    }

    // Method to view fired employee report details
    private void viewFiredEmployeeReportDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int warehouseReportId = Integer.parseInt(request.getParameter("warehouseReportId"));

        // Retrieve the fired employee report by ID
        FiredEmployeeReport report = firedEmployeeReportDAO.getFiredEmployeeReportById(warehouseReportId);

        // Retrieve search parameters
        Integer id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                ? Integer.parseInt(request.getParameter("id")) : null;
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String roleTitle = request.getParameter("roleTitle");
        Integer gender = request.getParameter("gender") != null && !request.getParameter("gender").isEmpty()
                ? Integer.parseInt(request.getParameter("gender")) : null;
        String warehouseName = request.getParameter("warehouseName");

        // Call DAO to search fired employees with filters
        List<FiredEmployee> firedEmployees = firedEmployeeDAO.searchFiredEmployees(id, firstName, lastName, email,
                phoneNumber, null, roleTitle, null,
                gender, null, warehouseName,
                warehouseReportId, null, null, null,
                null, null, null, null);

        // Set attributes for JSP
        request.setAttribute("reportDetails", report);
        request.setAttribute("firedEmployees", firedEmployees);

        // Forward to the detailed JSP page
        request.getRequestDispatcher("jsp/WarehouseManager/FiredEmployeeReportDetails.jsp").forward(request, response);
    }

    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
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
        return "Warehouse Manager Dashboard Controller";
    }

    private void viewOrderReportDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int warehouseReportId = Integer.parseInt(request.getParameter("warehouseReportId"));

            // Lấy thông tin báo cáo đơn hàng
            OrderReport report = orderReportDAO.getOrderReportById(warehouseReportId);

            // Lấy danh sách các đơn hàng thành công, nợ quá hạn, và công nợ từ báo cáo
            Vector<Order> successOrders = orderReportDAO.getSuccessOrders();
            Vector<Order> overdueDebtOrders = orderReportDAO.getOverdueDebtOrders();
            Vector<Order> inDebtOrders = orderReportDAO.getInDebtOrders();

            // Đặt các thuộc tính để truyền sang JSP
            request.setAttribute("orderReport", report);
            request.setAttribute("successOrders", successOrders);
            request.setAttribute("overdueDebtOrders", overdueDebtOrders);
            request.setAttribute("inDebtOrders", inDebtOrders);

            // Chuyển tiếp đến trang hiển thị chi tiết báo cáo đơn hàng
            request.getRequestDispatcher("jsp/WarehouseManager/OrderReportDetails.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving order report details.");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }

    private void takeNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/WarehouseManager/HomePage.jsp").forward(request, response);

    }

}
