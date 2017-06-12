package com.zjf.action;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.Broadcaster;

@ManagedService(path = "/websocket/test", atmosphereConfig = {
        "org.atmosphere.cpr.CometSupport.maxInactiveActivity=120000" })
public class TestWebSocket
{
    protected Logger log = Logger.getLogger(getClass());

    @Inject
    @Named("/websocket/test")
    private static Broadcaster broadcaster;

    @Ready
    public void onReady() {
        log.info("有用户接入");
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        log.info("有用户退出");
    }

    /**
     * 接收到消息
     */
    @Message
    public void onMessage(String data) throws IOException {
        if (data.startsWith("后台返回:"))
            return;
        log.info("收到消息：" + data);

        broadcaster.broadcast("后台返回:" + data);
    }
}
