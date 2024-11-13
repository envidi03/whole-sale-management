package controller;

import dal.TaskNotesDAO;
import model.TaskNotes;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import jakarta.servlet.http.HttpSession;

public class WarehouseManagerHome extends HttpServlet {

    private final TaskNotesDAO taskNotesDAO = new TaskNotesDAO();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy managerId từ session
        HttpSession session = request.getSession();
        Integer managerId = (Integer) session.getAttribute("userId");

        // Kiểm tra nếu managerId không tồn tại trong session
        if (managerId == null) {
            response.sendRedirect("jsp/Login.jsp"); // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            loadNotes(request, response);
            return;
        }

        switch (action) {
            case "addNote":
                addNote(request, response, managerId);
                break;
            case "editNote":
                editNoteForm(request, response, managerId);
                break;
            case "updateNote":
                updateNote(request, response, managerId);
                break;
            case "deleteNote":
                deleteNote(request, response, managerId);
                break;
            case "markCompleted":
                markNoteCompleted(request, response, managerId);
                break;
            default:
                loadNotes(request, response);
                break;
        }
    }

    private void loadNotes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<TaskNotes> notes = taskNotesDAO.getAllTasks();
        request.setAttribute("notes", notes);
        request.getRequestDispatcher("jsp/WarehouseManager/HomePage.jsp").forward(request, response);
    }

    private void addNote(HttpServletRequest request, HttpServletResponse response, int managerId)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        LocalDate startDate = LocalDate.parse(request.getParameter("startTime"), dateFormatter);
        LocalDate endDate = LocalDate.parse(request.getParameter("endTime"), dateFormatter);

        TaskNotes note = new TaskNotes();
        note.setManagerId(managerId);
        note.setTitle(title);
        note.setDescription(description);
        note.setStartTime(startDate.atStartOfDay());
        note.setEndTime(endDate.atStartOfDay());
        note.setStatus(0);
        note.setCreatedAt(LocalDateTime.now());

        if (taskNotesDAO.addTask(note)) {
            request.setAttribute("successMessage", "Ghi chú đã được thêm thành công.");
        } else {
            request.setAttribute("errorMessage", "Thêm ghi chú thất bại.");
        }
        loadNotes(request, response);
    }

    private void editNoteForm(HttpServletRequest request, HttpServletResponse response, int managerId)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        TaskNotes note = taskNotesDAO.getTaskById(id);
        request.setAttribute("note", note);
        loadNotes(request, response);
    }

    private void updateNote(HttpServletRequest request, HttpServletResponse response, int managerId)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate startDate = LocalDate.parse(request.getParameter("startTime"), dateFormatter);
        LocalDate endDate = LocalDate.parse(request.getParameter("endTime"), dateFormatter);

        TaskNotes note = new TaskNotes();
        note.setId(id);
        note.setManagerId(managerId);
        note.setTitle(title);
        note.setDescription(description);
        note.setStartTime(startDate.atStartOfDay());
        note.setEndTime(endDate.atStartOfDay());
        note.setStatus(0);
        note.setUpdatedAt(LocalDateTime.now());

        if (taskNotesDAO.updateTask(note)) {
            request.setAttribute("successMessage", "Ghi chú đã được cập nhật thành công.");
        } else {
            request.setAttribute("errorMessage", "Cập nhật ghi chú thất bại.");
        }
        loadNotes(request, response);
    }

    private void deleteNote(HttpServletRequest request, HttpServletResponse response, int managerId)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (taskNotesDAO.deleteTask(id)) {
            request.setAttribute("successMessage", "Ghi chú đã được xóa thành công.");
        } else {
            request.setAttribute("errorMessage", "Xóa ghi chú thất bại.");
        }
        loadNotes(request, response);
    }

    private void markNoteCompleted(HttpServletRequest request, HttpServletResponse response, int managerId)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        TaskNotes note = taskNotesDAO.getTaskById(id);
        note.setStatus(1);
        note.setUpdatedAt(LocalDateTime.now());

        if (taskNotesDAO.updateTask(note)) {
            request.setAttribute("successMessage", "Ghi chú đã được đánh dấu là hoàn thành.");
        } else {
            request.setAttribute("errorMessage", "Đánh dấu hoàn thành thất bại.");
        }
        loadNotes(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Warehouse Manager Home Servlet";
    }
}
