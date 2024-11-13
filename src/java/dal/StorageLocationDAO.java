/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.StorageLocation;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author Admin
 */
public class StorageLocationDAO extends DBContext {

    public Vector<StorageLocation> getAllStorageLocation() {
        Vector<StorageLocation> vectorST = new Vector<>();
        String sql = "SELECT *\n"
                + "FROM storage_location\n"
                + "WHERE consignment_id IS NOT NULL;";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int warehouse_id = rs.getInt("warehouse_id");
                int shelf = rs.getInt("shelf");
                int row = rs.getInt("row");
                int collum = rs.getInt("column");
                int consignment_id = rs.getInt("consignment_id");
                StorageLocation storage = new StorageLocation(warehouse_id, shelf, row, collum, consignment_id);
                vectorST.add(storage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vectorST;
    }

    public Vector<Integer> getWarehouseList() {
        Vector<Integer> warehouses = new Vector<>();
        String sql = "SELECT DISTINCT warehouse_id FROM storage_location";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                warehouses.add(rs.getInt("warehouse_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouses;
    }

    public Vector<Integer> getShelfList() {
        Vector<Integer> shelves = new Vector<>();
        String sql = "SELECT DISTINCT shelf FROM storage_location";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                shelves.add(rs.getInt("shelf"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shelves;
    }

    public Vector<StorageLocation> getStorageLocationByConsignmentID(int searchId) {
        Vector<StorageLocation> storage = new Vector<>();
        String sql = "SELECT * FROM storage_location WHERE consignment_id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, searchId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int warehouse_id = rs.getInt("warehouse_id");
                int shelf = rs.getInt("shelf");
                int row = rs.getInt("row");
                int collum = rs.getInt("column");
                int consignment_id = rs.getInt("consignment_id");
                StorageLocation storageLocation = new StorageLocation(warehouse_id, shelf, row, collum, consignment_id);
                storage.add(storageLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storage;
    }

    public Vector<StorageLocation> getStorageLocationsWithPagination(int warehouseId, int shelf, int offset, int limit) {
        Vector<StorageLocation> storageLocations = new Vector<>();

        String sql = "SELECT warehouse_id, shelf, `row`, `column`, consignment_id "
                + "FROM storage_location WHERE consignment_id IS NOT NULL";

        if (warehouseId != -1) {
            sql += " AND warehouse_id = ?";
        }
        if (shelf != -1) {
            sql += " AND shelf = ?";
        }

        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;

            if (warehouseId != -1) {
                ps.setInt(paramIndex++, warehouseId);
            }

            if (shelf != -1) {
                ps.setInt(paramIndex++, shelf);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StorageLocation storageLocation = new StorageLocation();
                storageLocation.setWarehouse_id(rs.getInt("warehouse_id"));
                storageLocation.setShelf(rs.getInt("shelf"));
                storageLocation.setRow(rs.getInt("row"));
                storageLocation.setCollum(rs.getInt("column"));
                storageLocation.setConsignment_id(rs.getInt("consignment_id"));
                storageLocations.add(storageLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storageLocations;
    }

    public int countStorageLocations(int warehouseId, int shelf) {
        String sql = "SELECT COUNT(*) FROM storage_location WHERE consignment_id IS NOT NULL";

        if (warehouseId != -1) {
            sql += " AND warehouse_id = ?";
        }
        if (shelf != -1) {
            sql += " AND shelf = ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;

            if (warehouseId != -1) {
                ps.setInt(paramIndex++, warehouseId);
            }

            if (shelf != -1) {
                ps.setInt(paramIndex, shelf);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalStorageLocationCount() {
        String sql = "SELECT COUNT(*) FROM storage_location WHERE consignment_id IS NOT NULL";
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

    public Vector<StorageLocation> getAllStorageLocationsWithPagination(int offset, int limit) {
        Vector<StorageLocation> storageLocations = new Vector<>();
        String sql = "SELECT warehouse_id, shelf, `row`, `column`, consignment_id \n"
                + "                FROM storage_location \n"
                + "                WHERE consignment_id IS NOT NULL\n"
                + "                LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StorageLocation storageLocation = new StorageLocation();
                // Populate storageLocation object from ResultSet
                storageLocation.setWarehouse_id(rs.getInt("warehouse_id"));
                storageLocation.setShelf(rs.getInt("shelf"));
                storageLocation.setRow(rs.getInt("row"));
                storageLocation.setCollum(rs.getInt("column"));
                storageLocation.setConsignment_id(rs.getInt("consignment_id"));
                storageLocations.add(storageLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storageLocations;
    }

    public Vector<StorageLocation> getSortedStorageLocations(int warehouseId, int shelf, int offset, int limit, boolean ascending) {
        Vector<StorageLocation> storageLocations = new Vector<>();

        String sql = "SELECT warehouse_id, shelf, `row`, `column`, consignment_id "
                + "FROM storage_location WHERE consignment_id IS NOT NULL";

        if (warehouseId != -1) {
            sql += " AND warehouse_id = ?";
        }
        if (shelf != -1) {
            sql += " AND shelf = ?";
        }

        sql += " ORDER BY consignment_id " + (ascending ? "ASC" : "DESC");
        sql += " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int paramIndex = 1;

            if (warehouseId != -1) {
                ps.setInt(paramIndex++, warehouseId);
            }

            if (shelf != -1) {
                ps.setInt(paramIndex++, shelf);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StorageLocation storageLocation = new StorageLocation();
                storageLocation.setWarehouse_id(rs.getInt("warehouse_id"));
                storageLocation.setShelf(rs.getInt("shelf"));
                storageLocation.setRow(rs.getInt("row"));
                storageLocation.setCollum(rs.getInt("column"));
                storageLocation.setConsignment_id(rs.getInt("consignment_id"));
                storageLocations.add(storageLocation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storageLocations;
    }

    public static void main(String[] args) {
        StorageLocationDAO stDao = new StorageLocationDAO();

        int totalStorageLocations = stDao.getTotalStorageLocationCount();
        System.out.println("Tổng số vị trí lưu trữ: " + totalStorageLocations);

        int warehouseId = 9;
        int shelf = 1;
        int filteredCount = stDao.countStorageLocations(warehouseId, shelf);
        System.out.println("Số lượng vị trí lưu trữ trong kho " + warehouseId + " và kệ " + shelf + ": " + filteredCount);

        int offset = 0;
        int limit = 10;
        Vector<StorageLocation> paginatedLocations = stDao.getStorageLocationsWithPagination(warehouseId, shelf, offset, limit);
        System.out.println("Danh sách vị trí lưu trữ với warehouseId=" + warehouseId + ", shelf=" + shelf + ", offset=" + offset + ", limit=" + limit + ":");
        for (StorageLocation location : paginatedLocations) {
            System.out.println(location);
        }

        offset = 0;
        limit = 10;
        Vector<StorageLocation> allPaginatedLocations = stDao.getAllStorageLocationsWithPagination(offset, limit);
        System.out.println("Danh sách tất cả vị trí lưu trữ với offset=" + offset + ", limit=" + limit + ":");
        for (StorageLocation location : allPaginatedLocations) {
            System.out.println(location);
        }

    }
}
