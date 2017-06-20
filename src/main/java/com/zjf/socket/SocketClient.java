package com.zjf.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient
{

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket socket = new Socket("127.0.0.1", 8111);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        //发送信息给服务器端
        byte[] bts = ("Hello Socket").getBytes();
        os.write(bts);
        os.flush();
        
        //接收有服务端信息
        bts = new byte[1024 * 10];
        int p = is.read(bts, 0, bts.length);
        String inline = new String(bts, 0, p);
        System.out.println("收到信息:" + inline);

        os.close();
        is.close();
        socket.close();
        System.out.println("客户端结束");
    }

}
