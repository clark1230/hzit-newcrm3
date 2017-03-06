package com.hzitshop.vo;

import java.util.List;

/**
 * Created by xianyaoji on 2017/2/8.
 */
public class Menu {
    private String menuid;
    //    private int id;
    private String name;
    private String[] app;


    public Menu(){
        
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String[] getApp() {
        return app;
    }

    public void setApp(String[] app) {
        this.app = app;
    }
}
