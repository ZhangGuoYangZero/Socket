package commom;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final  long serialVersionUID  = 1L;
    //发送者， 接收者， 内容 ,时间,类型
    private  String sender;
    private  String getSender;
    private  String content;
    private  String sendTime;
    private  String msgType;//在接口中定义已知的消息类型

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public  Message(){}

    public Message(String sender, String getSender, String content, String sendTime) {
        this.sender = sender;
        this.getSender = getSender;
        this.content = content;
        this.sendTime = sendTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetSender() {
        return getSender;
    }

    public void setGetSender(String getSender) {
        this.getSender = getSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
