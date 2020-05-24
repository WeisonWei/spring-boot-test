package com.weison.sbt.junit.annotation;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * @since
 */
public class JunitAnnotationTest {
    private static final Logger LOG = LoggerFactory.getLogger(JunitAnnotationTest.class);

    //execute before class
    @BeforeClass
    public static void beforeClass() {
        LOG.info("in before class");
    }

    //execute after class
    @AfterClass
    public static void afterClass() {
        LOG.info("in after class");
    }

    //execute before test
    @Before
    public void before() {
        LOG.info("in before");
    }

    //execute after test
    @After
    public void after() {
        LOG.info("in after");
    }

    //test case
    @Test
    public void test() {
        System.out.println("in test");
    }

    //test case ignore and will not execute
    @Ignore
    public void ignoreTest() {
        LOG.info("in ignore test");
    }
}

