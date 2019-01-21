package com.model.design.adapter;

import com.model.design.adapter.abstractfactory.provide.Provider;
import com.model.design.adapter.abstractfactory.provide.SendMailFactory;
import com.model.design.adapter.abstractfactory.provide.SendSmsFactory;
import com.model.design.adapter.abstractfactory.send.Sender;

/**
 * @author liuzheng
 * 2018/12/28 9:16
 */
public class Action {

    public static void main(String[] args) {
        // 发送邮件
        Provider provider = new SendMailFactory();
        Sender provide = provider.provide();
        provide.send();

        Provider smsProvider = new SendSmsFactory();
        Sender sms = smsProvider.provide();
        sms.send();
    }
}
