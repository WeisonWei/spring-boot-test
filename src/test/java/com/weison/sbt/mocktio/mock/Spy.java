package com.weison.sbt.mocktio.mock;

import com.weison.sbt.service.VerifyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * mock - mock是从根据被测类生成了一个派生类，用于跟踪与它的交互。
 * spy - spy是包装现有实例，它仍将以与普通实例相同的方式运行。
 * 唯一的区别是spy还将被检测以跟踪与它的所有交互。
 */
public class Spy {
    @Mock
    VerifyService verifyService;

    @org.mockito.Spy
    List<String> spyList = new ArrayList<String>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void spy1() {
        List<String> list = new ArrayList<String>();
        List<String> spyList1 = spy(list);

        spyList1.add("1");
        spyList1.add("2");

        verify(spyList1).add("1");
        verify(spyList1).add("2");

        assertEquals(2, spyList1.size());
    }

    @Test
    public void spy2() {
        List<String> list = new ArrayList<String>();
        List<String> spyList = spy(list);

        spyList.add("1");
        spyList.add("2");

        verify(spyList).add("1");
        verify(spyList).add("2");

        assertEquals(2, spyList.size());
    }

    @Test
    public void whenstubaspyThenstubbed() {
        List<String> list = new ArrayList<String>();
        List<String> spy = spy(list);
        assertEquals(0, spy.size());
        //spy新增了一个数据，真实的list并没有新增
        spyList.add("1");
        Mockito.doReturn(100).when(spy).size();
        assertEquals(100, spy.size());
        assertEquals(0, list.size());
    }
}