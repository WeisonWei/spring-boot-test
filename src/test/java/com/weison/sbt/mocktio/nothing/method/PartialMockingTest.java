package com.weison.sbt.mocktio.nothing.method;

import com.weison.sbt.service.CalculateService;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class PartialMockingTest {
    @Test
    public void whenPrintCalledRealMethodCalled() {
        //mock the tested class
        CalculateService calculateService = mock(CalculateService.class);
        //mock method data when it is be called
        doCallRealMethod().when(calculateService).print(any(Integer.class), any(Integer.class));
        //cass
        calculateService.print(1, 2);
        //verify
        verify(calculateService, times(1)).print(1, 2);
    }
}