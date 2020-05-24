package com.weison.sbt.spock.dependency

import com.weison.sbt.service.CalculateService
import com.weison.sbt.service.VerifyService
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