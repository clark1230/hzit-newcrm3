package com.hzitshop.email;

import java.util.Properties;

/**
 * Created by xianyaoji on 2017/2/26.
 */
public class MailSenderInfo {
    public static MailSenderInfo getDetault()
    {
        MailSenderInfo mailInfo=new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        // 邮箱用户名
        mailInfo.setUserName("邮箱用户名");
        // 邮箱密码
        mailInfo.setPassword("密码");
        // 发件人邮箱
        mailInfo.setFromAddress("st@hzitxx.com");
        // 收件人邮箱
        mailInfo.setToAddress("zixunshi@hzitxx.com");
        return mailInfo;
    }

    public static MailSenderInfo getDetault(String fromAddress, String toAddress)
    {
        MailSenderInfo mailInfo=new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        // 邮箱用户名
        mailInfo.setUserName("邮箱用户名");
        // 邮箱密码
        mailInfo.setPassword("密码");
        // 发件人邮箱
        mailInfo.setFromAddress(fromAddress);
        // 收件人邮箱
        mailInfo.setToAddress(toAddress);
        return mailInfo;
    }

    // 发送邮件的服务器的IP(或主机地址)
    private String mailServerHost;
    // 发送邮件的服务器的端口
    private String mailServerPort = "25";
    // 发件人邮箱地址
    private String fromAddress;
    // 收件人邮箱地址
    private String toAddress;
    // 登陆邮件发送服务器的用户名
    private String userName;
    // 登陆邮件发送服务器的密码
    private String password;
    // 是否需要身份验证
    private boolean validate = false;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;

    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }
    public String getMailServerHost() {
        return mailServerHost;
    }
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }
    public String getMailServerPort() {
        return mailServerPort;
    }
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    public boolean isValidate() {
        return validate;
    }
    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    public String[] getAttachFileNames() {
        return attachFileNames;
    }
    public void setAttachFileNames(String[] fileNames) {
        this.attachFileNames = fileNames;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String textContent) {
        this.content = textContent;
    }
}
