package com.weison.sbt.service;

public class VerifyService {
    public Boolean validate(Integer param) {
        if (param instanceof Integer)
            return true;
        return false;
    }
}
