package com.hzitshop.vo;

/**
 * Created by xianyaoji on 2017/2/8.
 */
public class Apps {

    private String appid;
    private int isicon;
    private String icon;      //图片
    private String iconbg;   //图标背景
    private String name;//图片名称
    private String url;//模块地址
    private String height;//高度
    private String width;//宽度

    public Apps() {

    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getIsicon() {
        return isicon;
    }

    public void setIsicon(int isicon) {
        this.isicon = isicon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconbg() {
        return iconbg;
    }

    public void setIconbg(String iconbg) {
        this.iconbg = iconbg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Apps{" +
                "appid='" + appid + '\'' +
                ", isicon=" + isicon +
                ", icon='" + icon + '\'' +
                ", iconbg='" + iconbg + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                '}';
    }
}
