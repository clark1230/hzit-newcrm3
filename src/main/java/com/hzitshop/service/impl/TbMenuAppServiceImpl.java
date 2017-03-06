package com.hzitshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hzitshop.entity.TbMenuApp;
import com.hzitshop.mapper.TbMenuAppDescMapper;
import com.hzitshop.mapper.TbMenuAppMapper;
import com.hzitshop.mapper.TbRoleMapper;
import com.hzitshop.service.ITbMenuAppService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.vo.TbMenuAppVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class TbMenuAppServiceImpl extends ServiceImpl<TbMenuAppMapper, TbMenuApp> implements ITbMenuAppService {
    @Autowired
    private TbRoleMapper tbRoleMapper;
    @Autowired
    private TbMenuAppMapper tbMenuAppMapper;
    @Autowired
    private TbMenuAppDescMapper tbMenuAppDescMapper;
   /* @Autowired
    private TbMenuAppDescMapper tbMenuAppDescMapper;*/
    @Override
    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for(Long resourceId : resourceIds) {
            TbMenuApp tbMenuApp =  this.selectOne(new EntityWrapper<TbMenuApp>().where("id={}",resourceId));
            if(tbMenuApp != null && !StringUtils.isEmpty(tbMenuApp.getPermission())) {
                permissions.add(tbMenuApp.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<TbMenuApp> findMenus(Set<String> permissions) {
        return null;
    }

    /**
     * 用户授权
     * @return
     */
    @Override
    public List<TbMenuAppVo> grantRole() {
        //1获取所有的资源
        List<TbMenuApp> tbMenuAppList = tbMenuAppMapper.selectList(new EntityWrapper<TbMenuApp>());
        List<TbMenuAppVo> tbMenuAppVoList = new ArrayList<>();
        TbMenuAppVo tbMenuAppVo = null;
        Map<String,Object> paramMap =new HashMap<>();
        for(TbMenuApp tbMenuApp : tbMenuAppList){
            tbMenuAppVo = new TbMenuAppVo();
            paramMap.put("menu_app_id",tbMenuApp.getId());
            BeanUtils.copyProperties(tbMenuApp,tbMenuAppVo);
            tbMenuAppVo.setTbMenuAppDescList(tbMenuAppDescMapper.selectByMap(paramMap));
            tbMenuAppVoList.add(tbMenuAppVo);
            paramMap.clear();//清空map集合
        }
        //.获取该资源下的详细资源信息

        return tbMenuAppVoList;
    }
}
