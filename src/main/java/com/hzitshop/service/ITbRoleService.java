package com.hzitshop.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzitshop.entity.TbRole;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.vo.BootstrapTable;
import com.hzitshop.vo.TbRoleVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-17
 */
public interface ITbRoleService extends IService<TbRole> {
    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Long... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Long[] roleIds);

    /**
     * 获取所有角色信息
     * @return
     */
    BootstrapTable<TbRoleVo>  ajaxData(Page<TbRole> page, Wrapper<TbRole> wrapper);
}
