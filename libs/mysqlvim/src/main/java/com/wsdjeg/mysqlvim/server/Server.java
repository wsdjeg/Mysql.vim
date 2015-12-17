package com.wsdjeg.mysqlvim.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main (String[] args) {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("wrong argument");
        }

    }
    public void run() {
        ServerSocket server = new ServerSocket(getAvailablePort());
        Socket socket = server.accept();
    }
    public int getAvailablePort() {
        return 0;
    }

}
