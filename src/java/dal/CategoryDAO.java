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
import model.ProductCategory;
import java.sql.PreparedStatement;
import java.util.Vector;
/**
 *
 * @author Acer
 */
public class CategoryDAO extends DBContext{
        // get all
    public List<ProductCategory> gettAllCategorys() {
        List<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM wholesalemanagement.product_category";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                ProductCategory pro = new ProductCategory(id, name);
                list.add(pro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
        public int getProductCategoryIdByProductId(int productId) {
    String sql = "SELECT category_id FROM product WHERE id = ?";
    try {
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setInt(1, productId);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getInt("category_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;  
}

    public int addProductCategory(ProductCategory productC) {
        int n = 0;
        String sql = "INSERT INTO product_category(name)"
                + "VALUE (?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, productC.getName());
            n = pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    public boolean checkProductCategoryExist(int cateId) {
        boolean exist = false;
        try {
            String sql = "SELECT COUNT(*) FROM product_category WHERE id = ?";
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                exist = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public Vector<ProductCategory> getAllProductCategory() {
        Vector<ProductCategory> pCategory = new Vector<ProductCategory>();
        String sql = "SELECT * FROM product_category";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                ProductCategory pCate = new ProductCategory(id, name);
                pCategory.add(pCate);
            }
            rs.close();
            pre.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pCategory;

    }
    
//        public static void main(String[] args) {
//        CategoryDAO categoryDAO = new CategoryDAO();
//        
//        List<ProductCategory> list = categoryDAO.gettAllCategorys();
//        for (ProductCategory o : list) {
//            System.out.println(o);
//        }
//    }
}
