package com.model.design.adapter.abstractfactory.send;

/**
 * @author liuzheng
 * 2018/12/28 9:07
 */
public class MailSender implements Sender{

    public void send() {
        System.out.println("发送邮件 --->> mail");
    }
}
