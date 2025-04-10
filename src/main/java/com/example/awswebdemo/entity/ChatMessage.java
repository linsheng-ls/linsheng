package com.example.awswebdemo.entity;

public class ChatMessage {
    private String sender;   // 发消息的人
    private String content;  // 消息内容

    // 空构造器
    public ChatMessage() {}

    // 带参数构造器
    public ChatMessage(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    // Getter 和 Setter
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
