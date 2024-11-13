/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ConsignmentDAO;
import dal.ContractDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import dal.SupplierDAO;
import dal.UnitDAO;
import dal.WarehouseDAO;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.swing.JFileChooser;
import model.CartItem;
import model.Consignment;
import model.Contract;
import model.Product;
import model.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.DataConvert;

/**
 *
 * @author Admin
 */
@MultipartConfig
public class OrderImportProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws ParseException if a ParseException occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String service = request.getParameter("service");

        // DAOs (Data Access Objects) for handling database queries
        SupplierDAO supplierDao = new SupplierDAO();
        ConsignmentDAO consDao = new ConsignmentDAO();
        ProductDAO productDao = new ProductDAO();
        UnitDAO unitDao = new UnitDAO();
        CategoryDAO categoryDao = new CategoryDAO();
        DataConvert dataConvert = new DataConvert();
        ContractDAO contractDao = new ContractDAO();
        WarehouseDAO wareDao = new WarehouseDAO();

        // Lấy danh sách nhà cung cấp và lô hàng từ CSDL
        Vector<Supplier> suppliers = supplierDao.getAllSupplier();
        Vector<Consignment> consignments = consDao.getAllConsignment1();
        Vector<Product> products = productDao.getAllProduct();

        // Mặc định là hiển thị cả nhà cung cấp và lô hàng
        if (service == null) {
            service = "displayBoth";
        }

        switch (service) {
            case "displayBoth":
                System.out.println("Product List: " + products);
                // Hiển thị danh sách cả nhà cung cấp và lô hàng
                request.setAttribute("products", products);
                request.setAttribute("listS", suppliers);
                request.setAttribute("listC", consignments);
                request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
                break;

            case "buyAgain":
                // Xử lý khi người dùng nhấn "Buy Again"
                handleBuyAgain(request, response, consDao);
                break;

            case "removeFromCart":
                // Xử lý khi người dùng xóa lô hàng khỏi giỏ hàng
                handleRemoveFromCart(request, response);
                break;

            case "addConsignment":
                // Xử lý khi thêm lô hàng vào giỏ
                handleAddConsignment(request, response, productDao, consDao);
                break;

            case "createOrder":
                // Xử lý khi tạo đơn đặt hàng mới
                handleCreateOrder(request, response, contractDao, consDao, categoryDao, wareDao, dataConvert);
                break;

            case "addSupplier":
                // Xử lý khi thêm mới nhà cung cấp
                handleAddSupplier(request, response, supplierDao, suppliers, consignments);
                break;

            case "uploadFile":
                handleFileUpload(request, response, productDao, consDao, contractDao, dataConvert, categoryDao);
                break;
            default:
                // Xử lý yêu cầu không hợp lệ
                request.setAttribute("errorMess", "Invalid service request!");
                request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
                break;
        }

    }

    private void handleBuyAgain(HttpServletRequest request, HttpServletResponse response, ConsignmentDAO consDao) throws IOException {
        String supplierIdStr = request.getParameter("supplierId");
        String deliveryDateStr = request.getParameter("deliveryDate");

        // Lưu lại thông tin nhà cung cấp và ngày nhập vào session
        if (supplierIdStr != null && !supplierIdStr.isEmpty()) {
            request.getSession().setAttribute("selectedSupplierId", supplierIdStr);
        }
        if (deliveryDateStr != null && !deliveryDateStr.isEmpty()) {
            request.getSession().setAttribute("selectedDate", deliveryDateStr);
        }

        String consignmentIdStr = request.getParameter("consignmentId");
        try {
            int consignmentId = Integer.parseInt(consignmentIdStr);
            Vector<Consignment> consignment = consDao.getConsignmentByID(consignmentId);

            if (!consignment.isEmpty()) {
                List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cartItems");
                if (cartItems == null) {
                    cartItems = new ArrayList<>();
                }
                cartItems.add(new CartItem(consignment.get(0)));
                request.getSession().setAttribute("cartItems", cartItems);

                // Gửi phản hồi thành công cho AJAX
                response.getWriter().write("success");
            } else {
                response.getWriter().write("Consignment không tồn tại.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("ID consignment không hợp lệ.");
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String removeConsignmentIdStr = request.getParameter("consignmentId");
        try {
            int removeConsignmentId = Integer.parseInt(removeConsignmentIdStr);

            // Lấy giỏ hàng từ session
            List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cartItems");

            if (cartItems != null) {
                // Duyệt qua giỏ hàng và xóa CartItem có consignmentId tương ứng
                cartItems.removeIf(item -> item.getConsignment().getProductId() == removeConsignmentId);

                // Cập nhật lại giỏ hàng trong session
                request.getSession().setAttribute("cartItems", cartItems);

                // Gửi phản hồi thành công cho AJAX
                response.getWriter().write("success");
            } else {
                response.getWriter().write("Giỏ hàng trống!");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("ID lô hàng không hợp lệ!");
        }
    }

    private void handleAddConsignment(HttpServletRequest request, HttpServletResponse response, ProductDAO productDao, ConsignmentDAO consDao) throws ServletException, IOException {

        String productIdStr = request.getParameter("productId");
        String purchasePriceStr = request.getParameter("import_price");
        String salePriceStr = request.getParameter("selling_price");
        String quantityAvailableStr = request.getParameter("number_of_product");

        String supplierIdStr = request.getParameter("supplierId");
        String deliveryDateStr = request.getParameter("deliveryDate");

        if (supplierIdStr == null || supplierIdStr.isEmpty()) {
            supplierIdStr = (String) request.getSession().getAttribute("selectedSupplierId");
        }

        if (deliveryDateStr == null || deliveryDateStr.isEmpty()) {
            deliveryDateStr = (String) request.getSession().getAttribute("selectedDate");
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            int purchasePrice = Integer.parseInt(purchasePriceStr);
            int salePrice = Integer.parseInt(salePriceStr);
            int quantityAvailable = Integer.parseInt(quantityAvailableStr);

            // Tạo đối tượng Consignment
            Consignment consignment = new Consignment(productId, purchasePrice, salePrice, quantityAvailable);

            // Lấy giỏ hàng từ session hoặc khởi tạo nếu giỏ hàng rỗng
            List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }

            // Thêm Consignment vào CartItem rồi thêm vào giỏ hàng
            cartItems.add(new CartItem(consignment));

            // Lưu lại giỏ hàng trong session
            request.getSession().setAttribute("cartItems", cartItems);

            // Cập nhật dữ liệu hiển thị
            request.setAttribute("selectedSupplierId", request.getParameter("supplierId"));
            request.setAttribute("selectedDate", request.getParameter("deliveryDate"));
            response.sendRedirect("OrderImportProduct?service=displayBoth");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMess", "Dữ liệu không hợp lệ. Vui lòng kiểm tra các trường nhập.");
            request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
        }
    }

    private void handleCreateOrder(HttpServletRequest request, HttpServletResponse response,
            ContractDAO contractDao, ConsignmentDAO consDao, CategoryDAO categoryDao, WarehouseDAO wareDao, DataConvert dataConvert)
            throws ServletException, IOException {

        String supplierIdStr = request.getParameter("supplierId");
        String deliveryDateStr = request.getParameter("deliveryDate");

        try {
            if (supplierIdStr == null || supplierIdStr.isEmpty()) {
                request.setAttribute("errorMessage", "Mã nhà cung cấp không được để trống!");
                response.sendRedirect("OrderImportProduct?service=displayBoth");
                return;
            }

            if (deliveryDateStr == null || deliveryDateStr.isEmpty()) {
                request.setAttribute("errorMessage", "Ngày giao hàng không được để trống!");
                response.sendRedirect("OrderImportProduct?service=displayBoth");
                return;
            }
            // Chuyển đổi String sang int và Date
            int supplierId = Integer.parseInt(supplierIdStr);
            Date deliveryDate = dataConvert.StringToUtilDate(deliveryDateStr);
            // Lấy giỏ hàng từ session
            List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cartItems");

            if (cartItems != null && !cartItems.isEmpty()) {
                int warehouseId = wareDao.getWarehouseId();
                if (warehouseId == 1) {
                    response.sendRedirect("OrderImportProduct?service=displayBoth");
                    return;
                }

                // Tạo Contract riêng cho mỗi consignment
                Contract contract = new Contract();
                contract.setSupplierId(supplierId);
                contract.setContractDeliveryDate(deliveryDate);
                contract.setStatus(1);  // Trạng thái mặc định là waiting_for_approval
                contract.setWarehouseId(warehouseId);

                int contractId = contractDao.addContractAndGetId(contract);  // Lưu contract và lấy contract ID
                for (CartItem item : cartItems) {

                    // Lấy product_category_id từ product_id
                    int productId = item.getConsignment().getProductId();
                    int productCategoryId = categoryDao.getProductCategoryIdByProductId(productId);

                    // Tạo đối tượng Consignment và liên kết với contract
                    Consignment consignment = new Consignment();
                    consignment.setProductId(productId);
                    consignment.setContractId(contractId);  // Liên kết với contract
                    consignment.setWareHouseId(warehouseId);
                    consignment.setImportPrice(item.getConsignment().getImportPrice());
                    consignment.setSellingPrice(item.getConsignment().getSellingPrice());
                    consignment.setNumberOfProduct(item.getConsignment().getNumberOfProduct());
                    consignment.setStatus(0);  // Mặc định là đang xử lý
                    consignment.setDeliveryDate(deliveryDate);
                    consignment.setProductCategoryId(productCategoryId); // Liên kết product_category_id

                    System.out.println("Product ID: " + productId);
                    System.out.println("Import Price: " + item.getConsignment().getImportPrice());
                    System.out.println("Selling Price: " + item.getConsignment().getSellingPrice());
                    System.out.println("Number of Product: " + item.getConsignment().getNumberOfProduct());
                    System.out.println("Product Category ID: " + productCategoryId);
                    // Lưu consignment vào database
                    consDao.addConsignment(consignment);
                }

                // Xóa giỏ hàng sau khi đặt hàng thành công
                request.getSession().removeAttribute("cartItems");
                request.getSession().removeAttribute("selectedSupplierId");
                request.getSession().removeAttribute("selectedDate");

                // Thông báo thành công và chuyển hướng
                response.sendRedirect(request.getContextPath() + "/OrderImportProductList");
            } else {
                request.setAttribute("errorMessage", "Giỏ hàng trống!");
                response.sendRedirect("OrderImportProduct?service=displayBoth");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình đặt hàng!");
            request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
        }
    }

    private void handleAddSupplier(HttpServletRequest request, HttpServletResponse response,
            SupplierDAO supplierDao, Vector<Supplier> suppliers, Vector<Consignment> consignments)
            throws ServletException, IOException {

        // Lấy thông tin từ form
        String supplierName = request.getParameter("supplierName");
        String supplierPhone = request.getParameter("phone");
        String supplierAddress = request.getParameter("address");
//        String supplierStatus = request.getParameter("status");

        // Kiểm tra dữ liệu đầu vào
        if (supplierName == null || supplierName.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tên nhà cung cấp là bắt buộc.");
            request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
            return;
        }

        if (supplierPhone == null || supplierPhone.length() != 10 || !supplierPhone.startsWith("0")) {
            response.sendRedirect("OrderImportProduct?service=displayBoth");
            return;
        }

//        if (supplierStatus == null || supplierStatus.trim().isEmpty()) {
//            request.getSession().setAttribute("errorMessage", "Trạng thái nhà cung cấp là bắt buộc.");
//            response.sendRedirect("OrderImportProduct?service=displayBoth");
//            return;
//        }
//        try {
//            int supStatus = Integer.parseInt(supplierStatus);
//        } catch (NumberFormatException e) {
//            request.getSession().setAttribute("errorMessage", "Trạng thái nhà cung cấp phải là một số hợp lệ.");
//            response.sendRedirect("OrderImportProduct?service=displayBoth");
//            return;
//        }
        try {
//            int supStatus = Integer.parseInt(supplierStatus);
            // Tạo đối tượng Supplier
            Supplier supplier = new Supplier();
            supplier.setSupplierName(supplierName);
            supplier.setPhone(supplierPhone);
            supplier.setAddress(supplierAddress);
//            supplier.setStatus(supStatus);

            // Thêm nhà cung cấp vào cơ sở dữ liệu
            supplierDao.addSupplier(supplier);

            // Lấy lại danh sách nhà cung cấp và lô hàng sau khi thêm nhà cung cấp
            suppliers = supplierDao.getAllSupplier();
            consignments = new ConsignmentDAO().getAllConsignment1(); // Just to make sure we refresh it

            // Đặt lại các tham số và render lại trang
            request.setAttribute("listS", suppliers);
            request.setAttribute("listC", consignments);
            request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình thêm nhà cung cấp!");
            request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
        }
    }

    private void handleFileUpload(HttpServletRequest request, HttpServletResponse response,
            ProductDAO productDao, ConsignmentDAO consDao, ContractDAO contractDao,
            DataConvert dataConvert, CategoryDAO categoryDao) throws IOException, ServletException {

        Part filePart = request.getPart("orderFile");
        List<CartItem> cartItems = new ArrayList<>();

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();

            try (InputStream fileContent = filePart.getInputStream()) {
//                if (fileName.endsWith(".csv")) {
//                    // Xử lý file CSV
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, StandardCharsets.UTF_8));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        if (line.trim().isEmpty()) {
//                            continue; // Bỏ qua dòng trống
//                        }
//                        String[] values = line.split(",");
//                        CartItem item = createCartItemFromData(values, productDao);
//                        System.out.println("CartItem created from CSV: " + item);
//                        if (item != null) {
//                            cartItems.add(item);
//                        } else {
//                            System.out.println("Invalid row in CSV: " + line); // In thông báo lỗi cho dòng không hợp lệ
//                        }
//                    }
//                } else
                if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                    // Xử lý file Excel
                    Workbook workbook = WorkbookFactory.create(fileContent);
                    Sheet sheet = workbook.getSheetAt(0); // Giả sử dữ liệu nằm ở trang đầu tiên
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }

                        List<String> values = new ArrayList<>();
                        for (Cell cell : row) {
                            switch (cell.getCellType()) {
                                case NUMERIC:
                                    // Kiểm tra nếu giá trị là số nguyên hoặc số thực và chuyển thành chuỗi
                                    values.add(String.valueOf((int) cell.getNumericCellValue()));
                                    break;
                                case STRING:
                                    values.add(cell.getStringCellValue().trim());
                                    break;
                                default:
                                    values.add(""); // Nếu loại ô không phù hợp, thêm giá trị trống
                            }
                        }

                        CartItem item = createCartItemFromData(values.toArray(new String[0]));
                        if (item != null) {
                            cartItems.add(item);
                        } else {
                            System.out.println("Invalid row in Excel at row number: " + row.getRowNum());
                        }
                    }
                    workbook.close();
                }

                System.out.println("Total CartItems added: " + cartItems.size());
                request.getSession().setAttribute("cartItems", cartItems);
                response.sendRedirect("OrderImportProduct");
            } catch (Exception e) {
                e.printStackTrace(); // In chi tiết lỗi ra console để tiện theo dõi
                request.setAttribute("errorMessage", "Xử lý file tải lên thất bại: " + e.getMessage());
                request.getRequestDispatcher("jsp/OrderImportProductCreate.jsp").forward(request, response);
            }
        }
    }

    private CartItem createCartItemFromData(String[] values) {
        try {
            if (values.length < 4) {
                System.out.println("Not enough values in row: " + Arrays.toString(values));
                return null;
            }

            int productId = Integer.parseInt(values[0].trim());
            int purchasePrice = Integer.parseInt(values[1].trim());
            int salePrice = Integer.parseInt(values[2].trim());
            int quantityAvailable = Integer.parseInt(values[3].trim());

            Consignment consignment = new Consignment(productId, purchasePrice, salePrice, quantityAvailable);
            System.out.println("Created Consignment: " + consignment);
            return new CartItem(consignment);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing row: " + Arrays.toString(values));
            e.printStackTrace(); // In ra thông báo lỗi chi tiết
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
