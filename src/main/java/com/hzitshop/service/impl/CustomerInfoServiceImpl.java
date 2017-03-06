package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.CustomerTraceRecordMapper;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.service.ICustomerInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.CompanyCount;
import com.hzitshop.vo.CustomerInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private TbDictMapper tbDictMapper;
    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;

    @Autowired
    private CustomerTraceRecordMapper customerTraceRecordMapper;

    /**
     * 获取数据
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public BootstrapTable<CustomerInfoVo> ajaxData(Page<CustomerInfo> page, Wrapper<CustomerInfo> wrapper) {
        Page<CustomerInfo> resultPage = this.selectPage(page,wrapper);
        BootstrapTable<CustomerInfoVo> bootstrapTable = new BootstrapTable<>();
        bootstrapTable.setTotal(resultPage.getTotal());//获取总记录数
        List<CustomerInfo> customerInfoList = page.getRecords();
        List<CustomerInfoVo> customerInfoVos = getCustomerInfoVos(customerInfoList);
        bootstrapTable.setRows(customerInfoVos);
        return bootstrapTable;
    }

    private List<CustomerInfoVo> getCustomerInfoVos(List<CustomerInfo> customerInfoList) {
        List<CustomerInfoVo> customerInfoVos = new ArrayList<>();
        //数据字典
        CustomerInfoVo customerInfoVo =  null;
        EmployeeInfo employeeInfo = null;
        TbDict tbDict =  null;
        for(CustomerInfo customerInfo : customerInfoList){
            customerInfoVo = new CustomerInfoVo();
            BeanUtils.copyProperties(customerInfo,customerInfoVo);
            //数据字典
            if(customerInfo.getEducationBg() != null){
                tbDict = tbDictMapper.selectById(customerInfo.getEducationBg());
                if(tbDict !=null){
                    customerInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //学历
            if(customerInfo.getEducationBg() !=null){
                tbDict = tbDictMapper.selectById(customerInfo.getEducationBg());
                if(tbDict != null){
                    customerInfoVo.setEducationBgMsg(tbDict.getName());
                }
            }
            //状态
            if(customerInfo.getCustomerState() != null){
                tbDict = tbDictMapper.selectById(customerInfo.getCustomerState());
                if(tbDict != null){
                    customerInfoVo.setCustomerStateMsg(tbDict.getName());
                }
            }
            //级别
            if(StringUtils.isNotEmpty(customerInfo.getCustomerLevel()) ){
                tbDict = tbDictMapper.selectById(customerInfo.getCustomerLevel());
                if(tbDict!= null){
                    customerInfoVo.setCustomerLevelMsg(tbDict.getName());
                }
            }
            //目标技能
             if(StringUtils.isNotEmpty(customerInfo.getTargetSkill())){
                tbDict = tbDictMapper.selectById(customerInfo.getTargetSkill());
                if(tbDict!= null){
                    customerInfoVo.setTargetSkillMsg(tbDict.getName());
                }

             }
             //应聘渠道
            if(customerInfo.getRecruitChannel() !=null){
                 tbDict =tbDictMapper.selectById(customerInfo.getRecruitChannel());
                 if(tbDict != null){
                     customerInfoVo.setRecruitChannelMsg(tbDict.getName());
                 }

            }
            //所属公司
            if(customerInfo.getCompanyId() !=null){
                tbDict = tbDictMapper.selectById(customerInfo.getCompanyId());
                if(tbDict != null){
                    customerInfoVo.setCompanyIdMsg(tbDict.getName());
                }
            }
            //关单人
            if(customerInfo.getGuandan()!= null){
               employeeInfo =  employeeInfoMapper.selectById(customerInfo.getGuandan());
               if(employeeInfo != null){
                   customerInfoVo.setGuandaMsg(employeeInfo.getName());
               }
            }
            //咨询师
            if(customerInfo.getUserId() !=null){
                employeeInfo = employeeInfoMapper.selectById(customerInfo.getUserId());
                if(employeeInfo !=null){
                    customerInfoVo.setUserIdMsg(employeeInfo.getName());
                }
            }
            //创量人员 (邀约人)
            if(StringUtils.isNotEmpty(customerInfo.getIntroducer())){
                employeeInfo = employeeInfoMapper.selectById(customerInfo.getIntroducer());
                if(employeeInfo !=null){
                    customerInfoVo.setIntroducerMsg(employeeInfo.getName());
                }
            }
            customerInfoVos.add(customerInfoVo);
        }
        return customerInfoVos;
    }

    /**
     * 彻底删除学员信息
     * 传入的是客户端传来的id数字字符串  {23,45}
     * 1.先删除该学员的跟进记录
     * 2.后删除该学员的具体信息
     * @param customerInfoArr
     */
    @Override
    public void deleteCustomerInfo(String customerInfoArr) {
        //1.先删除学员的根进记录
         //if(cu)
        String[] idArr =  null;
        List<String> listIds = new ArrayList<>();
        if(customerInfoArr!=null && !"".equals(customerInfoArr)){
            idArr = customerInfoArr.split(",");
        }
        Map<String,Object> paramMap = new HashMap<>();
        if(idArr != null){
            for(String id :idArr){
                listIds.add(id);
                paramMap.put("customer_id",id) ;
                customerTraceRecordMapper.deleteByMap(paramMap);
                paramMap.clear();
            }
        }

        //2.删除学员的具体信息
        customerInfoMapper.deleteBatchIds(listIds);

    }

    /**
     * 获取当天的等待着列表
     * @param ci
     * @param ei
     * @return
     */
    @Override
    public List<CustomerInfoVo> customerInfoWaitList(CustomerInfo ci,EmployeeInfo ei) {
        List<CustomerInfo> customerInfoVoList = this.selectList(new EntityWrapper<CustomerInfo>()
                .where("company_id="+ei.getCompanyId())
                .and("to_days(create_time)= to_days(now())"));
        return this.getCustomerInfoVos(customerInfoVoList);
    }

    @Override
    public List<CompanyCount> companyCount(Map<String, Object> map) {
        return customerInfoMapper.companyCount(map);
    }
}
