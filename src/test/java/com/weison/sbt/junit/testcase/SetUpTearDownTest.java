package com.weison.sbt.junit.testcase;

import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author
 * @since
 */
public class SetUpTearDownTest extends TestCase {

    protected int value1, value2;

    // assigning the values
    protected void setUp() {
        value1 = 3;
        value2 = 3;
    }

    @Test
    public void testAdd() {
        double result = value1 + value2;
        assertTrue(result == 6);
    }

    /**
     * This method is called after a test is executed.
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() {
        System.out.println("--------------");
    }
}
