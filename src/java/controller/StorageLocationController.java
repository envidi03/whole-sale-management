package controller;

import dal.StorageLocationDAO;
import java.io.IOException;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import model.StorageLocation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StorageLocationController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        StorageLocationDAO daoST = new StorageLocationDAO();

        String service = request.getParameter("service");
        if (service == null) {
            service = "locationList";
        }

        if (service.equals("locationList")) {

            Vector<Integer> warehouseList = daoST.getWarehouseList();
            Vector<Integer> shelfList = daoST.getShelfList();
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

            String warehouseParam = request.getParameter("warehouse");
            String shelfParam = request.getParameter("shelf");
            String sortOrder = request.getParameter("sortOrder"); // Nhận tham số sắp xếp
            boolean ascending = "asc".equalsIgnoreCase(sortOrder); // Xác định thứ tự sắp xếp

            int warehouseId = -1;
            int shelf = -1;

            try {
                if (warehouseParam != null && !warehouseParam.isEmpty()) {
                    warehouseId = Integer.parseInt(warehouseParam);
                }
            } catch (NumberFormatException e) {
                warehouseId = -1;
            }

            try {
                if (shelfParam != null && !shelfParam.isEmpty()) {
                    shelf = Integer.parseInt(shelfParam);
                }
            } catch (NumberFormatException e) {
                shelf = -1;
            }

            int totalItems;
            Vector<StorageLocation> vectorST;

            // Kiểm tra nếu cần lọc theo warehouseId hoặc shelf
            if (warehouseId != -1 || shelf != -1) {
                totalItems = daoST.countStorageLocations(warehouseId, shelf);
                vectorST = daoST.getSortedStorageLocations(warehouseId, shelf, (currentPage - 1) * itemsPerPage, itemsPerPage, ascending);
            } else {
                // Nếu không có lọc, lấy tất cả các vị trí lưu trữ đã sắp xếp
                totalItems = daoST.getTotalStorageLocationCount();
                vectorST = daoST.getSortedStorageLocations(-1, -1, (currentPage - 1) * itemsPerPage, itemsPerPage, ascending);
            }

            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            request.setAttribute("warehouseList", warehouseList);
            request.setAttribute("shelfList", shelfList);
            request.setAttribute("listST", vectorST);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("warehouseParam", warehouseParam);
            request.setAttribute("shelfParam", shelfParam);
            request.setAttribute("sortOrder", sortOrder); // Truyền thứ tự sắp xếp vào JSP
            request.getRequestDispatcher("jsp/StorageLocation.jsp").forward(request, response);
        }

        if (service.equals("searchLocation")) {
            String txtSearch = request.getParameter("search");

            if (txtSearch == null || txtSearch.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/StorageLocationController?service=locationList");
            } else {
                try {
                    int searchId = Integer.parseInt(txtSearch);
                    Vector<StorageLocation> vectorSt = daoST.getStorageLocationByConsignmentID(searchId);

                    if (vectorSt.isEmpty()) {
                        request.getSession().setAttribute("message", "Không tìm thấy ID phù hợp.");
                        response.sendRedirect(request.getContextPath() + "/StorageLocationController?service=locationList");
                    } else {
                        request.setAttribute("listST", vectorSt);
                        request.getRequestDispatcher("jsp/StorageLocation.jsp").forward(request, response);
                    }
                } catch (NumberFormatException e) {
                    request.getSession().setAttribute("message", "ID phải là một số hợp lệ.");
                    response.sendRedirect(request.getContextPath() + "/StorageLocationController?service=locationList");
                }
            }
        }

        if (service.equals("exportLocation")) {
            Vector<StorageLocation> listLocation = daoST.getAllStorageLocation();
            boolean isExport = exportLocation(listLocation);

            HttpSession session = request.getSession();
            if (isExport) {
                session.setAttribute("message", "Xuất file thành công!");
            } else {
                session.setAttribute("message", "Xuất file thất bại!");
            }

            // Chuyển hướng bằng sendRedirect
            response.sendRedirect(request.getContextPath() + "/StorageLocationController?service=locationList");
            return;
        }
    }

    public boolean exportLocation(Vector<StorageLocation> listLocation) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn Kho Lữu Trữ");
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
        Sheet sheet = workbook.createSheet("Location data");
        String[] headerTitle = {"Warehouse ID", "Shelf", "Column", "Row", "Consignment ID"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerTitle.length; i++) {
            headerRow.createCell(i).setCellValue(headerTitle[i]);
        }
        int rowNum = 1;
        for (StorageLocation storage : listLocation) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(storage.getWarehouse_id());
            row.createCell(1).setCellValue(storage.getShelf());
            row.createCell(2).setCellValue(storage.getCollum());
            row.createCell(3).setCellValue(storage.getRow());
            row.createCell(4).setCellValue(storage.getConsignment_id());
        }

        String baseFileName = "locationData";
        String fileExtention = ".xlsx";
        String fileName = baseFileName + fileExtention;
        int fileCount = 1;

        while (new File(directionToSave, fileName).exists()) {
            fileName = baseFileName + "(" + fileCount + ")" + fileExtention;
            fileCount++;
        }

        File excelFile = new File(directionToSave, fileName);
        try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
            workbook.write(fileOut);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
        return "Short description";
    }
}
