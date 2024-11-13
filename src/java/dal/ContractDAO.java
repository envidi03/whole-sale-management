package dal;

import model.Contract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import model.Consignment;
import util.DataConvert;

public class ContractDAO extends DBContext {

    DataConvert dc = new DataConvert();

    // Phương thức để lấy danh sách hợp đồng chi tiết dựa trên warehouseReportId
    public List<Contract> getContractsByReportId(int warehouseReportId) {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT c.id, c.contract_delivery_date, c.actual_delivery_date, "
                + "c.contract_value, c.status, c.description, s.name AS supplier_name, "
                + "w.name AS warehouse_name "
                + "FROM contract c "
                + "JOIN supplier s ON c.supplier_id = s.id "
                + "JOIN warehouse w ON c.warehouse_id = w.id "
                + "WHERE c.contract_report_id = ?";
        try (PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, warehouseReportId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setActualDeliveryDate(rs.getDate("actual_delivery_date"));
                contract.setContractValue(rs.getInt("contract_value"));
                contract.setStatus(rs.getInt("status"));
                contract.setDescription(rs.getString("description"));
                contract.setSupplierName(rs.getString("supplier_name"));
                contract.setWarehouseName(rs.getString("warehouse_name"));

                contracts.add(contract);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contracts;
    }

    // Hàm main để kiểm tra phương thức getContractsByReportId
    public static void main(String[] args) {
        ContractDAO contractDAO = new ContractDAO();
        int warehouseReportId = 1; // Thay đổi giá trị này để kiểm tra với các warehouseReportId khác

        List<Contract> contracts = contractDAO.getContractsByReportId(warehouseReportId);
        System.out.println("Danh sách hợp đồng cho warehouseReportId = " + warehouseReportId + ":");
        for (Contract contract : contracts) {
            System.out.println("ID: " + contract.getId());
            System.out.println("Ngày giao hàng: " + contract.getContractDeliveryDate());
            System.out.println("Ngày thực tế giao hàng: " + contract.getActualDeliveryDate());
            System.out.println("Giá trị hợp đồng: " + contract.getContractValue());
            System.out.println("Trạng thái: " + contract.getStatus());
            System.out.println("Mô tả: " + contract.getDescription());
            System.out.println("Nhà cung cấp: " + contract.getSupplierName());
            System.out.println("Kho: " + contract.getWarehouseName());
            System.out.println("-----");
        }

        if (contracts.isEmpty()) {
            System.out.println("Không có hợp đồng nào cho warehouseReportId = " + warehouseReportId);
        }
    }

    public int addContractAndGetId(Contract contract) {
        int contractId = 0;
        String sql = "INSERT INTO contract (contract_delivery_date, warehouse_id, supplier_id, status) "
                + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pre = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Gán các giá trị vào contract
            pre.setDate(1, dc.UtilDateToSqlDate(contract.getContractDeliveryDate()));
            pre.setInt(2, contract.getWarehouseId());
            pre.setInt(3, contract.getSupplierId());
            pre.setInt(4, contract.getStatus());

            pre.executeUpdate();
            ResultSet rs = pre.getGeneratedKeys();
            if (rs.next()) {
                contractId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractId;
    }

    public List<Contract> searchContracts(Integer id, Date contractDeliveryDate, Date actualDeliveryDate, Integer contractValue,
            Integer status, String description, String supplierName, String warehouseName) {
        List<Contract> contracts = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT c.id, c.contract_delivery_date, c.actual_delivery_date, c.contract_value, c.status, c.description, s.name AS supplier_name, w.name AS warehouse_name "
                + "FROM contract c "
                + "JOIN supplier s ON c.supplier_id = s.id "
                + "JOIN warehouse w ON c.warehouse_id = w.id "
                + "WHERE 1=1");

        // Tạo câu truy vấn động
        if (id != null) {
            sql.append(" AND c.id = ?");
        }
        if (contractDeliveryDate != null) {
            sql.append(" AND c.contract_delivery_date = ?");
        }
        if (actualDeliveryDate != null) {
            sql.append(" AND c.actual_delivery_date = ?");
        }
        if (contractValue != null) {
            sql.append(" AND c.contract_value = ?");
        }
        if (status != null) {
            sql.append(" AND c.status = ?");
        }
        if (description != null && !description.isEmpty()) {
            sql.append(" AND c.description LIKE ?");
        }
        if (supplierName != null && !supplierName.isEmpty()) {
            sql.append(" AND s.name LIKE ?");
        }
        if (warehouseName != null && !warehouseName.isEmpty()) {
            sql.append(" AND w.name LIKE ?");
        }

        try (PreparedStatement pre = connection.prepareStatement(sql.toString())) {
            int index = 1;

            // Gán giá trị cho các tham số
            if (id != null) {
                pre.setInt(index++, id);
            }
            if (contractDeliveryDate != null) {
                pre.setDate(index++, dc.UtilDateToSqlDate(contractDeliveryDate));
            }
            if (actualDeliveryDate != null) {
                pre.setDate(index++, dc.UtilDateToSqlDate(actualDeliveryDate));
            }
            if (contractValue != null) {
                pre.setInt(index++, contractValue);
            }
            if (status != null) {
                pre.setInt(index++, status);
            }
            if (description != null && !description.isEmpty()) {
                pre.setString(index++, "%" + description + "%");
            }
            if (supplierName != null && !supplierName.isEmpty()) {
                pre.setString(index++, "%" + supplierName + "%");
            }
            if (warehouseName != null && !warehouseName.isEmpty()) {
                pre.setString(index++, "%" + warehouseName + "%");
            }

            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Contract contract = new Contract();
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setActualDeliveryDate(rs.getDate("actual_delivery_date"));
                contract.setContractValue(rs.getInt("contract_value"));
                contract.setStatus(rs.getInt("status"));
                contract.setDescription(rs.getString("description"));
                contract.setSupplierName(rs.getString("supplier_name"));
                contract.setWarehouseName(rs.getString("warehouse_name"));

                contracts.add(contract);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return contracts;
    }

    public void updateContractStatus(int contractId, int status) {
        String sql = "UPDATE contract SET status = ? WHERE id = ?";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, contractId);

            pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vector<Contract> getContractsByStatusWithPagination(int status, int offset, int limit) {
        Vector<Contract> contracts = new Vector<>();
        String sql = "SELECT ct.id, ct.contract_delivery_date, ct.status, s.name AS supplierName "
                + "FROM contract ct "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "WHERE ct.status = ? LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                // Populate contract object with selected columns
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setStatus(rs.getInt("status"));
                contract.setSupplierName(rs.getString("supplierName"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public Vector<Contract> getAllContractsWithPagination(int offset, int limit) {
        Vector<Contract> contracts = new Vector<>();
        String sql = "SELECT ct.id, ct.contract_delivery_date, ct.status, s.name AS supplierName "
                + "FROM contract ct "
                + "JOIN supplier s ON ct.supplier_id = s.id "
                + "LIMIT ? OFFSET ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                // Populate contract object with selected columns
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setStatus(rs.getInt("status"));
                contract.setSupplierName(rs.getString("supplierName"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public int getTotalContractCountByStatus(int status) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM contract WHERE status = ?";

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

    public int getTotalContractCount() {
        String sql = "SELECT COUNT(*) FROM contract";
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

    public Vector<Contract> searchContractByID(int contractId) {
        Vector<Contract> contracts = new Vector<>();
        String sql = "SELECT ct.id, ct.contract_delivery_date, ct.status, s.name AS supplierName "
                + "FROM contract ct "
                + "JOIN supplier s ON ct.supplier_id = s.id WHERE ct.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contractId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                // Populate contract object with selected columns
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setStatus(rs.getInt("status"));
                contract.setSupplierName(rs.getString("supplierName"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public Vector<Contract> getAllContract() {
        Vector<Contract> contracts = new Vector<Contract>();
        String sql = "SELECT ct.id, ct.contract_delivery_date, ct.warehouse_id, ct.status, ct.contract_value, s.name AS supplierName "
                + "FROM contract ct "
                + "JOIN supplier s ON ct.supplier_id = s.id";  // Bỏ điều kiện WHERE để lấy tất cả hợp đồng

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery(); // Thực thi truy vấn

            while (rs.next()) {
                int id = rs.getInt("id");
                Date contractDeliveryDate = rs.getDate("contract_delivery_date");
                int warehouseId = rs.getInt("warehouse_id");
                int status = rs.getInt("status");
                String supplierName = rs.getString("supplierName");

                Contract contract = new Contract(id, contractDeliveryDate, warehouseId, status, supplierName);

                contracts.add(contract);
            }

            // Đóng tài nguyên
            rs.close();
            state.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contracts;
    }

    public Vector<Contract> searchContractsBySupplierName(String supplierName) {
        Vector<Contract> contracts = new Vector<>();
        String sql = "SELECT c.id, c.contract_delivery_date, c.status, c.contract_value, s.name AS supplierName "
                + "FROM contract c "
                + "JOIN supplier s ON c.supplier_id = s.id "
                + "WHERE LOWER(s.name) LIKE ?";  // Lọc theo tên nhà cung cấp
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + supplierName.toLowerCase() + "%");  // Đảm bảo tìm kiếm không phân biệt chữ hoa/thường
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contract contract = new Contract();
                // Thiết lập các thuộc tính cho đối tượng Contract
                contract.setId(rs.getInt("id"));
                contract.setContractDeliveryDate(rs.getDate("contract_delivery_date"));
                contract.setStatus(rs.getInt("status"));
                contract.setContractValue(rs.getInt("contract_value"));
                contract.setSupplierName(rs.getString("supplierName"));  // Thiết lập tên nhà cung cấp
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

}
