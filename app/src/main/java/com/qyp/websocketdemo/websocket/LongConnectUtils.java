package com.qyp.websocketdemo.websocket;

import com.qyp.websocketdemo.websocket.builder.WebSocketBuilderImp;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;

import java.util.HashMap;

/**
 * Created by qianyipeng on 2016/9/12.
 * 对外开放接口
 */
public class LongConnectUtils  {


    /**建立连接
     *
     */
    public static void startConnect() {
        if(!WebSocketBuilderImp.getInstance().getConnectState().equals(WebSocket.READYSTATE.OPEN)) {
            WebSocketBuilderImp.getInstance().connect();
        }

    }


    /**
     * 关闭连接
     */
    public static void closeConnect() {
        if(WebSocketBuilderImp.getInstance().getConnectState().equals(WebSocket.READYSTATE.OPEN)) {
            WebSocketBuilderImp.getInstance().close();
        }
    }


    /**
     * 设置新的连接头部信息
     * @param header
     */
    public static void setHeader(HashMap header) {
        WebSocketBuilderImp.getInstance().setHeader(header);
    }





    /**
     * 向服务器发送普通文本消息
     * @param msg
     * @return
     */
    public static boolean sendMsg(String msg){
        if( WebSocketBuilderImp.getInstance().getClientExist()) {
            WebSocketBuilderImp.getInstance().sendMsg(msg);
            return true;
        }else {
            return false;
        }
    }


    /**
     * 向服务器发送byte[]数组消息
     * @param data
     * @return
     */
    public static boolean sendByte(byte[] data){
        if( WebSocketBuilderImp.getInstance().getClientExist()) {
            WebSocketBuilderImp.getInstance().sendByte(data);
            return true;
        }else {
            return false;
        }
    }


    /**
     * 设置连接协议
     * @param draft
     * @return
     */
    public static boolean setDraft(Draft draft){
        if( WebSocketBuilderImp.getInstance().getClientExist()) {
            WebSocketBuilderImp.getInstance().setDraft(draft);
            return true;
        }else {
            return false;
        }
    }
}
