package controller;

import dal.BusinessReportDAO;
import model.BusinessReport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BusinessOwnerDashboardController extends HttpServlet {

    private final BusinessReportDAO reportDAO = new BusinessReportDAO();

    private Integer parseInteger(String value) {
        try {
            return (value != null && !value.trim().isEmpty()) ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Action received: " + action);

        if (action == null) {
            System.out.println("No action provided, redirecting to BusinessOwnerHomePage.jsp");
            loadStatisticsAndReports(request, response);
        } else {
            try {
                switch (action) {
                    case "viewReportDetails":
                        viewReportDetails(request, response);
                        break;
                    case "filterReports":
                        filterReports(request, response);
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
    }

    private void loadStatisticsAndReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy số liệu thống kê
        Map<String, Integer> statistics = reportDAO.getStatistics();
        request.setAttribute("statistics", statistics);

        // Lấy tất cả báo cáo
        List<BusinessReport> reports = reportDAO.getAllBusinessReports();
        request.setAttribute("orderReports", reports);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("jsp/BusinessOwner/BusinessOwnerHomePage.jsp").forward(request, response);
    }

    private void viewReportDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reportId = Integer.parseInt(request.getParameter("reportId"));
        BusinessReport report = reportDAO.getBusinessReportById(reportId);
        request.setAttribute("report", report);
        request.getRequestDispatcher("jsp/BusinessOwner/Details.jsp").forward(request, response);
    }

    private void filterReports(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer month = parseInteger(request.getParameter("month"));
        Integer year = parseInteger(request.getParameter("year"));
        Integer minSuccessOrders = parseInteger(request.getParameter("totalSuccessOrdersMin"));
        Integer maxSuccessOrders = parseInteger(request.getParameter("totalSuccessOrdersMax"));
        Integer minSuccessOrderValue = parseInteger(request.getParameter("totalValueSuccessOrdersMin"));
        Integer maxSuccessOrderValue = parseInteger(request.getParameter("totalValueSuccessOrdersMax"));
        Integer minOverdueDebtOrders = parseInteger(request.getParameter("totalOverdueDebtOrdersMin"));
        Integer maxOverdueDebtOrders = parseInteger(request.getParameter("totalOverdueDebtOrdersMax"));
        Integer minOverdueDebtValue = parseInteger(request.getParameter("totalValueOverdueDebtOrdersMin"));
        Integer maxOverdueDebtValue = parseInteger(request.getParameter("totalValueOverdueDebtOrdersMax"));
        Integer minIndebtOrders = parseInteger(request.getParameter("totalIndebtOrdersMin"));
        Integer maxIndebtOrders = parseInteger(request.getParameter("totalIndebtOrdersMax"));
        Integer minIndebtValue = parseInteger(request.getParameter("totalValueIndebtOrdersMin"));
        Integer maxIndebtValue = parseInteger(request.getParameter("totalValueIndebtOrdersMax"));
        Integer minContracts = parseInteger(request.getParameter("minContracts"));
        Integer maxContracts = parseInteger(request.getParameter("maxContracts"));
        Integer minContractValue = parseInteger(request.getParameter("minContractValue"));
        Integer maxContractValue = parseInteger(request.getParameter("maxContractValue"));
        Integer minFiredEmployees = parseInteger(request.getParameter("minFiredEmployees"));
        Integer maxFiredEmployees = parseInteger(request.getParameter("maxFiredEmployees"));

        System.out.println("Fetching filtered business reports");

        // Lấy báo cáo đã lọc
        List<BusinessReport> reports = reportDAO.searchBusinessReports(
                month, year, minSuccessOrders, maxSuccessOrders, minSuccessOrderValue, maxSuccessOrderValue,
                minOverdueDebtOrders, maxOverdueDebtOrders, minOverdueDebtValue, maxOverdueDebtValue,
                minIndebtOrders, maxIndebtOrders, minIndebtValue, maxIndebtValue,
                minContracts, maxContracts, minContractValue, maxContractValue, minFiredEmployees, maxFiredEmployees
        );

        // Lấy số liệu thống kê và đặt lại vào request
        Map<String, Integer> statistics = reportDAO.getStatistics();
        request.setAttribute("statistics", statistics);

        // Đặt kết quả lọc vào request
        request.setAttribute("orderReports", reports);
        request.getRequestDispatcher("jsp/BusinessOwner/BusinessOwnerHomePage.jsp").forward(request, response);
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
        return "BusinessOwnerDashboardController forwards to BusinessOwnerHomePage.jsp with business reports or to ReportDetails.jsp with report details";
    }
}
