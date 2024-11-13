package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class ContractReport {
    private int warehouseReportId;
    private int totalNumberOfNewContract;
    private int totalValueOfNewContract;
    
    // Các thuộc tính từ bảng warehouse_report
    private int month;
    private int year;
    private int reportType;

     // Các thuộc tính bổ sung từ bảng contract và các bảng liên kết
    private int contractId;
    private Date contractDeliveryDate;
    private Date actualDeliveryDate;
    private int contractValue;
    private int contractStatus;
    private String description;
    private String supplierName;
    private String warehouseName;
    
    public ContractReport(int warehouseReportId, int totalNumberOfNewContract, int totalValueOfNewContract, int month, int year, int reportType) {
        this.warehouseReportId = warehouseReportId;
        this.totalNumberOfNewContract = totalNumberOfNewContract;
        this.totalValueOfNewContract = totalValueOfNewContract;
        this.month = month;
        this.year = year;
        this.reportType = reportType;
    }

    // Constructor đầy đủ
    public ContractReport(int warehouseReportId, int totalNumberOfNewContract, int totalValueOfNewContract,
                          int month, int year, int reportType, int contractId, Date contractDeliveryDate,
                          Date actualDeliveryDate, int contractValue, int contractStatus, String description,
                          String supplierName, String warehouseName) {
        this.warehouseReportId = warehouseReportId;
        this.totalNumberOfNewContract = totalNumberOfNewContract;
        this.totalValueOfNewContract = totalValueOfNewContract;
        this.month = month;
        this.year = year;
        this.reportType = reportType;
        this.contractId = contractId;
        this.contractDeliveryDate = contractDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.contractValue = contractValue;
        this.contractStatus = contractStatus;
        this.description = description;
        this.supplierName = supplierName;
        this.warehouseName = warehouseName;
    }
    
    // Default constructor
    public ContractReport() {
    }

     // Getters and Setters
    public int getWarehouseReportId() {
        return warehouseReportId;
    }

    public void setWarehouseReportId(int warehouseReportId) {
        this.warehouseReportId = warehouseReportId;
    }

    public int getTotalNumberOfNewContract() {
        return totalNumberOfNewContract;
    }

    public void setTotalNumberOfNewContract(int totalNumberOfNewContract) {
        this.totalNumberOfNewContract = totalNumberOfNewContract;
    }

    public int getTotalValueOfNewContract() {
        return totalValueOfNewContract;
    }

    public void setTotalValueOfNewContract(int totalValueOfNewContract) {
        this.totalValueOfNewContract = totalValueOfNewContract;
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

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Date getContractDeliveryDate() {
        return contractDeliveryDate;
    }

    public void setContractDeliveryDate(Date contractDeliveryDate) {
        this.contractDeliveryDate = contractDeliveryDate;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public int getContractValue() {
        return contractValue;
    }

    public void setContractValue(int contractValue) {
        this.contractValue = contractValue;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    // toString method
    @Override
    public String toString() {
        return "ContractReport{" +
                "warehouseReportId=" + warehouseReportId +
                ", totalNumberOfNewContract=" + totalNumberOfNewContract +
                ", totalValueOfNewContract=" + totalValueOfNewContract +
                ", month=" + month +
                ", year=" + year +
                ", reportType=" + reportType +
                ", contractId=" + contractId +
                ", contractDeliveryDate=" + contractDeliveryDate +
                ", actualDeliveryDate=" + actualDeliveryDate +
                ", contractValue=" + contractValue +
                ", contractStatus=" + contractStatus +
                ", description='" + description + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
