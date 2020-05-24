package com.weison.sbt.mocktio.nothing.method;

import com.weison.sbt.service.CalculateService;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class MockingVerifying {

    @Test
    public void whenPrintCalledVerfied() {
        //模拟被测试对象
        CalculateService calculateService = mock(CalculateService.class);
        //打桩
        //doNothing() is Mockito’s default behavior for void methods
        doNothing().when(calculateService).print(isA(Integer.class), isA(Integer.class));

        //call
        calculateService.print(0, 1);

        //验证
        verify(calculateService, times(1)).print(0, 1);
    }

    @Test
    public void whenPrintCalledVerfiedWithException() {
        //模拟被测试对象
        CalculateService calculateService = mock(CalculateService.class);
        RuntimeException runtimeException = new RuntimeException("111");
        //打桩
        when(calculateService.check(any(String.class))).thenThrow(RuntimeException.class);
        doThrow(runtimeException).when(calculateService).check(isA(String.class));
        //call
        calculateService.check("123");

        //验证
        //verify(calculateService, times(1)).print(0, 1);
        verify(calculateService);
    }
}
