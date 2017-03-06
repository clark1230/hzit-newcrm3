package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.TbRole;
import com.hzitshop.mapper.TbMenuAppMapper;
import com.hzitshop.mapper.TbRoleMapper;
import com.hzitshop.service.IEmployeeInfoService;
import com.hzitshop.service.ITbMenuAppService;
import com.hzitshop.service.ITbRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.TbRoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-17
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements ITbRoleService {
   @Autowired
   private TbRoleMapper tbRoleMapper;
   @Autowired
   private TbMenuAppMapper tbMenuAppMapper;
   @Autowired
   private IEmployeeInfoService iEmployeeInfoService;

   @Autowired
   private ITbMenuAppService iTbMenuAppService;
    @Override
    public Set<String> findRoles(Long... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(Long roleId : roleIds) {
            TbRole role = this.selectOne(new EntityWrapper<TbRole>().where("id={}",roleId));
            //Role role = findOne(roleId);
            if(role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(Long[] roleIds) {
        Set<Long> resourceIds = new HashSet<Long>();
        //List<Long> resuourceId = null;
        for(Long roleId : roleIds) {
            //resuourceId = new ArrayList<Long>();
            TbRole role = this.selectOne(new EntityWrapper<TbRole>().where("id={}",roleId));
            if(role != null) {
                //resourceIds.addAll(role.getResourceId());
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(role.getResourceIds())){
                   String[] resourceArr =role.getResourceIds().split(",");
                   for(String str: resourceArr){
                       resourceIds.add(Long.parseLong(str));
                   }
                }
            }
        }
        return iTbMenuAppService.findPermissions(resourceIds);
    }

    /**
     * 获取所有的咨询师信息
     * @return
     */
    @Override
    public BootstrapTable<TbRoleVo> ajaxData(Page<TbRole> page, Wrapper<TbRole> wrapper) {
        Page<TbRole> resultPage = this.selectPage(page,wrapper);
        List<TbRole> tbRoles = resultPage.getRecords();
        BootstrapTable<TbRoleVo> bt = new BootstrapTable<>();
        bt.setTotal(resultPage.getTotal());//总记录数
        List<TbRoleVo> tbRoleVos = new ArrayList<>();
        TbRoleVo tbRoleVo = null;
        EmployeeInfo employeeInfo =null;
        if(tbRoles!= null && tbRoles.size() >0){
            for(TbRole tbRole : tbRoles){
                tbRoleVo = new TbRoleVo();
                BeanUtils.copyProperties(tbRole,tbRoleVo);
                //到用户表查询数据
                if(tbRole.getCreateBy() != null){
                   employeeInfo = iEmployeeInfoService.selectById(tbRole.getCreateBy());
                   tbRoleVo.setCreateByMsg(employeeInfo.getName());
                }
                if(tbRole.getUpdateBy() != null){
                   employeeInfo=  iEmployeeInfoService.selectById(tbRole.getUpdateBy());
                   tbRoleVo.setUpdateByMsg(employeeInfo.getName());
                }
                tbRoleVos.add(tbRoleVo);

            }
        }
        bt.setRows(tbRoleVos);
        return bt;
    }


}
