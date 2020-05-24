# Java Unit Test - Spock

Spock是Java和Groovy应用程序的测试和规范框架。它脱颖而出是因为它美丽而富有表现力的规范。
由于其基于JUnit，Spock与大多数IDE，构建工具和持续集成服务器兼容。
Spock的灵感来自[JUnit](https://blog.csdn.net/weixx3/article/details/93767257)， jMock，RSpec，Groovy，Scala， Vulcans等。
使用Spock需要具有`Groovy`和`单元测试`的基本知识。

>Spock的作用跟Junit一样，可以与其他模拟框架如[Mocktio](https://blog.csdn.net/weixx3/article/details/93890555)等结合使用。

## 1. 使用姿势

### 1.1 总览
gradle依赖：
```shell
testCompile group: 'org.spockframework', name: 'spock-core', version: '1.3-groovy-2.5'
```

```java
import spock.lang.Specification

class ReviewSpock extends Specification {

    def "test"() {
        given:

        def namelist = new ArrayList()
        def elem = "Weison"

        when:
        namelist.add(elem)

        then:
        !namelist.empty
        namelist.size() == 2
        namelist.get(0).equals("Weison")
        noExceptionThrown()
    }

    def "test exception"() {
        given:

        def namelist = new ArrayList()
        def elem = "Weison"

        when:
        namelist.add(elem)
        namelist.get(2)

        then:
        thrown(IndexOutOfBoundsException.class)
    }
}
```
将其中一个验证改为：`namelist.size() == 2`,看到验证失败信息：
```java
Condition not satisfied:

namelist.size() == 2
|        |      |
[Weison] 1      false

<Click to see difference>


	at com.wxx.st.spock.MyFirstSpecification.test(MyFirstSpecification.groovy:21)
```

### 1.2 测试类需继承Specification
Specification类包含许多用于编写规范的方法，它指示JUnit使用Sputnik(Spock的JUnit runner)运行规范程序。
Spock下的单元测试类需继承Specification。
```java
class MyFirstSpecification extends Specification {
  // fields
  // fixture methods
  // feature methods
  // helper methods
}
```
### 1.3 变量定义
```java

def obj = new ClassUnderSpecification()
def coll = new Collaborator()
```

### 1.4 夹具方法
有一下几个：
```java
def setupSpec() {}    // 执行一次 -  在第一个单元测试方法之前
def setup() {}        // 执行在每个单元测试方法之前
def cleanup() {}      // 执行在每个单元测试方法之后
def cleanupSpec() {}  // 执行一次 -  在最后一个单元测试方法之后
```
如果测试类中重写了夹具方法，那么执行顺序如下：
- 1 super.setupSpec
- 2 sub.setupSpec
- 3 super.setup
- 4 sub.setup
- 5 单元测试方法
- 6 sub.cleanup
- 7 super.cleanup
- 8 sub.cleanupSpec
- 9 super.cleanupSpec

### 1.4 测试方法
从概念上讲，一种特征方法包括四个阶段：
- 1 设置功能的夹具(可选)
- 2 根据规范为系统提供激励
- 3 描述系统预期的响应
- 4 清理功能的夹具(可选)

```java
def "test add an element with exception"() {
  // 测试代码
}
```
### 1.5 块
```java
def "test add an element with exception"() {
  // 测试代码
}
```

Spock内置支持实现特征方法的每个概念阶段。
为此，特征方法被构造成所谓的块。
块以标签开头，并延伸到下一个块的开头或方法的结尾。
有6种模块：`given`，`when`，`then`，`expect`，`cleanup`，和`where`块。
方法开头和第一个显式块之间的任何语句都属于隐式given块。


## 2. 与Mockito一起使用
```java
package com.wxx.st.spock.dependency

import com.wxx.st.service.CalculateService
import com.wxx.st.service.VerifyService
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class VerifyWithMocktio extends Specification {

    //CalculateService是被测试类
    //RemainderService是它的一个属性（依赖）

    @Mock
    VerifyService verifyService

    @InjectMocks
    CalculateService calculateService


    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test multiply"() {
        given:

        when(verifyService.validate(any())).thenReturn(true)

        when:
        //两者相乘
        def multiply = calculateService.multiply(int1, int2)

        then:
        assert (multiply == result)

        where:
        int1 | int2 | result
        1    | 2    | 2
        2    | 4    | 8
        3    | 6    | 18
        4    | 7    | 28

    }
}

```
`where`处的预至条件会进行遍历，遍历一次执行一次`when`和`then`中代码：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190717181202142.png)项目代码 --> [链接🔗](https://github.com/WeisonWei/java-test)

参考资料：
>[ 1 ]. http://spockframework.org/spock/docs/1.3/all_in_one.html#_introduction
