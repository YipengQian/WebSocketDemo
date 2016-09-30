package com.qyp.websocketdemo.websocket.imevent;


import com.qyp.websocketdemo.websocket.MessageCenter.MsgBean;

/**
 * Created by qianyipeng on 2016/9/14.
 */
public class ImThemeInfoEvent {
    private MsgBean msgBean;

    public ImThemeInfoEvent(MsgBean msgBean) {
        this.msgBean = msgBean;
    }

    public MsgBean getMsgBean() {
        return msgBean;
    }
}
