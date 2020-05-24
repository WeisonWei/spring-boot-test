# Java Unit Test
JUnit 是一个 Java 编程语言的单元测试框架。JUnit 在测试驱动的开发方面有很重要的发展，是起源于 JUnit 的一个统称为 xUnit 的单元测试框架之一。「经常与[Mocktio](https://blog.csdn.net/weixx3/article/details/93890555)一起使用」

JUnit 促进了“先测试后编码”的理念，强调建立测试数据的一段代码，可以先测试，然后再应用。
这个方法就好比“测试一点，编码一点，测试一点，编码一点……”，增加了程序员的产量和程序的稳定性，可以减少程序员的压力和花费在排错上的时间。
## 1 JUnit 中的重要的 API
**核心思想**：`通过断言来预言被测试方法是否按预期执行。`
**本文基于**：`JUnit 4.1.2`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190626233432237.jpg)
JUnit中的最重要的程序包是 `junit.framework` 它包含了所有的核心类。
一些重要的类列示如下：


| 序号 | 类的名称 |类的功能|
|--|--|--|
1 |Assert |assert 方法的集合|
2|TestCase |一个定义了运行多重测试的固定装置|
3|TestResult|TestResult 集合了执行测试样例的所有结果|
4 |TestSuite|TestSuite 是测试的集合|

### 1.1 Assert
`Assert`类的重要方法列式如下：
序号 |	方法|描述|
|--|--|--|
1 	|void assertEquals(boolean expected, boolean actual) |检查两个变量或者等式是否平衡|
2 |	void assertFalse(boolean condition)|检查条件是假的|
3 |	void assertNotNull(Object object)|检查对象不是空的|
4 |	void assertNull(Object object)|检查对象是空的|
5 |	void assertTrue(boolean condition)|检查条件为真|
6 |	void fail()|在没有报告的情况下使测试不通过|

`Assert`代码：
```java
public class AssertTest {
    @Test
    public void testAssertions() {
        //test data
        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = null;
        String str4 = "abc";
        String str5 = "abc";
        int val1 = 5;
        int val2 = 6;
        String[] expectedArray = {"one", "two", "three"};
        String[] resultArray = {"one", "two", "three"};

        //Check that two objects are equal
        assertEquals(str1, str2);

        //Check that a condition is true
        assertTrue(val1 < val2);

        //Check that a condition is false
        assertFalse(val1 > val2);

        //Check that an object isn't null
        assertNotNull(str1);

        //Check that an object is null
        assertNull(str3);

        //Check if two object references point to the same object
        assertSame(str4, str5);

        //Check if two object references not point to the same object
        assertNotSame(str1, str3);

        //Check whether two arrays are equal to each other.
        assertArrayEquals(expectedArray, resultArray);
    }

}
```


### 1.2  注解
Junit 中的注解的列表以及他们的含义：
序号|注解|描述
|--|--|--|
1 	| @Test |这个注解说明依附在 JUnit 的 public void 方法可以作为一个测试案例。|
2   |@Before |有些测试在运行前需要创造几个相似的对象，在 public void 方法加该注释是因为该方法需要在 test 方法前运行。|
3 	| @After |如果你将外部资源在 Before 方法中分配，那么你需要在测试运行后释放他们。在 public void 方法加该注释是因为该方法需要在 test 方法后运行。|
4 	| @BeforeClass |在 public void 方法加该注释是因为该方法需要在类中所有方法前运行。
5 	| @AfterClass |它将会使方法在所有测试结束后执行，这个可以用来进行清理活动。
6 	| @Ignore |这个注释是用来忽略有关不需要执行的测试的，Junit 在类级别上使用 @Ignore 来忽略所有的测试用例。
7   | @Test(timeout=1000) |测试用例执行时长大于指定的毫秒数，那么将自动被标记为失败。
8   | @Test(except=RuntimeException.class) |测试用例抛出RuntimeException则pass。
9   | @RunWith(Parameterized.class) |注入参数|
#### 1.2.1 @Before @After
使用`@Before`,`@After`后`JUnite`执行过程：

- 1 beforeClass() 方法首先执行，并且只执行一次。
- 2 afterClass() 方法最后执行，并且只执行一次。
- 3 before() 方法针对每一个测试用例执行，但是是在执行测试用例之前。
- 4 after() 方法针对每一个测试用例执行，但是是在执行测试用例之后。
- 5 在 before() 方法和 after() 方法之间，执行每一个测试用例。

代码如下：
```java

public class JunitAnnotation {
    //execute before class
    @BeforeClass
    public static void beforeClass() {
        System.out.println("in before class");
    }

    //execute after class
    @AfterClass
    public static void afterClass() {
        System.out.println("in after class");
    }

    //execute before test
    @Before
    public void before() {
        System.out.println("in before");
    }

    //execute after test
    @After
    public void after() {
        System.out.println("in after");
    }

    //test case
    @Test
    public void test() {
        System.out.println("in test");
    }

    //test case ignore and will not execute
    @Ignore
    public void ignoreTest() {
        System.out.println("in ignore test");
    }
}
```
#### 1.2.2 @Test expected & timeout
- 1 被测试类
```java
public class CalculateService {

    public Integer plus(Integer a, Integer b) {
        return a + b;
    }

    public Integer subtract(Object a, Integer b) {
        return (Integer) check(a) - b;
    }

    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    public Object check(Object a) {
        if (a instanceof Integer) {
            System.out.println("It is Integer ~");
            return a;
        } else
            throw new RuntimeException("It is not a Integer~");
    }
}
```

- 2 测试类

```java
public class CaculateServiceTest {
    private CaculateService caculateService = new CaculateService();

    @Test
    public void plus() {
        //check for equality
        assertEquals(new Integer(3), caculateService.plus(1, 2));

    }

    @Test(expected = RuntimeException.class)
    public void subtractException() {
        //check for equality
        assertEquals(new Integer(2), caculateService.subtract("4", 2));
    }

    @Test(timeout = 100)
    public void plusTimeout() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
        }
        //check for equality
        assertEquals(new Integer(3), caculateService.plus(1, 2));
    }
}
```
#### 1.2.3 @RunWith 参数化测试
Junit 4 引入了一个新的功能参数化测试。
参数化测试允许开发人员使用不同的值反复运行同一个测试。

你将遵循 5 个步骤来创建参数化测试。
- 1 用 @RunWith(Parameterized.class) 来注释 test 类。
- 2 创建一个由 @Parameters 注释的公共的静态方法，它返回一个对象的集合(数组)来作为测试数据集合。
- 3 创建一个公共的构造函数，它接受和一行测试数据相等同的东西。
- 4 为每一列测试数据创建一个实例变量。
- 5 用实例变量作为测试数据的来源来创建你的测试用例。

一旦每一行数据出现测试用例将被调用，代码如下：

被测试类：用来求`余数`
```java
public class RemainderService {
    public Boolean validate(final Integer primeNumber) {
        if (primeNumber % 2 == 0)
            return true;
        return false;
    }
}
```
测试类：
```java
@RunWith(Parameterized.class)
public class RemainderServiceTest {
    private Integer inputNumber;
    private Boolean expectedResult;
    private RemainderService remainderService;

    @Before
    public void initialize() {
        remainderService = new RemainderService();
    }

    // Each parameter should be placed as an argument here
    // Every time runner triggers, it will pass the arguments
    // from parameters we defined in primeNumbers() method
    public RemainderServiceTest(Integer inputNumber,
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
                remainderService.validate(inputNumber));
    }
}
```
执行结果：
```java
Testing started at 23:58 ...
23:58:15: Executing tasks ':cleanTest :test --tests "com.wxx.st.runwith.RemainderServiceTest.testPrimeNumberChecker[*]"'...

> Task :cleanTest
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :compileTestJava
> Task :processTestResources NO-SOURCE
> Task :testClasses
> Task :test
Parameterized Number is : 2
Parameterized Number is : 3
Parameterized Number is : 4
Parameterized Number is : 5
Parameterized Number is : 6
BUILD SUCCESSFUL in 1s
4 actionable tasks: 4 executed
23:58:17: Tasks execution finished ':cleanTest :test --tests "com.wxx.st.runwith.RemainderServiceTest.testPrimeNumberChecker[*]"'.

```
### 1.3 套件测试
捆绑几个单元测试用例并且一起执行他们。
在 JUnit 中，@RunWith 和 @Suite 注释用来运行套件测试。

代码Test1 ：
```java
public class Test1 {
    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
        System.out.println("Inside testPrintMessage()");
        assertEquals(message, messageUtil.printMessage());
    }
}
```

```java
public class Test2 {
    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message,messageUtil.salutationMessage());
    }
}
```

代码Test2 ：
```java
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Test1.class, Test2.class, CaculateServiceTest.class, AssertTest.class, SetUpTearDownTest.class
})
public class TestSuite {
}
```
捆绑多个单元测试，并打印成功和失败的案例 ：

```java
public class TestSuiteTest {
    public static void main(String[] args) {
        System.out.println("-- 开始 --");
        Result result = JUnitCore.runClasses(TestSuite.class);
        Integer i = 1;
        for (Failure failure : result.getFailures()) {
            System.out.println("--" + i + "--" + failure.toString());
            i++;
        }
        System.out.println("-- 结束 --" + result.wasSuccessful());
    }
}
```

### 1.4 TestCase类
`TestCase` 类的一些重要方法列式如下：
序号 |	方法|描述|
|--|--|--|
1 |	int countTestCases()|为被run(TestResult result) 执行的测试案例计数|
2 |	TestResult createResult()|创建一个默认的 TestResult 对象|
3 |	String getName()|获取 TestCase 的名称|
4 | TestResult run()|一个运行这个测试的方便的方法，收集由TestResult 对象产生的结果|
5 |	void run(TestResult result)|在 TestResult 中运行测试案例并收集结果|
6 |	void setName(String name)|设置 TestCase 的名称|
7 |	void setUp()|创建固定装置，例如，打开一个网络连接|
8 |	void tearDown()|拆除固定装置，例如，关闭一个网络连接|
9 |	String toString()|返回测试案例的一个字符串表示|

`TestCase `代码：
```java
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
```

代码地址：[链接](https://github.com/WeisonWei/java-test)
