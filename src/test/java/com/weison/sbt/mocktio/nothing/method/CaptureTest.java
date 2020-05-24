package com.weison.sbt.mocktio.nothing.method;

import com.weison.sbt.service.CalculateService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class CaptureTest {

    @Test
    public void captureParams() {
        //模拟被测对象
        CalculateService calculateService = mock(CalculateService.class);
        //new 参数捕获对象 ArgumentCaptor
        ArgumentCaptor valueCapture = ArgumentCaptor.forClass(Integer.class);
        //打桩
        doNothing().when(calculateService).print(any(Integer.class), (Integer) valueCapture.capture());

        //call
        calculateService.print(1, 2);

        //验证捕获到的参数是否正确
        assertEquals(2, valueCapture.getValue());
    }

}
