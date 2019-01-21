package com.model.design.adapter.abstractfactory.provide;

import com.model.design.adapter.abstractfactory.send.Sender;

/**
 * @author liuzheng
 * 2018/12/28 9:08
 */
public interface Provider {

    Sender provide();
}
