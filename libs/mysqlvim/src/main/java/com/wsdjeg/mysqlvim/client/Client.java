package com.wsdjeg.mysqlvim.client;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main (String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("wrong argument");
        }
        String server = args[0];
        byte[] argument = args[1].getBytes();
        int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
        SocketChannel clntchan = SocketChannel.open();
        clntchan.configureBlocking(false);
        if (!clntchan.connect(new InetSocketAddress(server,serverPort))) {
            while (!clntchan.finishConnect()) {
                System.out.println('.');
            }
        }
        System.out.println("\n");
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0;
        int bytesRcvd = 0;
        while (totalBytesRcvd < argument.length) {
            if (writeBuf.hasRemaining()) {
                clntchan.write(writeBuf);
            }
            if ((bytesRcvd = clntchan.read(readBuf)) == -1) {
                throw new SocketException("connection closed prematurely");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.println('.');
            System.out.println("Received :" + new String(readBuf.array(),0,totalBytesRcvd));
            clntchan.close();
        }
    }
}
