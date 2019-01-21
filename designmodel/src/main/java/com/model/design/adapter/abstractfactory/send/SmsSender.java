package com.model.design.adapter.abstractfactory.send;

/**
 * @author liuzheng
 * 2018/12/28 9:06
 */
public class SmsSender implements Sender{

    public void send() {
        System.out.println("发送短信");
    }
}
