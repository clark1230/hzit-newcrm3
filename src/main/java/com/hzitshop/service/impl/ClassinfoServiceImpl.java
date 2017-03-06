package com.hzitshop.service.impl;

import com.hzitshop.entity.Classinfo;
import com.hzitshop.mapper.ClassinfoMapper;
import com.hzitshop.service.IClassinfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
@Service
public class ClassinfoServiceImpl extends ServiceImpl<ClassinfoMapper, Classinfo> implements IClassinfoService {
    @Autowired
    private ClassinfoMapper classinfoMapper;
    @Override
    public List<Classinfo> selectClassInfo() {
        return classinfoMapper.selectClassInfo();
    }
}
