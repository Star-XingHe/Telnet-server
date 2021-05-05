package com.company;

import com.company.bean.User;
import com.company.dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class SwingServer extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private JButton btn_svrSender;
    private PrintWriter pw;
    private  ServerSocket serverSocket;
    private Socket socket;
    public static void main(String [] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    SwingServer frame = new SwingServer();
                    frame.setVisible(true);
                    frame.setSever();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }
    public SwingServer()
    {
        //super();
        setTitle("何星的服务器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,500,500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("服务器端发送消息框：");
        panel.add(lblNewLabel);

        textField = new JTextField();//单行文本
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strSvr=textField.getText();
                pw.println(strSvr);

                textArea.append("服务器发送的信息为："+strSvr+"\n");
                textField.setText("");

            }
        });
        panel.add(textField);
        textField.setColumns(10);

        btn_svrSender = new JButton("发送");
        btn_svrSender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //do_Svr_It(e);
            }

        });
        panel.add(btn_svrSender);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        textArea = new JTextArea();//多行文本
        scrollPane.setViewportView(textArea);

    }

    public void setSever()
    {
        //Java Swing中
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = false;

                try {
                    serverSocket = new ServerSocket(6666);
                    textArea.append("服务器启动已成功，等待客户端的连接...\n");
                    System.out.println("服务器回归原点");
                    Socket socket = serverSocket.accept();
                    textArea.append("客户端连接已成功...\n");
                    System.out.println("接到客户端socket请求");
                    while (true) {

                        InputStream is = socket.getInputStream();
                        DataInputStream dis = new DataInputStream(is);
                        String[] getStr = dis.readUTF().split(" ");//用空格把账号和用户名分开
                        System.out.println(getStr[0] + " " + getStr[1] + " " + getStr[2]);
                        User user = new User(getStr[0], getStr[1]);
                        textArea.append("收到客户端发来的信息：" + user.getName() + " " + user.getPassword() + "\n");
                        if (getStr[2].equals("Login")) {

                            System.out.println("进入登录界面");
                            isLogin = login(socket, user);//bool型？？

                        }else if(getStr[2].equals("Register"))
                        {
                            System.out.println("进入注册界面");
                            isLogin = register(socket, user);

                        }
                        if(isLogin)
                        {
                            textArea.append("客户端与服务器连接成功\n");
                            is = socket.getInputStream();
                            dis = new DataInputStream(is);
                            String choose = dis.readUTF();
                            System.out.print(choose);
                            if(choose=="calc")
                            {
                                System.out.println("开始计算器功能");
                                is = socket.getInputStream();
                                dis = new DataInputStream(is);
                                String formula = dis.readUTF();
                                Calculator calculator = new Calculator(formula);
                                calculator.calc();
                                String ans = calculator.getAns();
                                OutputStream os = socket.getOutputStream();
                                DataOutputStream dos = new DataOutputStream(os);
                                dos.writeUTF(ans);
                            }

                        }
                        //else if(getStr[2].equals("Registeres"))//针对进入注册界面但是没有完成注册就退出且再次进入注册界面情况的处理
                        dis.close();
                        is.close();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }
    public static Boolean login(Socket s, User user)
    {
        boolean flag=false;
        UserDAO userDAO = new UserDAO();
        if(userDAO.find(user))
        {
            System.out.println("登录user找到了");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("YES");
                flag = true;
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            System.out.println("登录user没有找到");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("NO");
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    public static Boolean register(Socket s, User user)
    {
        boolean flag=false;
        UserDAO userDAO = new UserDAO();
        if(userDAO.find(user))
        {
            System.out.println("注册user找到了");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("NO");
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        {
            System.out.println("注册user没有找到");
            try{
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                userDAO.add(user);
                dos.writeUTF("YES");
                flag = true;
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;

    }


}
