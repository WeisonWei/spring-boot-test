package com.weison.sbt.mocktio.verify;

import com.weison.sbt.service.CalculateService;
import com.weison.sbt.service.VerifyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class VerifyTest {

    @Mock
    VerifyService verifyService;

    @Mock
    CalculateService calculateService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void verifySimple() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        //verify mock的calculateService1执行了print(1,2)方法
        verify(calculateService1).print(1, 2);
    }

    @Test
    public void verifyCallNumber() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        //verify mock的calculateService1执行了1次print(1,2)方法
        verify(calculateService1, times(1)).print(1, 2);
    }

    @Test
    public void verifyNotCall() {
        CalculateService calculateService1 = mock(CalculateService.class);
        //verify mock的calculateService1没有被调用过
        verifyZeroInteractions(calculateService1);
        //等同
        verify(calculateService1, times(0)).print(1, 2);
    }

    @Test
    public void verifyWithUnHoldCall() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        calculateService1.check(1);
        //verify mock的calculateService1执行了1次print(1,2)方法
        verify(calculateService1, times(1)).print(1, 2);
        verifyNoMoreInteractions(calculateService1);
    }

    @Test
    public void verifyMethodCallOrder() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        calculateService1.check(1);
        //verify inOrder 先执行了print(1,2) 再执行了check(1)
        InOrder inOrder = inOrder(calculateService1);
        //可以尝试调换顺序
        inOrder.verify(calculateService1).print(1, 2);
        inOrder.verify(calculateService1).check(1);
    }

    @Test
    public void verifyMethodAtLeastCallTimes() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        calculateService1.print(1, 2);
        calculateService1.print(1, 2);
        //atLeast atMost 的次数
        verify(calculateService1, atLeast(1)).print(1, 2);
        verify(calculateService1, atMost(3)).print(1, 2);
    }

    @Test
    public void verifyMethodWithAnyArgument() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.print(1, 2);
        //anyInt anyString
        verify(calculateService1).print(anyInt(), anyInt());
    }

    /**
     * ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
     * verify(mock).doSomething(argument.capture());
     * assertEquals("John", argument.getValue().getName());
     */
    @Test
    public void verifyMethodCaptureParams() {
        CalculateService calculateService1 = mock(CalculateService.class);
        calculateService1.check(2L);
        //new 参数捕获对象 ArgumentCaptor
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(calculateService1).check(argumentCaptor.capture());
        Long value = argumentCaptor.getValue();
        //验证捕获到的参数是否正确
        assert (2L == value);
    }

    @Test
    public void verifyBehaverIntotal() {
        CalculateService calculateService = mock(CalculateService.class);
        //verify mock的calculateService1没有被调用过
        verifyZeroInteractions(calculateService);
        //等同
        verify(calculateService, times(0)).print(1, 2);

        calculateService.print(1, 2);
        calculateService.check(1);
        //verify mock的calculateService1执行了1次print(1,2)方法
        verify(calculateService, times(1)).print(1, 2);
        verifyNoMoreInteractions(calculateService);

        //verify inOrder 先执行了print(1,2) 再执行了check(1)
        InOrder inOrder = inOrder(calculateService);
        //可以尝试调换顺序
        inOrder.verify(calculateService).print(1, 2);
        inOrder.verify(calculateService).check(1);
    }
}
