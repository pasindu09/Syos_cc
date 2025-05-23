package main.billing;

public interface BillRepositoryOperations<T> {
    void createBill(T entity);
    int returnBillSerialNumber();
}