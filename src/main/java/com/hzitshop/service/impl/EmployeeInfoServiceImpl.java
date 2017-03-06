package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.mapper.TbMenuAppMapper;
import com.hzitshop.service.IEmployeeInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.EmployeeInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
@Service
public class EmployeeInfoServiceImpl extends ServiceImpl<EmployeeInfoMapper, EmployeeInfo> implements IEmployeeInfoService {
    @Autowired
    private TbDictMapper tbDictMapper;
    @Autowired
    private TbMenuAppMapper tbMenuAppMapper;
    @Override
    public BootstrapTable<EmployeeInfoVo> ajaxData(Page<EmployeeInfo> page, Wrapper<EmployeeInfo> wrapper) {
        Page<EmployeeInfo> resultPage = this.selectPage(page,wrapper);
        BootstrapTable<EmployeeInfoVo> bt = new BootstrapTable<>();
        bt.setTotal(resultPage.getTotal());
        List<EmployeeInfo> employeeInfoList = resultPage.getRecords();
        List<EmployeeInfoVo> employeeInfoVoList = getEmployeeInfoVos(employeeInfoList);
        bt.setRows(employeeInfoVoList);
        return bt;
    }

    private List<EmployeeInfoVo> getEmployeeInfoVos(List<EmployeeInfo> employeeInfoList) {
        List<EmployeeInfoVo> employeeInfoVoList = new ArrayList<>();
        Map<String,Object> paramMap = null;
        EmployeeInfoVo employeeInfoVo = null;
        List<TbDict> tbDictList = null;
        for(EmployeeInfo employeeInfo : employeeInfoList){
            employeeInfoVo = new EmployeeInfoVo();
            BeanUtils.copyProperties(employeeInfo,employeeInfoVo);
            paramMap = new HashMap<>();
            paramMap.put("id",employeeInfo.getCompanyId());
            tbDictList = tbDictMapper.selectByMap(paramMap);
            if(tbDictList != null && tbDictList.size() >0){
                employeeInfoVo.setCompany(tbDictList.get(0).getName());
            }
            paramMap = new HashMap<>();
            paramMap.put("id",employeeInfo.getDeptId());
            tbDictList = tbDictMapper.selectByMap(paramMap);
            if(tbDictList != null && tbDictList.size() >0){
                employeeInfoVo.setDept(tbDictList.get(0).getName());
            }
            employeeInfoVoList.add(employeeInfoVo);
        }
        return employeeInfoVoList;
    }

    /**
     * 获取咨询师数据
     * @param employeeInfo
     * @return
     */
    @Override
    public List<EmployeeInfoVo> consultantData(EmployeeInfo employeeInfo) {
        List<EmployeeInfo> employeeInfoList = this.selectList(
                new EntityWrapper<EmployeeInfo>().where("company_id="+employeeInfo.getCompanyId())
        .and("isConsultant=1").and("isLocked=0"));
        return this.getEmployeeInfoVos(employeeInfoList);
    }

    /**
     * 根据用户id查找用户所能访问的按钮资源
     * @param employeeInfo
     * @return
     */
    @Override
    public List<String> getButtonResource(EmployeeInfo employeeInfo) {
        //获取用户的resource_ids中的数据
        employeeInfo = this.selectById(employeeInfo.getUserId());
        String[] menuAppId = null;
        if(StringUtils.isNotEmpty(employeeInfo.getResourceIds())){
            menuAppId = employeeInfo.getResourceIds().split(",");
        }
        List<String> stringList = new ArrayList<>();
        //获取用户所能访问的权限
        if(menuAppId!=null){
            List<TbMenuApp> tbMenuAppList =   tbMenuAppMapper.selectBatchIds(Arrays.asList(menuAppId));
            if(tbMenuAppList!=null && tbMenuAppList.size() >0){
                for(TbMenuApp tbMenuApp : tbMenuAppList){
                    if("1".equals(tbMenuApp.getAppid())){
                         //添加到stringList中
                        stringList.add(tbMenuApp.getPermission());
                    }
                }
            }
            //stringList.add("");
        }
        return stringList;
    }
//	       @Override
}
