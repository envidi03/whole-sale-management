/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Consignment;
import model.Product;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.stream.Collectors;
import util.DataConvert;

/**
 *
 * @author LamHP
 */
public class ConsignmentDAO extends DBContext {

    DataConvert dc = new DataConvert();

    // lấy all congignment theo thứ tự hạn sử dụng các sản phẩm hết hạn lên trước
    public List<Consignment> getAllConsignmentASC() {
        List<Consignment> list = new ArrayList<>();
        String sql = "SELECT c.* FROM wholesalemanagement.consignment c "
                + "JOIN wholesalemanagement.product p ON c.product_id = p.id ORDER BY p.expire_date ASC";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int wareHouseId = rs.getInt(3);
                int contractId = rs.getInt(4);
                int status = rs.getInt(5);
                Date deliveryDate = rs.getDate(6);
                int productCategoryId = rs.getInt(7);
                int importPrice = rs.getInt(8);
                int sellingPrice = rs.getInt(9);
                int numberOfProduct = rs.getInt(10);
                float discountPercentage = rs.getFloat(11);
                Consignment con = new Consignment(id, productId, wareHouseId,
                        contractId, status, deliveryDate, productCategoryId,
                        importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // get all
    public List<Consignment> getAllConsignment() {
        List<Consignment> list = new ArrayList<>();
        String sql = "SELECT * FROM wholesalemanagement.consignment";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int wareHouseId = rs.getInt(3);
                int contractId = rs.getInt(4);
                int status = rs.getInt(5);
                Date deliveryDate = rs.getDate(6);
                int productCategoryId = rs.getInt(7);
                int importPrice = rs.getInt(8);
                int sellingPrice = rs.getInt(9);
                int numberOfProduct = rs.getInt(10);
                float discountPercentage = rs.getFloat(11);
                Consignment con = new Consignment(id, productId, wareHouseId,
                        contractId, status, deliveryDate, productCategoryId,
                        importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // get all Consignment where status = 1
    public List<Consignment> getAllWhereStatus1() {
        List<Consignment> list = new ArrayList<>();
        String sql = "select * from wholesalemanagement.consignment where status = 1";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int wareHouseId = rs.getInt(3);
                int contractId = rs.getInt(4);
                int status = rs.getInt(5);
                Date deliveryDate = rs.getDate(6);
                int productCategoryId = rs.getInt(7);
                int importPrice = rs.getInt(8);
                int sellingPrice = rs.getInt(9);
                int numberOfProduct = rs.getInt(10);
                float discountPercentage = rs.getFloat(11);
                Consignment con = new Consignment(id, productId, wareHouseId,
                        contractId, status, deliveryDate, productCategoryId,
                        importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // get all Consignment where status = 2
    public List<Consignment> getAllWhereStatus2() {
        List<Consignment> list = new ArrayList<>();
        String sql = "select * from wholesalemanagement.consignment where status = 2";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int productId = rs.getInt(2);
                int wareHouseId = rs.getInt(3);
                int contractId = rs.getInt(4);
                int status = rs.getInt(5);
                Date deliveryDate = rs.getDate(6);
                int productCategoryId = rs.getInt(7);
                int importPrice = rs.getInt(8);
                int sellingPrice = rs.getInt(9);
                int numberOfProduct = rs.getInt(10);
                float discountPercentage = rs.getFloat(11);
                Consignment con = new Consignment(id, productId, wareHouseId,
                        contractId, status, deliveryDate, productCategoryId,
                        importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Product> getAllProductsASC() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM wholesalemanagement.product ORDER BY expire_date ASC";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String productName = rs.getString(2);
                Date manufactureDate = rs.getDate(3);
                Date expireDate = rs.getDate(4);
                int unitId = rs.getInt(5);
                int retailPrice = rs.getInt(6);
                int categoryId = rs.getInt(7);
                Product pro = new Product(id, productName, manufactureDate, expireDate, unitId, retailPrice, categoryId);
                list.add(pro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Consignment getConsignmentById(int id) {
        String sql = "SELECT * FROM wholesalemanagement.consignment WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Consignment(id, rs.getInt("product_id"),
                        rs.getInt("warehouse_id"),
                        rs.getInt("contract_id"),
                        rs.getInt("status"),
                        rs.getDate("delivery_date"),
                        rs.getInt("product_category_id"),
                        rs.getInt("import_price"),
                        rs.getInt("selling_price"),
                        rs.getInt("number_of_product"),
                        rs.getFloat("discount_percentage"));

            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // search consignment by name of product
    public List<Consignment> search1(String txtSearch, int index, int size) {
        List<Consignment> list = new ArrayList<>();
        String sql = "  with x as\n"
                + "(select ROW_NUMBER() OVER (ORDER BY c.id) as stt, c.*\n"
                + " FROM  wholesalemanagement.consignment c join wholesalemanagement.product p on c.product_id = p.id\n"
                + " where p.name like ? ) select * from x where stt between  ?*5-4 and ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int wareHouseId = rs.getInt("warehouse_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                Date deliveryDate = rs.getDate("delivery_date");
                int productCategoryId = rs.getInt("product_category_id");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                int numberOfProduct = rs.getInt("number_of_product");
                float discountPercentage = rs.getFloat("discount_percentage");
                Consignment con = new Consignment(id, productId, wareHouseId,
                        contractId, status, deliveryDate, productCategoryId,
                        importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Consignment> search(String searchTerm, int index, int pageSize) {
        List<Consignment> consignments = new ArrayList<>();
        String sql = "SELECT \n"
                + "*FROM wholesalemanagement.consignment c join wholesalemanagement.product p on c.product_id = p.id\n"
                + "where p.name like ? LIMIT ? , ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%"); // Tìm kiếm theo tên sản phẩm
            ps.setInt(2, (index - 1) * pageSize); // Tính toán offset
            ps.setInt(3, pageSize); // Giới hạn số lượng trả về

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consignments.add(new Consignment(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("warehouse_id"),
                        rs.getInt("contract_id"),
                        rs.getInt("status"),
                        rs.getDate("delivery_date"),
                        rs.getInt("product_category_id"),
                        rs.getInt("import_price"),
                        rs.getInt("selling_price"),
                        rs.getInt("number_of_product"),
                        rs.getFloat("discount_percentage")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

//            public static void main(String[] args) {
//        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
//        List<Consignment> list = consignmentDAO.search("s", 1, 5);
//        for (Consignment o : list) {
//            System.out.println(o);
//        }
//    }
    public List<Consignment> getAvailableConsignments(List<Integer> cartIds) {
        List<Consignment> consignments = new ArrayList<>();
        String sql = "SELECT * FROM wholesalemanagement.Consignment";
        if (cartIds != null && !cartIds.isEmpty()) {
            String inClause = cartIds.stream().map(id -> "?").collect(Collectors.joining(", "));
            sql += " WHERE id NOT IN (" + inClause + ")";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (cartIds != null && !cartIds.isEmpty()) {
                for (int i = 0; i < cartIds.size(); i++) {
                    ps.setInt(i + 1, cartIds.get(i));
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consignments.add(new Consignment(rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getInt("warehouse_id"),
                        rs.getInt("contract_id"),
                        rs.getInt("status"), rs.getDate("delivery_date"),
                        rs.getInt("product_category_id"),
                        rs.getInt("import_price"), rs.getInt("selling_price"),
                        rs.getInt("number_of_product"),
                        rs.getFloat("discount_percentage")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    // search and pagging
    public int count(String txtSearch) {
        String sql = "SELECT count(*)\n"
                + "FROM wholesalemanagement.consignment c join wholesalemanagement.product p on c.product_id = p.id\n"
                + "where p.name like ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);

            }

        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalConsignment() {
        int total = 0;
        String query = "select count(*) from wholesalemanagement.consignment";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Consignment> getConsignmentByPage(int page, int consignmentPerPage) {
        List<Consignment> list = new ArrayList<>();
        String query = "select * from wholesalemanagement.consignment limit ?, ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * consignmentPerPage); // Bắt đầu từ vị trí sản phẩm
            ps.setInt(2, consignmentPerPage); // Số sản phẩm cần lấy
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int wareHouseId = rs.getInt("warehouse_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                Date deliveryDate = rs.getDate("delivery_date");
                int productCategoryId = rs.getInt("product_category_id");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                int numberOfProduct = rs.getInt("number_of_product");
                float discountPercentage = rs.getFloat("discount_percentage");
                Consignment con = new Consignment(id, productId, wareHouseId, contractId, status, deliveryDate, productCategoryId, importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Dùng
    public int addConsignment(Consignment consignment) {
        int n = 0;
        String sql = "INSERT INTO consignment (product_id, contract_id, warehouse_id, status, delivery_date, "
                + "product_category_id, import_price, selling_price, number_of_product) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, consignment.getProductId());
            pre.setInt(2, consignment.getContractId());
            pre.setInt(3, consignment.getWareHouseId());
            pre.setInt(4, consignment.getStatus());
            pre.setDate(5, dc.UtilDateToSqlDate(consignment.getDeliveryDate()));
            pre.setInt(6, consignment.getProductCategoryId());
            pre.setInt(7, consignment.getImportPrice());
            pre.setInt(8, consignment.getSellingPrice());
            pre.setInt(9, consignment.getNumberOfProduct());

            n = pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    //Dùng
    public Vector<Consignment> getConsignmentByID(int searchID) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT * FROM consignment WHERE id = ?";

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, searchID); // Gán giá trị searchID vào câu truy vấn
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int product_id = rs.getInt(2);
                int warehouse_id = rs.getInt(3);
                int contract_id = rs.getInt(4);
                int status = rs.getInt(5);
                Date delivery_date = rs.getDate(6);
                int product_category_id = rs.getInt(7);
                int import_price = rs.getInt(8);
                int selling_price = rs.getInt(9);
                int number_of_product = rs.getInt(10);
                float discount_percentage = rs.getFloat(11);

                Consignment con = new Consignment(id, product_id, warehouse_id,
                        contract_id, status, delivery_date, product_category_id,
                        import_price, selling_price, number_of_product, discount_percentage);
                consignments.add(con);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignments; // Trả về danh sách consignments
    }

    //Dùng
    public Vector<Consignment> searchConsignmentByID(int searchID) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT c.id, c.delivery_date, c.status, s.name AS supplierName, c.import_price "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE c.id = ?";
        try (PreparedStatement state = connection.prepareStatement(sql)) {
            state.setInt(1, searchID);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                Consignment con = new Consignment();
                con.setId(rs.getInt("id"));
                con.setDeliveryDate(rs.getDate("delivery_date"));
                con.setStatus(rs.getInt("status"));
                con.setSupplierName(rs.getString("supplierName"));
                con.setImportPrice(rs.getInt("import_price"));
                consignments.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    //Dùng
    public Vector<Consignment> getConsignmentBySupplierName(String supplierName) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT c.id, c.delivery_date, c.status, s.name AS supplierName, c.import_price "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE s.name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + supplierName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consignment con = new Consignment();
                con.setId(rs.getInt("id"));
                con.setDeliveryDate(rs.getDate("delivery_date"));
                con.setStatus(rs.getInt("status"));
                con.setSupplierName(rs.getString("supplierName"));
                con.setImportPrice(rs.getInt("import_price"));
                consignments.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    //Dùng
    public Consignment getConsignmentById1(int consignmentId) {
        Consignment consignment = null;
        String sql = "SELECT c.id, c.product_id, c.contract_id, c.import_price, c.selling_price, "
                + "c.delivery_date, c.status, s.name AS supplierName, s.phone, s.address "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE c.id = ?";

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, consignmentId);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                Date deliveryDate = rs.getDate("delivery_date");
                String supplierName = rs.getString("supplierName");
                String supplierPhone = rs.getString("phone");
                String supplierAddress = rs.getString("address");

                consignment = new Consignment(id, productId, contractId, status, deliveryDate,
                        importPrice, sellingPrice, supplierName, supplierPhone, supplierAddress);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignment;
    }

    public Vector<Consignment> getConsignmentsByContractId(int contractId) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT c.id, c.product_id, c.contract_id, c.import_price, c.selling_price, "
                + "c.delivery_date, c.status, s.name AS supplierName, s.phone, s.address "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE c.contract_id = ?";

        try (PreparedStatement state = connection.prepareStatement(sql)) {
            state.setInt(1, contractId);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int status = rs.getInt("status");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                Date deliveryDate = rs.getDate("delivery_date");
                String supplierName = rs.getString("supplierName");
                String supplierPhone = rs.getString("phone");
                String supplierAddress = rs.getString("address");

                Consignment consignment = new Consignment(id, productId, contractId, status, deliveryDate,
                        importPrice, sellingPrice, supplierName, supplierPhone, supplierAddress);
                consignments.add(consignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignments;
    }

    //Dùng
    public Vector<Consignment> getConsignmentsByStatusWithPagination(int status, int offset, int limit) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT c.id, c.product_id, c.contract_id, c.import_price, c.selling_price, "
                + "c.delivery_date, c.status, s.name AS supplierName "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE c.status = ? LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consignment consignment = new Consignment();
                // Populate consignment object from ResultSet
                consignment.setId(rs.getInt("id"));
                consignment.setImportPrice(rs.getInt("import_price"));
                consignment.setDeliveryDate(rs.getDate("delivery_date"));
                consignment.setStatus(rs.getInt("status"));
                consignment.setSupplierName(rs.getString("supplierName"));
                consignments.add(consignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    //Dùng
    public void updateConsignmentStatus(int contractId, int status) {
         String sql = "UPDATE consignment SET status = ? WHERE contract_id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, contractId);

            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Dùng
    // Phương thức lấy supplier_id từ bảng contract
    public int getSupplierIdFromContract(int contractId) {
        int supplierId = 0;
        String sql = "SELECT supplier_id FROM contract WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, contractId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                supplierId = rs.getInt("supplier_id");
            }
            rs.close();
            pre.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierId;
    }

    //Dùng
    // Phương thức lấy supplier_name từ bảng supplier
    public String getSupplierNameById(int supplierId) {
        String supplierName = "";
        String sql = "SELECT name FROM supplier WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, supplierId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                supplierName = rs.getString("name");
            }
            rs.close();
            pre.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierName;
    }

    //Dùng
    public Vector<Consignment> getAllConsignmentWithPagination(int offset, int limit) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT c.id, c.product_id, c.contract_id, c.import_price, c.selling_price, "
                + "c.delivery_date, c.status, s.name AS supplierName "
                + "FROM consignment c "
                + "JOIN contract ct ON c.contract_id = ct.id "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consignment consignment = new Consignment();
                // Populate consignment object from ResultSet
                consignment.setId(rs.getInt("id"));
                consignment.setImportPrice(rs.getInt("import_price"));
                consignment.setDeliveryDate(rs.getDate("delivery_date"));
                consignment.setStatus(rs.getInt("status"));
                consignment.setSupplierName(rs.getString("supplierName"));
                consignments.add(consignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    //Dùng
    public int getTotalConsignmentCountByStatus(int status) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM consignment WHERE status = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public Vector<Consignment> getConsignmentsByPage(int startIndex, int itemsPerPage) {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT * FROM Consignment LIMIT ?, ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, startIndex);
            ps.setInt(2, itemsPerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Consignment consignment = new Consignment();
                // Populate consignment object from ResultSet
                consignment.setId(rs.getInt("id"));
                consignment.setImportPrice(rs.getInt("import_price"));
                consignment.setDeliveryDate(rs.getDate("delivery_date"));
                consignment.setStatus(rs.getInt("status"));
                consignment.setSupplierName(rs.getString("supplier_name"));
                consignments.add(consignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignments;
    }

    //Dùng
    public int getTotalConsignmentCount() {
        String sql = "SELECT COUNT(*) FROM Consignment";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Dùng
    // Phương thức lấy tất cả consignments và bổ sung thêm tên nhà cung cấp
    public Vector<Consignment> getAllConsignmentWithSupplierName() {
        Vector<Consignment> consignments = new Vector<>();
        String sql = "SELECT * FROM consignment";

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                int product_id = rs.getInt(2);
                int warehouse_id = rs.getInt(3);
                int contract_id = rs.getInt(4);
                int status = rs.getInt(5);
                Date delivery_date = rs.getDate(6);
                int product_category_id = rs.getInt(7);
                int import_price = rs.getInt(8);
                int selling_price = rs.getInt(9);
                int number_of_product = rs.getInt(10);
                float discount_percentage = rs.getFloat(11);

                // Lấy supplier_id từ contract_id
                int supplierId = getSupplierIdFromContract(contract_id);
                // Lấy tên nhà cung cấp từ supplier_id
                String supplierName = getSupplierNameById(supplierId);

                // Tạo đối tượng Consignment và gán supplierName
                Consignment con = new Consignment(id, product_id, warehouse_id, contract_id, status,
                        delivery_date, product_category_id, import_price, selling_price, number_of_product,
                        discount_percentage);
                con.setSupplierName(supplierName); // Gán tên nhà cung cấp

                consignments.add(con);
            }

            rs.close();
            state.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignments;
    }

    //Dùng
    public Vector<Consignment> getAllConsignment1() {
        Vector<Consignment> consignments = new Vector<Consignment>();
        String sql = "SELECT * FROM consignment"; // Removed the WHERE clause

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery(); // Execute query without parameters

            while (rs.next()) {
                int id = rs.getInt(1);
                int product_id = rs.getInt(2);
                int warehouse_id = rs.getInt(3);
                int contract_id = rs.getInt(4);
                int status = rs.getInt(5);
                Date delivery_date = rs.getDate(6);
                int product_category_id = rs.getInt(7);
                int import_price = rs.getInt(8);
                int selling_price = rs.getInt(9);
                int number_of_product = rs.getInt(10);
                float discount_percentage = rs.getFloat(11);

                Consignment con = new Consignment(id, product_id, warehouse_id,
                        contract_id, status, delivery_date, product_category_id,
                        import_price, selling_price, number_of_product, discount_percentage);

                consignments.add(con);
            }

            // Close resources
            rs.close();
            state.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consignments;
    }

    public int addConsignmentAndGetId(Consignment consignment) {
        int consignmentId = 0;
        String sql = "INSERT INTO consignment (product_id, contract_id, status, delivery_date, "
                + "product_category_id, import_price, selling_price, number_of_product, discount_percentage) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pre.setInt(1, consignment.getProductId());
            pre.setInt(2, consignment.getContractId());  // Gán contract_id khi thêm consignment
            pre.setInt(3, consignment.getStatus());  // Trạng thái
            pre.setDate(4, dc.UtilDateToSqlDate(consignment.getDeliveryDate()));  // Chuyển đổi ngày
            pre.setInt(5, consignment.getProductCategoryId());  // ID loại sản phẩm
            pre.setInt(6, consignment.getImportPrice());  // Giá nhập
            pre.setInt(7, consignment.getSellingPrice());  // Giá bán
            pre.setInt(8, consignment.getNumberOfProduct());  // Số lượng
            pre.setFloat(9, consignment.getDiscountPercentage());  // Giảm giá nếu có

            // Thực thi truy vấn và lấy id consignment vừa được tạo
            pre.executeUpdate();
            ResultSet rs = pre.getGeneratedKeys();
            if (rs.next()) {
                consignmentId = rs.getInt(1);  // Lấy id của consignment vừa được tạo
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consignmentId;
    }

    // get product by price range
    public List<Consignment> getConsignmentsByPriceRange(int min, int max) {
        List<Consignment> list = new ArrayList<>();
        String query = "SELECT * FROM wholesalemanagement.consignment WHERE selling_price BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int wareHouseId = rs.getInt("warehouse_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                Date deliveryDate = rs.getDate("delivery_date");
                int productCategoryId = rs.getInt("product_category_id");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                int numberOfProduct = rs.getInt("number_of_product");
                float discountPercentage = rs.getFloat("discount_percentage");
                Consignment con = new Consignment(id, productId, wareHouseId, contractId, status, deliveryDate, productCategoryId, importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // get product by quantity
    public List<Consignment> getConsignmentsQuantity(int quantity) {
        List<Consignment> list = new ArrayList<>();
        String query = "SELECT * FROM wholesalemanagement.consignment WHERE number_of_product <= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, quantity);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int wareHouseId = rs.getInt("warehouse_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                Date deliveryDate = rs.getDate("delivery_date");
                int productCategoryId = rs.getInt("product_category_id");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                int numberOfProduct = rs.getInt("number_of_product");
                float discountPercentage = rs.getFloat("discount_percentage");
                Consignment con = new Consignment(id, productId, wareHouseId, contractId, status, deliveryDate, productCategoryId, importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // get product by discount
    public List<Consignment> getConsignmentsdiscount(float discount) {
        List<Consignment> list = new ArrayList<>();
        String query = "SELECT * FROM wholesalemanagement.consignment WHERE discount_percentage <= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setFloat(1, discount);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int wareHouseId = rs.getInt("warehouse_id");
                int contractId = rs.getInt("contract_id");
                int status = rs.getInt("status");
                Date deliveryDate = rs.getDate("delivery_date");
                int productCategoryId = rs.getInt("product_category_id");
                int importPrice = rs.getInt("import_price");
                int sellingPrice = rs.getInt("selling_price");
                int numberOfProduct = rs.getInt("number_of_product");
                float discountPercentage = rs.getFloat("discount_percentage");
                Consignment con = new Consignment(id, productId, wareHouseId, contractId, status, deliveryDate, productCategoryId, importPrice, sellingPrice, numberOfProduct, discountPercentage);
                list.add(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//        public static void main(String[] args) {
//        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
//        List<Consignment> list = consignmentDAO.getConsignmentsdiscount(1.0f);
//        for (Consignment o : list) {
//            System.out.println(o);
//        }
//    }
//    public static void main(String[] args) {
//        ConsignmentDAO consignmentDAO = new ConsignmentDAO();
//        Consignment consignment = consignmentDAO.getConsignmentById(2);
//        if (consignment != null) {
//            System.out.println(consignment);
//        } else {
//            System.out.println("Không tìm thấy consignment với id này.");
//        }
//    }
    public static void main(String[] args) {
        ConsignmentDAO condao = new ConsignmentDAO();
        int id = 92;
        Vector<Consignment> consignment = condao.getConsignmentsByContractId(92);
        for (Consignment consignment1 : consignment) {
            System.out.println(consignment1.getId());
            System.out.println(consignment1.getDeliveryDate());
            System.out.println(consignment1.getSupplierName());
            System.out.println(consignment1.getSupplierAddress());
            System.out.println(consignment1.getSupplierPhone());
            System.out.println(consignment1.getImportPrice());

        }

    }
}
