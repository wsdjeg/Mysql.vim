package com.wsdjeg.mysqlvim.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ProtocolImpl implements Protocol {
    private int bufSize;
    public ProtocolImpl(int bufSize){
        this.bufSize = bufSize;
    }
    public void handleWrite(SelectionKey key) throws IOException {
        //获取与该信道关联的缓冲区，里面有之前读取到的数据
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //重置缓冲区，准备将数据写入信道
        buf.flip();
        SocketChannel clntChan = (SocketChannel) key.channel();
        //将数据写入到信道中
        clntChan.write(buf);
        if (!buf.hasRemaining()){
            //如果缓冲区中的数据已经全部写入了信道，则将该信道感兴趣的操作设置为可读
            key.interestOps(SelectionKey.OP_READ);
        }
        //为读入更多的数据腾出空间
        buf.compact();
    }
    public void handleRead(SelectionKey key) throws IOException{
        SocketChannel clntChan = (SocketChannel) key.channel();
        //获取该信道所关联的附件，这里为缓冲区
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = clntChan.read(buf);
        //如果read（）方法返回-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全地关闭
        if (bytesRead == -1){
            clntChan.close();
        }else if(bytesRead > 0){
        //如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写
        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
        clntChan.configureBlocking(false);
        //将选择器注册到连接到的客户端信道，并指定该信道key值的属性为OP_READ，同时为该信道指定关联的附件
        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

}
