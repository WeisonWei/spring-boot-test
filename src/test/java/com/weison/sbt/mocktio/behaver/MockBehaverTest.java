package com.weison.sbt.mocktio.behaver;


import com.weison.sbt.service.CalculateService;
import com.weison.sbt.service.VerifyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class MockBehaverTest {

    @Mock
    VerifyService verifyService;
    @InjectMocks
    CalculateService calculateService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     */ /**
     * 目前看两种写法行为是一样的
     * when(...) thenReturn(...)
     * doReturn(...) when(...)
     */
    @Test
    public void whenThen() {

        when(verifyService.validate(any())).thenReturn(false);
        Integer multiply = calculateService.multiply(1, 2);
        assert (multiply == null);
    }

    @Test
    public void doReturnWhenThen() {
        doReturn(false).when(verifyService).validate(any());
        Integer multiply = calculateService.multiply(1, 2);
        assert (multiply == null);
    }

    @Test(expected = RuntimeException.class)
    public void whenThenWithException() {
        when(calculateService.check(any())).thenThrow(RuntimeException.class);
        calculateService.check(1);
    }

    @Test(expected = RuntimeException.class)
    public void voidWhenThenWithException() {
        CalculateService calculateService = mock(CalculateService.class);
        //void方法没有返回
        doThrow(RuntimeException.class).when(calculateService).checkInteger(any());
        calculateService.checkInteger("1");
    }

    @Test(expected = RuntimeException.class)
    public void whenThenWithManyCalls() {

        when(verifyService.validate(any()))
                .thenReturn(false)//第一次返回
                .thenReturn(true)//第二次返回
                .thenThrow(RuntimeException.class);//第三次抛出异常

        Integer resultFirstCall = calculateService.multiply(1, 2);
        assert (resultFirstCall == null);

        Integer resultSecondCall = calculateService.multiply(1, 2);
        assert (resultSecondCall == 2);

        Integer resultThirdCall = calculateService.multiply(1, 2);
    }

    @Test
    public void callRealName() {
        when(verifyService.validate(any())).thenCallRealMethod();
        Integer multiply = calculateService.multiply(1, 2);
        assert (multiply == 2);
    }

    @Test
    public void returnWithAnswer() {
        doAnswer(invocation -> {
            //有逻辑的返回
            Object arg0 = invocation.getArgument(0);

            if ((Integer) arg0 == 1)
                return true;
            return false;
        }).when(verifyService).validate(anyInt());
        Integer multiply = calculateService.multiply(1, 2);
        assert (multiply == null);
    }
}