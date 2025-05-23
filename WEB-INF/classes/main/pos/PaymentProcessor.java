package main.pos;

import main.pos.cart.Cart;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;
import main.pos.cart.PaymentDetails;
import main.pos.PaymentProcessing;

public class PaymentProcessor implements PaymentProcessing{

    private CartService cartService;
    private DiscountCalculator discountCalculator;
    private Cart cart;

    public PaymentProcessor(CartService cartService, DiscountCalculator discountCalculator, Cart cart) {
        this.cartService = cartService;
        this.discountCalculator = discountCalculator;
        this.cart = cart;
    }
    
    @Override
    public PaymentDetails handlePaymentProcess() {
        double subTotalDue = cartService.calculateSubTotal();
        double totalDue = cartService.calculateTotal();
        double discount = discountCalculator.calculateDiscount(cart);

        return new PaymentDetails(subTotalDue, totalDue, discount);       
       
    }
}
