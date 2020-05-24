# Java Unit Test - Mocktio
Mockito是专门用于有效编写某种测试的框架，是Java的最佳模拟框架，经常与[JUnit](https://blog.csdn.net/weixx3/article/details/93767257)一起使用，用来模拟构造需要测试的对象，解决对象间的依赖关系。

在下面的情形，可能需要使用模拟对象来代替真实对象：
>真实对象的行为是不确定的（例如，当前的时间或当前的温度）；
真实对象很难搭建起来；
真实对象的行为很难触发（例如，网络错误）；
真实对象速度很慢（例如，一个完整的数据库，在测试之前可能需要初始化）；
真实的对象是用户界面，或包括用户界面在内；
真实的对象使用了回调机制；
真实对象可能还不存在；
真实对象可能包含不能用作测试（而不是为实际工作）的信息和方法。

`单元测试三部曲` --> [链接🔗](https://blog.csdn.net/weixx3/article/details/96341218)
## 1 环境信息
JDK : 8
Junit : 4.12
Mocktio : 2.28.2
## 2 使用方法
模拟被测试对象 -->  对非关键方法进行打桩 -->  验证被测对象行为或结果是否符合预期

示例：
```java
	//模拟被测试对象
	List mockList = mock(List.class);
	//打桩：当要获取mockList长度时，返回20
	when(mockList.size()).thenReturn(20)
	//验证：有两次行为，结果是一个int
	verify(mockList, times(2)).get(anyInt())
```

## 3 原理
### 3.1 生成模拟对象的原理
根据被测对象类型生成一个继承这个类型的类 --> 实例化生成的类- -> 得到mock对象
### 3.2 打桩的原理
- 1 打桩完毕会生成一个Answer对象，存放到一个链表里。后面调用对应的方法的时候，就会从这个链表内找到对应的Answer对象，从中获取对应的值返回。
- 2 对2.1中生成的派生类设置hook回调方法，方法的返回值及打桩时给的值，所有的方法调用最终都会交由`MockHandlerImpl.handle`。
- 3 Mockito会假定每个方法调用需要被打桩，生成一个和这个方法调用相对应的OngoingStubbing对象，将这个对象暂时存起来。
当when方法执行的时候，就会取出这个暂存的OngoingStubbing对象返回，这样我们就能在这上面打桩（调用thenReturn等方法），返回我们需要的值了。
```java
	when(verifyService.validate(any())).thenReturn(true);
	doNothing().when(calculateService).print(isA(Integer.class), isA(Integer.class));
```
### 3.2 验证行为结果的原理
分阶段分步骤的执行：
- 1 Mockito会记录下要验证的对象，以及要验证的参数。
- 2 在执行方法调用的时候，取出要验证的对象、验证的参数并执行验证。
```java
    verify(mock).someMethod();
    verify(mock, times(10)).someMethod();
    verify(mock, atLeastOnce()).someMethod();
```
## 4 项目搭建
- 1 maven添加依赖：
```shell
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>2.28.2</version>
    <scope>test</scope>
</dependency>
```
- 2 gradle添加依赖：
```shell
dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
    testCompile 'org.mockito:mockito-core:2.28.2'
}
```

## 5  用法
### 5.1 Mocktio注解
#### 5.1.1 常用注解
- 1 @Mock：创建模拟
    - 1.1 可选地指定它应该如何表现Answer/MockSettings
    - 1.2 when()/ given()指定模拟应该如何表现
    - 1.3 如果提供的答案不符合您的需求，请自行编写一个扩展Answer界面
- 2 @Spy：部分模拟，调用实际方法但仍可以验证和存根
- 3 @InjectMocks：自动注入用@Spy或注释的模拟/间谍字段@Mock
- 4 verify()：检查使用给定参数调用的方法
    - 4.1 可以使用灵活的参数匹配，例如通过的任何表达式 any()
    - 4.2 或者使用@Captor相反的方法捕获调用的参数
- 5 @Captor：声明捕获对象

#### 5.1.2 开启注解
- 1 写个@Before(JUnit4)方法，里边调用MockitoAnnotations.initMocks(this)方法。
```java
@Before public void initMocks() {
    MockitoAnnotations.initMocks(this);
}

```

- 2 使用@RunWith，在测试类上使用。

```java
@RunWith(MockitoJUnitRunner.class)
```
### 5.2 模拟对象
>mock - mock是从根据被测类生成了一个派生类，用于跟踪与它的交互。
spy - spy是包装现有实例，它仍将以与普通实例相同的方式运行。
唯一的区别是spy还将被检测以跟踪与它的所有交互。

- 1 mock
```java
	//1 使用Mocktio.mock()静态方法
	List mockList = mock(List.class);

	//2 使用@Mock注解
    @Mock
    VerifyService verifyService;

    @InjectMocks
    CalculateService calculateService;
```
- 2 spy
```java
	List<String> list = new ArrayList<String>();
	List<String> spyList1 = spy(list);
```
### 5.3 打桩
```java
	//1 调用返回某值
	when(verifyService.validate(any())).thenReturn(false);
	//2 调用返回某值
	doReturn(false).when(verifyService).validate(any());
	//3 调用返回异常
	when(calculateService.check(any())).thenThrow(RuntimeException.class);
	//4 void方法调用返回异常
	doThrow(RuntimeException.class).when(calculateService).checkInteger(any());
	//5 多次调用返回不同的值
	when(verifyService.validate(any()))
                .thenReturn(false)//第一次返回
                .thenReturn(true)//第二次返回
                .thenThrow(RuntimeException.class);//第三次抛出异常
	//6 调用时调真实的方法
	when(verifyService.validate(any())).thenCallRealMethod();
	//7 调用时一个有逻辑的返回
	doAnswer(invocation -> {
                 //有逻辑的返回
                 Object arg0 = invocation.getArgument(0);
                 if ((Integer) arg0 == 1)
                    return true;
                 return false;
              }).when(verifyService).validate(anyInt());
```
### 5.4 验证
- 1 验证执行次数
```java
	@Test
    public void verifySimple() {
        CalculateService calculateService = mock(CalculateService.class);
        calculateService.print(1, 2);
        //verify mock的calculateService1执行了print(1,2)方法
        verify(calculateService).print(1, 2);//发生过
        verify(calculateService, times(1)).print(1, 2);//发生过一次
        verify(calculateService, atLeast(1)).print(1, 2);//发生过至少一次
        verify(calculateService, atMost(3)).print(1, 2);//发生过最多三次
    }
```
- 2 验证执行行为
```java
	@Test
    public void verifyBehaver() {
        CalculateService calculateService = mock(CalculateService.class);
        //verify mock的calculateService1没有被调用过
        verifyZeroInteractions(calculateService);
        //等同
        verify(calculateService, times(0)).print(1, 2);

        calculateService.print(1, 2);
        calculateService.check(1);
        //verify mock的calculateService1执行了1次print(1,2)方法
        verify(calculateService, times(1)).print(1, 2);
        verifyNoMoreInteractions(calculateService);

        //verify inOrder 先执行了print(1,2) 再执行了check(1)
        InOrder inOrder = inOrder(calculateService);
        //可以尝试调换顺序
        inOrder.verify(calculateService).print(1, 2);
        inOrder.verify(calculateService).check(1);
    }
```
- 2 验证捕获参数
```java
	/**
 	* ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
 	* verify(mock).doSomething(argument.capture());
 	* assertEquals("John", argument.getValue().getName());
 	*/
	@Test
	public void verifyMethodCaptureParams() {
    	CalculateService calculateService1 = mock(CalculateService.class);
    	calculateService1.check(2L);
    	//new 参数捕获对象 ArgumentCaptor
    	ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
    	verify(calculateService1).check(argumentCaptor.capture());
    	Long value = argumentCaptor.getValue();
    	//验证捕获到的参数是否正确
    	assert (2L == value);
}
```


`参考资料`：
>[ 1 ].https://site.mockito.org/
[ 2 ].https://static.javadoc.io/org.mockito/mockito-core/2.28.2/org/mockito/Mockito.html
[ 3 ].https://www.jianshu.com/p/eae0187900f8

