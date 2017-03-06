package com.hzitshop.service;

import com.hzitshop.entity.TbMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-02-12
 */
public interface ITbMenuService extends IService<TbMenu> {
    /**
     * 根据用户编号获取桌面图标
      * @param userId
     * @return
     */
  List<TbMenu> findMenuByUserId(int userId);
	
}
