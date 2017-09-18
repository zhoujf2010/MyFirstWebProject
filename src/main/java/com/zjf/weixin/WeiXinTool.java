package com.zjf.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class WeiXinTool
{
    public  static String getToken(String appid, String secret) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret="
                + secret;
        String ret = RestGet(url);

        JSONObject obj = JSONObject.fromObject(ret);
        Object o = obj.get("access_token");
        if (o == null)
            throw new Exception("获取出错:" + ret);
        return o.toString();
    }

    public static void SendMsg(String token,String tempID,String toUser,Map<String, Object> vals) throws Exception{
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("touser", toUser);
        ret.put("template_id", tempID);
        ret.put("url", "");//http://www.baidu.com");
        ret.put("miniprogram", new HashMap<String, Object>());
        Map<String, Object> data =  new HashMap<String, Object>();

        for(String name:vals.keySet()){
            Map<String, Object> val =  new HashMap<String, Object>();
            val.put("value", vals.get(name));
            val.put("color", "#173177");
            data.put(name, val);
        }
        ret.put("data", data);
        JSONObject jsonObject = JSONObject.fromObject(ret);
        String str = jsonObject.toString();
        
        
        WeiXinTool.RestPost(url, str.getBytes());
    }

    public static void RestPost(String surl, byte[] postdata) throws Exception {
        URL url = new URL(surl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // request.ContentType = "application/x-www-form-urlencoded";
        conn.setDoOutput(true);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(postdata);

        InputStream is = conn.getInputStream();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff, 0, buff.length)) > 0) {
            System.out.println(new String(buff, 0, len));
        }
    }
    
    public static String RestGet(String strurl) throws IOException {
        URL url = new URL(strurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 提交模式

        InputStream is = conn.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff, 0, buff.length)) > 0) {
            sb.append(new String(buff, 0, len));
        }
        return sb.toString();
    }
}
