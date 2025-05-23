package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import main.billing.BillSerialNumber;
import main.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class BillSerialNumberTest {
    @Mock
    private DatabaseConnection mockConnection;
    @Mock
    private Connection mockJdbcConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private AutoCloseable closeable;
    private BillSerialNumber billSerialNumber;

    @Before
    public void setUp() throws SQLException {
        closeable = MockitoAnnotations.openMocks(this);
        when(mockConnection.connectDatabase()).thenReturn(mockJdbcConnection);

        when(mockJdbcConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);  
        when(mockResultSet.getInt(anyInt())).thenReturn(1);  
        billSerialNumber = new BillSerialNumber(mockConnection);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetBillSerialNumber() {
        // Act
        int serialNumber = billSerialNumber.getBillSerialNumber();

        // Assert
        assertEquals(1, serialNumber);
    }
}