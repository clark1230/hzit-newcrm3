package com.hzitshop.vo;

import com.hzitshop.entity.TbMenu;
import com.hzitshop.entity.TbMenuApp;

import java.util.List;
import java.util.Map;

/**
 * Created by xianyaoji on 2017/2/8.
 */
public class DesktpData {
    private List<Menu> menu;
    private Map<String,TbMenuApp> apps;


    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public Map<String, TbMenuApp> getApps() {
        return apps;
    }

    public void setApps(Map<String, TbMenuApp> apps) {
        this.apps = apps;
    }
}
