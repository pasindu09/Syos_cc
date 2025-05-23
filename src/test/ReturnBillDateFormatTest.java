package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;

import main.billing.ReturnBillDateFormat;

import java.util.Date;

public class ReturnBillDateFormatTest {

    @Test
    public void testReturnDateFormat() {
        // Arrange
        Date mockDate = mock(Date.class);
        when(mockDate.getTime()).thenReturn(1652985600000L); 

        ReturnBillDateFormat formatter = new ReturnBillDateFormat(mockDate);

        // Act
        String result = formatter.returnDateFormat();

        // Assert
        assertEquals("20/May/2022 12:10 AM", result);
    }
}
