package com.hzitshop.service.impl;

import com.hzitshop.entity.CustomerInfo;
import com.hzitshop.entity.EmployeeInfo;
import com.hzitshop.entity.Studentinfo;
import com.hzitshop.entity.TbDict;
import com.hzitshop.mapper.CustomerInfoMapper;
import com.hzitshop.mapper.EmployeeInfoMapper;
import com.hzitshop.mapper.StudentinfoMapper;
import com.hzitshop.mapper.TbDictMapper;
import com.hzitshop.service.IStudentinfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hzitshop.vo.CustomerInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 冼耀基
 * @since 2017-03-02
 */
@Service
public class StudentinfoServiceImpl extends ServiceImpl<StudentinfoMapper, Studentinfo> implements IStudentinfoService {
    @Autowired
    private  StudentinfoMapper studentinfoMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private EmployeeInfoMapper employeeInfoMapper;
    @Autowired
    private TbDictMapper tbDictMapper;
    @Override
    public void insertStudentinfo(Studentinfo studentinfo, Integer customerId) throws ParseException {
        Map<String,String> paramMap = new HashMap<>();
        //paramMap.put("customerId",customerId);
        CustomerInfo customerInfo= customerInfoMapper.selectById(customerId);
        //CustomerInfoVo customerInfoVo;
        if(customerInfo!=null){
            studentinfo.setStudentName(customerInfo.getRealName());//姓名
            studentinfo.setSttudentSchool(customerInfo.getGraduateFrom());//毕业学校
            studentinfo.setStudentAge(customerInfo.getAge());//年龄
            studentinfo.setStudentHome(customerInfo.getNativePlace());//家庭住址
            if(customerInfo.getSex() != null){
                if(customerInfo.getSex() ==1){
                    studentinfo.setStudentSex("男");
                }else if(customerInfo.getSex() == 2){
                    studentinfo.setStudentSex("女");
                }else{
                    studentinfo.setStudentSex("未知");
                }
            }else{
                studentinfo.setStudentSex("未知");
            }
            EmployeeInfo employeeInfo = employeeInfoMapper.selectById(customerInfo.getUserId());
            studentinfo.setZixunshiName(employeeInfo.getName());//所属咨询师
            studentinfo.setStudentTel(customerInfo.getTel());//电话号码
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isNotEmpty(customerInfo.getGraduateTime())){
                studentinfo.setStudentTime(simpleDateFormat.parse(customerInfo.getGraduateTime()));
            }else{
                studentinfo.setStudentTime(null);
            }
            TbDict tbDict = tbDictMapper.selectById(customerInfo.getEducationBg());
            if(tbDict!= null){
                studentinfo.setStudentXl(tbDict.getName());//学历
            }
            studentinfo.setStudentYx(customerInfo.getMajorIn());//专业
            studentinfo.setStudentsal(0);
        }
        studentinfoMapper.insertStudentinfo(studentinfo);

        customerInfo = new CustomerInfo();
        customerInfo.setCustomerId(customerId);
        customerInfo.setCustomerState(33);//该学员进班
        customerInfoMapper.updateById(customerInfo);


    }
}
