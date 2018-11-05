package com.cource.nio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lz
 * 2018/10/31 10:59
 */
public class MainService {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MyNIORector[] rectors = new MyNIORector[Runtime.getRuntime().availableProcessors()];

        for(int i = 0; i < rectors.length; i++){
            rectors[i] = new MyNIORector(executorService);
            rectors[i].start();
        }
        NIOAcceptor acceptor = new NIOAcceptor(9000, rectors);
        acceptor.start();

        /*List<Map<String,Object>> list=new ArrayList<Map<String,Object>>(3);
        Map<String,Object> maps =new HashMap<String, Object>(2);
        maps.clear();
        maps.put("NAME","饼图1");
        maps.put("VALUE","20");
        list.add(maps);
        maps.clear();
        maps.put("NAME","饼图2");
        maps.put("VALUE","50");
        list.add(maps);
        maps.clear();
        maps.put("NAME","饼图3");
        maps.put("VALUE","90");
        list.add(maps);
        System.out.println(list);*/
    }

}
