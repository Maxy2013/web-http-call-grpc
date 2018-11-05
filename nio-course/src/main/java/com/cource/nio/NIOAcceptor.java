package com.cource.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lz
 * 2018/10/31 11:15
 */
public class NIOAcceptor extends Thread{

    private final ServerSocketChannel serverSocketChannel;

    private final MyNIORector[] rectors;

    public NIOAcceptor(int port, MyNIORector[] rectors) throws IOException {
        this.rectors = rectors;
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocketChannel.socket().bind(address);
        System.out.println("server start at " + address);
    }

    @Override
    public void run() {
        while (true){
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
//                System.out.println("Connection Accepted " + socketChannel.getRemoteAddress());
                int nextRector = ThreadLocalRandom.current().nextInt(0, rectors.length);
                rectors[nextRector].registerNewClient(socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
