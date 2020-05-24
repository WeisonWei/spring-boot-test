package com.weison.sbt.testcase;

public class MessageUtil {


    private String message;

    public MessageUtil(String message) {
        this.message = message;
    }


    public String salutationMessage() {
        return message;
    }

    public String printMessage() {
        System.out.printf("----->" + this.message);
        return this.message;
    }
}
