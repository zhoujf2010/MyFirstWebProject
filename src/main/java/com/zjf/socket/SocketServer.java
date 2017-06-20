package com.zjf.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8111);
        Socket socket = server.accept();
        
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        
        //接收客户端信息
        byte[] bts = new byte[1024 * 10];
        int p = is.read(bts, 0, bts.length);
        String inline = new String(bts, 0, p);
        System.out.println("收到信息:" + inline);
        
        //返回客户端
        bts = ("返回信息:" + inline).getBytes();
        os.write(bts);
        
        os.flush();
        os.close();
        is.close();
        socket.close();
        server.close();
        System.out.println("服务端结束");
    }

}
