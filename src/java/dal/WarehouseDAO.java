/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.Statement;
import model.StorageLocation;
import model.Warehouse;

/**
 *
 * @author Admin
 */
public class WarehouseDAO extends DBContext {

    public int getMaxWarehouseId() {
        int maxWarehouseId = 0;
        String sql = "SELECT MAX(id) AS max_id FROM warehouse";

        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs.next()) {
                maxWarehouseId = rs.getInt("max_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxWarehouseId;
    }

    public StorageLocation findEmptyLocation(int warehouseId) {
        String sql = "SELECT shelf, `row`, `column` "
                + "FROM storage_location "
                + "WHERE warehouse_id = ? AND consignment_id IS NULL "
                + "ORDER BY shelf, `row`, `column` "
                + "LIMIT 1";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, warehouseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int shelf = rs.getInt("shelf");
                int column = rs.getInt("column");
                int row = rs.getInt("row");

                // Trả về một đối tượng StorageLocation với vị trí tìm thấy
                return new StorageLocation(shelf, row, column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Không có vị trí trống
    }

    public void updateStorageLocation(int warehouseId, int shelf, int column, int row, int consignmentId) {
        String sql = "UPDATE storage_location "
                + "SET consignment_id = ? "
                + "WHERE warehouse_id = ? AND shelf = ? AND `column` = ? AND `row` = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, consignmentId);
            ps.setInt(2, warehouseId);
            ps.setInt(3, shelf);
            ps.setInt(4, column);
            ps.setInt(5, row);

            int rowsUpdated = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all warehouses from the database
    public Vector<Warehouse> getAllWarehouses() {
        Vector<Warehouse> warehouses = new Vector<>();
        String sql = "SELECT * FROM warehouse";
        try (
                PreparedStatement pre = connection.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setId(rs.getInt("id"));
                warehouse.setName(rs.getString("name"));
                warehouses.add(warehouse);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouses;
    }

    public int getWarehouseId() {
        int warehouseId = 1;  
        String sql = "SELECT id FROM warehouse LIMIT 1";
        try (PreparedStatement pre = connection.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            if (rs.next()) {
                warehouseId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouseId;
    }

    // Get a specific warehouse by its id
    public Warehouse getWarehouseById(int searchId) {
        Warehouse warehouse = null;
        String sql = "SELECT id, name, location, manager_id, number_of_shelf, number_of_row_per_shelf, number_of_column_per_shelf "
                + "FROM Warehouse WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, searchId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String location = rs.getString(3);
                int managerId = rs.getInt(4); // managerId as int
                int numberOfShelf = rs.getInt(5);
                int numberOfRowPerShelf = rs.getInt(6);
                int numberOfColumnPerShelf = rs.getInt(7);
                warehouse = new Warehouse(id, name, location, managerId, numberOfShelf, numberOfRowPerShelf, numberOfColumnPerShelf);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouse;
    }

    // Update an existing warehouse record
    public int updateWarehouse(Warehouse warehouse) {
        int n = 0;
        String sql = "UPDATE Warehouse SET name = ?, location = ?, manager_id = ?, number_of_shelf = ?, "
                + "number_of_row_per_shelf = ?, number_of_column_per_shelf = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, warehouse.getName());
            pre.setString(2, warehouse.getLocation());
            pre.setInt(3, warehouse.getManagerId()); // managerId as int
            pre.setInt(4, warehouse.getNumberOfShelf());
            pre.setInt(5, warehouse.getNumberOfRowPerShelf());
            pre.setInt(6, warehouse.getNumberOfColumnPerShelf());
            pre.setInt(7, warehouse.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Delete a warehouse by id
    public int deleteWarehouse(int warehouseId) {
        int n = 0;
        String sql = "DELETE FROM Warehouse WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, warehouseId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public static void main(String[] args) {
        WarehouseDAO wd = new WarehouseDAO();
        System.out.println(wd.getWarehouseId());
    }
}
