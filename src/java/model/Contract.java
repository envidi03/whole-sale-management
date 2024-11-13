package model;

import java.util.Date;

public class Contract {
    private int id;
    private Date contractDeliveryDate;
    private Date actualDeliveryDate;
    private int contractValue;
    private int status;
    private String description;
    private String supplierName;
    private String warehouseName;
    private int warehouseId;
    private int supplierId;
    private int contractReportId;
    
    // Constructor đầy đủ
    public Contract(int id, Date contractDeliveryDate, Date actualDeliveryDate, int contractValue,
                    int status, String description, String supplierName, String warehouseName) {
        this.id = id;
        this.contractDeliveryDate = contractDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.contractValue = contractValue;
        this.status = status;
        this.description = description;
        this.supplierName = supplierName;
        this.warehouseName = warehouseName;
    }

    public Contract(int id, Date contractDeliveryDate, Date actualDeliveryDate,
            int warehouseId, int contractValue, int supplierId, int status, int contractReportId, String  description ) {
        this.id = id;
        this.contractDeliveryDate = contractDeliveryDate;
        this.actualDeliveryDate = actualDeliveryDate;
        this.warehouseId = warehouseId;
        this.contractValue = contractValue;
        this.supplierId = supplierId;
        this.status = status;
        this.contractReportId = contractReportId;
        this.description = description;
    }

    public Contract(int id, Date contractDeliveryDate, int warehouseId, int status, String supplierName) {
        this.id = id;
        this.contractDeliveryDate = contractDeliveryDate;
        this.warehouseId = warehouseId;
        this.status = status;
        this.supplierName = supplierName;
        
    }
    
    
    
    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getContractReportId() {
        return contractReportId;
    }

    public void setContractReportId(int contractReportId) {
        this.contractReportId = contractReportId;
    }
    
    

    // Default constructor
    public Contract() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", contractDeliveryDate=" + contractDeliveryDate +
                ", actualDeliveryDate=" + actualDeliveryDate +
                ", contractValue=" + contractValue +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
