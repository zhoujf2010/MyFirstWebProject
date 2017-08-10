package com.zjf.weixin;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuCreate
{

    public static void main(String[] args) throws Exception {

        //1.获取access_token
        //getToken();//{"access_token":"j5iFE410cGvZNw-r-Sll3gzcYoHIy54h9pKaBh1RsTrW_Nkz26UD2vXJfS9D7Oudfojk58eIasKJR6PyVFmnMFAmub8JMdc4NeUrBfYnShxiVuj-n9tsU6cWbGSBf3JOEDAgACAYXU","expires_in":7200}


    
        //2.创建菜单
        // xxeecddggaawwwd
        String access_token = "C7ad7E1xOlBw73MWscqDh23k7wEZXImx35EDkntiP6FVj10nhUsOHNFq3GucQ7AuODWJCiSQpsI6Xr7P8CPvgjK1ZY4BMCzUbGAakEub5p7eB-g_Pp6F5vxf2LUl5yx7MGKaABADXB";
        String appid = "wxaf792784650e9b95";
        //String backurl = "http://172.93.40.179/MyFirstWebProject/weixin";
        String backurl = "http://demo.tobloan.com/MyFirstWebProject/weixin";
        String urltest = backurl + "/weixininfo";
        //String urllogin = backurl + "/weixinbind";
        
    
        String posturl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;

        String urllogin = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + backurl
                + "/weixinbind" + "&response_type=code&scope=snsapi_base&state=1STATE#wechat_redirect";
        String urlzx = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + backurl
                + "Pages/ZhongXinLogin.aspx" + "&response_type=code&scope=snsapi_base&state=1STATE#wechat_redirect";
        String menuStr = "{" + "\"button\":[" + "{\"name\":\"信息查询\",\"sub_button\":["
                + "{\"type\":\"click\",\"name\":\"招标公告\",\"key\":\"V001_XXCX_ZBGG\"},"
                + "{\"type\":\"click\",\"name\":\"中标公示\",\"key\":\"V001_XXCX_ZBGS\"},"
                + "{\"type\":\"click\",\"name\":\"通知公告\",\"key\":\"V001_XXCX_TZGG\"},"
                + "{\"type\":\"click\",\"name\":\"法律法规\",\"key\":\"V001_XXCX_FLFG\"},"
                + "{\"type\":\"click\",\"name\":\"办事指南\",\"key\":\"V001_XXCX_BSZN\"}]},"
                + "{\"name\":\"我的投标\",\"sub_button\":["
                + "{\"type\":\"view\",\"name\":\"测试页面2\",\"url\":\"" + urltest + "\"},"
                + "{\"type\":\"click\",\"name\":\"我的交易\",\"key\":\"V001_WDTB_WDJY\"},"
                + "{\"type\":\"click\",\"name\":\"今日开标\",\"key\":\"V001_WDTB_JRKB\"},"
                + "{\"type\":\"click\",\"name\":\"内部通知\",\"key\":\"V001_WDTB_NBTZ\"}]},"
                + "{\"name\":\"绑定用户\",\"sub_button\":[" + "{\"type\":\"view\",\"name\":\"用户绑定\",\"url\":\"" + urllogin
                + "\"}," + "{\"type\":\"view\",\"name\":\"中心人员登陆\",\"url\":\"" + urlzx + "\"}]}" + "]}";

        TestPost(posturl, menuStr.getBytes());

    }

    private static void getToken() throws Exception {
        String appid = "wxaf792784650e9b95";
        String secret = "0c9c1281db26a2fa723e8037f39d34b0";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret="
                + secret;

        TestGet(url);
    }

    private static void TestGet(String strurl) throws Exception {
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

    public static void TestPost(String surl, byte[] postdata) throws Exception {
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

}
