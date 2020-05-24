package com.weison.sbt.junit.service;

import com.weison.sbt.service.CalculateService;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author
 * @since
 */
public class CalculateServiceTest {
    private CalculateService calculateService = new CalculateService();

    @Test
    public void plus() {

        //check for equality
        assertEquals(new Integer(3), calculateService.plus(1, 2));

    }

    @Test(expected = RuntimeException.class)
    public void subtractException() {
        //check for equality
        assertEquals(new Integer(2), calculateService.subtract("4", 2));
    }

    @Test(timeout = 100)
    public void plusTimeout() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        //check for equality
        assertEquals(new Integer(3), calculateService.plus(1, 2));
    }
}

