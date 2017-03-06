package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.email.EmailUtil;
import com.hzitshop.email.MailSenderInfo;
import com.hzitshop.email.SimpleMailSender;
import com.hzitshop.entity.*;
import com.hzitshop.service.*;
import com.hzitshop.util.Md5Util;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.BootstrapEntity;
import com.hzitshop.vo.EmployeeInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Controller
public class EmployeeInfoController {
    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    @Autowired
    private ITbDictService iTbDictService;
    @Autowired
    private ITbMenuAppService iTbMenuAppService;
    @Autowired
    private ITbMenuService iTbMenuService;
    @Autowired
    private ITbRoleService iTbRoleService;
    /**
     * 跳转到用户主页
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:index"})
    @RequestMapping("/employeeInfo/index")
    protected String index(){
        return  "/employeeInfo/index";
    }

    /**
     * 获取数据
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:getAjaxData"})
    @RequestMapping("/employeeInfo/getAjaxData")
    @ResponseBody
    protected BootstrapTable<EmployeeInfoVo> getAjaxData(BootstrapEntity bt){
        if (bt.getOffset() == null || bt.getLimit() == null) {
            bt.setOffset(1);
            bt.setLimit(20);
        } else {
            bt.setOffset(bt.getOffset() / bt.getLimit());
        }
       BootstrapTable<EmployeeInfoVo> bootstrapTable = iEmployeeInfoService.ajaxData(new Page<EmployeeInfo>(
               bt.getOffset()+1,bt.getLimit()),
               new EntityWrapper<EmployeeInfo>());
        /*BootstrapTable bootstrapTable = new BootstrapTable();
        bootstrapTable.setRows(page.getRecords());
        bootstrapTable.setTotal(page.getTotal());*/
        return bootstrapTable;
    }

    /**
     * 跳转到个人信息修改页
     * @param employeeInfo
     * @param model
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:personInfo"})
    @RequestMapping("/employeeInfo/personInfo")
    protected String personInfo(EmployeeInfo employeeInfo, Model model){
       employeeInfo =iEmployeeInfoService.selectOne(new EntityWrapper<EmployeeInfo>().
               where("user_id="+employeeInfo.getUserId()));
       model.addAttribute("employeeInfo",employeeInfo);
        return "/employeeInfo/personInfo";
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequiresPermissions(value = {"employeeInfo:edit"})
    @RequestMapping(value="/employeeInfo/edit",method = RequestMethod.GET)
    protected String edit(String userId,Model model){
        //根据id找到该用户的数据并显示在页面中
        EmployeeInfo employeeInfo = iEmployeeInfoService.selectOne(new EntityWrapper<EmployeeInfo>().where("user_id="+userId));
        model.addAttribute("employeeInfo",employeeInfo);
        //获取公司信息
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pid",35); //获取所有pid为35的数据
        List<TbDict> tbDictList = iTbDictService.selectByMap(paramMap);
        model.addAttribute("tbDictList",tbDictList);
        return "/employeeInfo/edit";
    }

    /**
     * 保存信息
     * @param employeeInfo
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:edit"})
	@RequestMapping(value="/employeeInfo/edit",method=RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> edit(EmployeeInfo employeeInfo,HttpServletRequest request){
	    Map<String,Object> resultMap = new HashMap<>();
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo1 = (EmployeeInfo)httpSession.getAttribute("employeeInfo");
        employeeInfo.setUpdateBy(employeeInfo1.getName());
        employeeInfo.setUpdateTime(new Date());
	    try{
            iEmployeeInfoService.updateById(employeeInfo);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }catch (Exception e){
	        //e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("msg","保存失败!");
        }
	   
        return resultMap;
    }

    /**
     * 跳转到用户添加页面
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:add"})
    @RequestMapping(value="/employeeInfo/add",method=RequestMethod.GET)
    public String add(Model model){
        //获取公司信息
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("pid",35); //获取所有pid为35的数据
        List<TbDict> tbDictList = iTbDictService.selectByMap(paramMap);
        model.addAttribute("tbDictList",tbDictList);
        return "/employeeInfo/add";
    }

    /**
     * 请求方式为post请求
     * 获取保存数据，返回操作结果
     * code:200 表示成功   300表示操作失败
     * msg:表示操作结果信息
     * 密码是随机生成的，通过通过发送邮件的形式发送到用户的邮箱!!
     * @param employeeInfo
     * @return
     */
    @RequiresPermissions(value="employeeInfo:add")
    @RequestMapping(value="/employeeInfo/add",method=RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> add(EmployeeInfo employeeInfo, HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();
        HttpSession httpSession = request.getSession();
        EmployeeInfo employeeInfo1 = (EmployeeInfo) httpSession.getAttribute("employeeInfo");
        Random random = new Random();
        int randomValue = random.nextInt(1000000);
        String sendEmilMsg ="";
        try {
            employeeInfo.setPassword(Md5Util.getMD5(Md5Util.getMD5("hzit#"+randomValue)));
            //发送邮件!!
            sendEmilMsg = EmailUtil.sendEmail("","",employeeInfo.getEmail(),
                    "合众艾特咨询系统登录用户名:"+employeeInfo.getName()+" 密码:"+randomValue);//发送随机密码
            //employeeInfo.setRoleIds("");
            employeeInfo.setCreateBy(employeeInfo1.getName());//录入人
            employeeInfo.setCreateTime(new Date());
            if("成功".equals(sendEmilMsg)){
                employeeInfo.setIsEmailActive("1");
            }
            iEmployeeInfoService.insert(employeeInfo);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!+发送邮件:"+sendEmilMsg);
        } catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("msg","保存失败!+发送邮件"+sendEmilMsg);
        }
        return resultMap;
    }

    /**
     * /启用/禁用用户
     * @return
     */
    @RequiresPermissions(value = {"employeeInfo:locked"})
    @RequestMapping("/employeeInfo/locked")
    @ResponseBody
    protected  Map<String,Object> locked(String userIdArr,String isLocked){
        List<EmployeeInfo> listId = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isNotEmpty(userIdArr)){
           try{
               String[] idArr = userIdArr.split(",");
               for(String id : idArr){
                   listId.add(new EmployeeInfo(Integer.parseInt(id),isLocked));  //禁用
               }
               iEmployeeInfoService.updateBatchById(listId);
               resultMap.put("code",200);
               resultMap.put("msg","操作成功!");
           } catch ( Exception e){
                 resultMap.put("code",300);
                 resultMap.put("msg","操作失败!");
           }
        }
        return resultMap;
    }

    /**
     * 重置密码
     * @return
     */
    @RequiresPermissions(value={"employeeInfo:resetPwd"})
    @RequestMapping("/employeeInfo/resetPwd")
    @ResponseBody
    protected Map<String,Object> resetPwd(EmployeeInfo employeeInfo){
        Map<String,Object> resultMap = new HashMap<>();
        Random random = new Random();
        int randomValue = random.nextInt(1000000);
        String sendEmailMsg = "";
        try{
             employeeInfo.setPassword(Md5Util.getMD5(Md5Util.getMD5("hzit#"+randomValue)));
            sendEmailMsg = EmailUtil.sendEmail("","",employeeInfo.getEmail(),randomValue+"");
            iEmployeeInfoService.updateById(employeeInfo);
             resultMap.put("code",200);
             resultMap.put("msg","重置密码成功!发送邮件:"+ sendEmailMsg);
        }   catch ( Exception e){
            resultMap.put("code",300);
            resultMap.put("msg","重置密码失败!发送邮件:"+ sendEmailMsg );
        }
        return resultMap;
    }



    /**
     * 跳转到授权主页
     * @return
     */
    @RequiresPermissions("employeeInfo:grantResouce")
    @RequestMapping(value="/employeeInfo/grantResouce",method = RequestMethod.GET)
    protected String index(Model model,String userId){
        //保存到域对象中
        model.addAttribute("userId",userId);
        return  "/permission/index";
    }
    @RequiresPermissions("employeeInfo:resource")
    @RequestMapping("/employeeInfo/resource")
    @ResponseBody
    protected  List<TbMenuApp> respource(){
        //获取权限信息
        //List<TbMenuAppVo> tbMenuAppVoList =iTbMenuAppService.grantRole();
        List<TbMenuApp> tbMenuAppList = iTbMenuAppService.selectList(
                new EntityWrapper<TbMenuApp>());
        for(TbMenuApp tbMenuApp : tbMenuAppList){
            tbMenuApp.setIcon("");
        }
        return tbMenuAppList;
    }

    /**
     * 获取提交的授权数据
     * @return
     */
    @RequiresPermissions(value="employeeInfo:grantResouce")
    @RequestMapping(value="/employeeInfo/grantResouce",method = RequestMethod.POST)
    @ResponseBody
    protected Map<String,Object> grantResurce(EmployeeInfo employeeInfo,String appids){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            employeeInfo.setIsPermission("1");//权限授权
            iEmployeeInfoService.updateById(employeeInfo);
            //到tb_menu中插入数据
            TbMenu tbMenu = new TbMenu();
            tbMenu.setMenuid("m001");
           /* StringBuilder sb = new StringBuilder();
            *//*if(appids.contains("m")){
                    sb.append()
            }*//*
            String[] appidsArr = appids.split(",");
            for(String str : appidsArr){
                if(!"1".equals(str)){
                    sb.append(str);
                }
            }*/
            tbMenu.setApp(appids);
            tbMenu.setUserId(employeeInfo.getUserId());
            //iTbMenuService.insert(tbMenu);
            TbMenu tbMenu1 = iTbMenuService.selectOne(new EntityWrapper<TbMenu>()
                    .where("user_id="+employeeInfo.getUserId()));
            if(tbMenu1!=null){  //说明有数据,更新
                tbMenu1.setApp(appids);
                iTbMenuService.updateById(tbMenu1);
            }else{
                //插入数据
                iTbMenuService.insert(tbMenu);
            }
            resultMap.put("code",200);
            resultMap.put("msg","授权成功!");
        }  catch ( Exception e){
            e.printStackTrace();
            resultMap.put("code",300);
            resultMap.put("msg","授权失败");
        }
        return resultMap;
    }

    /**
     *
     * @return
     */
    @RequiresPermissions("employeeInfo:checkResource")
    @RequestMapping("/employeeInfo/checkResource")
    @ResponseBody
    protected  String checkResource(EmployeeInfo employeeInfo){
        employeeInfo = iEmployeeInfoService.selectById(employeeInfo);
        return employeeInfo.getResourceIds();
    }

    /**
     * 角色授权()
     * @return
     */
   @RequiresPermissions(value="employeeInfo:grantRole")
    @RequestMapping(value="/employeeInfo/grantRole",method = RequestMethod.GET)
    public String grantRole(Model model,String userId){
        //获取角色信息
        List<TbRole> tbRoleList = iTbRoleService.selectList(new EntityWrapper<TbRole>());
        model.addAttribute("tbRoleList",tbRoleList);
        model.addAttribute("userId",userId);
        return "/permission/grantRole";
    }
    /**
     * 授予角色
     * @return
     */
    @RequiresPermissions(value="employeeInfo:grantRole")
    @RequestMapping(value = "/employeeInfo/grantRole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> grantRole(TbRole tbRole,EmployeeInfo employeeInfo){
        Map<String,Object> resultMap = new HashMap<>();
        employeeInfo.setIsPermission("2");//角色授权
        //获取该角色的信息
        tbRole =iTbRoleService.selectById(tbRole.getId());
        if(StringUtils.isEmpty(tbRole.getResourceIds())){
            resultMap.put("code",300);
            resultMap.put("msg","该角色并没有拥有具体的权限,请问该角色分配权限!");
            return resultMap;
        }
        //修改该用户中 resource_ids数据
        employeeInfo.setResourceIds(tbRole.getResourceIds());

        //获取tb_menu_app数据
        String[] resurceIdArr  = null;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(tbRole.getResourceIds())){
            resurceIdArr = tbRole.getResourceIds().split(",");
        }
        List<TbMenuApp> tbMenuAppList = null;
        StringBuilder sb = new StringBuilder();
        if(resurceIdArr != null){
            tbMenuAppList = iTbMenuAppService.selectBatchIds(Arrays.asList(resurceIdArr));
            for(int i =0;i<tbMenuAppList.size();i++){
                if(tbMenuAppList.get(i).getAppid().contains("m")){
                    sb.append(tbMenuAppList.get(i).getAppid()+",");
                }
            }
        }
        String menuAppId = sb.toString();
        menuAppId = menuAppId.substring(0,menuAppId.length());
        //保存或更新tb_meu中的数据
        //employeeInfo = iEmployeeInfoService.selectById(employeeInfo.getUserId());
        TbMenu tbMenu = iTbMenuService.selectOne(new EntityWrapper<TbMenu>().where("user_id="+employeeInfo.getUserId()));
        TbMenu tbMenu2 =new TbMenu();

        tbMenu2.setMenuid("m001");
        tbMenu2.setUserId(employeeInfo.getUserId());
        tbMenu2.setApp(menuAppId);
        try{
            if(tbMenu!= null){//说明存在该用户
                tbMenu.setUserId(employeeInfo.getUserId());
                iTbMenuService.updateById(tbMenu);
            }else{ //没有直接插入
                iTbMenuService.insert(tbMenu2);
            }
            iEmployeeInfoService.updateById(employeeInfo);
            resultMap.put("code",200);
            resultMap.put("msg","授予角色成功!");
        } catch ( Exception e){
            e.printStackTrace();
           resultMap.put("code",300);
           resultMap.put("msg","授予角色失败!");
        }

        return resultMap;
    }

    /**
     * 获取部门信息
     * @return
     */
    @RequestMapping("/employeeInfo/getDept")
    @ResponseBody
    protected  List<TbDict> getDept(TbDict tbDict){
         List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid="+tbDict.getId()));
        return tbDictList;
    }


}
