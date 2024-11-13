package model;

/**
 * Class to represent an item in the shopping cart, which includes a consignment and a quantity.
 */
public class CartItem {

    private Consignment consignment;

    // Constructor
    public CartItem(Consignment consignment) {
        this.consignment = consignment;
    }

    // Getters and setters
    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment(Consignment consignment) {
        this.consignment = consignment;
    }


}
