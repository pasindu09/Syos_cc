package main.billing;

import java.util.List;
import java.util.ArrayList;
import main.billing.BillSerialNumber;



public class Bill {
  private List<Object[]> cartContents;
  private double totalPaid;
  private double change;
  private double subTotal;
  private double total;
  private double discount;
  private int billSerialNumber;




  public Bill( double totalPaid, double change, double discount, double subTotal, double total,BillSerialNumber billSerialNumber) {
    this.totalPaid = totalPaid;
    this.subTotal = subTotal;
    this.change = change;
    this.discount = discount;
    this.total = total;
    this.billSerialNumber = billSerialNumber.getBillSerialNumber();
  }

  


  public int getBillSerialNumber() {
    return billSerialNumber;
  }

  

  public List<Object[]> getCartContents() {
    return cartContents;
  }

  public double getTotalPaid() {
    return totalPaid;
  }

  public double getChange() {
    return change;
  }

  public double getSubTotal() {
    return subTotal;
  }

  public double getTotal() {
    return total;
  }

  public double getDiscount() {
    return discount;
  }

}
