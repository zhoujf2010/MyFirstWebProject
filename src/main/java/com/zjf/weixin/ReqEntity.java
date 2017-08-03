package com.zjf.weixin;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ReqEntity
{
    //消息接收方微信号，一般为公众平台账号微信号
    private String toUserName;

    //消息发送方微信号
    private String fromUserName;

    //创建时间
    private String createTime;

    //信息类型 地理位置:location,文本消息:text,消息类型:image
    private String msgType;

    //信息内容
    private String content;

    //事件的 KEY
    private String eventKey;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
    
    
    public static ReqEntity getInstance(byte[] bts){
        ReqEntity instance =new ReqEntity();
        Document document = null;
        try {
            document = DocumentHelper.parseText(new String(bts));
        }
        catch (DocumentException e1) {
            e1.printStackTrace();
        }

        Element rootElement = document.getRootElement();
        Element MsgType = rootElement.element("MsgType");//类型
        String MsgTypeValue = MsgType.getText();
        instance.setToUserName(rootElement.element("ToUserName").getText());//开发者微信号
        instance.setFromUserName(rootElement.element("FromUserName").getText());//发送方账号(openid)
        instance.setCreateTime(rootElement.element("CreateTime").getText());//创建时间
        instance.setMsgType(MsgTypeValue);

        if ("text".equals(MsgTypeValue))//文本
        {
            instance.setContent(rootElement.element("Content").getText());
        }
        else if ("event".equals(MsgTypeValue))//事件
        {
            instance.setEventKey(rootElement.element("EventKey").getText());
        }
        return instance;
        
    }

}
