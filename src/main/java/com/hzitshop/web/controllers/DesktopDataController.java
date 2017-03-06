package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbMenu;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.service.ITbMenuAppService;
import com.hzitshop.service.ITbMenuService;
import com.hzitshop.vo.DesktpData;
import com.hzitshop.vo.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by xianyaoji on 2017/2/8.
 */
@Controller
public class DesktopDataController {
    @Autowired
    private ITbMenuService iTbMenuService;
    @Autowired
    private ITbMenuAppService iTbMenuAppService;
    /**
     * 获取桌面数据
     *
     * @return
     */
    //@RequiresPermissions(value = {""})
    @RequestMapping("/getDesktopData")
    @ResponseBody
    protected DesktpData getDesktopData(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        DesktpData desktpData = new DesktpData();
        //根据用户编号获取桌面数据
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("user_id",employeeInfo.getUserId());
        List<TbMenu> tbMenuList = iTbMenuService.selectByMap(paramMap);//iTbMenuService.selectByMap(null);
        List<Menu> menuList = new ArrayList<>();
        Menu menu = null  ;
        List<String> menuAppId = new ArrayList<>();
        
        if(tbMenuList != null && tbMenuList.size() >0){
            for(TbMenu tbMenu : tbMenuList){
                menu = new Menu();
                String[] id = tbMenu.getApp().split(",");
                menu.setApp(id);
                menuAppId = Arrays.asList(id);
                BeanUtils.copyProperties(tbMenu,menu);
                menuList.add(menu);
                menuList.add(menu);
            }
        }
        //根据 tb_menu中的app获取数据
        List<TbMenuApp> tbMenuAppList = new ArrayList<>();

        for(String str: menuAppId){
            TbMenuApp tbMenuApp = iTbMenuAppService.selectOne(new EntityWrapper<TbMenuApp>().where("appid ='"+str+"'"));
            if(!"1".equals(tbMenuApp.getAppid())){
                tbMenuAppList.add(tbMenuApp);
            }
        }
        Map<String,TbMenuApp> map= new HashMap<>();
        if(tbMenuAppList!= null && tbMenuAppList.size() >0){
            for(TbMenuApp tbMenuApp : tbMenuAppList){
                if(tbMenuApp!=null){
                    map.put(tbMenuApp.getAppid(),tbMenuApp);
                }
            }
        }
        //map.put(tbMenuAppList)
        desktpData.setMenu(menuList);
        desktpData.setApps(map);
        return desktpData;
    }


}
