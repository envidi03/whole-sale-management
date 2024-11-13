package dal;

import model.Userlog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Date;
import util.DataConvert;

public class UserlogDAO extends DBContext {

    DataConvert dc = new DataConvert();

    // Insert new user log into database
    public int insertUserLog(Userlog userLog) {
        int n = 0;
        String sql = "INSERT INTO userlog (account_id, `date`, user_log_type) VALUES (?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userLog.getAccountId());
            pre.setDate(2, dc.UtilDateToSqlDate(userLog.getDate()));
            pre.setBoolean(3, userLog.isUserLogType());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
    public Vector<Userlog> getUserLogs(String sql) {
    Vector<Userlog> vector = new Vector<Userlog>();
    try {
        Statement state = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt(1);
            int accountId = rs.getInt(2);
            Date date = rs.getDate(3);
            boolean userLogType = rs.getBoolean(4);
            Userlog obj = new Userlog(id, accountId, date, userLogType);
            vector.add(obj);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return vector;
}


    // Get all user logs from the database
    public Vector<Userlog> getAllUserLogs() {
        Vector<Userlog> vector = new Vector<>();
        String sql = "SELECT id, account_id, `date`, user_log_type FROM userlog";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                int accountId = rs.getInt(2);
                Date date = rs.getDate(3);
                boolean userLogType = rs.getBoolean(4);
                Userlog userLog = new Userlog(id, accountId, date, userLogType);
                vector.add(userLog);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    // Get user log by ID
    public Userlog getUserLogById(int id) {
        String sql = "SELECT account_id, `date`, user_log_type FROM userlog WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int accountId = rs.getInt(1);
                Date date = rs.getDate(2);
                boolean userLogType = rs.getBoolean(3);
                return new Userlog(id, accountId, date, userLogType);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Update user log information based on ID
    public int updateUserLog(Userlog userLog) {
        int n = 0;
        String sql = "UPDATE userlog SET account_id = ?, `date` = ?, user_log_type = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userLog.getAccountId());
            pre.setDate(2, dc.UtilDateToSqlDate(userLog.getDate()));
            pre.setBoolean(3, userLog.isUserLogType());
            pre.setInt(4, userLog.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    // Delete user log by ID
    public int deleteUserLog(int id) {
        int n = 0;
        String sql = "DELETE FROM userlog WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }
//    public static void main(String[] args) {
//        UserlogDAO userlogDAO = new UserlogDAO();
//        Vector<Userlog> data = userlogDAO.getUserLogs("SELECT * FROM wholesalemanagement.userlog where user_log_type=0;");
//        if(data.isEmpty()){
//            System.out.println("Khong co gi");
//        }else{
//            System.out.println("co gi do");
//        }
//    }
}
