package com.weison.sbt.junit.runwith;

import com.weison.sbt.service.VerifyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author
 * @since
 */
@RunWith(Parameterized.class)
public class VerifyServiceTest {
    private Integer inputNumber;
    private Boolean expectedResult;
    private VerifyService verifyService;

    @Before
    public void initialize() {
        verifyService = new VerifyService();
    }

    // Each parameter should be placed as an argument here
    // Every time runner triggers, it will pass the arguments
    // from parameters we defined in primeNumbers() method
    public VerifyServiceTest(Integer inputNumber,
                             Boolean expectedResult) {
        this.inputNumber = inputNumber;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        List<Object[]> objects = Arrays.asList(
                new Object[][]{
                        {2, true},
                        {3, false},
                        {4, true},
                        {5, false},
                        {6, true}
                });
        return objects;
    }

    // This test will run 4 times since we have 5 parameters defined
    @Test
    public void testPrimeNumberChecker() {
        System.out.println("Parameterized Number is : " + inputNumber);
        assertEquals(expectedResult,
                verifyService.validate(inputNumber));
    }
}
