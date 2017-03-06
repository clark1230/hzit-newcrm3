package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.TbDict;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.entity.TbRole;
import com.hzitshop.service.ITbMenuAppService;
import com.hzitshop.service.ITbMenuService;
import com.hzitshop.service.ITbRoleService;
import com.hzitshop.util.StringUtil;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.TbRoleVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by xianyaoji on 2017/3/3.
 */
@Controller
public class TbRoleController {
    private Logger logger = LoggerFactory.getLogger(TbRoleController.class);
   @Autowired
   ITbRoleService iTbRoleService;

   @Autowired
    ITbMenuAppService iTbMenuAppService;

   @Autowired
    ITbMenuService iTbMenuService;

    /**
     * 跳转到角色列表页
     * @return
     */
   @RequestMapping(value="/role/index")
    protected  String index(){
       return "/role/index";
   }

    /**
     * 获取角色信息
     * @return
     */
    @RequestMapping(value="/role/ajaxData")
    @ResponseBody
    protected BootstrapTable<TbRoleVo> ajaxData(BootstrapEntity bt){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }

        BootstrapTable<TbRoleVo> bootstrapTable = iTbRoleService.ajaxData(
                new Page<TbRole>(bt.getOffset()+1,bt.getLimit()),
                new EntityWrapper<TbRole>());

        return bootstrapTable;
   }

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping(value="/role/add",method = RequestMethod.GET)
    public  String add(){
        return "/role/add";
    }

    /**
     * 保存新增角色信息
     * @param tbRole
     * @return
     */
    @RequestMapping(value="/role/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(TbRole tbRole, Integer userId){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            tbRole.setCreateBy(userId);   //录入人
            tbRole.setCreateTime(new Date()); //录入时间
            iTbRoleService.insert(tbRole);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        } catch ( Exception e){
            resultMap.put("code",300);
            resultMap.put("msg","保存失败!");
        }
        return resultMap;
   }

    /**
     * 跳转到编辑页
     * @return
     */
   @RequestMapping(value="/role/edit",method = RequestMethod.GET)
    protected  String edit(Integer id, Model model){
       TbRole tbRole = iTbRoleService.selectById(id);
       //保存到域对象中
       model.addAttribute("tbRole",tbRole);
        return "/role/edit";
   }

    /**
     * 保存编辑信息
     * @param tbRole
     * @return
     */
    @RequestMapping(value="/role/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(TbRole tbRole,Integer userId){
       Map<String,Object> resultMap = new HashMap<>();
       try{
           tbRole.setUpdateTime(new Date());
           tbRole.setUpdateBy(userId);
           iTbRoleService.updateById(tbRole);
           resultMap.put("code",200);
           resultMap.put("msg","保存成功!");
       }catch ( Exception e){
           resultMap.put("code",300);
           resultMap.put("msg","保存失败!");
       }
        return resultMap;
   }
      
    /**
     * 禁用角色
     * @return
     */
   @RequestMapping("/role/available")
   @ResponseBody
   protected Map<String,Object> available(String roleIds,Integer available){
        Map<String,Object> resultMap = new HashMap<>();
        String[] idArr = null;
        if(StringUtils.isNotEmpty(roleIds)){
            idArr = roleIds.split(",");
        }else{
            resultMap.put("code",300);
            resultMap.put("msg","请选择要禁用的角色!");
        }
        TbRole tbRole = null;
        if(idArr!=null){
            try{
                for(String id : idArr){
                    tbRole = new TbRole();
                    tbRole.setAvailable(available);
                    tbRole.setId(Integer.parseInt(id));
                    iTbRoleService.updateById(tbRole);
                }
                resultMap.put("code",200);
                resultMap.put("msg","操作成功!");
            } catch (Exception e){
                e.printStackTrace();
                resultMap.put("code",300);
                resultMap.put("msg","禁用失败!");
            } 
        }else{
            resultMap.put("code",300);
            resultMap.put("msg","服务器内部错误!!");
        }
        return resultMap;
   }

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    @RequestMapping(value="/role/delete")
    @ResponseBody
    protected  Map<String,Object> delete(String roleIds){
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isEmpty(roleIds)){
            resultMap.put("code",300);
            resultMap.put("msg","请选择要删除的角色!");
        }
        String[] idArr = roleIds.split(",");
        if(idArr!=null){
          try{
              iTbRoleService.deleteBatchIds(Arrays.asList(idArr));
              resultMap.put("code",200);
              resultMap.put("msg","删除成功!");
          }  catch (Exception e){
              resultMap.put("code",300);
              resultMap.put("msg","删除失败!");
          }
        } else{
            resultMap.put("code",300);
            resultMap.put("msg","服务器错误!");
        }
        return resultMap;
   }

    /**
     * 跳转到权限授权页
     * @return
     */
   @RequestMapping(value="/role/grantResource",method = RequestMethod.GET)
    protected String grantResource(TbRole tbRole, Model model){
       model.addAttribute("id",tbRole.getId());//保存角色编号
        return "/role/grantResoruce";
   }

    /**
     * 保存权限信息
     * @param tbRole
     * @return
     */
    @RequestMapping(value="/role/grantResource",method = RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object>  grantResource(TbRole tbRole){
       Map<String,Object> resultMap = new HashMap<>();
       try{
           //更新数据
           iTbRoleService.updateById(tbRole);
           resultMap.put("code",200);
           resultMap.put("msg","授权成功!");
       }  catch (Exception e){
           resultMap.put("code",300);
           resultMap.put("msg","授权失败！");
       }
        return resultMap;
   }

    /**
     * 数据回显
     * @param tbRole
     * @return
     */
   @RequestMapping("/role/checkResource")
   @ResponseBody
   public List<String> checkResource(TbRole tbRole){
        tbRole = iTbRoleService.selectById(tbRole.getId());
        List<String> resourceId =null;
        if(StringUtils.isNotEmpty(tbRole.getResourceIds())){
             String[] idArr = tbRole.getResourceIds().split(",");
              resourceId = Arrays.asList(idArr);
        }
        return resourceId;
   }

}
