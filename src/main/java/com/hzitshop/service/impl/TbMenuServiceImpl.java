package com.hzitshop.service.impl;

import com.hzitshop.entity.TbMenu;
import com.hzitshop.entity.TbUserMenu;
import com.hzitshop.mapper.TbMenuMapper;
import com.hzitshop.mapper.TbUserMenuMapper;
import com.hzitshop.service.ITbMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class TbMenuServiceImpl extends ServiceImpl<TbMenuMapper, TbMenu> implements ITbMenuService {
    @Autowired
    private TbMenuMapper tbMenuMapper;
    @Autowired
    private TbUserMenuMapper tbUserMenuMapper;
    @Override
    public List<TbMenu> findMenuByUserId(int userId) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        TbUserMenu  tbUserMenu= tbUserMenuMapper.selectByMap(paramMap).get(0);
        String[] menuIdArr = tbUserMenu.getMenuid().split(",");
        List<TbMenu> tbMenuList = new ArrayList<>();
        for(String str : menuIdArr){
            tbMenuList.add(tbMenuMapper.selectById(str));
        }
        return tbMenuList;
    }
}
