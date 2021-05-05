package com.company;


import java.net.Socket;

public class SocketClientWorker implements Runnable{

    //通信socket
    private Socket clientSocket;
    //客户端名称
    private String clientName;
    //发送消息间隔
    private long sleepTime;
    //发送的消息内容
    private String message;

    public SocketClientWorker(String host,int port,String clientName,long sleepTime,String message){
        try{
            clientSocket= new Socket(host, port);
            this.clientSocket = clientSocket;
            this.clientName = clientName;
            this.sleepTime = sleepTime;
            this.message =  message;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        {
            while (true) {
                try {
                    clientSocket.getOutputStream().write(message.getBytes());
                    System.out.println(clientName + "客户端向服务器发送消息: " + message);
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
