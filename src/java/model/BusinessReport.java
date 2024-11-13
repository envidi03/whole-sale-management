package model;

/**
 *
 * @author admin
 */
public class BusinessReport {

    private int id;
    private int month;
    private int year;
    private int totalNumberOfNewSuccessOrder;
    private int totalValueOfNewSuccessOrder;
    private int totalNumberOfNewOverdueDebtOrder;
    private int totalValueOfNewOverdueDebtOrder;
    private int totalNumberOfNewIndebtOrder;
    private int totalValueOfNewIndebtOrder;
    private int totalNumberOfNewContract;
    private int totalValueOfNewContract;
    private int totalNumberOfNewFiredEmployee;

    // Constructor không tham số
    public BusinessReport() {
    }

    // Constructor đầy đủ tham số
    public BusinessReport(int id, int month, int year, int totalNumberOfNewSuccessOrder, int totalValueOfNewSuccessOrder,
                          int totalNumberOfNewOverdueDebtOrder, int totalValueOfNewOverdueDebtOrder,
                          int totalNumberOfNewIndebtOrder, int totalValueOfNewIndebtOrder,
                          int totalNumberOfNewContract, int totalValueOfNewContract,
                          int totalNumberOfNewFiredEmployee) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.totalNumberOfNewSuccessOrder = totalNumberOfNewSuccessOrder;
        this.totalValueOfNewSuccessOrder = totalValueOfNewSuccessOrder;
        this.totalNumberOfNewOverdueDebtOrder = totalNumberOfNewOverdueDebtOrder;
        this.totalValueOfNewOverdueDebtOrder = totalValueOfNewOverdueDebtOrder;
        this.totalNumberOfNewIndebtOrder = totalNumberOfNewIndebtOrder;
        this.totalValueOfNewIndebtOrder = totalValueOfNewIndebtOrder;
        this.totalNumberOfNewContract = totalNumberOfNewContract;
        this.totalValueOfNewContract = totalValueOfNewContract;
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTotalNumberOfNewFiredEmployee() {
        return totalNumberOfNewFiredEmployee;
    }

    public void setTotalNumberOfNewFiredEmployee(int totalNumberOfNewFiredEmployee) {
        this.totalNumberOfNewFiredEmployee = totalNumberOfNewFiredEmployee;
    }

    // Phương thức toString để hiển thị thông tin của đối tượng
    @Override
    public String toString() {
        return "BusinessReport{"
                + "id=" + id
                + ", month=" + month
                + ", year=" + year
                + ", totalNumberOfNewSuccessOrder=" + totalNumberOfNewSuccessOrder
                + ", totalValueOfNewSuccessOrder=" + totalValueOfNewSuccessOrder
                + ", totalNumberOfNewOverdueDebtOrder=" + totalNumberOfNewOverdueDebtOrder
                + ", totalValueOfNewOverdueDebtOrder=" + totalValueOfNewOverdueDebtOrder
                + ", totalNumberOfNewIndebtOrder=" + totalNumberOfNewIndebtOrder
                + ", totalValueOfNewIndebtOrder=" + totalValueOfNewIndebtOrder
                + ", totalNumberOfNewContract=" + totalNumberOfNewContract
                + ", totalValueOfNewContract=" + totalValueOfNewContract
                + ", totalNumberOfNewFiredEmployee=" + totalNumberOfNewFiredEmployee
                + '}';
    }
}
