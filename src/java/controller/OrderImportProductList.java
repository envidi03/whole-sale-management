/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConsignmentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import javax.swing.JFileChooser;
import model.Consignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class OrderImportProductList extends HttpServlet {

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
        String service = request.getParameter("service");
        ConsignmentDAO conDao = new ConsignmentDAO();
        if (service == null) {
            service = "orderList";
        }

        if (service.equals("orderList")) {
            int itemsPerPage = 10;
            int currentPage = 1;
            String pageParam = request.getParameter("page");

            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1;
                }
            }

            String statusFilter = request.getParameter("statusFilter");
            int totalItems;
            Vector<Consignment> vector;

            if (statusFilter != null && !statusFilter.isEmpty()) {
                try {
                    int status = Integer.parseInt(statusFilter);
                    totalItems = conDao.getTotalConsignmentCountByStatus(status);
                    vector = conDao.getConsignmentsByStatusWithPagination(status, (currentPage - 1) * itemsPerPage, itemsPerPage);
                } catch (NumberFormatException e) {
                    totalItems = conDao.getTotalConsignmentCount();
                    vector = conDao.getAllConsignmentWithPagination((currentPage - 1) * itemsPerPage, itemsPerPage);
                }
            } else {
                totalItems = conDao.getTotalConsignmentCount();
                vector = conDao.getAllConsignmentWithPagination((currentPage - 1) * itemsPerPage, itemsPerPage);
            }

            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            request.setAttribute("listC", vector);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("statusFilter", statusFilter);
            request.getRequestDispatcher("jsp/OrderImportProduct.jsp").forward(request, response);
        }

        if (service.equals("consignmentDetail")) {
            String consignmentIdParam = request.getParameter("id");
            int consignmentId = Integer.parseInt(consignmentIdParam);

            // Lấy thông tin lô hàng từ CSDL
            Consignment consignment = conDao.getConsignmentById1(consignmentId);
            System.out.println(consignment);

            request.setAttribute("con", consignment);
            request.getRequestDispatcher("jsp/ConsignmentDetail.jsp").forward(request, response);
        }

        if (service.equals("searchConsignment")) {
            String searchQuery = request.getParameter("searchQuery");
            Vector<Consignment> consignments;

            if (searchQuery == null || searchQuery.isEmpty()) {
                // Khi không nhập gì, chuyển hướng về service orderList
                response.sendRedirect(request.getContextPath() + "/OrderImportProductList?service=orderList");
                return; // Dừng xử lý tiếp
            }

            try {
                int searchID = Integer.parseInt(searchQuery);
                consignments = conDao.searchConsignmentByID(searchID);

                if (consignments.isEmpty()) {
                    request.setAttribute("searchMess", "Không tìm thấy lô hàng với ID: " + searchID);
                } else {
                    request.setAttribute("listC", consignments);
                    System.out.println(consignments);
                }
            } catch (NumberFormatException e) {
                String searchQueryLower = searchQuery.toLowerCase();
                // Nếu không phải là số, tìm kiếm theo tên nhà cung cấp
                consignments = conDao.getConsignmentBySupplierName(searchQueryLower);
                if (consignments.isEmpty()) {
                    request.setAttribute("searchMess", "Không tìm thấy lô hàng với tên nhà cung cấp: " + searchQuery);
                } else {
                    request.setAttribute("listC", consignments);
                }
            }

            // Chuyển tiếp sang trang JSP với kết quả tìm kiếm hoặc thông báo lỗi
            request.getRequestDispatcher("jsp/OrderImportProduct.jsp").forward(request, response);
        }
        if (service.equals("exportConsignment")) {
            Vector<Consignment> listCon = conDao.getAllConsignment1();
            boolean isExport = exportToExcel(listCon);

            HttpSession session = request.getSession();
            if (isExport) {
                session.setAttribute("message", "Xuất file thành công!");
            } else {
                session.setAttribute("message", "Xuất file thất bại!");
            }

            // Chuyển hướng bằng sendRedirect
            response.sendRedirect(request.getContextPath() + "/ImportProduct?service=orderList");
            return;
        }
    }

    // Phương thức xuất dữ liệu ra file Excel
    public boolean exportToExcel(Vector<Consignment> listCon) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn kho lưu trữ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelect = fileChooser.showSaveDialog(null);
        if (userSelect != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File directionToSave = fileChooser.getSelectedFile();
        if (directionToSave == null) {
            return false;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Consignment Data");
        String[] headerTitle = {"ID", "Product ID", "Warehouse ID", "Contract ID", "Status", "Delivery Date",
            "Product Category ID", "Import Price", "Selling Price", "Number of Product", "Discount Percent"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTitle.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitle[i]);
        }

        int rowNum = 1;
        for (Consignment consignment : listCon) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(consignment.getId());
            row.createCell(1).setCellValue(consignment.getProductId());
            row.createCell(2).setCellValue(consignment.getWareHouseId());
            row.createCell(3).setCellValue(consignment.getContractId());
            row.createCell(4).setCellValue(consignment.getStatus());
            row.createCell(5).setCellValue(consignment.getDeliveryDate().toString());
            row.createCell(6).setCellValue(consignment.getProductCategoryId());
            row.createCell(7).setCellValue(consignment.getImportPrice());
            row.createCell(8).setCellValue(consignment.getSellingPrice());
            row.createCell(9).setCellValue(consignment.getNumberOfProduct());
            row.createCell(10).setCellValue(consignment.getDeliveryDate());
        }

        // Thay đổi tên file nếu đã tồn tại
        String baseFileName = "consignmentData";
        String fileExtension = ".xlsx";
        String fileName = baseFileName + fileExtension;
        int fileCount = 1;

        while (new File(directionToSave, fileName).exists()) {
            fileName = baseFileName + "(" + fileCount + ")" + fileExtension;
            fileCount++;
        }

        File excelFile = new File(directionToSave, fileName);
        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
            System.out.println("Success export to: " + excelFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
