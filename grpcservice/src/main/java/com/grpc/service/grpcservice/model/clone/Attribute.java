package com.grpc.service.grpcservice.model.clone;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * @author liuzheng
 * 2018/11/19 10:42
 */
public class Attribute implements Serializable, Cloneable {

    private String no;


    @Override
    public Object clone() {

        try {

            return super.clone();

        } catch (CloneNotSupportedException e) {

            return null;

        }

    }
}
