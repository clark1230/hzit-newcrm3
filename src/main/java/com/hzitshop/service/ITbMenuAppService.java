package com.hzitshop.service;

import com.hzitshop.entity.TbMenuApp;
import com.baomidou.mybatisplus.service.IService;
import com.hzitshop.vo.TbMenuAppVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
public interface ITbMenuAppService extends IService<TbMenuApp> {
    /**
     * 根据角色编号得到权限字符串列表
     * @param
     * @return
     */
    Set<String> findPermissions(Set<Long> resourceIds);
    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<TbMenuApp> findMenus(Set<String> permissions);

    /**
     * 用户授权
     * @return
     */
    List<TbMenuAppVo> grantRole();
}
