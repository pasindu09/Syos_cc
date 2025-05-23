package main.billing;
import main.billing.Bill;
import main.billing.BillSerialNumber;

public class BillFactory {

  public Bill createBill( double totalPaid, double change, double discount, double subTotal, double total,BillSerialNumber billSerialNumber) {
    return new Bill( totalPaid, change, discount, subTotal, total, billSerialNumber);
  }
  
}
