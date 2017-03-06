package com.hzitshop.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by xianyaoji on 2017/2/26.
 */
public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;
    public MyAuthenticator() {
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}