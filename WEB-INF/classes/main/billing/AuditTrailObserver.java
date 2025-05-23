package main.billing;
import java.time.LocalDateTime; 
import java.util.Arrays;

public class AuditTrailObserver implements BillObserver {
    @Override
    public void update(Bill bill) {
     // Prepare the log message
    //  StringBuilder logMessage = new StringBuilder();
    //  logMessage.append("Audit Record - ");
    //  logMessage.append("Date/Time: ").append(LocalDateTime.now()).append("; ");
    //  logMessage.append("Serial Number: ").append(bill.getBillSerialNumber()).append("; ");
    //  logMessage.append("Subtotal: ").append(bill.getSubTotal()).append("; ");
    //  logMessage.append("Total: ").append(bill.getTotal()).append("; ");
    //  logMessage.append("Paid: ").append(bill.getTotalPaid()).append("; ");
    //  logMessage.append("Change: ").append(bill.getChange()).append("; ");
    //  logMessage.append("Discount: ").append(bill.getDiscount()).append("; ");

    //  logMessage.append("Cart Contents: ");
    //  bill.getCartContents().forEach(item -> logMessage.append(Arrays.toString(item)).append(", "));

    //  System.out.println(logMessage.toString()); 
    }
  }
