package com.qyp.websocketdemo.websocket.MessageCenter;


/**
 * Created by qianyipeng on 2016/9/13.
 */
public class MsgBean {

    /**
     * Msg即时消息分类
     */
    //信息type分类
    public static final int IM_TYPE_TEXT = 1;//普通文本信息
    public static final int IM_TYPE_IMG = 2;//图片信息
    public static final int IM_TYPE_VOICE = 3;//语音信息
    public static final int IM_TYPE_VIDEO = 4;//视频信息
    //大类信息分类
    public static final int IM_THEME_POINT = 1;//点对点聊天信息
    public static final int IM_THEME_THEME = 2;//主题推送信息
    public static final int IM_THEME_PERSON = 3;//个人信息更新信息

    //消息类型，1:普通文本 2:图片 3:音频 4:视频
    private int type;
    //发送者用户Id
    private String from;
    //接收者用户Id
    private String to;
    //发送的文本
    private String text;
    //发送日期
    private String date;
    //1:用戶点对点消息  2:主题广告推送  3 用户个人信息更新
    private int theme;

    private String fromName;



    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
            return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MsgBean [type=" + type + ", from=" + from + ", to=" + to + ", text=" + text + ", date=" + date + "]";
    }


}
