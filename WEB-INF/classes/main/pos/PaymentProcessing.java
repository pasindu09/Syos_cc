package main.pos;
import main.pos.cart.PaymentDetails;

public interface PaymentProcessing {
    public PaymentDetails handlePaymentProcess();
}
