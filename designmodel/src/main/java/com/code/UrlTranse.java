package com.code;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author liuzheng
 * 2019/1/7 10:44
 */
public class UrlTranse {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "LxuzgLiG6jhwixJD4Ex93IqXszpse6HiZIILO08eTaaPyflcts4cUcn8/J3NiQi2CfTy7S+mUJ12xdl8ge6/L3qeltxyz0dWdoeCi2sHy3VYmBo9uWhjyxB/TOX4bFbD4dAYM/L4wY49Flf452jtWM4F8Mhv4V1AVlM98oNKEs4=";
        str = "充值缴费";
        String encode = URLEncoder.encode(str, "UTF-8");
        System.out.println(encode);
    }
}
