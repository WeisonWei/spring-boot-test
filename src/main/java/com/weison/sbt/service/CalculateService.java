package com.weison.sbt.service;

import javax.annotation.Resource;

public class CalculateService {

    @Resource
    private VerifyService verifyService;

    public Integer plus(Integer a, Integer b) {
        return a + b;
    }

    public Integer subtract(Object a, Integer b) {
        return (Integer) check(a) - b;
    }

    public Integer multiply(Integer a, Integer b) {
        if (verifyService.validate(a) && verifyService.validate(b))
            return a * b;
        return null;
    }

    public Long divide(Long a, Long b) {
        return a / b;
    }

    public void print(Integer a, Integer b) {
        System.out.printf("---->" + a + b);
    }

    public Object check(Object a) {
        if (a instanceof Integer) {
            System.out.println("It is Integer ~");
            return a;
        } else
            throw new RuntimeException("It is not a Integer~");
    }


    public void checkInteger(Object a) {
        if (a instanceof Integer) {
            System.out.println("It is Integer ~");
        } else
            throw new RuntimeException("It is not a Integer~");
    }
}

