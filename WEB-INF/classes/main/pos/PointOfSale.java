package main.pos;

import java.util.*;

import main.billing.Bill;
import main.billing.BillFactory;
import main.billing.BillSerialNumber;
import main.billing.PrintBill;
import main.billing.ReturnBillDateFormat;
import main.database.DatabaseConnection;
import main.pos.cart.Cart;
import main.pos.cart.CartService;
import main.pos.cart.DiscountCalculator;
import main.pos.cart.ReturnCartItems;
import main.pos.cart.ReturnCartItemsService;
import main.pos.ChangeAmount;
import main.pos.PayedAmount;
import main.pos.cart.PaymentDetails;
import main.pos.PaymentProcessor;



public class PointOfSale {
    private PaymentProcessing paymentProcessor;
    private CartService cartService;
    private DiscountCalculator discountCalculator;
    private Cart cart;
    private ReturnCartItemsService returnCartItems;
    private ChangeAmount changeAmount;
    private PayedAmount payedAmount;
    private DatabaseConnection dbConnection;
    private double discount;
    private double subTotalDue;
    private double totalDue;
    private BillFactory billFactory;
    private Date date;

    public PointOfSale(PayedAmount payedAmount, ChangeAmount changeAmount,DiscountCalculator discountCalculator, CartService cartService, Cart cart, DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.cart = cart;
        this.cartService = cartService;
        this.discountCalculator = discountCalculator;
        this.returnCartItems = new ReturnCartItems(cart);
        this.changeAmount = changeAmount;
        this.payedAmount = payedAmount;
        this.paymentProcessor = new PaymentProcessor(cartService, discountCalculator, cart);
        this.billFactory = new BillFactory();
    }

    public Map<String, Object> posTerminal() {     
        List<Object[]> cartContents = returnCartItems.returnItems();
        BillSerialNumber billSerialNumber = new BillSerialNumber(dbConnection);
        ReturnBillDateFormat returnBillDateFormat = new ReturnBillDateFormat(date);
        PaymentProcessing paymentProcessor = new PaymentProcessor(cartService, discountCalculator, cart);
        PaymentDetails paymentDetails = paymentProcessor.handlePaymentProcess();
        discount = paymentDetails.getDiscount();
        subTotalDue = paymentDetails.getSubTotalDue();
        totalDue = paymentDetails.getTotalDue();
        Bill bill = billFactory.createBill(
            payedAmount.getAmountPaid(),
            changeAmount.getamountChanged(),
            discount,
            subTotalDue,
            totalDue,
            billSerialNumber
            );

        PrintBill printBill = new PrintBill(bill, billSerialNumber, returnBillDateFormat, cartContents);

        return printBill.getBillData();


        
    }
}
