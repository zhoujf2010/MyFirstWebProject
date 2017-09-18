package com.zjf.weixin;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class MsgSend
{
    //模板信息发送

    public static void main(String[] args) throws Exception {

        String appid = "wxaf792784650e9b95";
        String secret = "0c9c1281db26a2fa723e8037f39d34b0";
        String token = WeiXinTool.getToken(appid, secret);

//        Map<String, Object> vals =  new HashMap<String, Object>();
//        vals.put("keyword1", "aaaa");
//        vals.put("keyword2", "bbb");
//        vals.put("keyword3", "ccc");
//        WeiXinTool.SendMsg(token, "Er0I2wcfgglQLh8xpntEMZPxCHEhizabYXbqCwJE2gc", "ogw8Xv1DNORXVW_MMmn5bycvJDV8", vals);
        
        Map<String, Object> vals =  new HashMap<String, Object>();
        vals.put("first", "你好，你的订单已经有银行接受");
        vals.put("keyword1", "000001");
        vals.put("keyword2", "ccc");
        vals.put("remark", "dddd");
        WeiXinTool.SendMsg(token, "jV6tVoN92sIhyoqSMR-UbNagi4oasBDq2ntsmrSXYVg", "ogw8Xv1DNORXVW_MMmn5bycvJDV8", vals);
       
    }

}
