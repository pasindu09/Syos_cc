package test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import main.billing.Bill;
import main.billing.BillObserver;
import main.billing.BillSerialNumber;
import main.billing.PrintBill;
import main.billing.ReturnBillDateFormat;

import org.junit.After;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PrintBillTest {
    private PrintBill printBill;
    private Bill billMock;
    private BillSerialNumber billSerialNumberMock;
    private ReturnBillDateFormat returnBillDateFormatMock;
    private List<Object[]> cartContentsMock;
    private BillObserver observerMock;

    @Before
    public void setUp() {
        billMock = mock(Bill.class);
        billSerialNumberMock = mock(BillSerialNumber.class);
        returnBillDateFormatMock = mock(ReturnBillDateFormat.class);
        cartContentsMock = new ArrayList<>();
        observerMock = mock(BillObserver.class);

        printBill = new PrintBill(billMock, billSerialNumberMock, returnBillDateFormatMock, cartContentsMock);
        printBill.addObserver(observerMock);
    }

    @Test
    public void testPrintBillReceiptCalls() {
        // Act
        printBill.printBillReceipt();

        // Verify that all the methods were called as expected
        verify(billSerialNumberMock, times(1)).getBillSerialNumber();
        // verify(returnBillDateFormatMock, times(1)).returnDateFormat();
        verify(billMock, times(1)).getSubTotal();
        verify(billMock, times(1)).getDiscount();
        verify(billMock, times(1)).getTotal();
        verify(billMock, times(1)).getTotalPaid();
        verify(billMock, times(1)).getChange();
    }

    @Test
    public void testNotifyObserversOnPrint() {
        printBill.printBillReceipt();

        verify(observerMock, times(1)).update(billMock);
    }
}
