package com.weison.sbt.mocktio;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 *
 * @author
 * @since
 */
public class OverviewTest {

    @Test
    public void firstTest() {

        //模拟被测试对象
        List mockList = mock(List.class);
        //使用模拟对象，它不会引发任何“意外交互”异常。
        mockList.add(1);
        mockList.clear();
        // 在实际执行之前，可定义返回的桩数据。
        when(mockList.get(0)).thenReturn("first");

        //支持多种验证 -- 验证添加行为
        verify(mockList).add(1);
        //验证清除行为
        verify(mockList).clear();

        // 将打印 "first"
        System.out.println(mockList.get(0));
        // 将打印 "null" ，因为 get(999) 没有定义桩数据
        System.out.println(mockList.get(999));
    }
}

