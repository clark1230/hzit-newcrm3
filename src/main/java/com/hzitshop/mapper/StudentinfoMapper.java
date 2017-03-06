package com.hzitshop.mapper;

import com.hzitshop.entity.Studentinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
public interface StudentinfoMapper extends BaseMapper<Studentinfo> {
      void insertStudentinfo(Studentinfo studentinfo);
}