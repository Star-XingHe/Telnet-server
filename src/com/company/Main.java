package com.company;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        SocketServerListenHandler socketServerListenHandler = new SocketServerListenHandler(6666);
        socketServerListenHandler.listenClientConnect();

    }



}
