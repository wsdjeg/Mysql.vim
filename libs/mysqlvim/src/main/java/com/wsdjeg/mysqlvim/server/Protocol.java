package com.wsdjeg.mysqlvim.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface Protocol {
    void handleAccept(SelectionKey key) throws IOException;
    void handleRead(SelectionKey key) throws IOException;
    void handleWrite(SelectionKey key) throws IOException;
}
