package com.company;

import java.io.InputStream;
import java.net.Socket;


public class SocketServerClientHandler extends Thread{

    //每个消息通过Socket进行传输
    private Socket clientConnectSocket;
    public SocketServerClientHandler(Socket clientConnectSocket){
        this.clientConnectSocket = clientConnectSocket;
    }

    @Override
    public void run(){
        try {
            InputStream inputStream = clientConnectSocket.getInputStream();
            while (true) {
                byte[] data = new byte[100];
                int len;
                while ((len = inputStream.read(data)) != -1) {
                    String message = new String(data, 0, len);
                    System.out.println("客户端传来消息: " + message);
                    clientConnectSocket.getOutputStream().write(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}