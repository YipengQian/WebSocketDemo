package com.qyp.websocketdemo.websocket.Client;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;

/**
 * Created by qianyipeng on 2016/9/12.
 * 枚举
 * 连接协议
 * 默认为draft_17
 */
public enum  DraftProtocol {


     DRAFT_17(new Draft_17())
    ,DRAFT_10(new Draft_10())
    ,DRAFT_76(new Draft_76())
    ,DRAFT_75(new Draft_75());
    private Draft draft;

    DraftProtocol(Draft Draft) {
        this.draft = Draft;
    }

    public Draft getDraft(){
        return draft;
    }
}
