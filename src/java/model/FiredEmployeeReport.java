package model;

/**
 *
 * @author admin
 */
public class FiredEmployeeReport {
    private int warehouseReportId;
    private int totalNumberOfNewFiredEmployee;
    private int month;
    private int year;
    private int reportType;

    // Constructor
    public FiredEmployeeReport(int warehouseReportId, int totalNumberOfNewFiredEmployee, int month, int year, int reportType) {
        this.warehouseReportId = warehouseReportId;
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
        this.month = month;
        this.year = year;
        this.reportType = reportType;
    }

    // Default constructor
    public FiredEmployeeReport() {
    }

    // Getters and Setters
    public int getWarehouseReportId() {
        return warehouseReportId;
    }

    public void setWarehouseReportId(int warehouseReportId) {
        this.warehouseReportId = warehouseReportId;
    }

    public int getTotalNumberOfNewFiredEmployee() {
        return totalNumberOfNewFiredEmployee;
    }

    public void setTotalNumberOfNewFiredEmployee(int totalNumberOfNewFiredEmployee) {
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
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

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    // toString method
    @Override
    public String toString() {
        return "FiredEmployeeReport{" +
                "warehouseReportId=" + warehouseReportId +
                ", totalNumberOfNewFiredEmployee=" + totalNumberOfNewFiredEmployee +
                ", month=" + month +
                ", year=" + year +
                ", reportType=" + reportType +
                '}';
    }
}
