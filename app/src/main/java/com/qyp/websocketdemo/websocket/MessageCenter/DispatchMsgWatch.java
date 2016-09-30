package com.qyp.websocketdemo.websocket.MessageCenter;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qianyipeng on 2016/9/13.
 *
 */
public interface DispatchMsgWatch {
     void update(LinkedBlockingQueue<MsgBean> str);
}
