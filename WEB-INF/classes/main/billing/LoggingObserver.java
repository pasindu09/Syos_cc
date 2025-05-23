package main.billing;

public class LoggingObserver implements BillObserver {
    @Override
    public void update(Bill bill) {
      System.out.println("Bill created with Serial Number: " + bill.getBillSerialNumber());
    }
  }