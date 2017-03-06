package com.hzitshop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xianyaoji on 2017/2/7.
 */
@Controller
public class IndexController {
    @RequestMapping(value={"/","/index"},method = RequestMethod.GET)
    protected String index(){
        /**
         * 用户登录时获取用户所能访问的app图标
         */
        return "index";
    }

    @RequestMapping("/themes")
    protected String themes(){
         return "themes";
    }

}
