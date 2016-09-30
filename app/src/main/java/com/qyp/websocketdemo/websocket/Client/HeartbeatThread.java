package com.qyp.websocketdemo.websocket.Client;

import com.qyp.websocketdemo.websocket.builder.WebSocketBuilder;
import com.qyp.websocketdemo.websocket.builder.WebSocketBuilderImp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qianyipeng on 2016/9/30.
 * 后续完善
 *
 *
 */
public class HeartbeatThread extends Thread {
  @Override
  public void run() {
    super.run();

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if(!WebSocketBuilderImp.getInstance().getClientExist()){
          WebSocketBuilderImp.getInstance().connect();
        }
      }
    },5000,5000);
  }
}
