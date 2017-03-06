package com.hzitshop.web.controllers;

import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbMenuAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 根据来显示用户所能点击的按钮
 * Created by xianyaoji on 2017/3/3.
 */
@Controller
public class ShowButtonController {
    @Autowired
    private ITbMenuAppService iTbMenuAppService;
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;

    /**
     * 前端页面的按钮默认是隐藏的，根据后台返回的数据
     * 来显示按钮
     *
     * 返回的是用户的所能访问的按钮资源
     * @return
     */
    @RequestMapping(value="/showButton/show",method = RequestMethod.GET)
    @ResponseBody
    protected List<String> show(EmployeeInfo employeeInfo){
        List<String> buttonList = iEmployeeInfoService.getButtonResource(employeeInfo);
        return buttonList;
    }
}
