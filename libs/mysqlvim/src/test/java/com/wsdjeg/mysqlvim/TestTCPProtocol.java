package com.wsdjeg.mysqlvim;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface TestTCPProtocol {
    void handleAccept(SelectionKey key) throws IOException;
    void handleRead(SelectionKey key) throws IOException;
    void handleWrite(SelectionKey key) throws IOException;

}
