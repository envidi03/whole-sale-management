package dal;

import model.TaskNotes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskNotesDAO extends DBContext {

    // Thêm một ghi chú công việc mới
    public boolean addTask(TaskNotes task) {
        String sql = "INSERT INTO task_notes (manager_id, title, description, start_time, end_time, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, task.getManagerId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(task.getStartTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(task.getEndTime()));
            stmt.setInt(6, task.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật thông tin của một ghi chú công việc
    public boolean updateTask(TaskNotes task) {
        String sql = "UPDATE task_notes SET title = ?, description = ?, start_time = ?, end_time = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(task.getStartTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(task.getEndTime()));
            stmt.setInt(5, task.getStatus());
            stmt.setInt(6, task.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa một ghi chú công việc theo ID
    public boolean deleteTask(int id) {
        String sql = "DELETE FROM task_notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy một ghi chú công việc theo ID
    public TaskNotes getTaskById(int id) {
        String sql = "SELECT * FROM task_notes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToTaskNotes(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả các ghi chú công việc của một manager cụ thể
    public List<TaskNotes> getTasksByManagerId(int managerId) {
        List<TaskNotes> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task_notes WHERE manager_id = ? ORDER BY start_time";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, managerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapResultSetToTaskNotes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Chuyển đổi một ResultSet thành một đối tượng TaskNotes
    private TaskNotes mapResultSetToTaskNotes(ResultSet rs) throws SQLException {
        TaskNotes task = new TaskNotes();
        task.setId(rs.getInt("id"));
        task.setManagerId(rs.getInt("manager_id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        task.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        task.setStatus(rs.getInt("status"));
        task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        task.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return task;
    }

    // Lấy tất cả các ghi chú công việc
    public List<TaskNotes> getAllTasks() {
        List<TaskNotes> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task_notes ORDER BY start_time";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                tasks.add(mapResultSetToTaskNotes(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

}
