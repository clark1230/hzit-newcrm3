package com.hzitshop.vo;

/**
 * Created by xianyaoji on 2017/2/27.
 */
public class Email {
    private String from;  //发送者邮箱地址
    private String fromPwd;  //发送者邮箱密码
    private String to; //接受者邮箱地址
    private String emailContent;

    public Email(){

    }

    public Email(String from, String fromPwd, String to, String emailContent) {
        this.from = from;
        this.fromPwd = fromPwd;
        this.to = to;
        this.emailContent = emailContent;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromPwd() {
        return fromPwd;
    }

    public void setFromPwd(String fromPwd) {
        this.fromPwd = fromPwd;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", fromPwd='" + fromPwd + '\'' +
                ", to='" + to + '\'' +
                ", emailContent='" + emailContent + '\'' +
                '}';
    }
}
