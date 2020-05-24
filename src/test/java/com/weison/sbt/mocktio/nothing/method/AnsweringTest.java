package com.weison.sbt.mocktio.nothing.method;

import com.weison.sbt.service.CalculateService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 *
 * @author
 * @since
 */
public class AnsweringTest {
    @Test
    public void whenPrintCalledAnswered() {
        //模拟被测对象
        CalculateService calculateService = mock(CalculateService.class);
        //使用自定义的Answer来打桩
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(3, arg0);
            assertEquals(4, arg1);
            return null;
        }).when(calculateService).print(any(Integer.class), any(Integer.class));
        //call
        calculateService.print(3, 4);
    }
}

