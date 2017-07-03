package com.zjf.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class callrest
{

    public static void main(String[] args) throws Exception {

        String strurl = "http://localhost:8080/MyFirstWebProject/rest/test/add2/1/2";
        URL url = new URL(strurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 提交模式

        InputStream is = conn.getInputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff, 0, buff.length)) > 0) {
            System.out.println(new String(buff, 0, len));
        }

    }
    
    public static void TestPost() throws Exception {
        String strurl = "http://localhost:8080/MyFirstWebProject/rest/test/user";
        URL url = new URL(strurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStream outStream = conn.getOutputStream();
        outStream.write("Hello".getBytes());

        InputStream is = conn.getInputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff, 0, buff.length)) > 0) {
            System.out.println(new String(buff, 0, len));
        }
    }


}
