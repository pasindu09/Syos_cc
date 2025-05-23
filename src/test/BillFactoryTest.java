package test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.billing.Bill;
import main.billing.BillFactory;
import main.billing.BillSerialNumber;
import main.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;




public class BillFactoryTest {
    private BillFactory billFactory;

    @Mock
    private BillSerialNumber billSerialNumber;
    @Mock
    private DatabaseConnection connectionInstance;
    @Mock
    private Connection jdbcConnection; 

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        billFactory = new BillFactory();

        when(connectionInstance.connectDatabase()).thenReturn(jdbcConnection);
        
        when(billSerialNumber.getBillSerialNumber()).thenReturn(123456);  

    }

    @Test
    public void testCreateBill() {
        // Arrange
        double totalPaid = 100.0;
        double change = 20.0;
        double discount = 10.0;
        double subTotal = 110.0;
        double total = 90.0;

        // Act
        Bill result = billFactory.createBill(totalPaid, change, discount, subTotal, total, billSerialNumber);

        // Assert
        assertEquals("Total paid should match", totalPaid, result.getTotalPaid(), 0.01);
        assertEquals("Change should match", change, result.getChange(), 0.01);
        assertEquals("Discount should match", discount, result.getDiscount(), 0.01);
        assertEquals("Subtotal should match", subTotal, result.getSubTotal(), 0.01);
        assertEquals("Total should match", total, result.getTotal(), 0.01);
        assertEquals("Serial number should match", 123456, result.getBillSerialNumber()); 
    }
}