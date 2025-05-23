package test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import main.pos.ChangeAmount;
import main.pos.PayedAmount;
import main.pos.PaymentProcessor;
import main.pos.cart.Cart;
import main.pos.cart.CartDisplayService;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;

import java.util.Scanner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentProcessorTest {

    @Mock private CartDisplayService displayService;
    @Mock private Scanner scanner;
    @Mock private CartService cartService;
    @Mock private DiscountCalculator discountCalculator;
    @Mock private Cart cart;
    @Mock private PayedAmount payedAmount;
    @Mock private ChangeAmount changeAmount;

    private PaymentProcessor paymentProcessor;

    @Before
    public void setUp() {
        when(cartService.calculateSubTotal()).thenReturn(100.0);
        when(cartService.calculateTotal()).thenReturn(90.0);
        when(discountCalculator.calculateDiscount(cart)).thenReturn(10.0);

        paymentProcessor = new PaymentProcessor(displayService, scanner, cartService, discountCalculator, cart, payedAmount, changeAmount);
    }

    @Test
    public void testHandlePaymentProcess_WithSufficientAmount() {
        when(scanner.hasNextDouble()).thenReturn(true);
        when(scanner.nextDouble()).thenReturn(100.0);

        paymentProcessor.handlePaymentProcess();

        verify(payedAmount).setAmountPaid(100.0);
        verify(changeAmount).setChangeAmount(10.0);
        verify(displayService).displayCart();
        verify(scanner).nextLine(); // Ensures we read the full line after capturing double
    }

    @Test
public void testHandlePaymentProcess_WithInsufficientAmount() {
    when(scanner.hasNextDouble()).thenReturn(true).thenReturn(true);
    when(scanner.nextDouble()).thenReturn(80.0).thenReturn(90.0); // Insufficient followed by exact payment

    paymentProcessor.handlePaymentProcess();

    verify(payedAmount, never()).setAmountPaid(80.0); // Verifying that insufficient payment wasn't mistakenly accepted
    verify(changeAmount, never()).setChangeAmount(10.0); // No change should be set on insufficient payment
    verify(scanner, times(2)).nextLine(); // Ensures we read the full line after capturing double

    // When correct payment is made
    verify(payedAmount).setAmountPaid(90.0);
    verify(changeAmount).setChangeAmount(0.0); // No change due for exact payment
}
}
