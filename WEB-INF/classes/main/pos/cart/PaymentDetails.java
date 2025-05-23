package main.pos.cart;

public class PaymentDetails {
    private double subTotalDue;
    private double totalDue;
    private double discount;

    public PaymentDetails(double subTotalDue, double totalDue, double discount) {
        this.subTotalDue = subTotalDue;
        this.totalDue = totalDue;
        this.discount = discount;
    }

    // Getters
    public double getSubTotalDue() {
        return subTotalDue;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Subtotal: " + subTotalDue + ", Total: " + totalDue + ", Discount: " + discount;
    }
}
