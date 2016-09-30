package com.qyp.websocketdemo.websocket.builder;

import android.os.Handler;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.qyp.websocketdemo.websocket.Client.DefineWebSocketClient;
import com.qyp.websocketdemo.websocket.Client.DraftProtocol;
import com.qyp.websocketdemo.websocket.Client.HeartbeatThread;
import com.qyp.websocketdemo.websocket.MessageCenter.DispatchMsg;
import com.qyp.websocketdemo.websocket.MessageCenter.DispatchMsgWatch;
import com.qyp.websocketdemo.websocket.MessageCenter.MsgBean;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qianyipeng on 2016/9/12.
 * 本类为Builder功能实现类，部分功能未完全实现，有新需求后续添加
 * 方便扩展，使用观察者模式，可添加多个可观察对象，
 * 本类已在构造函数中默认添加DisPatchMsg对象
 * 需在启动连接初始化url及超时时间后调用心跳检测
 */
public class WebSocketBuilderImp implements WebSocketBuilder {

  public static  String url=null;
  public static  int timeOut=0;
  private Handler handler = new Handler();
  private DefineWebSocketClient client;
  private DispatchMsg dm;
  private LinkedBlockingQueue<MsgBean> listMsg;
  private HashMap header = null;//默认请求头为空
  private Draft draft = DraftProtocol.DRAFT_17.getDraft();//默认协议为draft_17
  private List<DispatchMsgWatch> list = new ArrayList<DispatchMsgWatch>();


  private WebSocketBuilderImp() {
    dm = new DispatchMsg();
    listMsg = new LinkedBlockingQueue<>();
    addDispatchMsgWatcher(dm);
  }


  public LinkedBlockingQueue<MsgBean> getListMsg() {
    return listMsg;
  }


  /**
   * 设置全局单列
   */


  private static class Helper {
    private static final WebSocketBuilder helper = new WebSocketBuilderImp();
  }

  public static final WebSocketBuilder getInstance() {
    return Helper.helper;
  }


  /**
   * 开启连接（当关闭连接后需重新new 一个新的client，client不可复用）
   */
  @Override
  public void connect(String url, int timeOut) {
    this.url = url;
    this.timeOut =timeOut;
    Log.i("qyp", "自动重连功能");
    //重置连接状态
    client = new DefineWebSocketClient(URI.create(url), draft, getHeader(), timeOut) {
      @Override
      public void onOpen(ServerHandshake handshakedata) {
        Log.e("root_web_isConnect", "打开");
        Log.e("root_web_socket_open", handshakedata.getHttpStatus() + ">>>" + handshakedata.getHttpStatusMessage());

      }

      @Override
      public void onMessage(final String message) {
        Log.e("root_web_socket_string", message);

        new Thread(new Runnable() {
          @Override
          public void run() {
            Log.e("root_web_socket_string", message);
            if (listMsg == null) {
              listMsg = new LinkedBlockingQueue<>();
            }
            MsgBean msgBean = JSON.parseObject(message, MsgBean.class);
            try {
              listMsg.put(msgBean);
              notifyDispatchMsgWatcher(listMsg);
              Log.e("root_web_socket_string", message);
            } catch (InterruptedException e) {
              e.printStackTrace();
              Log.e("root_web_socket_string", message);
            }
          }
        }).start();


      }

      @Override
      public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        Log.e("root_web_socket_byte[]", "返回字节数组");
      }

      @Override
      public void onClose(int code, String reason, boolean remote) {
        Log.e("root_web_isConnect", "关闭" + "reason=" + reason);
      }

      @Override
      public void onError(Exception ex) {

        Log.e("root_web_socket_error", ex.getMessage());
      }
    };
    client.connect();
    header = null;
  }

  @Override
  public void connect() {
    if(url!=null){
      connect(url,timeOut);
    }else {
      Log.e("root_web_socket", "未初始化数据");
    }
  }


/**
 *  开启心跳检测
 */

  private Thread t;
  @Override
  public void startHeartBeatThread() {
    if(t==null) {
      t = new HeartbeatThread();
      t.start();
    }
  }


  /**
   * 初始化连接数据
   * @param url
   * @param timeOut
     */
  @Override
  public void init(String url, int timeOut) {
    this.url=url;
    this.timeOut=timeOut;
  }


  /**
   * 设置请求头
   *
   * @param header
   */
  @Override
  public void setHeader(HashMap header) {
    this.header = header;
  }

  /**
   * 获取请求头
   *
   * @return
   */
  @Override
  public HashMap getHeader() {
    return header;
  }


  /**
   * 关闭连接
   */
  @Override
  public void close() {
    if (getClientExist()) {
      client.close();
    }
  }

  /**
   * 设置协议类型默认为draft_17
   *
   * @param draft
   */
  @Override
  public void setDraft(Draft draft) {
    this.draft = draft;
  }


  /**
   * 发送文字消息
   *
   * @param text
   */
  @Override
  public void sendMsg(String text) {
    if (getClientExist()) {
      try {
        client.send(text);
      } catch (NotYetConnectedException e) {
        e.printStackTrace();
        Log.i("root_error", "连接失效");
      }
    }
  }

  /**
   * 发送byte数组数据
   *
   * @param data
   */
  @Override
  public void sendByte(byte[] data) {
    if (getClientExist()) {
      client.send(data);
    }
  }

  /**
   * 获取连接实例是否存在
   *
   * @param
   */
  public boolean getClientExist() {
    if (client != null) {
      if (!getConnectState().equals(WebSocket.READYSTATE.CLOSED) && !getConnectState().equals(WebSocket.READYSTATE.CLOSING)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }


  /**
   * 获取连接实例
   *
   * @return
   */
  public DefineWebSocketClient getConnect() {
    return client;
  }

  /**
   * 获取连接状态
   *
   * @return
   */
  public WebSocket.READYSTATE getConnectState() {
    if (client != null) {
      return client.getReadyState();
    }
    return WebSocket.READYSTATE.CLOSED;
  }


  /**
   * 设置观察者，进行消息提醒
   *
   * @param mDispatchMsg
   */
  @Override
  public void addDispatchMsgWatcher(DispatchMsgWatch mDispatchMsg) {
    list.add(mDispatchMsg);
  }

  @Override
  public void removeDispatchMsgWatcher(DispatchMsgWatch mDispatchMsg) {
    list.remove(mDispatchMsg);
  }

  @Override
  public void notifyDispatchMsgWatcher(LinkedBlockingQueue str) {
    for (DispatchMsgWatch mDispatchMsg : list) {
      mDispatchMsg.update(str);
    }
  }


//    private OnMsgListener mOnMsgListener;
//    public void setOnMsgListener(OnMsgListener mOnMsgListener){
//        this.mOnMsgListener = mOnMsgListener;
//    }
//
//    public interface OnMsgListener{
//        void onOpen();
//        void onClose();
//        void onError();
//    }

}
