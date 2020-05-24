package com.weison.sbt.junit;

import com.weison.sbt.junit.suite.TestSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author
 * @since
 */
public class TestSuiteTest {
    private static final Logger LOG = LoggerFactory.getLogger(TestSuiteTest.class);

    public static void main(String[] args) {
        LOG.info("-- begin --");
        Result result = JUnitCore.runClasses(TestSuite.class);
        Integer i = 1;
        for (Failure failure : result.getFailures()) {
            LOG.info("--" + i + "--" + failure.toString());
            i++;
        }
        LOG.info("-- end --" + result.wasSuccessful());
    }
}
