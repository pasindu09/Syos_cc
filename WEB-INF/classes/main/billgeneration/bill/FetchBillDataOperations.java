package main.billgeneration.bill;

import java.util.List;
import main.billgeneration.bill.FetchedBill;


public interface FetchBillDataOperations {
   List<FetchedBill> getAllBills();
   List<FetchedBill> getAllBillsByDate(String date);
}
