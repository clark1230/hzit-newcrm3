package com.hzitshop.service;

import com.hzitshop.entity.Studentinfo;
import com.baomidou.mybatisplus.service.IService;

import java.text.ParseException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
public interface IStudentinfoService extends IService<Studentinfo> {
    /**
     * 保存进班学员数据
     * @param studentinfo
     * @param customerId
     */
    void insertStudentinfo(Studentinfo studentinfo,Integer customerId) throws ParseException;
}
