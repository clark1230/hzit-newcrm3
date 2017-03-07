package com.hzitshop.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.CompanyCount;
import com.hzitshop.vo.CustomerInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
public interface ICustomerInfoService extends IService<CustomerInfo> {
    BootstrapTable<CustomerInfoVo> ajaxData(Page<CustomerInfo> page, Wrapper<CustomerInfo> wrapper);

    /**
     * 彻底删除学员数据
     * 传入的是客户端传来的id数组字符串  {23,45}  
     * @param customerInfoArr
     */
   void deleteCustomerInfo(String customerInfoArr);

    /**
     * 获取当天学员等待列表
     * @param ci
     * @param ei
     * @return
     */
   List<CustomerInfoVo> customerInfoWaitList(CustomerInfo ci, EmployeeInfo ei);

    /**
     * 根据公司获取目标技能
     * @param map
     * @return
     */
    List<CompanyCount> companyCount(@Param("map") Map<String,Object> map);
    /**
     * 每月每个校区每个咨询师的报名量!!!
     * @param map
     * @return
     */
    List<CompanyCount> baoming(Map<String,Object> map);
}
