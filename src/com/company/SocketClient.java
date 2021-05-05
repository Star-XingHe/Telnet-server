package com.company;


import java.io.IOException;

public class SocketClient {
    public static void main(String[] args)throws IOException{
        new Thread(new SocketClientWorker("127.0.0.1", 6666,"ClientA",5000,"A发送消息。。")).start();
        new Thread(new SocketClientWorker("127.0.0.1", 6666,"ClientB",4000,"B发送消息。。")).start();
        new Thread(new SocketClientWorker("127.0.0.1", 6666,"ClientC",3000,"C发送消息。。")).start();

    }
}
