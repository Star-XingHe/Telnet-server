package com.company;

import com.company.bean.User;
import com.company.dao.UserDAO;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;


public class MyServer {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while (true) {
                System.out.println("服务器回归原点");
                Socket socket = serverSocket.accept();
                System.out.println("接到客户端socket请求");
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                String []getStr = dis.readUTF().split(" ");//用空格把账号和用户名分开
                System.out.println(getStr[0]+" "+getStr[1]+" "+getStr[2]);
                User user = new User(getStr[0],getStr[1]);

                if(getStr[2].equals("Login"))
                {
                    System.out.println("进入登录界面");
                    login(socket,user);

                }
                //else if(getStr[2].equals("Registeres"))//针对进入注册界面但是没有完成注册就退出且再次进入注册界面情况的处理
                dis.close();
                is.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void login(Socket s, User user)
    {
        UserDAO userDAO = new UserDAO();
        if(userDAO.find(user))
        {
            System.out.println("user找到了");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("YES");
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            System.out.println("user没有找到");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("NO");
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
