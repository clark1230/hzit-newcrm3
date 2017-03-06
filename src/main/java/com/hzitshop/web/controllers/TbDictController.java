package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.ITbDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
public class TbDictController {

    @Autowired
    private ITbDictService iTbDictService;
    @RequiresPermissions("dict:index")
    @RequestMapping("/dict/index")
    protected String index(){
        return "/dict/index";
    }

    /**
     * 获取数据字典数据
     * @return
     */
    @RequiresPermissions("dict:getTreeList")
    @RequestMapping("/dict/getTreeList")
    @ResponseBody
    protected List<TbDict> getTreeList(){
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>());
        return tbDictList;
    }

    /**
     * 根据父编号获取信息
     * @param tbDict
     * @return
     */
    //@RequiresPermissions("dict:getCustomerState")
    @RequestMapping("/dict/getCustomerState")
    @ResponseBody
    protected  List<TbDict> getDict(TbDict tbDict){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pid",tbDict.getpId());
        return iTbDictService.selectByMap(paramMap);
    }

    /**
     * 跳转到数据字典添加页
     * @return
     */
    @RequiresPermissions("dict:add")
    @RequestMapping(value="/dict/add",method = RequestMethod.GET)
    protected String add(TbDict tbDict, ModelMap modelMap){
        return "/dict/add";
    }
    @RequiresPermissions("dict:add")
    @RequestMapping(value="/dict/add",method = RequestMethod.POST)
    @ResponseBody
    protected  Map<String,Object> add(TbDict tbDict){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            iTbDictService.insert(tbDict);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }  catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("code","保存失败!");
        }
        return resultMap;
    }

    /**
     * 跳转到修改数据字典页
     * @param modelMap
     * @param tbDict
     * @return
     */
    @RequiresPermissions("dict:edit")
    @RequestMapping(value="/dict/edit",method = RequestMethod.GET)
    protected String edit(ModelMap modelMap,TbDict tbDict){
        tbDict = iTbDictService.selectById(tbDict.getId());
        modelMap.addAttribute("tbDict",tbDict);
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>());
        modelMap.addAttribute("tbDictList",tbDictList);
        return "/dict/edit";
    }

    /**
     * 保存编辑信息
     * @param tbDict
     * @return
     */
    @RequiresPermissions("dict:edit")
    @RequestMapping(value="/dict/edit",method=RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> edit(TbDict tbDict){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            iTbDictService.updateById(tbDict);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        } catch (Exception e){
           e.printStackTrace();
           resultMap.put("code",300);
           resultMap.put("msg","保存失败!");
        }
        return resultMap;
    }

    /**
     * 彻底删除数据字典
     * @param ids
     * @return
     */
    @RequiresPermissions("dict:delete")
    @RequestMapping("/dict/delete")
    @ResponseBody
    protected  Map<String,Object> delete(String ids){
        Map<String,Object> reslutMap = new HashMap<>();
        String[] idArr = ids.split(",");

        try{
            if(idArr!=null){
                iTbDictService.deleteBatchIds(Arrays.asList(idArr));//批量删除
                reslutMap.put("code",200);
                reslutMap.put("msg","删除成功!");
            }else{
                reslutMap.put("code",300);
                reslutMap.put("msg","删除失败!");
            }

        } catch (Exception e){
           reslutMap.put("code",200);
           reslutMap.put("msg","删除失败!");
        }
        return reslutMap;
    }
}
