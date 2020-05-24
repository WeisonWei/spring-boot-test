package com.weison.sbt.junit.suite;

import com.weison.sbt.junit.ass.AssertTest;
import com.weison.sbt.junit.runwith.VerifyServiceTest;
import com.weison.sbt.junit.service.CalculateServiceTest;
import com.weison.sbt.junit.testcase.SetUpTearDownTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author
 * @since
 */
//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestJunit1.class, TestJunit2.class, CalculateServiceTest.class, AssertTest.class,
        SetUpTearDownTest.class, VerifyServiceTest.class
})
public class TestSuite {
}

