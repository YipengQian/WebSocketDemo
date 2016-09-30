package com.qyp.websocketdemo.websocket.builder;


import com.qyp.websocketdemo.websocket.Client.DefineWebSocketClient;
import com.qyp.websocketdemo.websocket.MessageCenter.DispatchMsgWatch;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qianyipeng on 2016/9/12.
 * 先总结builder需要实现的功能，使用建造者模式开发
 * 1.开启连接功能
 * 2.关闭连接功能
 * 3.设置请求头功能
 * 4.获取请求头参数
 * 5.设置使用协议功能，使用枚举类中协议
 * 6.发送文字消息的功能
 * 7.发送字节消息的功能
 * 。。后续添加
 */
public interface WebSocketBuilder {
    void connect(String url, int timeOut);
    void connect();
    void startHeartBeatThread();
    void init(String url, int timeOut);
    void setHeader(HashMap header);
    HashMap getHeader();
    void close();
    void setDraft(Draft draft);
    void sendMsg(String text);
    void sendByte(byte[] data);
    DefineWebSocketClient getConnect();
    WebSocket.READYSTATE getConnectState();
//    void setOnMsgListener(WebSocketBuilderImp.OnMsgListener mOnMsgListener);
    boolean getClientExist();
//    void setOpreateConnect(boolean isConnect);


     void addDispatchMsgWatcher(DispatchMsgWatch mDispatchMsg);

     void removeDispatchMsgWatcher(DispatchMsgWatch mDispatchMsg);

     void notifyDispatchMsgWatcher(LinkedBlockingQueue str);


}
