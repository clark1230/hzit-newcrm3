package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.service.ITbMenuAppService;
import com.hzitshop.service.ITbMenuService;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.BootstrapEntity;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-07
 */
@Controller
public class TbMenuAppController {
    @Autowired
    private ITbMenuService iTbMenuService;
    @Autowired
    private ITbMenuAppService iTbMenuAppService;

    /**
     * 跳转到图标列表
     *
     * @return
     */
    @RequestMapping("/menuApp/list")
    protected String list() {
        return "/menuapp/list";
    }


    @RequestMapping("/menuApp/getPageData")
    @ResponseBody
    protected BootstrapTable<TbMenuApp> getPageData(BootstrapEntity bootstrapEntity) {
        Page<TbMenuApp> page = iTbMenuAppService.selectPage(new Page<TbMenuApp>(bootstrapEntity.getOffset()/bootstrapEntity.getLimit()+1,
                        bootstrapEntity.getLimit()), new EntityWrapper<TbMenuApp>().where("appid like'm%'"));

        BootstrapTable<TbMenuApp> bootstrapDatable = new BootstrapTable<>();
        bootstrapDatable.setTotal( page.getTotal());
        bootstrapDatable.setRows(page.getRecords());

        return bootstrapDatable;
    }

    /**
     * 跳转到
     * @return
     */
    @RequestMapping(value = "/menuApp/addMenuApp",method = RequestMethod.GET)
    protected String addMenuApp(){
        return "/menuapp/addMenuApp";
    }
    @RequestMapping(value="/menuApp/addMenuApp",method = RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> addMenuApp(TbMenuApp tbMenuApp){
        Map<String,Object> resultMap = new HashedMap();
        try{
            iTbMenuAppService.insert(tbMenuApp);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }  catch (Exception e){
            resultMap.put("code",300);
            resultMap.put("msg","保存失败!");
            e.printStackTrace();
        }
        return resultMap;
    }
}
