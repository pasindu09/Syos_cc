package main.billing;

public interface BillObserver {
    void update(Bill bill);
}
