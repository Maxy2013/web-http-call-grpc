package com.model.design.adapter.abstractfactory.provide;

import com.model.design.adapter.abstractfactory.send.Sender;
import com.model.design.adapter.abstractfactory.send.SmsSender;

/**
 * @author liuzheng
 * 2018/12/28 9:15
 */
public class SendSmsFactory implements Provider{

    public Sender provide() {
        return new SmsSender();
    }
}
