/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import model.AccountType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 84941
 */
public class AccountTypeDAO extends DBContext {

    //insert new account type into database. Attribute id is AUTO_INCREMENT 
    public int insertAccount(AccountType obj) {
        int n = 0;
        String sql = "INSERT INTO accounttype\n"
                + "           (title)\n"
                + "     VALUES\n"
                + "           (?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getTitle());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;

    }

    // get an account type list based on specific conditions
    public Vector<AccountType> getAccountTypes(String sql) {
        Vector<AccountType> vector = new Vector<AccountType>();
        try {
            Statement state = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                AccountType obj = new AccountType(id, title);
                vector.add(obj);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;

    }
   


    // update account type based on id
    public int updateAccountType(AccountType obj) {
        int n = 0;
        String sql = "UPDATE accounttype\n"
                + "   SET title = ?\n"
                + " WHERE id = ?";
        try {

            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, obj.getTitle());
            pre.setInt(2, obj.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    //get account type by title
    public AccountType getAccountTypeByTitle(String search_title) {
        Vector<AccountType> vector = new Vector<AccountType>();
        String sql = "SELECT id\n"
                + "      ,title\n"
                + "  FROM accounttype"
                + "  WHERE title like '" + search_title+"'";

        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                AccountType obj = new AccountType(id, title);
                return obj;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;

    }


}
