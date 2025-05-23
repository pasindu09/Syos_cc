package main.billing;

import main.database.DatabaseConnection;
import main.billing.BillRepository;


public class BillSerialNumber {
    private DatabaseConnection connectionInstance;

    public BillSerialNumber(DatabaseConnection connectionInstance) {
        this.connectionInstance = connectionInstance;
    }

    public int getBillSerialNumber() {
        BillRepository billRepository = new BillRepository(connectionInstance);
        int billSerialnumber = billRepository.returnBillSerialNumber();
        return billSerialnumber;
    }
}
