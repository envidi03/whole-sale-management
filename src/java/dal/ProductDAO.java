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

import java.sql.PreparedStatement;
import model.Product;
import java.sql.PreparedStatement;
import java.util.Vector;
import util.DataConvert;

/**
 *
 * @author LamHP
 */
public class ProductDAO extends DBContext {

    DataConvert dc = new DataConvert();

    // get all
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM wholesalemanagement.product";
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

    // get allProduct by category
    public List<Product> getAllProductsByCategory(String cid) {
        List<Product> list = new ArrayList<>();
        String sql = "select * from wholesalemanagement.product where category_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cid);
            ResultSet rs = ps.executeQuery();

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

    // search product by name
    public List<Product> search(String txtSearch, int index, int size) {
        List<Product> list = new ArrayList<>();
        String sql = "  with x as  (SELECT \n"
                + "    ROW_NUMBER() OVER (ORDER BY p.expire_date) as stt, \n"
                + "    p.*\n"
                + "FROM \n"
                + "    wholesalemanagement.product p\n"
                + "WHERE \n"
                + "    p.name LIKE ?) select *from x where stt between  ?*5-4 and ? * 5";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + txtSearch + "%");
            ps.setInt(2, index);
            ps.setInt(3, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");
                Date manufactureDate = rs.getDate("manufacture_date");
                Date expireDate = rs.getDate("expire_date");
                int unitId = rs.getInt("unit_id");
                int retailPrice = rs.getInt("retail_price");
                int categoryId = rs.getInt("category_id");
                Product pro = new Product(id, productName, manufactureDate, expireDate, unitId, retailPrice, categoryId);
                list.add(pro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // search and pagging
    public int count(String txtSearch) {
        String sql = "SELECT COUNT(*)\n"
                + "FROM wholesalemanagement.product\n"
                + "WHERE name LIKE ?";

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

    public List<Product> getProductsByPage(int page, int productsPerPage) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM wholesalemanagement.product LIMIT ?, ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, (page - 1) * productsPerPage); // Bắt đầu từ vị trí sản phẩm
            ps.setInt(2, productsPerPage); // Số sản phẩm cần lấy
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");
                Date manufactureDate = rs.getDate("manufacture_date");
                Date expireDate = rs.getDate("expire_date");
                int unitId = rs.getInt("unit_id");
                int retailPrice = rs.getInt("retail_price");
                int categoryId = rs.getInt("category_id");
                Product pro = new Product(id, productName, manufactureDate, expireDate, unitId, retailPrice, categoryId);
                list.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalProducts() {
        int total = 0;
        String query = "SELECT COUNT(*) FROM wholesalemanagement.product";
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

    // get product by price range
    public List<Product> getProductsByPriceRange(int minPrice, int maxPrice) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM wholesalemanagement.product WHERE retail_price BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, minPrice);
            ps.setInt(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");
                Date manufactureDate = rs.getDate("manufacture_date");
                Date expireDate = rs.getDate("expire_date");
                int unitId = rs.getInt("unit_id");
                int retailPrice = rs.getInt("retail_price");
                int categoryId = rs.getInt("category_id");
                Product pro = new Product(id, productName, manufactureDate, expireDate, unitId, retailPrice, categoryId);
                list.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
//        public static void main(String[] args) {
//        ProductDAO productDAO = new ProductDAO();
//        List<Product> list = productDAO.getProductsByPriceRange(10000, 20000);
//        for (Product o : list) {
//            System.out.println(o);
//        }
//    }

    public int addProduct(Product product) {
        int n = 0;
        String sql = "INSERT INTO product(name, manufacture_date, expire_date,unit_id,retail_price,category_id)"
                + "VALUE (?,?,?,?,?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, product.getProductName());
            pre.setDate(2, dc.UtilDateToSqlDate(product.getManufactureDate()));
            pre.setDate(3, dc.UtilDateToSqlDate(product.getExpireDate()));
            pre.setInt(4, product.getUnitId());
            pre.setInt(5, product.getRetailPrice());
            pre.setInt(6, product.getCategoryId());
            n = pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public Product getProductById(int productId) {
        Product product = null;
        try {
            String query = "SELECT * FROM product WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getDate("manufacture_date"), rs.getDate("expire_date"),
                        rs.getInt("unit_id"), rs.getInt("retail_price"),
                        rs.getInt("category_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean checkProductExists(int productId) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM product WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public Vector<Product> getAllProduct() {
        Vector<Product> vectorP = new Vector<>();
        String sql = "SELECT id, name FROM product";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String productName = rs.getString("name");

                Product pro = new Product(id, productName);
                vectorP.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vectorP;
    }


//    public static void main(String[] args) {
//        ProductDAO productDAO = new ProductDAO();
//        int count = productDAO.count("đỏ");
//        int min = 5000;
//        int max = 20000;
//        List<Product> list = productDAO.getProductsByPriceRange(min, max);
//        
//        for(Product lists : list){
//            System.out.println(lists.getProductName());
//        }
//        
//        System.out.println("List: " + productDAO.getAllProduct());
//    }
//    
//    public static void main(String[] args) {
//        ProductDAO productDAO = new ProductDAO();
//       String categoryId = "1";
//        List<Product> list = productDAO.getProductsByPriceRange(100000, 150000);
//        for (Product o : list) {
//            System.out.println(o);
//        }
//    }
}
