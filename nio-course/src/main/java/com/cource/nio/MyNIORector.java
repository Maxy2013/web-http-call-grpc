package com.cource.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author lz
 * 2018/10/31 9:44
 */
public class MyNIORector extends Thread {

    final Selector selector;
    final ExecutorService executor;

    public MyNIORector(ExecutorService executorService) throws IOException {
        selector = Selector.open();
        this.executor = executorService;
    }

    public void registerNewClient(SocketChannel socketChannel) throws IOException {
        System.out.println("由"+ this.getName() +"新注册的channel");
        new TelnetIOHandler(selector, socketChannel);
    }

    @Override
    public void run() {
        while (true){
            Set<SelectionKey> selectionKeys = new HashSet<SelectionKey>();
            try {
                selector.select();
                selectionKeys = selector.selectedKeys();

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            for (SelectionKey selectionKey : selectionKeys){
                IOHandler ioHandler = (IOHandler) selectionKey.attachment();
                this.executor.execute(ioHandler);
            }
        }
    }
}
