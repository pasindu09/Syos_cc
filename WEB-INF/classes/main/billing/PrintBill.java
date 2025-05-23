package main.billing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.billing.Bill;
import main.billing.BillSerialNumber;
import main.billing.ReturnBillDateFormat;


public class PrintBill {
    private Bill bill;
    private BillSerialNumber billSerialNumber;
    private ReturnBillDateFormat returnBillDateFormat;
    private List<Object[]> cartContents;
    LocalDate date = LocalDate.now();
    String today = date.toString();

    public PrintBill(Bill bill, BillSerialNumber billSerialNumber, ReturnBillDateFormat returnBillDateFormat, List<Object[]> cartContents) {
        this.bill = bill;
        this.billSerialNumber = billSerialNumber;
        this.returnBillDateFormat = returnBillDateFormat;
        this.cartContents = cartContents;
    }

    // Method to get the bill details in a structured format (for servlet or other uses)
    public Map<String, Object> getBillData() {
        Map<String, Object> billData = new HashMap<>();

        // Add general bill information
        billData.put("storeName", "Synex Outlet Store (SYOS)");
        billData.put("storeLocation", "Colombo 05");
        billData.put("phone", "011-1234567");
        billData.put("email", "SYOS@gmail.com");
        billData.put("serialNumber", billSerialNumber.getBillSerialNumber());
        billData.put("date", today);

        // Add cart items
        List<Map<String, Object>> items = new ArrayList<>();
        for (Object[] item : cartContents) {
            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("no", (int) item[0]);
            cartItem.put("description", (String) item[1]);
            cartItem.put("quantity", (int) item[2]);
            cartItem.put("price", (double) item[3]);
            items.add(cartItem);
        }
        billData.put("cartContents", items);

        // Add billing details
        billData.put("subTotal", bill.getSubTotal());
        billData.put("tax", 0); // Assuming no tax
        billData.put("discount", bill.getDiscount());
        billData.put("total", bill.getTotal());
        billData.put("totalPaid", bill.getTotalPaid());
        billData.put("change", bill.getChange());

        return billData;
        //notifier comes here
    }

   
}
