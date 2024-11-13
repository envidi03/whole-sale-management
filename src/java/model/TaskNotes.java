package model;

import java.time.LocalDateTime;

public class TaskNotes {

    private int id;
    private int managerId; // ID của warehouse manager từ bảng account
    private String title; // Tiêu đề công việc
    private String description; // Mô tả chi tiết công việc
    private LocalDateTime startTime; // Thời gian bắt đầu công việc
    private LocalDateTime endTime; // Thời gian kết thúc công việc
    private int status; // 0 là chưa hoàn thành, 1 là đã hoàn thành
    private LocalDateTime createdAt; // Thời gian tạo ghi chú
    private LocalDateTime updatedAt; // Thời gian cập nhật ghi chú

    // Constructor không tham số
    public TaskNotes() {
    }

    // Constructor với các tham số
    public TaskNotes(int id, int managerId, String title, String description, LocalDateTime startTime, LocalDateTime endTime, int status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.managerId = managerId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter và Setter cho các thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Phương thức tiện ích để kiểm tra xem công việc đã hoàn thành hay chưa
    public boolean isCompleted() {
        return this.status == 1;
    }

    @Override
    public String toString() {
        return "TaskNotes{"
                + "id=" + id
                + ", managerId=" + managerId
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", status=" + status
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + '}';
    }
}
