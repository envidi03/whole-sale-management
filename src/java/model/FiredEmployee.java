package model;

import java.util.Date;

public class FiredEmployee {

    private int id;                  // Mã nhân viên bị nghỉ việc
    private String firstName;        // Tên
    private String lastName;         // Họ
    private String email;            // Email
    private String phoneNumber;      // Số điện thoại
    private int roleId;              // Mã vai trò
    private String roleTitle;        // Tiêu đề vai trò
    private Date dob;                // Ngày sinh
    private int gender;              // Giới tính
    private int warehouseId;         // Mã kho
    private String warehouseName;    // Tên kho
    private int warehouseReportId;   // Mã báo cáo kho
    private int month;               // Tháng báo cáo
    private int year;                // Năm báo cáo
    private int totalNumberOfNewFiredEmployee; // Tổng số nhân viên bị sa thải
    private Date terminationDate;    // Ngày chấm dứt hợp đồng
    private String reason;           // Lý do nghỉ việc
    private String department;       // Phòng ban
    private String status;           // Tình trạng nghỉ việc (ví dụ: nghỉ có lý do, tự nghỉ, v.v.)

    // Constructors
    public FiredEmployee() {
    }

    public FiredEmployee(int id, String firstName, String lastName, String email, String phoneNumber, int roleId,
                         String roleTitle, Date dob, int gender, int warehouseId, String warehouseName, 
                         int warehouseReportId, int month, int year, int totalNumberOfNewFiredEmployee, 
                         Date terminationDate, String reason, String department, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.roleTitle = roleTitle;
        this.dob = dob;
        this.gender = gender;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseReportId = warehouseReportId;
        this.month = month;
        this.year = year;
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
        this.terminationDate = terminationDate;
        this.reason = reason;
        this.department = department;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getWarehouseReportId() {
        return warehouseReportId;
    }

    public void setWarehouseReportId(int warehouseReportId) {
        this.warehouseReportId = warehouseReportId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTotalNumberOfNewFiredEmployee() {
        return totalNumberOfNewFiredEmployee;
    }

    public void setTotalNumberOfNewFiredEmployee(int totalNumberOfNewFiredEmployee) {
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "FiredEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleId=" + roleId +
                ", roleTitle='" + roleTitle + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseReportId=" + warehouseReportId +
                ", month=" + month +
                ", year=" + year +
                ", totalNumberOfNewFiredEmployee=" + totalNumberOfNewFiredEmployee +
                ", terminationDate=" + terminationDate +
                ", reason='" + reason + '\'' +
                ", department='" + department + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
