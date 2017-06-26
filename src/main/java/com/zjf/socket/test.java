package com.zjf.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class test
{

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8090);

        while (true) {
            Socket socket = server.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            Socket socket2 = new Socket("192.168.201.63", 4399);
            InputStream is2 = socket2.getInputStream();
            OutputStream os2 = socket2.getOutputStream();

            new Thread(new Runnable()
            {
                @Override
                public void run() {
                    try {
                        TranforamData(is, os2);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

            new Thread(new Runnable()
            {
                @Override
                public void run() {
                    try {
                        TranforamData(is2, os);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }

//        os.close();
//        is.close();
//        os2.close();
//        is2.close();
//        socket.close();
//        socket2.close();
//        server.close();
//        System.out.println("End");
    }

    private static void TranforamData(InputStream is, OutputStream os) throws IOException {

        byte[] bts = new byte[1024 * 10];
        int p = is.read(bts, 0, bts.length);
//        String send = new String(bts, 0, p);
//        System.out.println("收到信息:" + send);

        // 转发
        os.write(bts);
        os.flush();
    }
}
