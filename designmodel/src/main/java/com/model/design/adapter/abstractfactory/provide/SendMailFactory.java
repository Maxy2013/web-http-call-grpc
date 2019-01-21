package com.model.design.adapter.abstractfactory.provide;

import com.model.design.adapter.abstractfactory.send.MailSender;
import com.model.design.adapter.abstractfactory.send.Sender;

/**
 * @author liuzheng
 * 2018/12/28 9:09
 */
public class SendMailFactory implements Provider{


    public Sender provide() {
        return new MailSender();
    }
}
