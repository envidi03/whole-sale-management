package model;

/**
 *
 * @author admin
 */
public class OrderReport {
    private int warehouseReportId;
    private int totalNumberOfNewSuccessOrder;
    private int totalValueOfNewSuccessOrder;
    private int totalNumberOfNewOverdueDebtOrder;
    private int totalValueOfNewOverdueDebtOrder;
    private int totalNumberOfNewIndebtOrder;
    private int totalValueOfNewIndebtOrder;
    private int month;
    private int year;
    private int reportType;

    // Constructor
    public OrderReport(int warehouseReportId, int totalNumberOfNewSuccessOrder, int totalValueOfNewSuccessOrder,
                       int totalNumberOfNewOverdueDebtOrder, int totalValueOfNewOverdueDebtOrder,
                       int totalNumberOfNewIndebtOrder, int totalValueOfNewIndebtOrder,
                       int month, int year, int reportType) {
        this.warehouseReportId = warehouseReportId;
        this.totalNumberOfNewSuccessOrder = totalNumberOfNewSuccessOrder;
        this.totalValueOfNewSuccessOrder = totalValueOfNewSuccessOrder;
        this.totalNumberOfNewOverdueDebtOrder = totalNumberOfNewOverdueDebtOrder;
        this.totalValueOfNewOverdueDebtOrder = totalValueOfNewOverdueDebtOrder;
        this.totalNumberOfNewIndebtOrder = totalNumberOfNewIndebtOrder;
        this.totalValueOfNewIndebtOrder = totalValueOfNewIndebtOrder;
        this.month = month;
        this.year = year;
        this.reportType = reportType;
    }

    // Default constructor
    public OrderReport() {
    }

    // Getters and Setters
    public int getWarehouseReportId() {
        return warehouseReportId;
    }

    public void setWarehouseReportId(int warehouseReportId) {
        this.warehouseReportId = warehouseReportId;
    }

    public int getTotalNumberOfNewSuccessOrder() {
        return totalNumberOfNewSuccessOrder;
    }

    public void setTotalNumberOfNewSuccessOrder(int totalNumberOfNewSuccessOrder) {
        this.totalNumberOfNewSuccessOrder = totalNumberOfNewSuccessOrder;
    }

    public int getTotalValueOfNewSuccessOrder() {
        return totalValueOfNewSuccessOrder;
    }

    public void setTotalValueOfNewSuccessOrder(int totalValueOfNewSuccessOrder) {
        this.totalValueOfNewSuccessOrder = totalValueOfNewSuccessOrder;
    }

    public int getTotalNumberOfNewOverdueDebtOrder() {
        return totalNumberOfNewOverdueDebtOrder;
    }

    public void setTotalNumberOfNewOverdueDebtOrder(int totalNumberOfNewOverdueDebtOrder) {
        this.totalNumberOfNewOverdueDebtOrder = totalNumberOfNewOverdueDebtOrder;
    }

    public int getTotalValueOfNewOverdueDebtOrder() {
        return totalValueOfNewOverdueDebtOrder;
    }

    public void setTotalValueOfNewOverdueDebtOrder(int totalValueOfNewOverdueDebtOrder) {
        this.totalValueOfNewOverdueDebtOrder = totalValueOfNewOverdueDebtOrder;
    }

    public int getTotalNumberOfNewIndebtOrder() {
        return totalNumberOfNewIndebtOrder;
    }

    public void setTotalNumberOfNewIndebtOrder(int totalNumberOfNewIndebtOrder) {
        this.totalNumberOfNewIndebtOrder = totalNumberOfNewIndebtOrder;
    }

    public int getTotalValueOfNewIndebtOrder() {
        return totalValueOfNewIndebtOrder;
    }

    public void setTotalValueOfNewIndebtOrder(int totalValueOfNewIndebtOrder) {
        this.totalValueOfNewIndebtOrder = totalValueOfNewIndebtOrder;
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
        return "OrderReport{" +
                "warehouseReportId=" + warehouseReportId +
                ", totalNumberOfNewSuccessOrder=" + totalNumberOfNewSuccessOrder +
                ", totalValueOfNewSuccessOrder=" + totalValueOfNewSuccessOrder +
                ", totalNumberOfNewOverdueDebtOrder=" + totalNumberOfNewOverdueDebtOrder +
                ", totalValueOfNewOverdueDebtOrder=" + totalValueOfNewOverdueDebtOrder +
                ", totalNumberOfNewIndebtOrder=" + totalNumberOfNewIndebtOrder +
                ", totalValueOfNewIndebtOrder=" + totalValueOfNewIndebtOrder +
                ", month=" + month +
                ", year=" + year +
                ", reportType=" + reportType +
                '}';
    }
}
