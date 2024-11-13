/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConsignmentDAO;
import dal.ContractDAO;
import dal.SupplierDAO;
import dal.WarehouseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import model.Consignment;
import model.Contract;
import model.StorageLocation;
import model.Supplier;
import model.Warehouse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class ImportProduct extends HttpServlet {

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
        ContractDAO contractDao = new ContractDAO();
        WarehouseDAO warehouseDao = new WarehouseDAO();

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
            Vector<Contract> vector;

            if (statusFilter != null && !statusFilter.isEmpty()) {
                try {
                    int status = Integer.parseInt(statusFilter);
                    totalItems = contractDao.getTotalContractCountByStatus(status);
                    vector = contractDao.getContractsByStatusWithPagination(status, (currentPage - 1) * itemsPerPage, itemsPerPage);
                } catch (NumberFormatException e) {
                    totalItems = contractDao.getTotalContractCount();
                    vector = contractDao.getAllContractsWithPagination((currentPage - 1) * itemsPerPage, itemsPerPage);
                }
            } else {
                totalItems = contractDao.getTotalContractCount();
                vector = contractDao.getAllContractsWithPagination((currentPage - 1) * itemsPerPage, itemsPerPage);
            }

            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            request.setAttribute("listC", vector);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("statusFilter", statusFilter);
            request.getRequestDispatcher("jsp/ImportProduct.jsp").forward(request, response);
        }

        if (service.equals("searchContract")) {
            String searchQuery = request.getParameter("searchQuery");
            Vector<Contract> contracts;

            if (searchQuery == null || searchQuery.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ImportProduct?service=orderList");
                return;
            }

            try {
                int contractID = Integer.parseInt(searchQuery);
                contracts = contractDao.searchContractByID(contractID);

                if (contracts.isEmpty()) {
                    request.setAttribute("searchMess", "Không tìm thấy hợp đồng với ID: " + contractID);
                } else {
                    request.setAttribute("listC", contracts);
                    System.out.println(contracts);
                }
            } catch (NumberFormatException e) {
                // Nếu không phải là số, tìm kiếm theo tên nhà cung cấp
                String searchLower = searchQuery.toLowerCase();
                contracts = contractDao.searchContractsBySupplierName(searchLower);
                if (contracts.isEmpty()) {
                    request.setAttribute("searchMess", "Không tìm thấy hợp đồng với tên nhà cung cấp: " + searchQuery);
                } else {
                    request.setAttribute("listC", contracts);
                }
            }

            // Chuyển tiếp sang trang JSP với kết quả tìm kiếm hoặc thông báo lỗi
            request.getRequestDispatcher("jsp/ImportProduct.jsp").forward(request, response);
        }

        if (service.equals("contractDetail")) {
            Vector<Warehouse> vectorW = warehouseDao.getAllWarehouses();
            String contractIdParam = request.getParameter("contractId");

            if (contractIdParam == null || contractIdParam.isEmpty()) {
                // Chuyển hướng đến trang lỗi hoặc hiển thị thông báo lỗi
                request.setAttribute("errorMessage", "ID hợp đồng bị thiếu hoặc không hợp lệ.");
                request.getRequestDispatcher("jsp/errorPage.jsp").forward(request, response);
                return;
            }

            int contractId = Integer.parseInt(contractIdParam);

            Vector<Consignment> consignments = conDao.getConsignmentsByContractId(contractId);
            System.out.println("List: " + consignments);

            for (Consignment consignment : consignments) {
                System.out.println("Supplier Name: " + consignment.getSupplierName());
                System.out.println("Supplier Phone: " + consignment.getSupplierPhone());
                System.out.println("Supplier Address: " + consignment.getSupplierAddress());
            }
            boolean hasNonProcessingStatus = consignments.stream().anyMatch(consignment -> consignment.getStatus() != 0);

            if (hasNonProcessingStatus) {
                Vector<Consignment> vector = conDao.getAllConsignmentWithSupplierName();
                request.setAttribute("listC", vector);
                request.getRequestDispatcher("ImportProduct?service=orderList").forward(request, response);
                return;
            }

            request.setAttribute("listW", vectorW);
            request.setAttribute("listC", consignments);
            request.getRequestDispatcher("jsp/ImportProductDetail.jsp").forward(request, response);
        }

        // Xử lý khi nhấn nút Hủy đơn
        if (service.equals("cancelOrder")) {
            String contractIdStr = request.getParameter("contractId");
            try {
                int contractId = Integer.parseInt(contractIdStr);
                // Cập nhật trạng thái consignment và contract thành Hủy
                conDao.updateConsignmentStatus(contractId, -2);  // -2: Hủy
                contractDao.updateContractStatus(contractId, 4);  // 4: Hủy
                response.sendRedirect(request.getContextPath() + "/ImportProduct?service=orderList");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().write("Có lỗi xảy ra khi hủy đơn hàng. Vui lòng kiểm tra lại thông tin.");
            }
        }

        // Quy trình nhập kho
        if (service.equals("confirmWarehouse")) {
            String contractIdStr = request.getParameter("contractId");
            String warehouseIdStr = request.getParameter("warehouseId");
            try {
                int contractId = Integer.parseInt(contractIdStr);
                int warehouseId = Integer.parseInt(warehouseIdStr);

                // Lấy danh sách tất cả các Consignment trong Contract
                List<Consignment> consignments = conDao.getConsignmentsByContractId(contractId);

                boolean allConsignmentsStored = true; // Để theo dõi nếu tất cả Consignment đã nhập kho thành công

                for (Consignment consignment : consignments) {
                    // Tìm vị trí trống cho mỗi consignment
                    StorageLocation emptyLocation = warehouseDao.findEmptyLocation(warehouseId);

                    if (emptyLocation != null) {
                        // Log thông tin vị trí tìm thấy
                        System.out.println("Tìm thấy vị trí trống: Shelf " + emptyLocation.getShelf()
                                + ", Column " + emptyLocation.getCollum()
                                + ", Row " + emptyLocation.getRow());

                        // Cập nhật consignment ID vào vị trí trống
                        warehouseDao.updateStorageLocation(warehouseId, emptyLocation.getShelf(),
                                emptyLocation.getCollum(), emptyLocation.getRow(), consignment.getId());
                        conDao.updateConsignmentStatus(consignment.getId(), -1);  // -1: Đã nhập kho

                        // Log thông tin vị trí đã cập nhật
                        System.out.println("Đã cập nhật vị trí cho consignment " + consignment.getId()
                                + ": Shelf " + emptyLocation.getShelf()
                                + ", Column " + emptyLocation.getCollum()
                                + ", Row " + emptyLocation.getRow());
                    } else {
                        allConsignmentsStored = false;
                        System.out.println("Không tìm thấy vị trí trống trong kho cho consignment " + consignment.getId());
                        request.setAttribute("errorMessage", "Không có đủ vị trí trống trong kho cho tất cả các consignment.");
                        break; // Ngừng lặp khi không đủ vị trí
                    }
                }

                // Cập nhật trạng thái của Contract nếu tất cả Consignment đã được lưu trữ
                if (allConsignmentsStored) {
                    contractDao.updateContractStatus(contractId, 3); // 3: Đã nhập kho
                    response.sendRedirect("ImportProduct");
                } else {
                    request.getRequestDispatcher("jsp/ImportProductDetail.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().write("Có lỗi xảy ra khi nhập đơn hàng. Vui lòng kiểm tra lại thông tin.");
            }
        }

        if (service.equals("exportContract")) {
            Vector<Contract> listContr = contractDao.getAllContract();
            boolean isExport = exportToExcel(listContr);

            HttpSession session = request.getSession();
            if (isExport) {
                session.setAttribute("message", "Xuất file thành công!");
            } else {
                session.setAttribute("message", "Xuất file thất bại!");
            }

            response.sendRedirect(request.getContextPath() + "/ImportProduct?service=orderList");
            return;
        }

    }

    // Phương thức xuất dữ liệu ra file Excel
    public boolean exportToExcel(Vector<Contract> listContr) {
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
        Sheet sheet = workbook.createSheet("Contract Data");
        String[] headerTitle = {"ID", "Delivery Date", "Warehouse ID", "Status", "Supplier Name"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTitle.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitle[i]);
        }

        int rowNum = 1;
        for (Contract contract : listContr) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contract.getId());
            row.createCell(1).setCellValue(contract.getActualDeliveryDate());
            row.createCell(2).setCellValue(contract.getWarehouseId());
            row.createCell(3).setCellValue(contract.getStatus());
            row.createCell(4).setCellValue(contract.getSupplierName());

        }

        // Thay đổi tên file nếu đã tồn tại
        String baseFileName = "contractData";
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
