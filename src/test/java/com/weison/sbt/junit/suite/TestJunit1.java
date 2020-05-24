package com.weison.sbt.junit.suite;

import com.weison.sbt.testcase.MessageUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author
 * @since
 */
public class TestJunit1 {
    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
        System.out.println("Inside testPrintMessage()");
        assertEquals(message, messageUtil.printMessage());
    }
}
