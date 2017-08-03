package com.zjf.weixin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zjf.test.StringUtil;
@RestController
@RequestMapping("/weixin")
public class weixinAction
{

    @RequestMapping("/service")
    @ResponseBody
    public void saveTest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getMethod().equals("GET")){
//            if (CheckSignature())
            String echoStr = req.getParameter("echostr");
            if (StringUtil.isBlank(echoStr))
                echoStr = "error";
            PrintWriter pw = resp.getWriter();
            pw.write(echoStr);
            pw.close();
        }
        if (req.getMethod().equals("POST")){
            InputStream is = req.getInputStream();
            byte[] bts = ReadStreamByte(is);
            String ss = new String(bts);
            System.out.println(ss);
            ReqEntity entity = ReqEntity.getInstance(bts);
            String ret = DealMsg(entity);
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.write(ret);
            pw.close();
        }
    }
    
    private String DealMsg(ReqEntity entity){
        String resxml="";
        resxml = "<xml><ToUserName><![CDATA[" + entity.getFromUserName()
        + "]]></ToUserName><FromUserName><![CDATA[" + entity.getToUserName()
        + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(new Date())
        + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您,HelloWorld，请通过点击菜单访问平台功能！";
      resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
      
        return resxml;
    }
    
    private long ConvertDateTimeInt(Date time)
    {
        return time.getTime()/1000;
    }

    
    public static byte[] ReadStreamByte(InputStream is) {
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int bufsize = 10240;
            byte[] buff = new byte[bufsize];
            int rc = 0;
            while ((rc = is.read(buff, 0, bufsize)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] in2b = swapStream.toByteArray();
            return in2b;
            /*
             * List<Byte> lst = new ArrayList<Byte>();
             * 
             * byte[] buf = new byte[100]; while (true) { int p = is.read(buf,
             * 0, buf.length); if (p <= 0) break; for (int i = 0; i < p; i++)
             * lst.add(buf[i]); } byte[] re = new byte[lst.size()]; for (int i =
             * 0; i < re.length; i++) re[i] = lst.get(i); return re;
             */
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
//  /// <summary>
//    /// 验证微信签名
//    /// </summary>
//    /// * 将token、timestamp、nonce三个参数进行字典序排序
//    /// * 将三个参数字符串拼接成一个字符串进行sha1加密
//    /// * 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信。
//    /// <returns></returns>
//    private bool CheckSignature()
//    {
//        string signature = Request.QueryString["signature"];
//        string timestamp = Request.QueryString["timestamp"];
//        string nonce = Request.QueryString["nonce"];
//        string[] ArrTmp = { Token, timestamp, nonce };
//        Array.Sort(ArrTmp);     //字典排序
//        string tmpStr = string.Join("", ArrTmp);
//        tmpStr = FormsAuthentication.HashPasswordForStoringInConfigFile(tmpStr, "SHA1");
//        tmpStr = tmpStr.ToLower();
//        if (tmpStr == signature)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
    
//    /// <summary>
//    /// 回复消息(微信信息返回)
//    /// 返回消息接口参考http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF
//    /// 先只做事件
//    /// </summary>
//    /// <param name="weixinXML"></param>
//    private void ResponseMsg(RequestXML requestXML)
//    {
//        try
//        {
//            string resxml = "";
//            if (requestXML.MsgType == "text")//
//            {
//                resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                  + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                  + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                  + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请通过点击菜单访问平台功能！";
//                resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//            }
//            else if (requestXML.MsgType == "event")
//            {
//                //招标公告
//                if (requestXML.EventKey == "V001_XXCX_ZBGG")
//                {
//                    //点击 招标公告，展示招标公告列表图文返回10以内条
//                    List<ListMsg> lm = new List<ListMsg>();
//                    lm = DBMsg.GetInfoList("cqnoguid", 7, "", 1, "001");
//                    int size = lm.Count + 2;
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                        + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                    resxml += "<item><Title><![CDATA[招标公告]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                       + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                    foreach (var item in lm)
//                    {
//                        resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                            + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                            + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=001]]></Url></item>";
//                    }
//                    resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                       + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=001]]></Url></item>";
//                    resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                }
//                else if (requestXML.EventKey == "V001_XXCX_ZBGS")
//                {
//                    //点击 中标公示，展示中标公示列表图文
//                    //返回10以内条
//                    List<ListMsg> lm = new List<ListMsg>();
//                    lm = DBMsg.GetInfoList("cqnoguid", 7, "", 1, "002");
//                    int size = lm.Count + 2;
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                        + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                    resxml += "<item><Title><![CDATA[中标公示]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                       + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                    foreach (var item in lm)
//                    {
//                        resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                            + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                            + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=002]]></Url></item>";
//                    }
//                    resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                       + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=002]]></Url></item>";
//                    resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                }
//                else if (requestXML.EventKey == "V001_XXCX_TZGG")
//                {
//                    List<ListMsg> lm = new List<ListMsg>();
//                    lm = DBMsg.GetInfoList("cqnoguid", 7, "", 1, "003");
//                    int size = lm.Count + 2;
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                        + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//
//                    resxml += "<item><Title><![CDATA[通知公告]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                        + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                    foreach (var item in lm)
//                    {
//                        resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                            + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                            + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=003]]></Url></item>";
//                    }
//                    resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                       + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=003]]></Url></item>";
//                    resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                }
//                else if (requestXML.EventKey == "V001_XXCX_FLFG")
//                {
//                    //点击 法律法规，展示法律法规列表图文
//                    //返回10以内条
//                    List<ListMsg> lm = new List<ListMsg>();
//                    lm = DBMsg.GetInfoList("cqnoguid", 7, "", 1, "004");
//                    int size = lm.Count + 2;
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                        + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                    resxml += "<item><Title><![CDATA[法律法规]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                        + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                    foreach (var item in lm)
//                    {
//                        resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                            + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                            + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=004]]></Url></item>";
//                    }
//                    resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                       + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=004]]></Url></item>";
//                    resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                }
//                else if (requestXML.EventKey == "V001_XXCX_BSZN")
//                {
//                    //点击 办事指南，展示办事指南列表图文
//                    //返回10以内条
//                    List<ListMsg> lm = new List<ListMsg>();
//                    lm = DBMsg.GetInfoList("cqnoguid", 7, "", 1, "005");
//                    int size = lm.Count + 2;
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                        + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                    resxml += "<item><Title><![CDATA[办事指南]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                        + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                    foreach (var item in lm)
//                    {
//                        resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                            + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                             + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=005]]></Url></item>";
//                    }
//                    resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                       + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=005]]></Url></item>";
//                    resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                }
//                else if (requestXML.EventKey == "V001_WDTB_TBTX")
//                {
//                    string UserGuid = "";
//
//                    string LoginDate = "";
//                    int nowDate = 0;
//                    string Dates = "";
//
//                    UserGuid = MyCache.GetUserGuid(requestXML.FromUserName, Global.GetInstance().tbAddress);
//
//                    Dates = DateTime.Now.ToString("yyMMddHHmm");
//                    LoginDate = MyCache.GetDataTime(requestXML.FromUserName, Global.GetInstance().tbAddress);
//                    if (LoginDate != "")
//                    {
//                        nowDate = Convert.ToInt32(Dates) - Convert.ToInt32(LoginDate);
//                    }
//
//                    if (UserGuid == "" || nowDate >= Convert.ToInt32(Global.GetInstance().LimitTime) || LoginDate == "")
//                    {
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                         + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                         + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                         + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请先登录！";
//                        resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                    }
//                    else
//                    {
//                        //返回10以内条
//                        List<ListMsg> lm = new List<ListMsg>();
//                        lm = DBMsg.GetInfoList(UserGuid, 7, "", 1, "101");
//                        int size = lm.Count + 2;
//
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                          + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                        resxml += "<item><Title><![CDATA[投标提醒]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                            + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                        foreach (var item in lm)
//                        {
//                            resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                                + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                             + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=101 ]]></Url></item>";
//                        }
//                        resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                           + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=101&UserGuid=" + CommonDesc.Encrypt(UserGuid) + "]]></Url></item>";
//                        resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                    }
//                }
//                else if (requestXML.EventKey == "V001_WDTB_WDJY")
//                {
//                    //点击 我的交易，判断是否登录，登录的话，进list  
//                    string UserGuid = "";
//
//                    string LoginDate = "";
//                    int nowDate = 0;
//                    string Dates = "";
//
//                    UserGuid = MyCache.GetUserGuid(requestXML.FromUserName, Global.GetInstance().tbAddress);
//
//                    Dates = DateTime.Now.ToString("yyMMddHHmm");
//                    LoginDate = MyCache.GetDataTime(requestXML.FromUserName, Global.GetInstance().tbAddress);
//                    if (LoginDate != "")
//                    {
//                        nowDate = Convert.ToInt32(Dates) - Convert.ToInt32(LoginDate);
//                    }
//
//                    if (UserGuid == "" || nowDate >= Convert.ToInt32(Global.GetInstance().LimitTime) || LoginDate == "")
//                    {
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                         + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                         + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                         + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请先登录！";
//                        resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                    }
//                    else
//                    {
//                        //返回10以内条
//                        List<ListMsg> lm = new List<ListMsg>();
//                        lm = DBMsg.GetInfoList(UserGuid, 7, "", 1,"102");
//                        int size = lm.Count + 2;
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                            + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                        resxml += "<item><Title><![CDATA[我的交易]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                            + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                        foreach (var item in lm)
//                        {
//                            resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                                + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().WcfHostUrl + "Pages/TimeLine.aspx?a="
//                                 + CommonDesc.Encrypt(UserGuid) + "&b=" + CommonDesc.Encrypt(Convert.ToString(item.OtherGuid)) + "&c=" + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&d=" + CommonDesc.Encrypt("1") + "]]></Url></item>";
//                        }
//                        resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                           + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=102&UserGuid=" + CommonDesc.Encrypt(UserGuid) + "]]></Url></item>";
//                        resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                    }
//                }
//
//                else if (requestXML.EventKey == "V001_WDTB_JRKB")
//                {
//                    string UserGuid = "";
//                    string LoginDate = "";
//                    int nowDate = 0;
//                    string Dates = "";
//
//                    UserGuid = MyCache.GetUserGuid(requestXML.FromUserName, Global.GetInstance().zxtbAddress);
//                    Dates = DateTime.Now.ToString("yyMMddHHmm");
//                    LoginDate = MyCache.GetDataTime(requestXML.FromUserName, Global.GetInstance().zxtbAddress);
//                    if (LoginDate != "")
//                    {
//                        nowDate = Convert.ToInt32(Dates) - Convert.ToInt32(LoginDate);
//                    }
//                    if (UserGuid == "" || nowDate >= Convert.ToInt32(Global.GetInstance().LimitTime) || LoginDate == "")
//                    {
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                         + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                         + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                         + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请先登录！";
//                        resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                    }
//                    else
//                    {
//                        //返回10以内条
//                        List<ListMsg> lm = new List<ListMsg>();
//                        lm = DBMsg.GetInfoList(UserGuid, 7, "", 1,"103");
//                        int size = lm.Count + 2;
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                          + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                        resxml += "<item><Title><![CDATA[今日开标]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                            + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                        foreach (var item in lm)
//                        {
//                            resxml += "<item><Title><![CDATA[" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                                + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/JrkbDetail.aspx?MsgGuid="
//                             + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=103]]></Url></item>";
//                        }
//                        resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                           + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=103&UserGuid=" + CommonDesc.Encrypt(UserGuid) + "]]></Url></item>";
//                        resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                    }
//                }
//                else if (requestXML.EventKey == "V001_WDTB_NBTZ")
//                {
//                    string UserGuid = "";
//                    string LoginDate = "";
//                    int nowDate = 0;
//                    string Dates = "";
//                    UserGuid = MyCache.GetUserGuid(requestXML.FromUserName, Global.GetInstance().zxtbAddress);
//                    Dates = DateTime.Now.ToString("yyMMddHHmm");
//                    LoginDate = MyCache.GetDataTime(requestXML.FromUserName, Global.GetInstance().zxtbAddress);
//                    if (LoginDate != "")
//                    {
//                        nowDate = Convert.ToInt32(Dates) - Convert.ToInt32(LoginDate);
//                    }
//
//                    if (UserGuid == "" || nowDate >= Convert.ToInt32(Global.GetInstance().LimitTime) || LoginDate == "")
//                    {
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                         + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                         + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                         + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请先登录！";
//                        resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                    }
//                    else
//                    {
//                        //返回10以内条
//                        List<ListMsg> lm = new List<ListMsg>();
//                        lm = DBMsg.GetInfoList(UserGuid, 7, "", 1,"104");
//                        int size = lm.Count + 2;
//
//                        resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName + "]]>"
//                          + "</FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now) + "</CreateTime><MsgType><![CDATA[news]]></MsgType><Content><![CDATA[]]></Content><ArticleCount>" + size + "</ArticleCount><Articles>";
//
//                        resxml += "<item><Title><![CDATA[内部通知]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA["
//                            + Global.GetInstance().HostUrl + "Images/logo.gif]]></PicUrl><Url><![CDATA[]]></Url></item>";
//
//                        foreach (var item in lm)
//                        {
//                            resxml += "<item><Title><![CDATA[" + Convert.ToString("[" + item.CategoryNameList + "]") + ":" + Convert.ToString(item.MsgTitle) + "]]></Title><Description><![CDATA[]]>"
//                                + "</Description><PicUrl><![CDATA[]]></PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "Pages/WebInfoDetail.aspx?MsgGuid="
//                             + CommonDesc.Encrypt(Convert.ToString(item.MsgGuid)) + "&InfoType=104]]></Url></item>";
//                        }
//                        resxml += "<item><Title><![CDATA[查看更多]]></Title><Description><![CDATA[]]></Description><PicUrl><![CDATA[]]>"
//                           + "</PicUrl><Url><![CDATA[" + Global.GetInstance().HostUrl + "/Pages/MoreListInfo.aspx?InfoType=104&UserGuid=" + CommonDesc.Encrypt(UserGuid) + "]]></Url></item>";
//                        resxml += "</Articles><FuncFlag>1</FuncFlag></xml>";
//                    }
//                }
//
//                else if (requestXML.EventKey == "V001_NO_OPEN")
//                {
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                     + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                     + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                     + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，该功能尚未开通，请访问其他功能！";
//                    resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                }
//                else
//                {
//                    resxml = "<xml><ToUserName><![CDATA[" + requestXML.FromUserName
//                     + "]]></ToUserName><FromUserName><![CDATA[" + requestXML.ToUserName
//                     + "]]></FromUserName><CreateTime>" + ConvertDateTimeInt(DateTime.Now)
//                     + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[欢迎您关注【" + Global.GetInstance().WXPTName + "】，请通过点击菜单访问平台功能！";
//                    resxml += "]]></Content><FuncFlag>0</FuncFlag></xml>";
//                }
//            }
//            Response.Write(resxml);
//        }
//        catch (Exception ex)
//        {
//            WriteTxt("异常：" + ex.Message + "Struck:" + ex.StackTrace.ToString());
//        }
//    }
}
