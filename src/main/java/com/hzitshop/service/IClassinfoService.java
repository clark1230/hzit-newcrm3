package com.hzitshop.service;

import com.hzitshop.entity.Classinfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
public interface IClassinfoService extends IService<Classinfo> {
    public List<Classinfo> selectClassInfo();
}
