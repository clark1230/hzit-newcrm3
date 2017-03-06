package com.hzitshop.mapper;

import com.hzitshop.entity.Classinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
public interface ClassinfoMapper extends BaseMapper<Classinfo> {
     public List<Classinfo> selectClassInfo();
}