package com.hzitshop.web.controllers;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.service.ICustomerInfoService;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbDictService;
import com.hzitshop.vo.CustomerInfoVo;
import com.hzitshop.vo.EmployeeInfoVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     前台控制器
 * </p>
 * Created by xianyaoji on 2017/3/2.
 */
@Controller
public class ForegroundController {

    @Autowired
    private IEmployeeInfoService iEmployeeInfoService;
    @Autowired
    private ICustomerInfoService iCustomerInfoService;

    @Autowired
    private ITbDictService iTbDictService;

    private Logger logger = LoggerFactory.getLogger(ForegroundController.class);
    /**
     * 跳转到前台首页
     * @return
     */
    @RequiresPermissions("foreground:index")
    @RequestMapping("/foreground/index")
    protected  String index(Model model){
        //获取所有的校区
        List<TbDict> tbDictList = iTbDictService.selectList(new EntityWrapper<TbDict>().where("pid=35"));//获取所有的校区
        model.addAttribute("tbDictList",tbDictList);
        return "/foreground/index";
    }

    /**
     * 获取咨询师数据
     * 接受请求参数:companyId
     * 根据id获取咨询师数据
     * @return
     */
    @RequiresPermissions("foreground:consultantData")
    @RequestMapping("/foreground/consultantData")
    @ResponseBody
    protected List<EmployeeInfoVo> consultantData(EmployeeInfo employeeInfo){
       List<EmployeeInfoVo> bt = iEmployeeInfoService.consultantData(employeeInfo);
        return bt;
    }

    /**
     * 添加学员信息
     * @param customerInfo
     * @return
     */
    @RequiresPermissions("foreground:addCustomerInfo")
    @RequestMapping("/foreground/addCustomerInfo")
    @ResponseBody
    protected Map<String,Object> addCustomerInfo(CustomerInfo customerInfo){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            customerInfo.setCreateTime(new Date());
            customerInfo.setCustomerState(28);
            iCustomerInfoService.insert(customerInfo);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }catch ( Exception e){
            logger.error("---------------前台添加学员数据--------"+e.getMessage());
            resultMap.put("code",300);
            resultMap.put("code","保存失败!");
        }
        return resultMap;
    }

    /**
     * 根据companyId获取该公司当天的等待者列表
     * @param ci
     * @param ei
     * @return
     */
    @RequiresPermissions("foreground:customerInfoWaitList")
    @RequestMapping("/foreground/customerInfoWaitList")
    @ResponseBody
    protected  List<CustomerInfoVo> customerInfoWaitList(CustomerInfo ci,EmployeeInfo ei){
       // Wrapper<CustomerInfo> wrapper = new EntityWrapper<>();
        List<CustomerInfoVo> customerInfoVoList = iCustomerInfoService.customerInfoWaitList(ci,ei);
        return customerInfoVoList;
    }

    /**
     * 前天编辑学员信息
     * @param ci
     * @param  ei
     * @return
     */
    @RequiresPermissions("foreground:editCustomerInfo")
    @RequestMapping(value ="/foreground/editCustomerInfo",method = RequestMethod.GET)
    protected String editCustomerInfo(CustomerInfo ci, EmployeeInfo ei, Model model){
        ci = iCustomerInfoService.selectById(ci.getCustomerId());
        ei.setCompanyId(ci.getCompanyId());
        //获取咨询师信息
        List<EmployeeInfoVo> employeeInfoVoList = iEmployeeInfoService.consultantData(ei);
        //保存到域对象中
        model.addAttribute("customerInfo",ci);
        model.addAttribute("employeeInfoVoList",employeeInfoVoList);
        //获取该学员信息
        return "/foreground/editCustomerInfo";
    }

    /**
     * 保存编辑信息
     * @return
     */
    @RequiresPermissions("foreground:editCustomerInfo")
    @RequestMapping(value="/foreground/editCustomerInfo",method= RequestMethod.POST)
    @ResponseBody
    protected  Map<String,Object> editCustomerInfo(CustomerInfo customerInfo){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            //Thread.sleep(20*1000);  //模拟网络延时
            iCustomerInfoService.updateById(customerInfo);
            resultMap.put("code",200);
            resultMap.put("msg","保存成功!");
        }   catch (Exception e){
            logger.error("------------------前台保存编辑学员信息--------------------"+e.getMessage());
            resultMap.put("code",300);
            resultMap.put("msg","保存失败!");
        }
        return resultMap;
    }

    
}
