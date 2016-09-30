package com.qyp.websocketdemo.websocket.Client;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;

import java.net.URI;
import java.util.Map;

/**
 * Created by qianyipeng on 2016/9/12.
 * webSocket客户端
 */
public abstract class DefineWebSocketClient extends WebSocketClient {



    public DefineWebSocketClient(URI serverURI) {
        super(serverURI);
        initWebSocketClient();
    }



    public DefineWebSocketClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
        initWebSocketClient();
    }


    /**
     * 需要请求头，后续代码将全部使用该构造方法创建
     * @param serverUri
     * @param draft
     * @param headers
     * @param connecttimeout
     */
    public DefineWebSocketClient(URI serverUri, Draft draft, Map<String, String> headers, int connecttimeout) {
        super(serverUri, draft, headers, connecttimeout);
        initWebSocketClient();

    }

    /**
     * 创建webSocket连接时，初始化配置
     */
    private void initWebSocketClient() {
        //设置debug模式
        WebSocketImpl.DEBUG = true;
        //指定不获取ipv6地址
        System.setProperty("java.net.preferIPv6Addresses", "false");
        //指定获取ipv4地址
        System.setProperty("java.net.preferIPv4Stack", "true");
    }
}
