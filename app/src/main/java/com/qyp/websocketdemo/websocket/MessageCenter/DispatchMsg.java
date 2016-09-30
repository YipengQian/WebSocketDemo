package com.qyp.websocketdemo.websocket.MessageCenter;



import com.qyp.websocketdemo.websocket.imevent.ImPersonInfoEvent;
import com.qyp.websocketdemo.websocket.imevent.ImPointToPointInfoEvent;
import com.qyp.websocketdemo.websocket.imevent.ImThemeInfoEvent;

import java.util.concurrent.LinkedBlockingQueue;

import de.greenrobot.event.EventBus;

/**
 * Created by qianyipeng on 2016/9/14.
 * 消息分发
 * 如需进行对消息处理，需使用MsgOperation类中进行解耦操作
 */
public class DispatchMsg implements DispatchMsgWatch {

    /**
     * 对消息进行分类发送
     * @param str
     */
    @Override
    public void update(LinkedBlockingQueue<MsgBean> str) {
        for(int  i=0;i<str.size();i++) {
            MsgBean msgBean = null;
            try {
                msgBean = str.take();
                //更人信息更新
                if (msgBean.getTheme() == MsgBean.IM_THEME_PERSON) {
                    EventBus.getDefault().post(new ImPersonInfoEvent(msgBean));
                    return;
                }

                if (msgBean.getTheme() == MsgBean.IM_THEME_POINT) {
                    EventBus.getDefault().post(new ImPointToPointInfoEvent(msgBean));
                    return;
                }

                if (msgBean.getTheme() == MsgBean.IM_THEME_THEME) {
                    EventBus.getDefault().post(new ImThemeInfoEvent(msgBean));
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }




}
