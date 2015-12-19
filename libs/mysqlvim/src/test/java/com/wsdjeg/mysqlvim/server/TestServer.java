package com.wsdjeg.mysqlvim.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TestServer {
    private static final int BUFSIZE = 256;
    private static final int TIMEOUT = 3000;
    public static void main (String[] args) throws IOException{
        if (args.length < 1) {
            throw new IllegalArgumentException("wrong args");
        }
        Selector selector = Selector.open();
        for (String arg : args) {
            ServerSocketChannel listnChannel = ServerSocketChannel.open();
            listnChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)));
            listnChannel.configureBlocking(false);
            listnChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

    }
}

