package main.billgeneration.bill;

import java.util.List;
import main.billgeneration.bill.FetchedBill;


public class BillProcessData implements ProcessData {
    private List<FetchedBill> bills;

    public BillProcessData(List<FetchedBill> bills) {
        this.bills = bills;
    }

    @Override
    public String processData() {
        StringBuilder reportBuilder = new StringBuilder();
        
        reportBuilder.append("\n\n");
        reportBuilder.append("          ----------- BILL REPORT( CUSTOMER TRANSACTIONS ) ---------");
        reportBuilder.append("\n\n");

        int counter = 1; // Start numbering from 1
        for (FetchedBill bill : bills) {
            reportBuilder.append(counter).append(". ").append(bill).append("\n");
            counter++; // Increment the counter for the next bill
        }
        
        reportBuilder.append("\n\n");
        
        // Return the complete report as a string
        return reportBuilder.toString();
    }
}
