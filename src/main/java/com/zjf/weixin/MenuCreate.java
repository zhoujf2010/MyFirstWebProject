package com.zjf.weixin;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuCreate
{

    public static void main(String[] args) throws Exception {
        // xxeecddggaawwwd
        String access_token = "SLzXvHTZjR6CT5VUN0OaTK40cQkkBgOh4MXFscT6JgcARFJs6R2Z8GU834WxFHnrD31xn939A9_QgafW7Zxprm3Efk5gTMUFebXLBUomPNVt2da9w1_vDGom9dH--mC6FUBbAIAZOV";
        String appid = "wx4656182b513afe08";
        String backurl = "http://www.demo.com/aa/";

        //获取access_token
        //getToken();//{"access_token":"SLzXvHTZjR6CT5VUN0OaTK40cQkkBgOh4MXFscT6JgcARFJs6R2Z8GU834WxFHnrD31xn939A9_QgafW7Zxprm3Efk5gTMUFebXLBUomPNVt2da9w1_vDGom9dH--mC6FUBbAIAZOV","expires_in":7200}

        
        String posturl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;

        String urltb = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + backurl
                + "Pages/WeiXinLogin.aspx" + "&response_type=code&scope=snsapi_base&state=1STATE#wechat_redirect";
        String urlzx = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + backurl
                + "Pages/ZhongXinLogin.aspx" + "&response_type=code&scope=snsapi_base&state=1STATE#wechat_redirect";
        String menuStr = "{" + "\"button\":[" + "{\"name\":\"信息查询\",\"sub_button\":["
                + "{\"type\":\"click\",\"name\":\"招标公告\",\"key\":\"V001_XXCX_ZBGG\"},"
                + "{\"type\":\"click\",\"name\":\"中标公示\",\"key\":\"V001_XXCX_ZBGS\"},"
                + "{\"type\":\"click\",\"name\":\"通知公告\",\"key\":\"V001_XXCX_TZGG\"},"
                + "{\"type\":\"click\",\"name\":\"法律法规\",\"key\":\"V001_XXCX_FLFG\"},"
                + "{\"type\":\"click\",\"name\":\"办事指南\",\"key\":\"V001_XXCX_BSZN\"}]},"
                + "{\"name\":\"我的投标\",\"sub_button\":["
                + "{\"type\":\"click\",\"name\":\"投标提醒\",\"key\":\"V001_WDTB_TBTX\"},"
                + "{\"type\":\"click\",\"name\":\"我的交易\",\"key\":\"V001_WDTB_WDJY\"},"
                + "{\"type\":\"click\",\"name\":\"今日开标\",\"key\":\"V001_WDTB_JRKB\"},"
                + "{\"type\":\"click\",\"name\":\"内部通知\",\"key\":\"V001_WDTB_NBTZ\"}]},"
                + "{\"name\":\"绑定用户\",\"sub_button\":[" + "{\"type\":\"view\",\"name\":\"投标单位登陆\",\"url\":\"" + urltb
                + "\"}," + "{\"type\":\"view\",\"name\":\"中心人员登陆\",\"url\":\"" + urlzx + "\"}]}" + "]}";

        TestPost(posturl, menuStr.getBytes());

    }

    private static void getToken() throws Exception {
        String appid = "wx4656182b513afe08";
        String secret = "89b407b4edf14c7e6b475622c990c49e";
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
