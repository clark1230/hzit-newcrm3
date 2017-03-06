package com.hzitshop.web.controllers;

import com.hzitshop.entity.TbMenu;
import com.hzitshop.service.ITbMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-07
 */
@Controller
public class TbMenuController {
    @Autowired
    private ITbMenuService iTbMenuService;
    @RequestMapping("/Menu/list")
    @ResponseBody
    protected List<TbMenu> list() {
        return iTbMenuService.selectByMap(null);
    }
}
