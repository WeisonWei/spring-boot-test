package com.weison.sbt.mocktio.values.method;

import com.weison.sbt.service.CalculateService;
import com.weison.sbt.service.VerifyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 *
 * @author
 * @since
 */ //@RunWith(MockitoJUnitRunner.class)
public class MockingVerifying {
    //CalculateService是被测试类
    //RemainderService是它的一个属性（依赖）

    @Mock
    VerifyService verifyService;

    @InjectMocks
    CalculateService calculateService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenPrintCalledVerfied() {
        //模拟被测试对象
        //打桩
        //doNothing() is Mockito’s default behavior for void methods
        when(verifyService.validate(any())).thenReturn(true);

        //call
        calculateService.multiply(0, 1);

        /**
         *     verify(mock).someMethod();
         *     verify(mock, times(10)).someMethod();
         *     verify(mock, atLeastOnce()).someMethod();
         */

        //验证
        verify(calculateService, times(1)).multiply(0, 1);
    }
}